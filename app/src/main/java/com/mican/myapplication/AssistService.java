package com.mican.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/9/23 2:19 PM
 * @change
 * @chang
 * @class describe
 */
public class AssistService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public AssistService() {
    }

    public class LocalBinder extends Binder
    {
        public AssistService getService()
        {
            return AssistService.this;
        }
    }


}
