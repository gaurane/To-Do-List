package com.example.to_do_notepad;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private Button btnSave, btnSetReminder;
    private long reminderTime = 0; // Store reminder time in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.btnSave);
        btnSetReminder = findViewById(R.id.btnSetReminder);

        btnSetReminder.setOnClickListener(v -> pickReminderTime());
        btnSave.setOnClickListener(v -> saveNote());
    }

    private void pickReminderTime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                reminderTime = calendar.getTimeInMillis();
                Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty()) {
            Note note = new Note(title, description, reminderTime);
            long noteId = NoteDatabase.getInstance(this).noteDao().insert(note); // Get inserted note ID

            if (reminderTime > 0) {
                scheduleReminder(noteId, title, description, reminderTime);
            }

            finish(); // Close activity after saving
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleReminder(long noteId, String title, String description, long time) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, (int) noteId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // Fix for Android 12+

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }
}
