package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;

/**
 * Created by Administrator on 2018/4/8.
 */

public class BorrowBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BorrowBean> books;


    public BorrowBookAdapter(Context context, List<BorrowBean> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrow_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            final BorrowBean book = books.get(position);
            ((MyViewHolder) holder).tvName.setText("书名："+book.getName());
            ((MyViewHolder) holder).tvDuration.setText("剩余还书时间："+book.getDuration()+"天");
            ((MyViewHolder) holder).tvTime.setText("借书时间："+book.getTime());
            ((MyViewHolder) holder).tvState.setText("状态:"+(book.getState()==0?"未还":"已还"));
            if(book.getState()==0){
                ((MyViewHolder) holder).button.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int result = DbUtil.updateMyBorrow(book.getId(), context);
                        if(result == 1){
                            ToastUtil.showShort(context,"还书成功");
                            ((MyViewHolder) holder).button.setVisibility(View.GONE);
                            ((MyViewHolder) holder).tvState.setText("状态：已还");
                            //书籍的数量加一
                            DbUtil.updateBookCount(book.getName(),context);
                        }else {
                            ToastUtil.showShort(context,"还书失败");
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
        TextView tvDuration;
        TextView tvState;
        TextView tvTime;
        Button button;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvState = itemView.findViewById(R.id.tv_state);
            tvTime = itemView.findViewById(R.id.tv_time);
            button = itemView.findViewById(R.id.btn_back);
        }
    }
}
