package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.Contract.HomeContract;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbHelper;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.DetailActivity;

import static android.content.Context.MODE_PRIVATE;
import static io.reactivex.Observable.create;

/**
 * Created by Administrator on 2018/4/7.
 */

public class HomePresenter implements HomeContract.Presenter {

    Context context;
    HomeContract.View view;
    List<BookBean> list = new ArrayList<>();


    public HomePresenter(Context context, HomeContract.View view){
        this.context = context;
        this.view = view;
        System.out.println("presenter中设置view.setPresenter(this)");
        view.setPresenter(this);
    }

    @Override
    public void start(boolean clear) {
        loadData("",clear);
    }

    @Override
    public void toDetailActivity(int postion){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("book",list.get(postion));

        context.startActivity(intent);
    }

    @Override
    public void loadData(String url, boolean clearing) {
        view.showLoading();
        if(clearing){
            list.clear();
        }

        DbHelper helper = new DbHelper(context);
        list = helper.getAllBooks();
        if(list.size()<1){
            helper.addDefaultData();
            list = helper.getAllBooks();
        }
        view.showResult(list);
        view.stopLoading();
        context.getSharedPreferences("book",MODE_PRIVATE).edit().putBoolean("update",false).commit();
    }

    @Override
    public void reFresh() {
        loadData("",true);
    }

    @Override
    public List<BookBean> doSearch(String text){
        List<BookBean> allbooks = new ArrayList<>();
        for(BookBean bean:list){
            if(bean.getName().contains(text)){
                allbooks.add(bean);
            }
        }
        return allbooks;
    }

    @Override
    public Observable<String> createTextChangeObservable(final EditText etContent){
        //创建一个observable
        Observable<String> observable = create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                //监听edittext值得变化
                final TextWatcher watch = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("----s:"+s.toString());
                        e.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                //绑定监听
                etContent.addTextChangedListener(watch);
                //移除监听，防止内存泄漏
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        etContent.removeTextChangedListener(watch);
                    }
                });
            }
        });

        //过滤掉长度小于三的字符串，
        //debounce防抖动时间大于1.9s才emitter才发送数据
        return observable.debounce(1000, TimeUnit.MILLISECONDS);
    }
}
