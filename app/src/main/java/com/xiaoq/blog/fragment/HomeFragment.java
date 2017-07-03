package com.xiaoq.blog.fragment;

import android.view.View;

import com.xiaoq.base.BaseFragment;
import com.xiaoq.blog.R;

/**
 * Created by user on 2017/6/29.
 * packageName：com.xiaoq.blog.fragment.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: HomeFragment
 */
public class HomeFragment extends BaseFragment {


    @Override
    protected View initView() {
        View homeView = View.inflate(getActivity(), R.layout.fragment_home, null);
        return homeView;
    }
}
