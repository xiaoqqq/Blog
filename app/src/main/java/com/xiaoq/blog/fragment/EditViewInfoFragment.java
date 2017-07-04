package com.xiaoq.blog.fragment;

import android.view.View;
import android.widget.TextView;

import com.xiaoq.base.BaseFragment;
import com.xiaoq.base.utils.SPUtils;
import com.xiaoq.blog.R;
import com.xiaoq.blog.common.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2017/7/3.
 * packageName：com.xiaoq.blog.fragment.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public class EditViewInfoFragment extends BaseFragment {

    private TextView mNickName;
    private TextView mSex;
    private TextView mEmail;
    private TextView mPhoneNumber;

    @Override
    protected View initView() {
        View editInfoView = View.inflate(getActivity(), R.layout.fragment_edit_view_info, null);
        mNickName = (TextView) editInfoView.findViewById(R.id.tv_user_nick_name);
        mSex = (TextView) editInfoView.findViewById(R.id.tv_user_sex);
        mEmail = (TextView) editInfoView.findViewById(R.id.tv_user_email);
        mPhoneNumber = (TextView) editInfoView.findViewById(R.id.tv_user_phone_number);
        return editInfoView;
    }

    @Override
    protected void initData() {
        super.initData();
        String userInfo = (String) SPUtils.get(getActivity(), Constants.USER_INFO, "");
        showUserInfo(userInfo);
    }

    private void showUserInfo(String userInfo) {
        try {
            JSONObject jsonObject = new JSONObject(userInfo);
            if (!jsonObject.isNull("data")) {
                JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                String nickname = data.getString("nickname");
                String phoneNumber = data.getString("mobile");
                String email = data.getString("email");
                int sex = data.getInt("sex");
                mNickName.setText(nickname);
                mEmail.setText(email);
                mPhoneNumber.setText(phoneNumber);
                if (sex == 0) {
                    mSex.setText("女");
                } else {
                    mSex.setText("男");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();

    }
}
