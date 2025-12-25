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

public class LendActivity extends AppCompatActivity {
    private TextView textViewPhoneId;
    private TextView textViewBrand;
    private TextView textViewModel;
    private TextView textViewStatus;
    private EditText editTextBorrower;
    private EditText editTextLender;
    private Button buttonConfirmLend;
    private Button buttonCancel;
    private PhoneRepository phoneRepository;
    private Phone phone;
    private String phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

        // 初始化视图组件
        textViewPhoneId = findViewById(R.id.textViewPhoneId);
        textViewBrand = findViewById(R.id.textViewBrand);
        textViewModel = findViewById(R.id.textViewModel);
        textViewStatus = findViewById(R.id.textViewStatus);
        editTextBorrower = findViewById(R.id.editTextBorrower);
        editTextLender = findViewById(R.id.editTextLender);
        buttonConfirmLend = findViewById(R.id.buttonConfirmLend);
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
        } else {
            Toast.makeText(this, "未找到该手机信息", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 设置按钮点击事件
        buttonConfirmLend.setOnClickListener(v -> confirmLend());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void confirmLend() {
        String borrower = editTextBorrower.getText().toString().trim();
        String lender = editTextLender.getText().toString().trim();

        if (TextUtils.isEmpty(borrower)) {
            Toast.makeText(this, "请输入借用人姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lender)) {
            Toast.makeText(this, "请输入出借人姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone != null) {
            // 执行借出操作
            boolean success = phoneRepository.lendPhone(phoneId, borrower, lender);
            if (success) {
                Toast.makeText(this, "借出成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "借出失败，手机可能已被借出", Toast.LENGTH_SHORT).show();
            }
        }
    }
}