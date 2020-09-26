package com.mican.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mican.myapplication.R;
import com.mican.myapplication.util.BarUtils;
import com.mican.myapplication.util.SizeUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

/**
 * Created by Administrator on 2017/7/18.
 */

public class TitleBarView extends RelativeLayout {
    private LinearLayout layout_left, layout_right;
    private TextView tv_left, tv_title, tv_right;
    private ImageView iv_left, iv_right;
    private onViewClick mClick;
    private ConstraintLayout title_layout_parent;
    private TextView title_android_bar;
    private Context context;
    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TitleBarView_leftTextColor) {
                tv_left.setTextColor(array.getColor(attr, Color.BLACK));
            } else if (attr == R.styleable.TitleBarView_leftDrawble) {
                iv_left.setImageResource(array.getResourceId(attr, 0));
            } else if (attr == R.styleable.TitleBarView_leftText) {
                tv_left.setText(array.getString(attr));
            } else if (attr == R.styleable.TitleBarView_centerTextColor) {
                tv_title.setTextColor(array.getColor(attr, Color.BLACK));
            } else if (attr == R.styleable.TitleBarView_centerTitle) {
                tv_title.setText(array.getString(attr));
            } else if (attr == R.styleable.TitleBarView_rightDrawable) {
                iv_right.setImageResource(array.getResourceId(attr, 0));
            } else if (attr == R.styleable.TitleBarView_rightText) {
                tv_right.setText(array.getString(attr));
            } else if (attr == R.styleable.TitleBarView_rightTextColor) {
                tv_right.setTextColor(array.getColor(attr, Color.BLACK));
            } else if (attr == R.styleable.TitleBarView_bar_height) {
                title_layout_parent.setMaxHeight((int) array.getDimensionPixelSize(attr, SizeUtils.dp2px(48)));
            }
        }
        array.recycle();
        layout_left.setOnClickListener(view -> {
            if(iv_left.getDrawable()==null)return;
         if(mClick!=null) mClick.leftClick();
        });
        layout_right.setOnClickListener(view -> {
          if(mClick!=null) mClick.rightClick();
        });

    }

    private void initView() {
        tv_left = findViewById(R.id.tv_left);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        layout_left = findViewById(R.id.layout_left);
        layout_right = findViewById(R.id.layout_right);
        title_layout_parent= findViewById(R.id.title_layout_parent);
        title_android_bar   = findViewById(R.id.title_android_bar);
    }

    public void setOnViewClick(onViewClick click) {
        this.mClick = click;
    }

    public void defSet_1(){
        /**
         * def 设置
         */
        if(context==null)return;
        if(tv_right==null)return;
        tv_right.setTextColor(ContextCompat.getColor(context,R.color.white));
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP,  14);

        tv_left.setTextColor(ContextCompat.getColor(context,R.color.white));
        tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP,  14);

        tv_title.setTextColor(ContextCompat.getColor(context,R.color.white));
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,  16);
    }

    //设置标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    //设置标题
    public void setTitleColor(int color) {
        if (color!=0) {
            tv_title.setTextColor(ContextCompat.getColor(context,color));
        }
    }


    //设置左标题
    public void setLeftText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_left.setText(title);
        }
    }

    //设置右标题
    public void setRightTextColor(int color) {
        if (color!=0) {
         if(tv_right!=null)tv_right.setTextColor(ContextCompat.getColor(context,color));
        }
    }

    //设置右标题
    public void setRightTextColorList(int color) {
        if (color!=0) {
            Resources resources = getResources();
            @SuppressLint("UseCompatLoadingForColorStateLists")
            ColorStateList colorStateList = resources.getColorStateList(color);
            if(tv_right!=null)tv_right.setTextColor(colorStateList);
        }
    }
    //设置右标题
    public void setRightText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_right.setText(title);
        }
    }

    //设置标题大小
    public void setTitleSize(int size) {
        if (tv_title != null) {
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置左标题大小
    public void setLeftTextSize(int size) {
        if (tv_left != null) {
            tv_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置右标题大小
    public void setRightTextSize(int size) {
        if (tv_right != null) {
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    //设置左图标
    public void setLeftDrawable(int res) {
        if (iv_left != null) {
            iv_left.setImageResource(res);
        }
    }

    public void setRightTvEnable(boolean b){
        if (layout_right != null) {
            layout_right.setEnabled(b);
        }
        if (tv_right != null) {
            tv_right.setEnabled(b);
        }
    }
    //设置左图标 R.color.text_color_666
    public void setRightDrawable(int res,int color) {
        if (iv_left != null) {
            iv_right.setImageResource(res);
            iv_right.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));
        }
    }

    //设置左图标 R.color.text_color_666
    public void setLeftDrawable(int res,int color) {
        if (iv_left != null) {
            iv_left.setImageResource(res);
            iv_left.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));
        }
    }
   public  void setTitleHeight(int dip){
       ViewGroup.LayoutParams layoutParams = title_layout_parent.getLayoutParams();
       layoutParams.height=SizeUtils.dp2px(dip);
       title_layout_parent.setLayoutParams(layoutParams);
    }
    public void setBar(){
      title_android_bar.setVisibility(VISIBLE);
      title_android_bar.setHeight(BarUtils.getStatusBarHeight());
  }
    //设置右图标
    public void setRightDrawable(int res) {
        if (iv_right != null) {
            iv_right.setImageResource(res);
        }
    }

    public interface onViewClick {
        void leftClick();

        void rightClick();
    }

}
