package org.example.phonemanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.phonemanager.R;
import org.example.phonemanager.model.Phone;
import org.example.phonemanager.model.PhoneRepository;

public class ReturnActivity extends AppCompatActivity {
    private TextView textViewPhoneId;
    private TextView textViewBrand;
    private TextView textViewModel;
    private TextView textViewStatus;
    private TextView textViewBorrower;
    private TextView textViewLendDate;
    private EditText editTextReturnPerson;
    private Button buttonConfirmReturn;
    private Button buttonCancel;
    private PhoneRepository phoneRepository;
    private Phone phone;
    private String phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        // 初始化视图组件
        textViewPhoneId = findViewById(R.id.textViewPhoneId);
        textViewBrand = findViewById(R.id.textViewBrand);
        textViewModel = findViewById(R.id.textViewModel);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewBorrower = findViewById(R.id.textViewBorrower);
        textViewLendDate = findViewById(R.id.textViewLendDate);
        editTextReturnPerson = findViewById(R.id.editTextReturnPerson);
        buttonConfirmReturn = findViewById(R.id.buttonConfirmReturn);
        buttonCancel = findViewById(R.id.buttonCancel);

        // 初始化仓库
        phoneRepository = new PhoneRepository(this);

        // 获取扫描到的手机ID
        phoneId = getIntent().getStringExtra("phone_id");

        // 查询手机信息
        phone = phoneRepository.getPhoneByPhoneId(phoneId);

        if (phone != null) {
            // 显示手机信息
            textViewPhoneId.setText(phone.getPhoneId());
            textViewBrand.setText(phone.getBrand());
            textViewModel.setText(phone.getModel());
            textViewStatus.setText(phone.getStatus());
            textViewBorrower.setText(phone.getBorrower());
            textViewLendDate.setText(phone.getLendDate() != null ? phone.getLendDate().toString() : "");
        } else {
            Toast.makeText(this, "未找到该手机信息", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 设置按钮点击事件
        buttonConfirmReturn.setOnClickListener(v -> confirmReturn());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void confirmReturn() {
        String returnPerson = editTextReturnPerson.getText().toString().trim();

        if (TextUtils.isEmpty(returnPerson)) {
            Toast.makeText(this, "请输入归还人姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone != null) {
            // 执行归还操作
            boolean success = phoneRepository.returnPhone(phoneId, returnPerson);
            if (success) {
                Toast.makeText(this, "归还成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "归还失败，手机可能未被借出", Toast.LENGTH_SHORT).show();
            }
        }
    }
}