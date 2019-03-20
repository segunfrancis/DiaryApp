package com.example.computer.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

    // intents name
    public static final String NOTE = "note";
    public static final String CATEGORY = "category";
    public static final String TIME = "time";
    public static final String DATE = "date";

    public static final String EXTRA_REPLY = "com.example.android.diaryapp.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.diaryapp.REPLY_ID";

    private EditText mEditNoteView;
    private Spinner mSpinnerCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mEditNoteView = findViewById(R.id.edit_note);
        mSpinnerCategory = findViewById(R.id.spinner);
        int id = -1;
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String note = extras.getString(MainActivity.EXTRA_DATA_UPDATE_NOTE, "");
            String category = extras.getString(MainActivity.EXTRA_DATA_UPDATE_CATEGORY, "");

            if (!note.isEmpty()) {
                mEditNoteView.setText(note);
                mEditNoteView.setSelection(note.length());
                mEditNoteView.requestFocus();
            }
            ArrayAdapter arrayAdapter = (ArrayAdapter) mSpinnerCategory.getAdapter();
            int spinnerPosition = arrayAdapter.getPosition(category);
            mSpinnerCategory.setSelection(spinnerPosition);
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText().toString().trim())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = mEditNoteView.getText().toString().trim();
                    String category = mSpinnerCategory.getSelectedItem().toString();
                    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    String time = new SimpleDateFormat("h:mm a").format(Calendar.getInstance().getTime());

                    if (extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)) {
                        int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }

                    replyIntent.putExtra(NOTE, note);
                    replyIntent.putExtra(CATEGORY, category);
                    replyIntent.putExtra(DATE, date);
                    replyIntent.putExtra(TIME, time);

                    if (extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)) {
                        int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}