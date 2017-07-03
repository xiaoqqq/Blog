package com.xiaoq.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoq.base.utils.Logger;

/**
 * Created by user on 2017/6/29.
 * packageName：com.xiaoq.base.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    private static final java.lang.String TAG = BaseFragment.class.getSimpleName();

    protected Activity mContext;

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate: ========================");
        mContext = getActivity();
        init();
    }

    /**
     * 一些初始化操作
     */
    protected void init() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView: ========================");
        return initView();
    }

    /**
     * 子类具体实现该方法,返回对应的视图
     *
     * @return 子类具体需要返回的视图
     */
    protected abstract View initView();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG, "onActivityCreated: ========================");
        initListener();
        initData();
        getTransaction();
    }

    protected android.app.FragmentTransaction getTransaction() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        return transaction;
    }

    /**
     * 加载/刷新数据
     */
    protected void initData() {

    }

    /**
     * 设置监听
     */
    protected void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(TAG, "onStart: ========================");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume: ========================");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG, "onPause: ========================");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(TAG, "onStop: ========================");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG, "onDestroyView: ========================");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy: ========================");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(TAG, "onDetach: ========================");
    }

}
