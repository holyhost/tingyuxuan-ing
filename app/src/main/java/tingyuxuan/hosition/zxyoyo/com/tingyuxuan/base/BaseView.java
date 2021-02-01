package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base;

import android.view.View;

/**
 * ---------------------------------------------
 * Created by small-star-star on 2017/6/5.
 * 纸上得来终觉浅
 * ---------------------------------------------
 */

public interface BaseView<T> {

    void initViews(View view);
    void setPresenter(T presenter);
}
