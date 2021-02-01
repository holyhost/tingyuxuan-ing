package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

public class UpdateBookActivity extends BaseActivity {




    @BindView(R.id.et_book_name)
    EditText et_book_name;
    @BindView(R.id.et_book_price)
    EditText et_book_price;
    @BindView(R.id.et_book_count)
    EditText et_book_count;
    @BindView(R.id.et_book_intro)
    EditText et_book_intro;
    @BindView(R.id.et_book_pub)
    EditText et_book_pub;
    @BindView(R.id.et_book_type)
    EditText et_book_type;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private BookBean book;


    @Override
    public int getLayoutId() {
        return R.layout.activity_update_book;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        book = (BookBean) getIntent().getSerializableExtra("book");
        et_book_name.setHint(book.getName());
        et_book_count.setHint(book.getCount()+"");
        et_book_price.setHint(book.getPrice()+"");
        et_book_type.setHint(book.getType());
        et_book_pub.setHint(book.getPublic_com());
        et_book_intro.setHint(book.getIntroduce());
    }

    @OnClick(R.id.btn_book_update)
    void onClick(View view){
        String name = et_book_name.getText().toString();
        String type = et_book_type.getText().toString();
        String price = et_book_price.getText().toString();
        String count = et_book_count.getText().toString();
        String pub = et_book_pub.getText().toString();
        String intro = et_book_intro.getText().toString();
        if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(type)&&TextUtils.isEmpty(count)&&TextUtils.isEmpty(price)
                &&TextUtils.isEmpty(pub)&&TextUtils.isEmpty(intro)){
            ToastUtil.showLong(this,"未做任何修改");
            return;
        }
        initBook( name,type,price,count,pub,intro );
        try{
            DbUtil.updateBook(book,this);
        }catch (SQLException e){
            e.printStackTrace();
            ToastUtil.showShort(this,"更新失败");
        }
        ToastUtil.showShort(this,"更新成功");
    }

    private void initBook(String name, String type, String price, String count, String pub, String intro) {
        if(!TextUtils.isEmpty(name)){
            book.setName(name);
        }
        if(!TextUtils.isEmpty(type)){
            book.setType(type);
        }
        if(!TextUtils.isEmpty(pub)){
            book.setPublic_com(pub);
        }
        if(!TextUtils.isEmpty(intro)){
            book.setIntroduce(intro);
        }
        if(!TextUtils.isEmpty(price)){
            book.setPrice(Float.parseFloat(price));
        }
        if(!TextUtils.isEmpty(count)){
            book.setCount(Integer.parseInt(count));
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle("更新书籍信息");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
