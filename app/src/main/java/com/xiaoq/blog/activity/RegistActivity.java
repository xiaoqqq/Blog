package com.xiaoq.blog.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.xiaoq.base.BaseActivity;
import com.xiaoq.base.utils.Logger;
import com.xiaoq.base.utils.ToastUtils;
import com.xiaoq.base.views.ClearableEditText;
import com.xiaoq.blog.R;
import com.xiaoq.blog.common.Constants;
import com.xiaoq.blog.request.RegistServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017/6/24.
 * packageName：com.xiaoq.blog.activity.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class RegistActivity extends BaseActivity {

    private ClearableEditText mRegistName;
    private ClearableEditText mRegistPwd;
    private ClearableEditText mRegistPwdAgain;
    private Button mBtRegist;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {
        mRegistName = (ClearableEditText) findViewById(R.id.et_regist_name);
        mRegistPwd = (ClearableEditText) findViewById(R.id.et_regist_password);
        mRegistPwdAgain = (ClearableEditText) findViewById(R.id.et_regist_password_again);
        mBtRegist = (Button) findViewById(R.id.bt_regist);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void init() {
        super.init();
         /*去掉Activity上面的状态栏*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registName = getTextStr(mRegistName);
                String registPwd = getTextStr(mRegistPwd);
                String registPwdAgain = getTextStr(mRegistPwdAgain);
                if (!isNull(registName) && !isNull(registPwd) && !isNull(registPwdAgain)) {
                    if (!registPwd.equals(registPwdAgain)) {
                        ToastUtils.show(RegistActivity.this, "两次密码不一致,请重新输入");
                        mRegistPwdAgain.setText("");
                    } else {
                        regist(registName, registPwd);
                    }
                } else {
                    ToastUtils.show(RegistActivity.this, "用户名或者密码为空");
                }

            }
        });
    }

    private void regist(String registName, String registPwd) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistServices registServices = retrofit.create(RegistServices.class);
        Call<ResponseBody> registCall = registServices.regist(registName, registPwd);
        registCall.enqueue(new Callback<ResponseBody>() {
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
                ToastUtils.show(RegistActivity.this, "网络连接超时!!!");
            }
        });

    }

    /**
     * 注册返回的json解析
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
}
