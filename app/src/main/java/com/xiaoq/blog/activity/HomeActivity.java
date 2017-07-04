package com.xiaoq.blog.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoq.base.BaseActivity;
import com.xiaoq.base.BaseFragment;
import com.xiaoq.base.utils.Logger;
import com.xiaoq.base.utils.SPUtils;
import com.xiaoq.base.utils.ToastUtils;
import com.xiaoq.blog.R;
import com.xiaoq.blog.common.Constants;
import com.xiaoq.blog.fragment.AboutAuthorFragment;
import com.xiaoq.blog.fragment.EditViewInfoFragment;
import com.xiaoq.blog.fragment.HeadFragment;
import com.xiaoq.blog.fragment.HomeFragment;
import com.xiaoq.blog.fragment.SettingFragment;
import com.xiaoq.blog.views.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

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
    private ImageView mIvHead;
    private LinearLayout mLlHome;
    private LinearLayout mLlSetting;
    private LinearLayout mLlAboutAuthor;
    private TextView mTvNickName;
    private TextView mTvEditViewInfo;
    private TextView mTvTitleRight;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        mTvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        setImmerseLayout(this, toolbar);
        setImmerseLayout(this, mTvTitleRight);
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
        // 显示昵称
        String result = (String) SPUtils.get(this, Constants.USER_INFO, "");
        showNickName(result);
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, new HomeFragment());
        // 事务提交
        transaction.commit();
    }

    /**
     * 显示用户昵称
     *
     * @param result 登录成功保存的json字符串
     */
    private void showNickName(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("data")) {
                JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                String nickname = data.getString("nickname");
                mTvNickName.setText(nickname);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        mTvTitleRight.setOnClickListener(this);
    }

    /**
     * 展示fragment界面
     *
     * @param fragment 具体的fragment
     */
    private void displayFragmentPage(BaseFragment fragment) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        // 事务提交
        transaction.commit();
        mTvTitleRight.setText("");
        mDrawerLayout.closeDrawers();
    }

    /**
     * 展示fragment界面
     *
     * @param fragment 具体的fragment
     * @param right    toolbar右侧的文字内容
     */
    private void displayFragmentPage(BaseFragment fragment, String right) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        // 事务提交
        transaction.commit();
        mTvTitleRight.setText(right);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left_home:
                displayFragmentPage(new HomeFragment());
                break;
            case R.id.ll_left_setting:
                displayFragmentPage(new SettingFragment());
                break;
            case R.id.ll_left_about_author:
                displayFragmentPage(new AboutAuthorFragment());
                break;
            case R.id.tv_left_edit_view_info:
                displayFragmentPage(new EditViewInfoFragment(), "编辑");
                break;
            case R.id.iv_left_head:
                displayFragmentPage(new HeadFragment());
                break;
            case R.id.tv_title_right:
                ToastUtils.show(this, mTvTitleRight.getText());
                break;
            default:
                break;
        }
    }
}
