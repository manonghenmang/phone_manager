package org.example.phonemanager.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.example.phonemanager.R;
import org.example.phonemanager.model.Phone;
import org.example.phonemanager.model.PhoneRepository;

import java.util.ArrayList;
import java.util.List;

public class PhoneListActivity extends AppCompatActivity {
    private ListView listViewPhones;
    private EditText editTextSearch;
    private Button buttonSearch;
    private PhoneRepository phoneRepository;
    private List<Phone> phoneList;
    private PhoneListAdapter phoneListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list);

        // 初始化视图组件
        listViewPhones = findViewById(R.id.listViewPhones);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);

        // 初始化仓库
        phoneRepository = new PhoneRepository(this);

        // 获取所有手机信息
        phoneList = phoneRepository.getAllPhones();
        
        // 调试：输出所有手机信息
        Log.d("PhoneListActivity", "Total phones: " + phoneList.size());
        for (Phone phone : phoneList) {
            Log.d("PhoneListActivity", "Phone ID: " + phone.getPhoneId() + ", Brand: " + phone.getBrand());
        }

        // 初始化适配器
        phoneListAdapter = new PhoneListAdapter(this, phoneList);
        listViewPhones.setAdapter(phoneListAdapter);

        // 设置列表项点击事件
        phoneListAdapter.setOnItemClickListener((phone, position) -> {
            showEditDeleteDialog(phone, position);
        });

        // 设置搜索功能
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPhones(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 设置搜索按钮点击事件
        buttonSearch.setOnClickListener(v -> {
            String searchText = editTextSearch.getText().toString().trim();
            filterPhones(searchText);
            
            // 调试：搜索时输出所有手机数据和搜索条件
            Log.d("PhoneListActivity", "Searching for: " + searchText);
            for (Phone phone : phoneList) {
                Log.d("PhoneListActivity", "Phone ID: " + phone.getPhoneId() + ", Contains search text: " + phone.getPhoneId().toLowerCase().contains(searchText.toLowerCase()));
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // 从数据库重新获取所有手机信息
        phoneList = phoneRepository.getAllPhones();
        // 重新过滤列表以更新显示
        String searchText = editTextSearch.getText().toString().trim();
        filterPhones(searchText);
    }

    /**
     * 过滤手机列表
     * @param searchText 搜索文本
     */
    private void filterPhones(String searchText) {
        List<Phone> filteredList = new ArrayList<>();
        String lowerCaseSearch = searchText.toLowerCase();
        
        for (Phone phone : phoneList) {
            // 检查手机编号、拥有者、品牌、型号是否包含搜索文本
            boolean matches = false;
            
            if (phone.getPhoneId() != null && phone.getPhoneId().toLowerCase().contains(lowerCaseSearch)) {
                matches = true;
            } else if (phone.getOwner() != null && phone.getOwner().toLowerCase().contains(lowerCaseSearch)) {
                matches = true;
            } else if (phone.getBrand() != null && phone.getBrand().toLowerCase().contains(lowerCaseSearch)) {
                matches = true;
            } else if (phone.getModel() != null && phone.getModel().toLowerCase().contains(lowerCaseSearch)) {
                matches = true;
            }
            
            if (matches) {
                filteredList.add(phone);
            }
        }
        phoneListAdapter.filterList(filteredList);
    }

    /**
     * 显示编辑和删除对话框
     * @param phone 选中的手机信息
     * @param position 选中的位置
     */
    private void showEditDeleteDialog(Phone phone, int position) {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("操作选项");
        builder.setMessage("请选择要执行的操作");

        // 添加编辑按钮
        builder.setPositiveButton("编辑", (dialog, which) -> {
            // 启动编辑活动
            Intent intent = new Intent(PhoneListActivity.this, EditPhoneActivity.class);
            intent.putExtra("phone", phone);
            startActivity(intent);
        });

        // 添加删除按钮
        builder.setNegativeButton("删除", (dialog, which) -> {
            // 显示删除确认对话框
            showDeleteConfirmationDialog(phone, position);
        });

        // 显示对话框
        builder.create().show();
    }

    /**
     * 显示删除确认对话框
     * @param phone 要删除的手机信息
     * @param position 要删除的位置
     */
    private void showDeleteConfirmationDialog(Phone phone, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("确定要删除手机编号为" + phone.getPhoneId() + "的记录吗？");

        builder.setPositiveButton("确定", (dialog, which) -> {
            // 从数据库中删除手机信息
            boolean deleted = phoneRepository.deletePhone(phone.getPhoneId());
            if (deleted) {
                // 从原始列表和过滤列表中都移除
                phoneList.remove(phone);
                // 重新过滤列表以更新显示
                String searchText = editTextSearch.getText().toString().trim();
                filterPhones(searchText);
                Toast.makeText(PhoneListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PhoneListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }
}