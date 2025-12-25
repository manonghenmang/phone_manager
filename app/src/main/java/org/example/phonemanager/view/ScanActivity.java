package org.example.phonemanager.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.example.phonemanager.R;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private DecoratedBarcodeView barcodeScannerView;
    private Button buttonCancel;
    private String operationType;
    private BarcodeCallback barcodeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // 获取操作类型
        operationType = getIntent().getStringExtra("operation_type");

        // 初始化取消按钮
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> finish());

        // 检查摄像头权限
        if (checkCameraPermission()) {
            initializeScanner();
        } else {
            requestCameraPermission();
        }
    }

    /**
     * 检查摄像头权限
     */
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求摄像头权限
     */
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    /**
     * 初始化扫描器
     */
    private void initializeScanner() {
        // 初始化扫描器
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);

        // 设置扫描回调
        barcodeCallback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result != null) {
                    String phoneId = result.getText();
                    handleScanResult(phoneId);
                }
            }
        };

        // 开始扫描
        barcodeScannerView.decodeSingle(barcodeCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限授予成功，初始化扫描器
                initializeScanner();
            } else {
                // 权限授予失败
                Toast.makeText(this, "摄像头权限被拒绝，无法使用扫描功能", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeScannerView != null) {
            barcodeScannerView.resume();
            barcodeScannerView.decodeSingle(barcodeCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeScannerView != null) {
            barcodeScannerView.pause();
        }
    }

    private void handleScanResult(String phoneId) {
        if (phoneId != null && !phoneId.isEmpty()) {
            Intent intent;
            if ("lend".equals(operationType)) {
                intent = new Intent(ScanActivity.this, LendActivity.class);
            } else {
                intent = new Intent(ScanActivity.this, ReturnActivity.class);
            }
            intent.putExtra("phone_id", phoneId);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "扫描结果无效", Toast.LENGTH_SHORT).show();
        }
    }
}