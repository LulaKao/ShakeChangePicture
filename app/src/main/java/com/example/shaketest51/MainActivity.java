package com.example.shaketest51;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static LinearLayout square; // 要設成 public，這樣其他的 class 才可以用
    public static ImageView square_image;
    Button btn_change;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_change = findViewById(R.id.btnChange);
        square = findViewById(R.id.square);
        square_image = findViewById(R.id.squareImage);

        intent = new Intent(this, ShakeService_Color_Img.class);
        startService(intent); // 啟動服務 --> 要寫這句才可以 run

        // 跳到變換圖片那一頁
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ImgChangeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // ---  設定 Menu 開始 --- //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this,"你按下了星星!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    // ---  設定 Menu 結束 --- //
}
