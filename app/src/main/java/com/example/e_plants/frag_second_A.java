package com.example.e_plants;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutionException;

public class frag_second_A extends AppCompatActivity {
    private Button buttonRecord;
    private Button buttonStop;
    private PreviewView surface;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider mCameraProvider;
    private boolean mRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        buttonRecord = findViewById(R.id.record);
        buttonStop = findViewById(R.id.stoprecord);
        surface = findViewById(R.id.previewView);


        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                // 這裡不用處理
                Toast.makeText(this, "哥炸了", Toast.LENGTH_SHORT).show();
                Log.d("Error", "測試測試");
            }
        }, ContextCompat.getMainExecutor(this));
        buttonRecord.setOnClickListener(view -> {
            if (mCameraProvider != null && !mRunning) {
                bindPreview(mCameraProvider);
            }
        });
        buttonStop.setOnClickListener(view -> {
            mCameraProvider.unbindAll();
            mRunning = false;
        });

    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        if (cameraProvider == null) {
            Toast.makeText(getApplicationContext(), "沒獲取到相機", Toast.LENGTH_SHORT).show();
            return;
        }
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(surface.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);
        mRunning = true;
    }


    /**
     * 权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200) {
            checkPermission();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(frag_second_A.this, com.example.e_plants.frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_first2:
                Intent first2 = new Intent(frag_second_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds = new Intent(frag_second_A.this, com.example.e_plants.new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first = new Intent(frag_second_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_second_A.this, com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
