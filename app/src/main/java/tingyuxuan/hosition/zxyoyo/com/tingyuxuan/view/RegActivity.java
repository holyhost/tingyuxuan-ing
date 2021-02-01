package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.CommonUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

/**
 * A login screen that offers login via email/password.
 */
public class RegActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_icon_left)
    ImageView mLeftLogo;
    @BindView(R.id.iv_icon_right)
    ImageView mRightLogo;
    @BindView(R.id.delete_username)
    ImageView mDeleteUserName;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password2)
    EditText et_password2;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.btn_reg)
    Button btn_reg;


    @Override
    public int getLayoutId() {
        return R.layout.activity_reg;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && et_username.getText().length() > 0) {
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    mDeleteUserName.setVisibility(View.GONE);
                }
                mLeftLogo.setImageResource(R.drawable.ic_22);
                mRightLogo.setImageResource(R.drawable.ic_33);
            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                mLeftLogo.setImageResource(R.drawable.ic_22_hide);
                mRightLogo.setImageResource(R.drawable.ic_33_hide);
            }
        });
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_password.setText("");
                if (charSequence.length() > 0) {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle("注册新用户");
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_reg,R.id.delete_username,R.id.btn_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_reg:
                //注册
                boolean isNetConnected = CommonUtil.isNetworkAvailable(this);
                if (!isNetConnected) {
                    ToastUtil.showShort(this,"当前网络不可用,请检查网络设置");
                    return;
                }
                if(TextUtils.isEmpty(et_username.getText().toString())
                        ||TextUtils.isEmpty(et_password.getText().toString())
                        ||TextUtils.isEmpty(et_email.getText().toString())
                        ||TextUtils.isEmpty(et_password2.getText().toString())){
                    ToastUtil.showShort(this,"请输入帐号-密码-邮箱");
                    return;
                }
                toReg();
                break;
            case R.id.delete_username:
                //清除输入的用户名以及密码
                et_username.setText("");
                et_password.setText("");
                et_password2.setText("");
                et_email.setText("");
                mDeleteUserName.setVisibility(View.GONE);
                et_username.setFocusable(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                break;
            case  R.id.btn_back:
                onBackPressed();
                finish();
                break;
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what>0){
                DbUtil.recordOperation("注册:注册成功",RegActivity.this);
                ToastUtil.showShort(RegActivity.this,"注册成功");
                et_username.setText("");
                et_password.setText("");
                et_password2.setText("");
                et_email.setText("");
                mDeleteUserName.setVisibility(View.GONE);

            }else {
                DbUtil.recordOperation("注册:注册失败，用户名被占用-"+et_username.getText().toString(),RegActivity.this);
                ToastUtil.showShort(RegActivity.this,"注册失败，用户名被占用");
            }
            btn_reg.setText("注册");
            return false;
        }
    });

    private void toReg() {
        btn_reg.setText("注册帐号中···");
        final String sql = "insert into user values('"
                +et_username.getText().toString()+"','"
                +et_password.getText().toString()+"','"
                +et_email.getText().toString()+"')";
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = DbUtil.toReg(RegActivity.this,
                        et_username.getText().toString(),
                        et_password.getText().toString(),
                        et_email.getText().toString(),
                        1);

                handler.sendEmptyMessage(result?1:0);
            }
        }).start();

    }


}

