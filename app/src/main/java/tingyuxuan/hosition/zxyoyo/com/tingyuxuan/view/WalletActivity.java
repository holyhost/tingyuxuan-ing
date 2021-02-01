package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

public class WalletActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_yue)
    TextView tv_yue;
    @BindView(R.id.et_input_yue)
    EditText et_input_yue;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        float yue = getSharedPreferences("wallet", MODE_PRIVATE).getFloat("yue", 18.0f);
        tv_yue.setText(yue+"");
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle("余额");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_ok)
    public void okClick(){
        float yue = getSharedPreferences("wallet", MODE_PRIVATE).getFloat("yue", 18.0f);
        float inputYue = Float.parseFloat(et_input_yue.getText().toString());
        et_input_yue.requestFocus();
        yue+=inputYue;
        tv_yue.setText(yue+"");
        et_input_yue.setText("");
        et_input_yue.clearFocus();
        getSharedPreferences("wallet",MODE_PRIVATE).edit().putFloat("yue",yue).commit();
        ToastUtil.showShort(this,"充值成功");
    }
}
