package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view;

import android.os.Bundle;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.BorrowRecordFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.OperationRecordFragment;

public class OperationRecordActivity extends BaseActivity  {



    @Override
    public int getLayoutId() {
        return R.layout.activity_all_book;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        OperationRecordFragment allBookFragment = OperationRecordFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, allBookFragment)
                .show(allBookFragment).commit();
    }

    @Override
    public void initToolBar() {

    }


}
