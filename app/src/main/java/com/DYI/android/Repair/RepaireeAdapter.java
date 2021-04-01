package com.DYI.android.Repair;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.baronzhang.android.weather.R;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepaireeAdapter extends RecyclerView.Adapter<RepaireeAdapter.ViewHolder>{

private static final String TAG="FruitAdapter";

private Context mContext;

private List<Repairee> repaireesList;

static class ViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    ImageView repaireeImage;
    TextView repaireeName;
    TextView repaireeSkill;

    public ViewHolder(View view) {
        super(view);
        cardView = (CardView) view;
        repaireeImage = (ImageView) view.findViewById(R.id.ic_image);
        repaireeName = (TextView) view.findViewById(R.id.ic_name);
        repaireeSkill=(TextView) view.findViewById(R.id.ic_skill);
    }

}

    public RepaireeAdapter(List<Repairee> mrepaireesList) {
        repaireesList = mrepaireesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.repairee_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Repairee repairee = repaireesList.get(position);
                Intent intent = new Intent(mContext, RepaireeRequsetFormRead.class);
                intent.putExtra(repairee_actitvity.repairee_NAME, repairee.getName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @NonNull


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repairee repairee = repaireesList.get(position);
        holder.repaireeName.setText(repairee.getName());
        holder.repaireeSkill.setText(repairee.getSkill());
        Glide.with(mContext).load(repairee.getImageId()).into(holder.repaireeImage);
    }

    @Override
    public int getItemCount() {
        return repaireesList.size();
    }
}
