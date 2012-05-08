package com.github.super8.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.ignition.core.widgets.RemoteImageView;
import com.github.super8.R;
import com.github.super8.model.Person;
import com.github.super8.support.Fonts;

public class PersonListAdapter extends ArrayAdapter<Person> {

  private Typeface elementFont;
  
  public PersonListAdapter(Context context) {
    super(context, R.layout.person_list_item, android.R.id.text1);
    elementFont = Fonts.loadFont(context, "Roboto-Light.ttf");
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View itemView = super.getView(position, convertView, parent);

    Person person = getItem(position);
    TextView nameText = (TextView) itemView.findViewById(android.R.id.text1);
    nameText.setText(person.getName());
    nameText.setTypeface(elementFont);

    RemoteImageView imageView = (RemoteImageView) itemView.findViewById(R.id.person_image);

    String imageUrl = person.getScaledImageUrl(getContext());
    if (imageUrl != null) {
      imageView.setImageUrl(imageUrl);
      imageView.loadImage();
    } else {
      imageView.setImageDrawable(null);
    }

    return itemView;
  }
}
