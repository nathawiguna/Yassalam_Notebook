package com.example.Yassalam_Notebook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoClass {
    //menampilkan semua entitycllass
    @Query("SELECT * FROM Catatan ORDER BY uid DESC")
    List<Catatan> getAll();

    @Insert
    void InsertOnce(Catatan catatan);

    @Delete
    void delete(Catatan catatan);
}
