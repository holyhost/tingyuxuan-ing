package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.HomeContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.MainActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter.TypedBookAdapter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.listener.OnlineCardViewListener;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter.HomePresenter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.LogUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.DetailActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.design.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/5.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_user_avatar)
    CircleImageView mCircleImageView;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.ib_search)
    ImageButton ibSearch;
    @BindView(R.id.et_search_words)
    EditText etSearch;
    boolean isOpen=false;

    List<BookBean> allBooks;
    List<BookBean> resultBooks;
    HomeContract.Presenter presenter ;

    public void reFreshData(){
//       presenter.reFresh();

    }


    TypedBookAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void finishCreateView(Bundle state) {
        presenter = new HomePresenter(getContext(),this);
        initViews(null);

        presenter.start(true);
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mCircleImageView.setImageResource(R.drawable.ic_33);
    }

    @OnClick(R.id.navigation_layout)
    void toggleDrawer(){
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).toggleDrawer();
        }
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        initToolbar();
        sl.setColorSchemeResources(R.color.colorPrimaryDark,R.color.color_white,R.color.blue);
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = null;
                presenter.reFresh();

            }
        });
        allBooks = new ArrayList<>();
        resultBooks = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    showSearch();

                }else {
                    showCancle();
                }
                isOpen=!isOpen;
            }
        });

        Observable<String> textChangeObservable = presenter.createTextChangeObservable(etSearch);
        textChangeObservable.observeOn(Schedulers.io())
                .map(new Function<String, List<BookBean>>() {
                    @Override
                    public List<BookBean> apply(String s) throws Exception {

                        return presenter.doSearch(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookBean>>() {
                    @Override
                    public void accept(List<BookBean> bookBeans) throws Exception {
                        showResult(bookBeans);
                    }
                });
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        if(presenter!=null){
            this.presenter = presenter;
        }
    }

    @Override
    public void showResult(List<BookBean> books) {
        if(adapter!=null){
            LogUtil.e("books"+books.size());
            adapter.update(books);

        }else {
            adapter = new TypedBookAdapter(getContext(),books);
            adapter.setOnClickListener(new OnlineCardViewListener() {
                @Override
                public void onCoverClickListener(int position) {

                    presenter.toDetailActivity(position);
                }

                @Override
                public void onReviewClickListener(int position) {

                }

            });
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setAdapter(adapter);
                }
            });
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


    @Override
    public void onResume() {
        super.onResume();
        adapter = null;
        showSearch();
        presenter.reFresh();
        DbHelper helper = new DbHelper(getContext());
        allBooks = helper.getAllBooks();
    }

    private void showSearch(){
        etSearch.setText("");
        ibSearch.setBackground(getContext().getResources().getDrawable(R.drawable.ic_search_black_24dp));
        etSearch.setFocusable(false);
        etSearch.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
    private void showCancle(){
        etSearch.setVisibility(View.VISIBLE);
        ibSearch.setBackground(getContext().getResources().getDrawable(R.drawable.ic_cancle));
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);

        etSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

}
