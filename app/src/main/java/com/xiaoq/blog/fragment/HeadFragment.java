package com.xiaoq.blog.fragment;

import android.view.View;

import com.xiaoq.base.BaseFragment;
import com.xiaoq.blog.R;

/**
 * Created by user on 2017/7/4.
 * packageName：com.xiaoq.blog.fragment.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class HeadFragment extends BaseFragment {
    @Override
    protected View initView() {
        View headView = View.inflate(getActivity(), R.layout.fragment_head, null);
        return headView;
    }
}
