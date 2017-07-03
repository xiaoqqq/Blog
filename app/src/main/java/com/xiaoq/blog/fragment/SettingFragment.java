package com.xiaoq.blog.fragment;

import android.view.View;

import com.xiaoq.base.BaseFragment;
import com.xiaoq.blog.R;

/**
 * Created by user on 2017/7/3.
 * packageName：com.xiaoq.blog.fragment.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class SettingFragment extends BaseFragment {
    @Override
    protected View initView() {
        View settingView = View.inflate(getActivity(), R.layout.fragment_setting, null);
        return settingView;
    }
}
