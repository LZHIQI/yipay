package com.example.testmodule.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.testmodule.R;


public class MyCount extends AdvancedCountdownTimer {
    private Context mContext;

    private  int TOTALTIME = 60 * 1000;//定时60秒
    private  int COUNTDOWNINTERVAL = 1000;//间隔1秒
    private TextView tvGetCode;
    ForegroundColorSpan fcs ;
    private boolean isCount=false;
    private int type=0;

    private ICallBack callBack;
    /**
     *
     * @param millisInFuture  定时总时间
     * @param countDownInterval 时间间隔
     * @param context
     * @param tvGetCode
     */
    public MyCount(long millisInFuture, long countDownInterval, Context context, TextView tvGetCode) {
        super(millisInFuture, countDownInterval);
        this.TOTALTIME=(int) millisInFuture;
        this.COUNTDOWNINTERVAL=(int)countDownInterval;
        this.mContext=context;
        fcs = new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent));
        this.tvGetCode=tvGetCode;
        this.type=0;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    /**
     *
     * @param millisInFuture  定时总时间
     * @param countDownInterval 时间间隔
     * @param context
     * @param tvGetCode
     */
    public MyCount(long millisInFuture, long countDownInterval, Context context, TextView tvGetCode, int type) {
        super(millisInFuture, countDownInterval);
        this.TOTALTIME=(int) millisInFuture;
        this.COUNTDOWNINTERVAL=(int)countDownInterval;
        this.mContext=context;
        fcs = new ForegroundColorSpan(context.getResources().getColor(R.color.fail_color));
        this.tvGetCode=tvGetCode;
        this.type=type;
    }
    public   void  setText(){
        isCount=true;
        if(type!=0){
            tvGetCode.setEnabled(false);
            String format = String.format(mContext.getString(R.string.identify_code_reload), String.valueOf(TOTALTIME / COUNTDOWNINTERVAL) );
            tvGetCode.setText(format);
        }else {
            tvGetCode.setEnabled(false);
            SpannableString ssb = new SpannableString(String.valueOf(TOTALTIME / COUNTDOWNINTERVAL));
            ssb.setSpan(fcs, 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvGetCode.setText( String.format(mContext.getString(R.string.identify_code_reload),ssb ));
        }
    }

    @Override
    public void onFinish() {
        isCount=false;
        if(type!=0){
            tvGetCode.setText(mContext.getString(R.string.identify_code_reloading));
            tvGetCode.setEnabled(true);
        }else {
            tvGetCode.setText(mContext.getString(R.string.identify_code_reloading));
           // tvGetCode.setTextColor(ContextCompat.getColorStateList(mContext, R.color.sendcode));
            tvGetCode.setEnabled(true);
        }
        if(callBack!=null){
            callBack.back(0);
        }

    }

    //更新剩余时间
    @Override
    public void onTick(long millisUntilFinished, int percent) {
        long time = (millisUntilFinished / 1000);
        isCount=true;
        if(type!=0){
            tvGetCode.setEnabled(false);
            String format = String.format(mContext.getString(R.string.identify_code_reload), String.valueOf(time));
            tvGetCode.setText(format);
        }else {
            tvGetCode.setEnabled(false);
          //  tvGetCode.setTextColor(ContextCompat.getColor(mContext, R.color.text666));
            SpannableString ssb = new SpannableString(String.valueOf(time));
            ssb.setSpan(fcs, 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            String format = String.format(mContext.getString(R.string.identify_code_reload), String.valueOf(ssb));
            tvGetCode.setText(format);
        }
    }

    /**
     * 获取状态**用于验证码按键监听事件
     * @return
     */
    public boolean getStatus(){
        if(tvGetCode!=null){
            return !isCount;
        }
        return false;
    }
    public interface ICallBack {

        void back(Object o);
    }


}
