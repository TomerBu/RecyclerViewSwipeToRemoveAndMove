package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        loadImage(holder, item);
        holder.tvtitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
    }

    void loadImage(final DataViewHolder holder, Movie item) {
        Context context = holder.tvtitle.getContext();
        if (item.getImageURL() == null || item.getImageURL().isEmpty()) {
            item.setImageURL("None");
        }
        Picasso.with(context).load(item.getImageURL()).
                error(R.drawable.ic_broken_image_150dp).
                placeholder(R.drawable.ic_placeholder).
                into(holder);
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
        Movie item = data.get(position);
        data.remove(item);

        //Only remove bad items from parse. don't remove the demo ones.
        if (!item.getImageURL().startsWith("http")) {
            ParseObject o = ParseObject.createWithoutData(AddMovieActivity.MOVIE, item.getObjectID());
            o.deleteEventually(new DeleteCallback() {
                @Override
                public void done(ParseException e) {
                    System.out.println(e == null ? "Removed" : e.getLocalizedMessage());
                }
            });
        }
        notifyItemRemoved(position);
    }

    public void addItems(List<Movie> movies) {
        if (data == null) data = new ArrayList<>();
        data.addAll(0, movies);
        notifyItemRangeInserted(0, movies.size());
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements Target {
        @Bind(R.id.ivItem)
        ImageView ivItem;
        @Bind(R.id.tvtitle)
        TextView tvtitle;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.cardView)
        CardView cardView;
        @Bind(R.id.lineSep)
        View lineSep;

        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivItem.setImageBitmap(bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    lineSep.setBackgroundColor(palette.getVibrantColor(0x000/*default*/));
                    /* Vibrant, Vibrant Dark, Vibrant Light,
                     Muted, Muted Dark, Muted Light */
                }
            });
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ivItem.setImageDrawable(errorDrawable);
            lineSep.setBackgroundColor(0x000);
        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            ivItem.setImageDrawable(placeHolderDrawable);
            lineSep.setBackgroundColor(0x000);
        }
    }

}
