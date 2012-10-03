package com.github.super8.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ignition.support.images.remote.RemoteImageLoader;
import com.github.super8.R;
import com.github.super8.Super8Application;
import com.github.super8.model.Person;
import com.github.super8.support.Fonts;

public class PersonListAdapter extends ArrayAdapter<Person> {

  private Typeface elementFont;
  private RemoteImageLoader imageLoader;

  public PersonListAdapter(Context context) {
    super(context, R.layout.person_list_item, android.R.id.text1);
    elementFont = Fonts.loadFont(context, "Roboto-Light.ttf");
    imageLoader = ((Super8Application) context.getApplicationContext()).getImageLoader();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View itemView = super.getView(position, convertView, parent);

    ViewHolder holder = (ViewHolder) itemView.getTag();
    if (holder == null) {
      holder = new ViewHolder();
      holder.nameText = (TextView) itemView.findViewById(android.R.id.text1);
      holder.nameText.setTypeface(elementFont);
      holder.imageView = (ImageView) itemView.findViewById(R.id.person_image);
      itemView.setTag(holder);
    }

    Person person = getItem(position);

    holder.nameText.setText(person.getName());
    holder.imageView.setImageDrawable(null);

    String imageUrl = person.getScaledImageUrl(getContext());
    if (imageUrl != null) {
      imageLoader.loadImage(imageUrl, holder.imageView);
    }

    return itemView;
  }

  static class ViewHolder {
    TextView nameText;
    ImageView imageView;
  }
}
