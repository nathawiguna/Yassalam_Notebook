package com.example.Yassalam_Notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BuatCatatan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_catatan);
    }

    public void buatCatatanBaruTahap2 (View pressedButton) {
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this.getApplicationContext());
        DaoClass daoClass = noteDatabase.userDao();
        EditText judulCatatan = findViewById(R.id.judul);
        EditText isiCatatan = findViewById(R.id.isi);
        String judulCatatanStr = judulCatatan.getText().toString();
        String isiCatatanStr = isiCatatan.getText().toString();
        Date tanggalDibuat = Calendar.getInstance().getTime();
        DateFormat formaterTanggalDibuat= DateFormat.getDateInstance(DateFormat.MEDIUM);
        String tanggalCatatanDibuatDiformat= formaterTanggalDibuat.format(tanggalDibuat);
        Catatan newNote = new Catatan();
        newNote.Judul = judulCatatanStr; newNote.Isi = isiCatatanStr; newNote.TanggalDibuat = tanggalCatatanDibuatDiformat;
        daoClass.InsertOnce(newNote);
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentToMainActivity);
        finish();
    }
}