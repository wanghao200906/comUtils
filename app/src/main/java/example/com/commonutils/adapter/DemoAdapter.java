package example.com.commonutils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.commonutils.R;
import example.com.commonutils.interfaces.OnItemClickListener;

/**
 * Created by wanghao on 2017/2/23.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.MyHolder> {
    private List<String> mDatas;
    private LayoutInflater mInflater;

    public DemoAdapter(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());

        MyHolder holder = new MyHolder(mInflater.inflate(
                R.layout.demo_item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.tv.setText(mDatas.get(position));

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        System.out.println("size:"+mDatas.size() );
        return mDatas != null ? mDatas.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_demo_item);

        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
}
