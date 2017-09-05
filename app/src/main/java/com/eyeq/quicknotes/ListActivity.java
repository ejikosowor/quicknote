package com.eyeq.quicknotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        mTextview = (TextView) findViewById(R.id.note_text);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mToolbar.setTitle(bundle.getString("titleNote"));
            mTextview.setText(bundle.getString("bodyNote"));
        }
    }
}
