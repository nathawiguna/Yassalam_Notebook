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

    //mencari note melalui id
    @Query("SELECT * FROM Catatan WHERE uid IN (:EntityClassIds)")
    List<Catatan> loadAllByIds(int[] EntityClassIds);

    //mencari note melalui judulnya
    @Query("SELECT * FROM Catatan WHERE Judul LIKE :Judul LIMIT 1")
    Catatan findByJudul(String Judul);

    @Insert
    void insertAll(Catatan... catatan);

    @Insert
    void InsertOnce(Catatan catatan);

    @Delete
    void delete(Catatan catatan);

}
