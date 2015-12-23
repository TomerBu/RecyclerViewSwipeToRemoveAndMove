package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataViewHolder> {

    //private data members:
    private List<Movie> data = Collections.emptyList();

    //ctor:
    public RecyclerAdapter(List<Movie> data) {
        this.data = data;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Movie item = data.get(position);

        Picasso.with(holder.tvtitle.getContext()).
                load(item.getImageURL()).
                into(holder.ivItem);

        holder.tvtitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivItem)
        ImageView ivItem;
        @Bind(R.id.tvtitle)
        TextView tvtitle;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.cardView)
        CardView cardView;


        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
