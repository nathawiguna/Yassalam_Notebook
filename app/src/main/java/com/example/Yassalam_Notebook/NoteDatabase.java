package com.example.Yassalam_Notebook;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Catatan.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract DaoClass userDao();
    private static NoteDatabase noteDatabaseObj;
    public static synchronized NoteDatabase getDaoClass(Context context) {
        if (noteDatabaseObj == null) {
            noteDatabaseObj = Room.databaseBuilder(context, NoteDatabase.class,
                    "DatabaseNotebook").allowMainThreadQueries().build();
            return noteDatabaseObj;
        } else {
            return noteDatabaseObj;
        }
    }
}
