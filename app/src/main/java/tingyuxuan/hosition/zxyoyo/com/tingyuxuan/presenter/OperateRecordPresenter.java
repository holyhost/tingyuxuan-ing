package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.OperationRecordContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.RecordContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.OperationBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/7.
 */

public class OperateRecordPresenter implements OperationRecordContract.Presenter {

    Context context;
    OperationRecordContract.View view;
    List<OperationBean> list = new ArrayList<>();


    public OperateRecordPresenter(Context context, OperationRecordContract.View view){
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


        list = DbUtil.getAllOperationRecord(context);
        view.showResult(list);
        view.stopLoading();
    }

    @Override
    public void reFresh() {
        loadData("",true);
    }
}
