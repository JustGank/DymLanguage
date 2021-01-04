package com.xjl.dymdemo.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xjl.dymdemo.demo.R;

import java.util.List;

public class Display2Adapter extends RecyclerView.Adapter<Display2Adapter.Display2ViewHolder> {

    private List<String> list;
    private Context context;

    public Display2Adapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Display2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Display2ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_display2, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Display2ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.setPosition(position);
    }


    public void setList(List<String> strings) {
        this.list.clear();
        this.list.addAll(strings);
        notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View v, int position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Display2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        int position;

        public Display2ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.content);
            textView.setOnClickListener(this);
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.content) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        }
    }
}
