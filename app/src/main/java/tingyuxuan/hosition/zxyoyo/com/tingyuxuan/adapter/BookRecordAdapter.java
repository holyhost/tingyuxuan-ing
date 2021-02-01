package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;

/**
 * Created by Administrator on 2018/4/8.
 */

public class BookRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BorrowBean> books;


    public BookRecordAdapter(Context context, List<BorrowBean> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
             BorrowBean book = books.get(position);
            ((MyViewHolder) holder).tvName.setText("书名："+book.getName());
            ((MyViewHolder) holder).tvDuration.setText("剩余还书时间："+book.getDuration()+"天");
            ((MyViewHolder) holder).tvTime.setText("借书时间："+book.getTime());
            ((MyViewHolder) holder).tvState.setText("状态:"+(book.getState()==0?"未还":"已还"));
            ((MyViewHolder) holder).tvUser.setText("借书人："+ DbUtil.getUserName(context,book.getUserid()));

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
        TextView tvUser;



        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvState = itemView.findViewById(R.id.tv_state);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvUser = itemView.findViewById(R.id.tv_user);
        }
    }
}
