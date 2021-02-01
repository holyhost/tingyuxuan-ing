package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

public class AddBookActivity extends BaseActivity {




    @BindView(R.id.et_book_name)
    EditText et_book_name;
    @BindView(R.id.et_book_price)
    EditText et_book_price;
    @BindView(R.id.et_book_count)
    EditText et_book_count;
    @BindView(R.id.et_book_type)
    EditText et_book_type;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_book_add)
    Button btn_add;



    @Override
    public int getLayoutId() {
        return R.layout.activity_add_book;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_book_add)
    void onClick(View view){
        String name = et_book_name.getText().toString();
        String type = et_book_type.getText().toString();
        String price = et_book_price.getText().toString();
        String count = et_book_count.getText().toString();
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(type)||TextUtils.isEmpty(count)||TextUtils.isEmpty(price)){
            ToastUtil.showLong(this,"请填写完整书籍信息");
            return;
        }
        BookBean book = new BookBean();
        book.setName(name);
        book.setPrice(Float.parseFloat(price));
        book.setCount(Integer.parseInt(count));
        book.setType(type);
//        DbHelper helper = new DbHelper(this,"tingyuxuan",null,1);
        try{
//            helper.addBook(book);
            DbUtil.addNewBook(book,this);
        }catch (SQLException e){
            e.printStackTrace();
            ToastUtil.showShort(this,"添加失败");
        }
        ToastUtil.showShort(this,"添加成功");
        getSharedPreferences("book",MODE_PRIVATE).edit().putBoolean("update",true).commit();
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle("填写书籍相关信息");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
