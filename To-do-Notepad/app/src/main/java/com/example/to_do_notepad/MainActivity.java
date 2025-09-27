package com.example.to_do_notepad;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteDatabase = NoteDatabase.getInstance(this);
        List<Note> notes = noteDatabase.noteDao().getAllNotes();

        noteAdapter = new NoteAdapter(notes);
        recyclerView.setAdapter(noteAdapter);

        // Remove the FAB click listener and intent to NewActivity
        // Find your FAB button if you still need it, but no longer start any new activity
        findViewById(R.id.fab).setOnClickListener(v -> {
            // You can add functionality here, like adding a new note to the list
        });
    }
}
