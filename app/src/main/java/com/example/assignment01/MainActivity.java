package com.example.assignment01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    public final static String KEYWORD = "com.example.assignment01_keyword";

    Button search_Button;
    EditText search_EditText;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_Button = findViewById(R.id.search_Button);
        search_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        search_Button = findViewById(R.id.search_Button);
        search_EditText = findViewById(R.id.search_EditText);

        String keyword = search_EditText.getText().toString();

        if ((v == search_Button && !keyword.isEmpty())){
            Log.i(TAG, keyword);
            Intent i = new Intent(this, Search_General.class);
            i.putExtra(KEYWORD, keyword);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
        }
    }
}