package org.example.phonemanager.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.phonemanager.R;
import org.example.phonemanager.model.PhoneRepository;

public class MainActivity extends AppCompatActivity {
    private Button buttonLend;
    private Button buttonReturn;
    private Button buttonAddPhone;
    private Button buttonBrowsePhones;
    private PhoneRepository phoneRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图组件
        buttonLend = findViewById(R.id.buttonLend);
        buttonReturn = findViewById(R.id.buttonReturn);
        buttonAddPhone = findViewById(R.id.buttonAddPhone);
        buttonBrowsePhones = findViewById(R.id.buttonBrowsePhones);

        // 初始化仓库
        phoneRepository = new PhoneRepository(this);

        // 设置按钮点击事件
        buttonLend.setOnClickListener(v -> {
            // 跳转到扫描界面，传递操作类型
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            intent.putExtra("operation_type", "lend");
            startActivity(intent);
        });

        buttonReturn.setOnClickListener(v -> {
            // 跳转到扫描界面，传递操作类型
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            intent.putExtra("operation_type", "return");
            startActivity(intent);
        });

        buttonAddPhone.setOnClickListener(v -> {
            // 跳转到添加手机界面
            Intent intent = new Intent(MainActivity.this, AddPhoneActivity.class);
            startActivity(intent);
        });

        buttonBrowsePhones.setOnClickListener(v -> {
            // 跳转到手机列表界面
            Intent intent = new Intent(MainActivity.this, PhoneListActivity.class);
            startActivity(intent);
        });
    }
}