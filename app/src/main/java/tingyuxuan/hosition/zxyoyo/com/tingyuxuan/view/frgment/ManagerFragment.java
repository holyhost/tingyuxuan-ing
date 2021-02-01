package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter.BookRecordPresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.AddBookActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.AllBookActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.AllUserActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.BorrowRecordActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.DetailActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.OperationRecordActivity;

/**
 * Created by Administrator on 2018/4/6.
 */

public class ManagerFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_delete_number)
    EditText etDelteNumber;


    public static ManagerFragment newInstance() {
        return new ManagerFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_manager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("管理界面");
        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity1 = getActivity();
                if (activity1 instanceof MainActivity) {
                    ((MainActivity) activity1).toggleDrawer();
                }
            }
        });

    }
    @OnClick({R.id.btn_select_one,R.id.btn_manager_add, R.id.btn_manager_delete, R.id.btn_manager_record_borrow, R.id.btn_manager_login, R.id.btn_manager_select_all_user, R.id.btn_manager_select_all_book})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_manager_add:
                startActivity(new Intent(getContext(), AddBookActivity.class));
                break;
//            case R.id.btn_manager_add_ten:
//                DbHelper helper = new DbHelper(getContext(),"tingyuxuan.db",null,1);
//                helper.addBooks();
//
//                break;
            case R.id.btn_manager_delete:
                String s = etDelteNumber.getText().toString();
                int i=0;
                try{
                    i = Integer.parseInt(s);
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtil.showShort(getContext(),"输入有误");
                    etDelteNumber.setText("");
                }

                if(!TextUtils.isEmpty(s)){
                    int result = DbUtil.deleteBookByNumber(i, getContext());
                    if(result==1){
                        ToastUtil.showShort(getContext(),"成功删除");
                        etDelteNumber.setText("");
                    }else {
                        ToastUtil.showShort(getContext(),"删除失败");
                    }
                }
                break;
            case R.id.btn_manager_login:
                startActivity(new Intent(getContext(), OperationRecordActivity.class));
                //用户登录记录
                break;
            case R.id.btn_manager_record_borrow:
                //用户借书记录
                startActivity(new Intent(getContext(), BorrowRecordActivity.class));
                break;
            case R.id.btn_manager_select_all_user:
                startActivity(new Intent(getContext(), AllUserActivity.class));
                break;
            case R.id.btn_manager_select_all_book:
                startActivity(new Intent(getContext(), AllBookActivity.class));
                break;
                //通过id查询书籍
            case R.id.btn_select_one:
                Intent intent = new Intent(getContext(), DetailActivity.class);
                String num = etDelteNumber.getText().toString();
                int in=0;
                try{
                    in = Integer.parseInt(num);
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtil.showShort(getContext(),"输入有误");
                    etDelteNumber.setText("");
                }

                if(!TextUtils.isEmpty(num)){
                    BookBean result = DbUtil.getBookById(in, getContext());
                    if(TextUtils.isEmpty(result.getName())){
                        ToastUtil.showShort(getContext(),"查询失败");
                        etDelteNumber.setText("");
                    }else {
                        intent.putExtra("book",result);
                        startActivity(intent);
                    }
                }

                break;
        }
    }

}
