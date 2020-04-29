package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;
import com.byted.camp.todolist.operation.activity.DatabaseActivity;
import com.byted.camp.todolist.operation.activity.DebugActivity;
import com.byted.camp.todolist.operation.activity.SettingActivity;
import com.byted.camp.todolist.ui.NoteListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private TodoDbHelper todoDbHelper;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoDbHelper = new TodoDbHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(
//                        new Intent(MainActivity.this, NoteActivity.class),
//                        REQUEST_CODE_ADD);
//            }
//        });
        fab.setOnClickListener(view -> startActivityForResult(
                new Intent(MainActivity.this, NoteActivity.class),
                REQUEST_CODE_ADD));

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);
        noteList = loadNotesFromDatabase();
        notesAdapter.refresh(noteList);

        //notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            //notesAdapter.refresh(loadNotesFromDatabase());
            noteList = loadNotesFromDatabase();
            notesAdapter.refresh(noteList);
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans
        SQLiteDatabase db = todoDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TodoContract.TodoEntry.TABLE_NAME, null);
        ArrayList<Note> noteList = new ArrayList<>();
        while (cursor.moveToNext()){
            Note note = new Note(cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_ID)));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            int time = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_DATE));
            note.setDate(new Date(time));
            note.setContent(cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_CONTENT)));
            note.setState(State.values()[cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_STATE))]);
            note.setPriority(Note.Priority.values()[cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_PRIORITY))]);
            noteList.add(note);
        }
        return noteList;
    }

    private void deleteNote(Note note) {
        // TODO 删除数据
        SQLiteDatabase db =todoDbHelper.getWritableDatabase();
        db.delete(TodoContract.TodoEntry.TABLE_NAME, "id=?", new String[]{String.valueOf(note.id)});
        noteList.remove(note);
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        notesAdapter.refresh(noteList);

    }

    private void updateNode(Note note) {
        // TODO 更新数据
        SQLiteDatabase db = todoDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_NAME_STATE, note.getState().ordinal());
        db.update(TodoContract.TodoEntry.TABLE_NAME, values, "id=?",new String[]{String.valueOf(note.id)});
        notesAdapter.refresh(noteList);
    }

}
