package com.example.to_do_notepad;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "notes_table") // Ensuring table name consistency
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description; // Keeping "description" instead of "content"
    private long reminderTime; // Timestamp for reminders in milliseconds

    // Constructor
    public Note(String title, String description, long reminderTime) {
        this.title = title;
        this.description = description;
        this.reminderTime = reminderTime;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getReminderTime() { return reminderTime; }
    public void setReminderTime(long reminderTime) { this.reminderTime = reminderTime; }
}
