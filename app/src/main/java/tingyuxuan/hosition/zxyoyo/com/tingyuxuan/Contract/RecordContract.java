package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BasePresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;

/**
 * Created by Administrator on 2018/4/7.
 */

public interface RecordContract {

    interface View extends BaseView<Presenter> {
        void showResult(List<BorrowBean> moods);
        void showLoading();
        void stopLoading();

    }


    interface Presenter extends BasePresenter {
        void loadData(String url, boolean clearing);
        void reFresh();
        void toDetailActivity(int postion);
    }
}
