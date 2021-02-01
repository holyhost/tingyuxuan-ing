package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.CommonUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
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
    @BindView(R.id.btn_login)
    Button btn_login;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        mToolbar.setTitle("登录");
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_login,R.id.delete_username,R.id.tv_forget,R.id.tv_register})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                //登录
                boolean isNetConnected = CommonUtil.isNetworkAvailable(this);
                if (!isNetConnected) {
                    ToastUtil.showShort(this,"当前网络不可用,请检查网络设置");
                    return;
                }
                if(TextUtils.isEmpty(et_username.getText().toString())){
                    et_username.setError("用户名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(et_password.getText().toString())){
                    et_password.setError("密码不能为空");
                    return;
                }
                btn_login.setText("验证帐号中···");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();

                break;
            case R.id.delete_username:
                //清除输入的用户名以及密码
                et_username.setText("");
                et_password.setText("");
                mDeleteUserName.setVisibility(View.GONE);
                et_username.setFocusable(true);
                et_username.setFocusableInTouchMode(true);
                et_username.requestFocus();
                break;
            case R.id.tv_forget:
                //忘记密码
                forget();
                break;
            case R.id.tv_register:
                //注册
                register();
                break;
        }
    }

    private void register() {
        startActivity(new Intent(this,RegActivity.class));
    }

    private void forget() {
        ToastUtil.showLong(this,"此功能正在上线中");
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if(message.what>0){
                ToastUtil.showShort(LoginActivity.this,"登录成功！");
                //跳转主界面
                getSharedPreferences("user",MODE_PRIVATE).edit().putString("username",et_username.getText().toString()).commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //记录用户操作
                DbUtil.recordOperation("登录成功",LoginActivity.this);
                finish();
            }else {
                DbUtil.recordOperation("登录失败:用户名或密码错误",LoginActivity.this);
                ToastUtil.showShort(LoginActivity.this,"用户名或密码错误！");
                btn_login.setText("登陆");
            }
            return false;
        }
    });
    private void login() {
        int result = DbUtil.checkLogin(LoginActivity.this,"select * from user where name = '" + et_username.getText().toString() + "' and password = '" + et_password.getText().toString() + "'");
        handler.sendEmptyMessage(result);
    }
}

