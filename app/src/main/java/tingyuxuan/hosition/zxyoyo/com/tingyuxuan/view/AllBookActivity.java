package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.os.Bundle;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.AllBookFragment;

public class AllBookActivity extends BaseActivity  {



    @Override
    public int getLayoutId() {
        return R.layout.activity_all_book;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        AllBookFragment allBookFragment = AllBookFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, allBookFragment)
                .show(allBookFragment).commit();
    }

    @Override
    public void initToolBar() {

    }


}
