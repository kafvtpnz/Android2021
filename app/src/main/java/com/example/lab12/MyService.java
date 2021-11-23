package com.example.lab12;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Random;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private int mRandomNumber;
    private boolean isRunning = false;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            for(int i =0;i<10;i++) {
                try {
                    Thread.sleep(1000);
                    if (isRunning) {
                        mRandomNumber = new Random().nextInt(1000);
                        Log.i(TAG,  "Random Number from service: " + mRandomNumber);
                    }
                } catch (InterruptedException e) {
                    Log.i(TAG, "Thread Interrupted");
                }
            }
            stopSelf();
        }
    }

    @Override
    public void onCreate(){
        HandlerThread thread = new HandlerThread("ServiceStartArgument");
        thread.start();

        serviceLooper =thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

        Log.i(TAG, "Service onCreate");
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG,"Service onStartCommand");

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }
    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "Service onDestroy");

    }
}