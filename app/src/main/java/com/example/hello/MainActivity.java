package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Gpio ledGpio;   // GPIO4
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Properties ps = System.getProperties();
        Log.v("brad", "os name: " + ps.getProperty("os.name"));

        PeripheralManager peripheralManager = PeripheralManager.getInstance();
//        List<String> gpios = peripheralManager.getGpioList();
//        for (String gpio: gpios){
//            Log.v("brad", gpio);
//        }
        try {
            ledGpio = peripheralManager.openGpio("BCM4");
            ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            ledGpio.setValue(true);
        }catch (Exception e){
            Log.v("brad", e.toString());
        }

        timer = new Timer();
        timer.schedule(new FlashTask(), 1000, 1000);
    }

    private class FlashTask extends TimerTask {
        @Override
        public void run() {
            try {
                ledGpio.setValue(!ledGpio.getValue());
            }catch (Exception e){
                Log.v("brad", e.toString());
            }
        }
    }

    @Override
    public void finish() {
        Log.v("brad", "finish");
        super.finish();
    }

    @Override
    protected void onDestroy() {
        Log.v("brad", "destroy");
        try {
            ledGpio.setValue(false);
        }catch (Exception e){
            Log.v("brad", e.toString());
        }


        super.onDestroy();
    }
}