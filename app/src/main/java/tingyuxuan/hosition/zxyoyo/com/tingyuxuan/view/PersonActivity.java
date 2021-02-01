package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.UserBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

/**
 * 本页面复制 UpdateBookActivity,因为参数名未做修改
 */

public class PersonActivity extends BaseActivity {




    @BindView(R.id.et_user_sex)
    EditText et_sex;
    @BindView(R.id.et_user_class)
    EditText et_class;
    @BindView(R.id.et_book_name)
    EditText et_user_name;

    @BindView(R.id.et_book_price)
    EditText et_user_price;
    @BindView(R.id.et_book_count)
    EditText et_user_count;
    @BindView(R.id.et_book_intro)
    EditText et_user_intro;
    @BindView(R.id.et_book_pub)
    EditText et_user_pub;
    @BindView(R.id.et_book_type)
    EditText et_user_type;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private UserBean user;


    @Override
    public int getLayoutId() {
        return R.layout.activity_person;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String name = getSharedPreferences("user", MODE_PRIVATE).getString("username", "");
        user = DbUtil.getUserByName(this,name);
        et_user_name.setHint(user.getName());
        et_user_count.setHint(user.getType()+"(1为普通用户，大于1为管理员)");
        et_user_price.setHint(user.getAge()+"(年龄)");
        et_user_type.setHint(user.getMajor()+"(专业)");
        et_user_pub.setHint("新密码···");
        et_user_intro.setHint(user.getEmail()+"(邮箱)");
        et_sex.setHint(user.getEmail()+"(性别)");
        et_class.setHint(user.getEmail()+"(班级)");
    }

    @OnClick(R.id.btn_book_update)
    void onClick(View view){
        String name = et_user_name.getText().toString();
        String type = et_user_type.getText().toString();
        String price = et_user_price.getText().toString();
        String count = et_user_count.getText().toString();
        String pub = et_user_pub.getText().toString();
        String intro = et_user_intro.getText().toString();

        String  clazznumber = et_class.getText().toString();
        String  sex = et_sex.getText().toString();


        if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(type)&&TextUtils.isEmpty(count)&&TextUtils.isEmpty(price)
                &&TextUtils.isEmpty(pub)&&TextUtils.isEmpty(intro)
                &&TextUtils.isEmpty(sex)&&TextUtils.isEmpty(clazznumber)){
            ToastUtil.showLong(this,"未做任何修改");
            return;
        }
        inituser( name,type,price,count,pub,intro,clazznumber,sex );
        try{
            DbUtil.updateUser(user,this);
        }catch (SQLException e){
            e.printStackTrace();
            ToastUtil.showShort(this,"更新失败");
        }
        ToastUtil.showShort(this,"更新成功");
    }

    private void inituser(String name, String type, String price, String count, String pub, String intro,String clanumber,String sex) {
        if(!TextUtils.isEmpty(name)){
            user.setName(name);
        }
        if(!TextUtils.isEmpty(type)){
            user.setMajor(type);
        }
        if(!TextUtils.isEmpty(pub)){
            user.setPassword(pub);
        }
        if(!TextUtils.isEmpty(intro)){
            user.setEmail(intro);
        }
        if(!TextUtils.isEmpty(price)){
            user.setAge(Integer.parseInt(price));
        }
        if(!TextUtils.isEmpty(sex)){
            user.setSex(sex);
        }
        if(!TextUtils.isEmpty(sex)){
            user.setClassnum(Integer.parseInt(clanumber));
        }
        if(!TextUtils.isEmpty(count)){
            int level = Integer.parseInt(count);
            String username = getSharedPreferences("user", MODE_PRIVATE).getString("username", "");
            if(!TextUtils.isEmpty(username)){
                DbHelper helper = new DbHelper(this);
                int userLevl = helper.getUserLevl(username);
                if(level<userLevl){
                    user.setType(level);
                }else {
                    et_user_count.setError("只能设置比自己等级低类型");
                    return;
                }
            }


        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle("更新用户信息");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
