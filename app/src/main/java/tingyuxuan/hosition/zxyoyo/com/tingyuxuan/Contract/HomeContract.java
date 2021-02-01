package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract;

import android.widget.EditText;

import java.util.List;

import io.reactivex.Observable;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BasePresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;

/**
 * Created by Administrator on 2018/4/7.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void showResult(List<BookBean> moods);
        void showLoading();
        void stopLoading();

    }


    interface Presenter extends BasePresenter {
        void loadData(String url, boolean clearing);
        void reFresh();
        void toDetailActivity(int postion);

        List<BookBean> doSearch(String text);
         Observable<String> createTextChangeObservable(final EditText etContent);
    }
}
