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

public class AddPhoneActivity extends AppCompatActivity {
    private EditText editTextPhoneId;
    private EditText editTextOwner;
    private EditText editTextBrand;
    private EditText editTextModel;
    private Button buttonAdd;
    private Button buttonCancel;
    private PhoneRepository phoneRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        // 初始化视图组件
        editTextPhoneId = findViewById(R.id.editTextPhoneId);
        editTextOwner = findViewById(R.id.editTextOwner);
        editTextBrand = findViewById(R.id.editTextBrand);
        editTextModel = findViewById(R.id.editTextModel);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        // 初始化仓库
        phoneRepository = new PhoneRepository(this);

        // 设置按钮点击事件
        buttonAdd.setOnClickListener(v -> addPhone());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void addPhone() {
        // 获取输入信息
        String phoneId = editTextPhoneId.getText().toString().trim();
        String owner = editTextOwner.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(phoneId)) {
            Toast.makeText(this, "请输入手机ID", Toast.LENGTH_SHORT).show();
            return;
        }

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

        // 检查手机是否已存在
        if (phoneRepository.getPhoneByPhoneId(phoneId) != null) {
            Toast.makeText(this, "该手机ID已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建手机对象
        Phone phone = new Phone();
        phone.setPhoneId(phoneId);
        phone.setOwner(owner);
        phone.setBrand(brand);
        phone.setModel(model);
        phone.setStatus("available");

        // 添加手机到数据库
        Log.d("AddPhoneActivity", "Adding phone: PhoneID=" + phoneId + ", Owner=" + owner + ", Brand=" + brand + ", Model=" + model);
        long id = phoneRepository.addPhone(phone);
        Log.d("AddPhoneActivity", "Add phone result: ID=" + id);
        
        if (id != -1) {
            Toast.makeText(this, "手机信息添加成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "手机信息添加失败", Toast.LENGTH_SHORT).show();
        }
    }
}