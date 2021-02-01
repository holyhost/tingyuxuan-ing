package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.R;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.listener.OnlineCardViewListener;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.CommonUtil;

/**
 * ---------------------------------------------
 * Created by small-star-star on 2017/9/8.
 * tel:zx9797997@outlook.com
 * 纸上得来终觉浅
 * ---------------------------------------------
 */

public class TypedBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<BookBean> books;
    private OnlineCardViewListener listener;




    public TypedBookAdapter(Context context, List<BookBean> books) {
        this.context = context;
        this.books = books;
    }

    public void update(List<BookBean> bookBeans){
        this.books = bookBeans;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_online_book, parent, false);
            return new CommenViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommenViewHolder){
            BookBean book = books.get(position);
            Glide.with(context)
                    .load("")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bookcover)
                    .dontAnimate()
                    .into(((CommenViewHolder)holder).mCover);
            ((CommenViewHolder)holder).mTitle.setText(book.getName());
            ((CommenViewHolder)holder).mPlayNumber.setText(CommonUtil.getLoadTime(book.getLook_number()));
            ((CommenViewHolder)holder).mReviewNumber.setText(book.getStar_number()+"");
        }

    }

    public void setOnClickListener(OnlineCardViewListener onClickListener){
        listener = onClickListener;

    }

    @Override
    public int getItemCount() {
        return books.size();
    }





    class CommenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mCover;
        TextView mTitle;
        TextView mPlayNumber;
        TextView mReviewNumber;

        public CommenViewHolder(View itemView) {
            super(itemView);
            mCover = (ImageView) itemView.findViewById(R.id.item_img);
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
            mPlayNumber = (TextView) itemView.findViewById(R.id.item_play);
            mReviewNumber = (TextView) itemView.findViewById(R.id.item_review);
            mCover.setOnClickListener(this);
            mReviewNumber.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            System.out.println("position--"+position);
            if(listener!=null){
                listener.onCoverClickListener(position);
                listener.onReviewClickListener(position);
            }

        }


    }



}
