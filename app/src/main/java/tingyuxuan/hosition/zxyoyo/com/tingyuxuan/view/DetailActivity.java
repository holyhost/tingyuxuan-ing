package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.FavorBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvName;
    TextView tvPrice;
    TextView tvPub;
    TextView tvStar;
    TextView tvLook;
    TextView tvCount;
    TextView tvIntro;
    Button btnStar;
    Button btnBorrow;
    Button btnOK;
    Button btnRead;

    BookBean book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.dialog_detail);
        initView();
        Intent intent = getIntent();
        //反序列化数据对象
        Serializable se = intent.getSerializableExtra("book");
        if(se instanceof BookBean) {
            book = (BookBean) se;
            tvName.setText(((BookBean) se).getName());
            tvPrice.setText("价格："+((BookBean) se).getPrice()+"元");
            tvPub.setText("出版社："+((BookBean) se).getPublic_com());
            tvStar.setText("收藏数："+((BookBean) se).getStar_number());
            tvLook.setText("浏览量："+((BookBean) se).getLook_number());
            tvCount.setText("剩余藏书量："+((BookBean) se).getCount());
            tvIntro.setText("介绍："+((BookBean) se).getIntroduce());
        }
        DbUtil.updateLookNumber(book.getNumber(),book.getLook_number()+1,this);
    }

    private void initView() {
        tvCount = findViewById(R.id.dialog_count);
        tvPrice = findViewById(R.id.dialog_price);
        tvPub = findViewById(R.id.dialog_pub);
        tvName = findViewById(R.id.dialog_title);
        tvIntro = findViewById(R.id.dialog_introduce);
        tvStar = findViewById(R.id.dialog_star);
        tvLook = findViewById(R.id.dialog_look);
        btnRead = findViewById(R.id.btn_read);
        btnBorrow = findViewById(R.id.btn_borrow);
        btnOK = findViewById(R.id.btn_ok);
        btnStar = findViewById(R.id.btn_give);
        btnStar.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnBorrow.setOnClickListener(this);
        btnRead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_read:
                DbUtil.recordOperation("read"+book.getName(),DetailActivity.this);
                ToastUtil.showShort(this,"read success!");
                onBackPressed();
                break;
            case R.id.btn_ok:
                onBackPressed();
                break;
            case R.id.btn_borrow:
                if(book.getCount()>0){
                    String name = getSharedPreferences("user", MODE_PRIVATE).getString("username", "");
                    if(TextUtils.isEmpty(name)){
                        ToastUtil.showShort(this,"借阅失败");
                        DbUtil.recordOperation("借阅:失败-"+book.getName(),DetailActivity.this);
                        return;
                    }
                    DbHelper db = new DbHelper(this);
                    db.addToMyBorreow(name,book.getName());
                    ToastUtil.showShort(this,"已成功借阅"+book.getName());
                    DbUtil.recordOperation("借阅:成功-"+book.getName(),DetailActivity.this);
                    DbUtil.updateBookCount(book.getNumber(),book.getCount()-1,this);
                    tvCount.setText("剩余藏书量:"+(book.getCount()-1));
                    onBackPressed();
                }
                break;
            case R.id.btn_give:
                String name = getSharedPreferences("user", MODE_PRIVATE).getString("username", "");
                DbUtil.addNewFavouriteBook(new FavorBean(DbUtil.getCurrentTime(),DbUtil.getUserByName(this,name).getId(),book.getName(),1),this);
                DbUtil.recordOperation("收藏:成功-"+book.getName(),DetailActivity.this);
                DbUtil.updateStar(book.getNumber(),book.getStar_number()+1,this);

                ToastUtil.showShort(this,"已添加收藏！");
                break;
        }
    }
}
