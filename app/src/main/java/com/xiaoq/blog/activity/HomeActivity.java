package com.xiaoq.blog.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoq.base.BaseActivity;
import com.xiaoq.base.utils.Logger;
import com.xiaoq.base.utils.ToastUtils;
import com.xiaoq.blog.R;
import com.xiaoq.blog.views.GlideCircleTransform;

/**
 * Created by user on 2017/6/24.
 * packageName：com.xiaoq.blog.activity.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
    private ArrayAdapter arrayAdapter;
    private ImageView mIvHead;
    private LinearLayout mLlHome;
    private LinearLayout mLlSetting;
    private LinearLayout mLlAboutAuthor;
    private TextView mTvNickName;
    private TextView mTvEditViewInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        setImmerseLayout(this, toolbar);
        // 头像
        mIvHead = (ImageView) findViewById(R.id.iv_left_head);
        // 昵称
        mTvNickName = (TextView) findViewById(R.id.tv_left_nick_name);
        // 编辑/查看资料
        mTvEditViewInfo = (TextView) findViewById(R.id.tv_left_edit_view_info);
        // 首页
        mLlHome = (LinearLayout) findViewById(R.id.ll_left_home);
        // 设置
        mLlSetting = (LinearLayout) findViewById(R.id.ll_left_setting);
        // 关于作者
        mLlAboutAuthor = (LinearLayout) findViewById(R.id.ll_left_about_author);

    }


    @Override
    protected void initData() {
        super.initData();
        toolbar.setTitle("Blog");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Logger.i("drawerView", "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Logger.i("drawerView", "onDrawerClosed");
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        Glide.with(this).load(R.mipmap.moren).transform(new GlideCircleTransform(this)).into(mIvHead);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initClick() {
        super.initClick();
        /*初始化左侧菜单栏点击事件*/
        mIvHead.setOnClickListener(this);
        mTvEditViewInfo.setOnClickListener(this);
        mLlHome.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
        mLlAboutAuthor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left_home:
                ToastUtils.show(this, "Home");
                mDrawerLayout.closeDrawers();
                break;
            case R.id.ll_left_setting:
                ToastUtils.show(this, "Setting");
                mDrawerLayout.closeDrawers();
                break;
            case R.id.ll_left_about_author:
                ToastUtils.show(this, "About Author");
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_left_edit_view_info:
                ToastUtils.show(this, "Edit or View Info");
                mDrawerLayout.closeDrawers();
                break;
            case R.id.iv_left_head:
                ToastUtils.show(this, "Head click!");
                mDrawerLayout.closeDrawers();
                break;
            default:
                break;
        }
    }
}
