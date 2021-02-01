package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.OperationRecordContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter.OperationRecordAdapter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.OperationBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter.OperateRecordPresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter.ReadRecordPresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.LogUtil;

/**
 * Created by Administrator on 2018/4/5.
 */

public class ReadRecordFragment extends BaseFragment implements OperationRecordContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;

    List<BookBean> allBooks;
    OperationRecordContract.Presenter presenter ;

    OperationRecordAdapter adapter;
    private LinearLayoutManager layoutManager;

    public static ReadRecordFragment newInstance() {
        return new ReadRecordFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_all_book;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initViews(null);
        presenter = new ReadRecordPresenter(getContext(),this);
        presenter.start(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        mToolbar.setTitle("阅读记录");
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


    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        initToolbar();
        sl.setColorSchemeResources(R.color.colorPrimaryDark,R.color.color_white,R.color.blue);
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopLoading();


            }
        });
        allBooks = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void setPresenter(OperationRecordContract.Presenter presenter) {
        if(presenter!=null){
            this.presenter = presenter;
        }
    }

    @Override
    public void showResult(List<OperationBean> books) {
        if(adapter!=null){
            LogUtil.e("books"+books.size());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(adapter);
                }
            });

        }else {
            adapter = new OperationRecordAdapter(getContext(),books);
        }
    }

    @Override
    public void showLoading() {
        sl.post(new Runnable() {
            @Override
            public void run() {
                sl.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        sl.post(new Runnable() {
            @Override
            public void run() {
                sl.setRefreshing(false);
            }
        });
    }


}
