package com.example.testmodule.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class AdvancedCountdownTimer {
    private static final int MSG_RUN = 1;
    private final long mCountdownInterval;// 定时间隔，以毫秒计
    private long mTotalTime;// 定时时间
    private long mRemainTime;// 剩余时间
    private TimeHandler mHandler;
    // 构造函数
    public AdvancedCountdownTimer(long millisInFuture, long countDownInterval) {
        mTotalTime = millisInFuture;
        mCountdownInterval = countDownInterval;
        mRemainTime = millisInFuture;
        mHandler=new TimeHandler(countDownInterval,this);
    }


    public void remove(){
        mHandler.removeCallbacksAndMessages(null);
    }


    // 开始计时
    public synchronized final AdvancedCountdownTimer start() {
        if (mRemainTime <= 0) {// 计时结束后返回
            onFinish();
            return this;
        }
        mHandler.init();
        // 设置计时间隔
        Message message = mHandler.obtainMessage(MSG_RUN);
        mHandler.sendMessageDelayed(message,
                mCountdownInterval);
        return this;
    }

    public abstract void onTick(long millisUntilFinished, int percent);// 计时中

    public abstract void onFinish();// 计时结束

    // 通过handler更新android UI，显示定时时间  My
    private static class TimeHandler  extends Handler {
        WeakReference<AdvancedCountdownTimer> weekTimer;
        AdvancedCountdownTimer mTimer;
        public TimeHandler(long countdownInterval,AdvancedCountdownTimer timer) {
            mCountdownInterval = countdownInterval;
            weekTimer=new WeakReference<AdvancedCountdownTimer>(timer);

        }
        public  void   init(){
            mTimer=  weekTimer.get();
            mTotalTime=weekTimer.get().mTotalTime;
            mRemainTime=weekTimer.get().mRemainTime;
        }


        private final long mCountdownInterval;// 定时间隔，以毫秒计
        private long mTotalTime;// 定时时间
        private long mRemainTime;// 剩余时间

        @Override
        public void handleMessage(Message msg) {
            if(weekTimer==null)return;
            synchronized (weekTimer){
                if (msg.what == MSG_RUN) {
                    mRemainTime = mRemainTime - mCountdownInterval;

                    if (mRemainTime <= 0) {
                        if(weekTimer!=null&&weekTimer.get()!=null){
                            weekTimer.get().onFinish();
                        }
                    } else if (mRemainTime < mCountdownInterval) {
                        sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
                    } else {
                        if(weekTimer!=null&&weekTimer.get()!=null){
                            weekTimer.get(). onTick(mRemainTime, Long.valueOf(100
                                    * (mTotalTime - mRemainTime) / mTotalTime)
                                    .intValue());

                            sendMessageDelayed(obtainMessage(MSG_RUN),
                                    mCountdownInterval);
                        }


                    }
                }
            }
        }


    };
}
