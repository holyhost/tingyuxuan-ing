package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.OperationRecordContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.OperationBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.UserBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;

/**
 * Created by Administrator on 2018/4/7.
 */

public class ReadRecordPresenter implements OperationRecordContract.Presenter {

    Context context;
    OperationRecordContract.View view;
    List<OperationBean> list = new ArrayList<>();


    public ReadRecordPresenter(Context context, OperationRecordContract.View view){
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

        String name = context.getSharedPreferences("user", context.MODE_PRIVATE).getString("username", "");

        list = DbUtil.getAllOperationRecord(context,name);
        view.showResult(list);
        view.stopLoading();
    }

    @Override
    public void reFresh() {
        loadData("",true);
    }
}
