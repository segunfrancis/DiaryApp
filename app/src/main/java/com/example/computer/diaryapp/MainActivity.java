package com.example.computer.diaryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_NOTE = "extra_data_update_note";
    public static final String EXTRA_DATA_UPDATE_CATEGORY = "extra_data_update_category";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    private NoteViewModel mNoteViewModel;

    private Drawable icon;
    private ColorDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        icon = getResources().getDrawable(R.drawable.ic_delete_black_24dp);
        background = new ColorDrawable(getResources().getColor(R.color.colorRed));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Note myNote = adapter.getNoteAtPosition(position);
                // Delete the word
                mNoteViewModel.deleteNote(myNote);

                CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "DELETED", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoDelete(viewHolder, position);
                    }
                });
                snackbar.setActionTextColor(getResources().getColor(R.color.colorYellow)).show();

                View snackbarView = snackbar.getView();

                // styling for background of snackbar
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            private void undoDelete(RecyclerView.ViewHolder viewHolder, int position) {
                position = viewHolder.getAdapterPosition();
                Note myNote = adapter.getNoteAtPosition(position);
                mNoteViewModel.insert(myNote);
            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX > 0) { // Swiping to the right
                    int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    int iconLeft = itemView.getLeft() + iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
                } else if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(),
                            itemView.getRight(), itemView.getBottom());
                } else { // view is unswiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                icon.draw(c);
            }
        });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Note note = adapter.getNoteAtPosition(position);
                launchUpdateNoteActivity(note);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            confirmDelete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmDelete() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_all_dialog);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button delete = dialog.findViewById(R.id.delete);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteViewModel.deleteAllNotes();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NewNoteActivity.NOTE),
                    data.getStringExtra(NewNoteActivity.CATEGORY),
                    data.getStringExtra(NewNoteActivity.DATE),
                    data.getStringExtra(NewNoteActivity.TIME));
            mNoteViewModel.insert(note);
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String note_data = data.getStringExtra(NewNoteActivity.NOTE);
            String note_category = data.getStringExtra(NewNoteActivity.CATEGORY);
            String note_time = data.getStringExtra(NewNoteActivity.TIME);
            String note_date = data.getStringExtra(NewNoteActivity.DATE);
            int id = data.getIntExtra(NewNoteActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                mNoteViewModel.updateNote(new Note(id, note_data, note_category, note_date, note_time));
            } else {
                Toast.makeText(this, R.string.unable_to_update, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUpdateNoteActivity(Note note) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_NOTE, note.getNote());
        intent.putExtra(EXTRA_DATA_UPDATE_CATEGORY, note.getCategory());
        intent.putExtra(EXTRA_DATA_ID, note.getId());
        startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }
}
