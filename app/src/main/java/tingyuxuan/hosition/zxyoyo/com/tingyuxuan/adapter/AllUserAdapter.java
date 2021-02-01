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
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.UserBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.UpdateUserActivity;

/**
 * Created by Administrator on 2018/4/8.
 */

public class AllUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<UserBean> users;


    public AllUserAdapter(Context context, List<UserBean> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final UserBean userBean = users.get(position);
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).tvName.setText("用户名："+userBean.getName());
            ((MyViewHolder) holder).tvSex.setText("性别："+userBean.getSex());
            ((MyViewHolder) holder).tvTel.setText("用户邮箱："+userBean.getEmail());
            ((MyViewHolder) holder).tvMajor.setText("专业名称："+userBean.getMajor());
            ((MyViewHolder) holder).tvClass.setText("班级信息："+userBean.getClassnum());
            ((MyViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateUserActivity.class);
                    intent.putExtra("user",userBean);
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvSex;
        TextView tvMajor;
        TextView tvTel;
        TextView tvClass;
        Button button;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvClass = itemView.findViewById(R.id.tv_class);
            tvMajor = itemView.findViewById(R.id.tv_major);
            tvTel = itemView.findViewById(R.id.tv_tel);
            button = itemView.findViewById(R.id.btn_bianji);
        }
    }
}
