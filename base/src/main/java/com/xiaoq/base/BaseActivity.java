package com.xiaoq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

}
