package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.FavorBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FavorBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FavorBean> books;


    public FavorBookAdapter(Context context, List<FavorBean> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favor_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            final FavorBean book = books.get(position);
            ((MyViewHolder) holder).tvName.setText("书名："+book.getBookname());
            ((MyViewHolder) holder).tvTime.setText("收藏时间："+book.getTime());
            if(book.getState()==1){
                ((MyViewHolder) holder).button.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        book.setState(0);
                        int result = DbUtil.updateFavouriteBook(book, context);
                        if(result == 1){
                            ToastUtil.showShort(context,"取消收藏成功");
                            ((MyViewHolder) holder).button.setVisibility(View.GONE);

                        }else {
                            ToastUtil.showShort(context,"取消收藏失败");
                        }

                    }
                });

            }else {
                ((MyViewHolder) holder).button.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvTime;
        Button button;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            button = itemView.findViewById(R.id.btn_back);
        }
    }
}
