package com.DYI.android.weather.feature.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baronzhang.android.weather.base.BaseRecyclerViewAdapter;
import com.baronzhang.android.weather.R;
import com.baronzhang.android.weather.data.db.entities.minimalist.LifeIndex;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baronzhang.android.weather.R.drawable.ic_index_sunscreen;


public class LifeIndexAdapter extends BaseRecyclerViewAdapter<LifeIndexAdapter.ViewHolder> {

    private Context context;
    private List<LifeIndex> indexList;

    public LifeIndexAdapter(Context context, List<LifeIndex> indexList) {
        this.context = context;
        this.indexList = indexList;
    }

    @Override
    public LifeIndexAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_life_index, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(LifeIndexAdapter.ViewHolder holder, int position) {
        LifeIndex index = indexList.get(position);
        holder.indexIconImageView.setImageDrawable(getIndexDrawable(context, index.getName()));
        holder.indexLevelTextView.setText(index.getIndex());
        holder.indexNameTextView.setText(index.getName());
    }

    @Override
    public int getItemCount() {
        return indexList == null ? 0 : indexList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.index_icon_image_view)
        ImageView indexIconImageView;
        @BindView(R.id.index_level_text_view)
        TextView indexLevelTextView;
        @BindView(R.id.index_name_text_view)
        TextView indexNameTextView;

        ViewHolder(View itemView, LifeIndexAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> adapter.onItemHolderClick(LifeIndexAdapter.ViewHolder.this));
        }
    }

    private Drawable getIndexDrawable(Context context, String indexName) {


        int colorResourceId = ic_index_sunscreen;
        if (indexName.contains("??????")) {
            colorResourceId = ic_index_sunscreen;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_dress;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_sport;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_shopping;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_sun_cure;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_car_wash;
        } else if (indexName.contains("??????")) {
            colorResourceId = R.drawable.ic_index_clod;
        } else if (indexName.contains("?????????")) {
            colorResourceId = R.drawable.ic_index_dance;
        }
        return context.getResources().getDrawable(colorResourceId);
    }
}
