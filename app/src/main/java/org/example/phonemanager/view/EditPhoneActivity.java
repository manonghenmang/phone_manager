package org.example.phonemanager.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.phonemanager.R;
import org.example.phonemanager.model.Phone;
import org.example.phonemanager.model.PhoneRepository;

public class EditPhoneActivity extends AppCompatActivity {
    private EditText editTextPhoneId;
    private EditText editTextOwner;
    private EditText editTextBrand;
    private EditText editTextModel;
    private Button buttonUpdate;
    private Button buttonCancel;
    private PhoneRepository phoneRepository;
    private Phone currentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        // 初始化视图组件
        editTextPhoneId = findViewById(R.id.editTextPhoneId);
        editTextOwner = findViewById(R.id.editTextOwner);
        editTextBrand = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        buttonUpdate = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        // 初始化仓库
        phoneRepository = new PhoneRepository(this);

        // 获取传递过来的手机信息
        currentPhone = (Phone) getIntent().getSerializableExtra("phone");

        // 检查是否成功获取手机信息
        if (currentPhone == null) {
            Toast.makeText(this, "获取手机信息失败", Toast.LENGTH_SHORT).show();
            Log.e("EditPhoneActivity", "Failed to get phone from intent");
            finish();
            return;
        }

        // 设置编辑模式
        buttonUpdate.setText("更新");
        editTextPhoneId.setEnabled(false); // 手机ID不可编辑

        // 填充现有数据
        editTextPhoneId.setText(currentPhone.getPhoneId());
        editTextOwner.setText(currentPhone.getOwner());
        editTextBrand.setText(currentPhone.getBrand());
        editTextModel.setText(currentPhone.getModel());

        // 设置按钮点击事件
        buttonUpdate.setOnClickListener(v -> updatePhone());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void updatePhone() {
        // 获取输入信息
        String phoneId = editTextPhoneId.getText().toString().trim();
        String owner = editTextOwner.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(owner)) {
            Toast.makeText(this, "请输入拥有者", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(brand)) {
            Toast.makeText(this, "请输入品牌", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(model)) {
            Toast.makeText(this, "请输入型号", Toast.LENGTH_SHORT).show();
            return;
        }

        // 更新手机对象
        currentPhone.setOwner(owner);
        currentPhone.setBrand(brand);
        currentPhone.setModel(model);

        // 更新手机到数据库
        Log.d("EditPhoneActivity", "Updating phone: PhoneID=" + phoneId + ", Owner=" + owner + ", Brand=" + brand + ", Model=" + model);
        boolean updated = phoneRepository.updatePhone(currentPhone);

        if (updated) {
            Toast.makeText(this, "手机信息更新成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "手机信息更新失败", Toast.LENGTH_SHORT).show();
        }
    }
}