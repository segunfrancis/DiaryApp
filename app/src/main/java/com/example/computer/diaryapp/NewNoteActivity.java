package com.example.computer.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "send";

    // intents name
    public static final String NOTE = "note";
    public static final String CATEGORY = "category";
    public static final String TIME = "time";
    public static final String DATE = "date";

    private EditText mEditNoteView;
    private Spinner mSpinnerCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mEditNoteView = findViewById(R.id.edit_note);
        mSpinnerCategory = findViewById(R.id.spinner);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = mEditNoteView.getText().toString();
                    String category = mSpinnerCategory.getSelectedItem().toString();
                    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                    replyIntent.putExtra(NOTE, note);
                    replyIntent.putExtra(CATEGORY, category);
                    replyIntent.putExtra(DATE, date);
                    replyIntent.putExtra(TIME, time);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
