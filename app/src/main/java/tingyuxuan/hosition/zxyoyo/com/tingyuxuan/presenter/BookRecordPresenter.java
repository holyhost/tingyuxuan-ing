package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.HomeContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.RecordContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.DetailActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/7.
 */

public class BookRecordPresenter implements RecordContract.Presenter {

    Context context;
    RecordContract.View view;
    List<BorrowBean> list = new ArrayList<>();


    public BookRecordPresenter(Context context, RecordContract.View view){
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start(boolean clear) {
        loadData("",clear);
    }

    @Override
    public void toDetailActivity(int postion){

    }

    @Override
    public void loadData(String url, boolean clearing) {
        view.showLoading();
        if(clearing){
            list.clear();
        }


        list = DbUtil.getAllBorrowBooks(context);
        view.showResult(list);
        view.stopLoading();
        context.getSharedPreferences("book",MODE_PRIVATE).edit().putBoolean("update",false).commit();
    }

    @Override
    public void reFresh() {
        loadData("",true);
    }
}
