package com.xiaoq.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 2017/6/24.
 * packageName：com.xiaoq.base.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(initLayoutId());
        getSupportActionBar().hide();
        initView();
        initTitle();
        initClick();
        initData();
    }

    protected abstract int initLayoutId();

    abstract protected void initView();

    abstract protected void initTitle();

    /**
     * 在setContentView（）之前的初始化工作
     */
    protected void init() {

    }

    /**
     * 点击事件
     */
    protected void initClick() {

    }

    /**
     * 初始化数据/刷新
     */
    protected void initData() {

    }

    /**
     * 获取view中的文字
     *
     * @param view
     * @return
     */
    protected String getTextStr(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * 检查字符串是否是空对象或空字符串
     *
     * @param str
     * @return true:空 false:非空
     */
    public boolean isNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 跳转activity
     *
     * @param clazz
     */
    public void startMyActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 点击EditText文本框之外任何地方隐藏键盘的解决办法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
