package com.github.super8.adapters;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ignition.core.adapters.EndlessListAdapter;
import com.github.super8.R;
import com.github.super8.R.id;
import com.github.super8.R.layout;
import com.github.super8.model.Movie;
import com.github.super8.support.Fonts;

public class MovieListAdapter extends EndlessListAdapter<Movie> {

    private Context context;
    private LayoutInflater layoutInflater;

    private Typeface itemFont;

    public MovieListAdapter(ListActivity context) {
        super(context, R.layout.list_progress_item);
        this.context = context.getApplicationContext();
        this.layoutInflater = context.getLayoutInflater();
        this.itemFont = Fonts.loadFont(context, "adler.ttf");
    }

    @Override
    protected View doGetView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movie_list_item, getListView(), false);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.movie_list_item_title);
            //holder.yearView = (TextView) convertView.findViewById(R.id.movie_list_item_year);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        //holder.titleView.setText("(" + movie.getYear() + ") " + movie.getTitle());
        holder.titleView.setTypeface(itemFont);
        //holder.yearView.setText(context.getString(R.string.movie_item_year, movie.getYear()));

        return convertView;
    }

    private static final class ViewHolder {
        private TextView titleView;
        private TextView yearView;
    }
}
