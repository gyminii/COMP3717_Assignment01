package com.example.assignment01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousByteChannel;

public class Search_Detail extends AppCompatActivity {
    private static final String TAG = Search_Detail.class.getSimpleName();
    private Article article;
    private ImageView articleImage;
    private ProgressBar imageProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__detail);
        String articleString = getIntent().getStringExtra(Search_General.ARTICLE);

        Gson gson = new Gson();

        article = gson.fromJson(articleString, Article.class);
        Log.i(TAG, articleString);

        imageProgressBar = findViewById(R.id.imageProgressBar);
        articleImage = findViewById(R.id.article_Image);
        TextView articleTitle = findViewById(R.id.article_title);
        TextView articleAuthor = findViewById(R.id.article_author);
        TextView articleBody = findViewById(R.id.article_body);
        TextView articleDescription = findViewById(R.id.article_description);
        TextView articlePublishedDate = findViewById(R.id.article_publish_date);
        TextView articleSource = findViewById(R.id.article_source);
        TextView articleSourceURL = findViewById(R.id.source_url);

        articleTitle.setText(article.getTitle());
        articleAuthor.setText(article.getAuthor());
        articleBody.setText(article.getContent());
        articleDescription.setText(article.getDescription());
        articlePublishedDate.setText(article.getPublishedAt());
        articleSource.setText(article.getSource().getName());
        articleSourceURL.setText(article.getUrl());

        Log.i(TAG, article.getSource().getName());

        getImage getImages = new getImage();
        getImages.execute();

    }
    private class getImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageProgressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Bitmap temp) {
            super.onPostExecute(temp);
            if (temp != null && articleImage != null) {
                Log.i(TAG, articleImage.toString());
                articleImage.setImageBitmap(temp);
            } else {
                Log.i(TAG, "Image not found.");
            }
            imageProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return downloadBitmap(article.getUrlToImage());
        }

        private Bitmap downloadBitmap(String url) {
            int statusCode;
            InputStream inputStream;
            HttpURLConnection urlConnection = null;
            try {
                URL urlString = new URL(url);
                urlConnection = (HttpURLConnection) urlString.openConnection();
                statusCode = urlConnection.getResponseCode();

                if (statusCode !=  HttpURLConnection.HTTP_OK) {
                    return null;
                }

                inputStream = urlConnection.getInputStream();

                if (inputStream != null) {
                    return BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                Log.w("getImage", "Error Image cannot be downloaded " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}