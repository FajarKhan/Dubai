package com.example.root.dubai.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.dubai.InfoActivity;
import com.example.root.dubai.R;

public class ExploreDubaiAdapter extends RecyclerView.Adapter<ExploreDubaiAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context context;

    public ExploreDubaiAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.explore_dubai_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position == 0) {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
            layoutParams.setMargins(30, 6, 0, 6);
            holder.cvExplore.requestLayout();
        } else if (position == 6) {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
            layoutParams.setMargins(16, 6, 30, 6);
            holder.cvExplore.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cvExplore.getLayoutParams();
            layoutParams.setMargins(16, 6, 0, 6);
            holder.cvExplore.requestLayout();
        }


    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvExplore;
        TextView tvTitleText, tvSubText;
        ImageView ivExplore;
        int position;

        ViewHolder(View itemView) {
            super(itemView);

            cvExplore = (CardView) itemView.findViewById(R.id.cv_explore);
            tvTitleText = (TextView) itemView.findViewById(R.id.tv_explore_text);
            tvSubText = (TextView) itemView.findViewById(R.id.tv_explore_sub_text);
            ivExplore = (ImageView) itemView.findViewById(R.id.iv_explore);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Log.e("pos", position + "");
            Intent intent = new Intent(context, InfoActivity.class);
            context.startActivity(intent);

        }
    }
}
