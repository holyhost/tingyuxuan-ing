package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.LogUtil;

/**
 * ---------------------------------------------
 * Created by small-star-star on 2017/10/18.
 * tel:zx9797997@outlook.com
 * 纸上得来终觉浅
 * fragment基础类
 * ---------------------------------------------
 */

public abstract class BaseFragment extends Fragment {
    protected View parentView;
    //初始化完成
    protected boolean isPrepared;
    //fragment可见
    protected boolean isVisiable;

    private Unbinder bind;

    public abstract
    @LayoutRes
     int getLayoutResId();

    private FragmentActivity activity;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getSupportActivity();
        parentView = inflater.inflate(getLayoutResId(),container,false);
        LogUtil.e("fragmentlife","onCreateView");
        return parentView;
    }


    public FragmentActivity getSupportActivity(){
        return super.getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this,view);
        finishCreateView(savedInstanceState);
        LogUtil.e("fragmentlife","onViewCreated");
    }

    /**
     * 初始化view
     * @param state
     */
    public abstract void finishCreateView(Bundle state);


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("fragmentlife","onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        LogUtil.e("fragmentlife","onDestroy");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        LogUtil.e("fragmentlife","onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
        LogUtil.e("fragmentlife","onDetach");
    }

    /**
     * 数据懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.e("fragmentlife","setUserVisibleHint-"+isVisibleToUser);
        if(getUserVisibleHint()){
            isVisiable = true;
            onVisiable();
        }else {
            isVisiable  = false;
            onInvisiable();
        }
    }

    /**
     * fragment不可见
     */
    protected  void onInvisiable(){

    }

    /**
     * fragment 可见
     */
    protected  void onVisiable(){
        loadData();
    }

    /**
     * 加载数据
     */
    protected  void loadData(){

    }

    /**
     * 初始化recyclerView
     */
    protected void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    protected void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    protected void finishTask() {
    }


}
