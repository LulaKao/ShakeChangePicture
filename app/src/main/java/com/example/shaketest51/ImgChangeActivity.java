package com.example.shaketest51;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImgChangeActivity extends AppCompatActivity {
    Button btn_change;
    public static ImageView change_ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_change);

        change_ImageView = findViewById(R.id.changeImageView);

        // 跳到變換顏色的那一頁
        btn_change = findViewById(R.id.btnChange2);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ImgChangeActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Intent intent = new Intent(this, ShakeService_Img.class);
        startService(intent); // 啟動服務 --> 要寫這句才可以 run
    }
}
