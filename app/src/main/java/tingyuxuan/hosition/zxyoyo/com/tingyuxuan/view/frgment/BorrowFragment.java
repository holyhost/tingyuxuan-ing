package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter.BorrowBookAdapter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;

/**
 * Created by Administrator on 2018/4/6.
 */

public class BorrowFragment extends BaseFragment {


    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_list)
    RecyclerView rv;

    BorrowBookAdapter adapter;

    public static BorrowFragment newInstance() {
        return new BorrowFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_borrow;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("我的借阅");
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
        sl.setColorSchemeResources(R.color.colorPrimaryDark,R.color.color_white,R.color.blue);
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            adapter=null;
            initData();
            }
        });
        initData();

    }

    private void initData() {
        showLoading();
        List<BorrowBean> myBorrowBooks = DbUtil.getMyBorrowBooks(getContext());
        adapter = new BorrowBookAdapter(getContext(),myBorrowBooks);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        stopLoading();
    }

    public void showLoading() {
        sl.post(new Runnable() {
            @Override
            public void run() {
                sl.setRefreshing(true);
            }
        });
    }

    public void stopLoading() {
        sl.post(new Runnable() {
            @Override
            public void run() {
                sl.setRefreshing(false);
            }
        });
    }

}
