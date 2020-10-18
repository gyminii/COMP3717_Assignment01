package com.example.assignment01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Article> {
    Context context;
    public Adapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) context;
        // Get the data item for this position
        final Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_list, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.article_title);
        if (article.getTitle() != null) {
            textView.setText(article.getTitle());
        } else {
            textView.setText("Title");
        }
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Search_Detail.class);
                Gson gson = new Gson();
                String articleInStr = gson.toJson(article, Article.class);
                intent.putExtra(Search_General.ARTICLE, articleInStr);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
