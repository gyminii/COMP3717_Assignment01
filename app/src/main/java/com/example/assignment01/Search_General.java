package com.example.assignment01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

public class Search_General extends AppCompatActivity {
    private final static String TAG = Search_General.class.getSimpleName();
    public final static String ARTICLE = "com.example.assignment01_article";

    private ListView search_General_ListView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__general);

        search_General_ListView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progressBar);

        MyAsyncTask task = new MyAsyncTask();
        task.setKeyword(getIntent().getStringExtra(MainActivity.KEYWORD));
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private String keyword = "";
        private News_Search_General newsSearchResult;

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler handler = new HttpHandler();
            String url = String.format("https://newsapi.org/v2/everything?q=%s&from=2020-10-06&sortBy=publishedAt&apiKey=f647cf8e71234111ba3b49285c56840a", this.keyword);
            String jsonString = handler.makeServiceCall(url);

            Log.i(TAG, "Response: " + jsonString);

            Gson gson = new Gson();
            newsSearchResult = gson.fromJson(jsonString, News_Search_General.class);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Adapter adapter = new Adapter(Search_General.this, newsSearchResult.getArticles());
            search_General_ListView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

    }
}