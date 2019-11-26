package com.example.shaketest51;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.Random;

public class ShakeService_Color_Img extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // 除重力外的加速度 acceleration apart from gravity
    private float mAccelCurrent; // 當前加速度，包括重力 current acceleration including gravity
    private float mAccelLast; // 最後的加速度，包括重力 last acceleration including gravity
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 取得系統預設的Sensor
        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_UI,new Handler()); // 跟系統註冊要使用這項服務
        return START_STICKY; // 系統的常數，功能是：Service被殺掉, 系統會重啟, 但是不保留Intent
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0]; // 獲得 x 座標
        float y = event.values[1]; // 獲得 y 座標
        float z = event.values[2]; // 獲得 z 座標
        mAccelLast = mAccelCurrent; // 將當前的加速度變成最後的加速度
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z)); //  sqrt 是平方根，後面是公式。先轉double，再轉float
        float delta = mAccelCurrent - mAccelLast; // delta：獲得 x, y, z 的變化值
        mAccel = mAccel * 0.9f + delta; // 執行低切濾波器

        if(mAccel > 11){
            // 設定亂數變換顏色
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)); // 有包含透明值
            MainActivity.square.setBackgroundColor(color); // 連結到 MainActivity 的 square

            // 設定亂數變換圖片
            TypedArray ar = getResources().obtainTypedArray(R.array.random_imgs);
            /* TypedArray：自定義控制元件屬性
            　　數組方式獲取資源文件中的圖片 */
            int len = ar.length(); // len = TypedArray的項目長度
            int img_index = rnd.nextInt(20);
            /* 以亂數的方式獲取 integer-array 的索引值
            　　bound（範圍）有20個　*/
            int[] resIds = new int[len];
            /* 創建資源 ID 的整數數組，
            　　數組的長度即為 TypedArray 的項目長度 */
            for (int i = 0; i < len; i++) {
                resIds[i] = ar.getResourceId(i, 0);
                /* getResourceId：檢索索引處屬性的資源識別碼。
                   defValue：default Value，默認值。
                 　如果第一個參數沒有找到對應的資源，則返回defValue設置的值。*/
            }
            ar.recycle();
            /* recycle()：回收TypedArray，供以後的調用者重新使用。
            官方強調TypedArray用完後一定要回收）*/
            MainActivity.square_image.setImageResource(resIds[img_index]);
            /* 連結到 ImgChangeActivity 的 change_ImageView
            　　setImageResource：設定圖片資源。
            　　利用ImageView 在畫面上顯示圖片 */
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
