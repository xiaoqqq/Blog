package com.xiaoq.blog.activity;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoq.base.BaseActivity;
import com.xiaoq.base.utils.Logger;
import com.xiaoq.base.utils.SystemUtils;
import com.xiaoq.base.utils.ToastUtils;
import com.xiaoq.base.views.ClearableEditText;
import com.xiaoq.blog.R;
import com.xiaoq.blog.common.Constants;
import com.xiaoq.blog.request.LoginSerives;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private ClearableEditText mUserName;
    private ClearableEditText mPassWord;
    private Button mBtLogin;
    private TextView mVersionCode;
    private TextView mTvForgetPassWord;
    private TextView mTvRegist;


    @Override
    protected void init() {
        super.init();
         /*去掉Activity上面的状态栏*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        // 用户名
        mUserName = (ClearableEditText) findViewById(R.id.et_username);
        // 密码
        mPassWord = (ClearableEditText) findViewById(R.id.et_password);
        // 登录按钮
        mBtLogin = (Button) findViewById(R.id.bt_login);
        // 版本号
        mVersionCode = (TextView) findViewById(R.id.tv_version_code);
        // 忘记密码
        mTvForgetPassWord = (TextView) findViewById(R.id.tv_forget_password);
        // 连接设置
        mTvRegist = (TextView) findViewById(R.id.tv_regist);
    }

    @Override
    protected void initData() {
        super.initData();
        String versionName = SystemUtils.getVersionName(this);
        mVersionCode.setText("版本: V" + versionName);
    }

    @Override
    protected void initTitle() {

    }

    protected void initClick() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getTextStr(mUserName);
                String password = getTextStr(mPassWord);
                if (!isNull(username) && !isNull(password)) {
                    login(username, password);
                } else {
                    ToastUtils.show(MainActivity.this, "用户名或者密码不能为空");
                }
            }
        });
    }

    // /login/login
    private void login(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //生成对象的Service
        LoginSerives loginSerives = retrofit.create(LoginSerives.class);
        Call<ResponseBody> loginCall = loginSerives.login(username, password);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte[] bytes = response.body().bytes();
                    String result = new String(bytes);
                    Logger.e(result);
                    parseJson(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.show(MainActivity.this, t.getMessage());

            }
        });

    }

    /**
     * 解析登录返回的json
     *
     * @param result
     */
    private void parseJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int errorCode = jsonObject.getInt("errorCode");
            if (errorCode == 0) { // 0:失败
                String msg = jsonObject.getString("msg");
                ToastUtils.show(this, msg);
            } else { // 1:成功
                String msg = jsonObject.getString("msg");
                ToastUtils.show(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return false;
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
