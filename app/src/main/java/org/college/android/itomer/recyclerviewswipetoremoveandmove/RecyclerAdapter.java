package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        pallete(holder);
    }

    void pallete(final DataViewHolder holder) {
        Context context = holder.tvtitle.getContext();
        //TODO: Call this method instead of loading the image to the imageView in the viewHolder.
        //When the Image is loaded into the target -> take it's Muted color and use it as a background
        //Test how to use the target with maximum efficiency so we don't waste memory
        //It looks like we will share a reference so it's not bad. only that we need to verify that the
        //ImageView will not hold a reference to the Bitmap once it's not in use.
//        Picasso.with(context).load("").into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//            }
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
        Bitmap myBitmap = BitmapFactory.decodeResource(
                context.getResources(), R.mipmap.ic_launcher
        );
        if (myBitmap != null && !myBitmap.isRecycled()) {
            AsyncTask<Bitmap, Void, Palette> palette =
                    Palette.from(myBitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int bgColor = palette.getLightMutedColor(0x000/*default*/);
                    holder.cardView.setBackgroundColor(bgColor);
                    /* Vibrant, Vibrant Dark, Vibrant Light,
                     Muted, Muted Dark, Muted Light */
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void moveItem(int fromPos, int toPos) {
        Movie from = data.get(fromPos);
        Movie to = data.set(toPos, from);
        data.set(fromPos, to);
        notifyItemMoved(fromPos, toPos);
    }

    public void removeItem(int position) {
        if (position < 0) return;

        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItems(List<Movie> movies) {
        if (data == null) data = new ArrayList<>();
        data.addAll(0, movies);
        notifyItemRangeInserted(0, movies.size());
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
