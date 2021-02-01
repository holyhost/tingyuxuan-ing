package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.UpdateBookActivity;

/**
 * Created by Administrator on 2018/4/8.
 */

public class AllBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BookBean> books;


    public AllBookAdapter(Context context, List<BookBean> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final BookBean bookBean = books.get(position);
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).tvName.setText("书籍名："+bookBean.getName());
            ((MyViewHolder) holder).tvSex.setText("价格："+bookBean.getPrice());
            ((MyViewHolder) holder).tvTel.setText("数量："+bookBean.getCount());
            ((MyViewHolder) holder).tvMajor.setText("类型："+bookBean.getType());
            ((MyViewHolder) holder).tvClass.setText("浏览量："+bookBean.getLook_number());
            ((MyViewHolder) holder).tvNumbver.setText("编号："+bookBean.getNumber());
            ((MyViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateBookActivity.class);
                    intent.putExtra("book",bookBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("0------------size"+books.size());
        return books.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvSex;
        TextView tvMajor;
        TextView tvTel;
        TextView tvClass;
        TextView tvNumbver;
        Button btn;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvClass = itemView.findViewById(R.id.tv_class);
            tvMajor = itemView.findViewById(R.id.tv_major);
            tvTel = itemView.findViewById(R.id.tv_tel);
            tvNumbver = itemView.findViewById(R.id.tv_number);
            btn = itemView.findViewById(R.id.btn_bianji);
        }
    }
}
