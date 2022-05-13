package com.example.Yassalam_Notebook;

import static java.text.DateFormat.MEDIUM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BuatCatatan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mengset tampilan halaman pembuatan catatan menjadi sesuai dengan file xml activity_buat_catatan
        setContentView(R.layout.activity_buat_catatan);
    }

    public void buatCatatanBaruTahap2 (View pressedButton) throws ParseException {
        //mengakses database
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this.getApplicationContext());
        //mengakses DAO (Data access object) yaitu objek yang kita gunakan untuk berinteraksi dengan
        //database
        DaoClass daoClass = noteDatabase.userDao();
        //menggunakan findViewById objek objek untuk mengakses teks di halaman pembuatan catatan
        EditText judulCatatan = findViewById(R.id.judul);
        EditText isiCatatan = findViewById(R.id.isi);
        //mengakses judul catatan yang sudah di ketik oleh user lalu memasukannya di variabel judulCatatanStr
        String judulCatatanStr = judulCatatan.getText().toString();
        //mengakses isi catatan yang sudah diketik oleh user lalu memasukannya di variabel isiCatatanStr
        String isiCatatanStr = isiCatatan.getText().toString();
        //membuat objek untuk mengakses tanggal hari ini
        Date tanggalDibuat = Calendar.getInstance().getTime();
        //mengakses objek dateFormat untuk memformat tanggal agar berbentuk string
        //lalu mengset format yang dipakai objek dateFormat menjadi medium (salah satu format bawaaan aplikasi)
        DateFormat formaterTanggalDibuat= DateFormat.getDateInstance(MEDIUM);
        //memformat tanggal ini hinggal menjadi string atau teks
        String tanggalCatatanDibuatDiformat= formaterTanggalDibuat.format(tanggalDibuat);
        //membuat objek catatan untuk disimpan ke database
        Catatan newNote = new Catatan();
        //memasukan judul catatan dan isi catatan yang di ketik oleh user kedalam objek catatan yang telah dibuat
        //memasukan tanggal hari ini yang sudah berbentuk teks atau string kedalam objek catatan yang telah dibuat
        newNote.Judul = judulCatatanStr; newNote.Isi = isiCatatanStr; newNote.TanggalDibuat = tanggalCatatanDibuatDiformat;
        //memasukan objek catatan yang telah dibuat kedalam database
        daoClass.InsertOnce(newNote);
        //membuat intent untuk berpindah halaman menuju halaman utama aplikasi
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //menjalankan fungsi startActivity untuk berpindah halaman menuju ke halaman utama aplikasi
        startActivity(intentToMainActivity);
        //menutup halaman pembuatan catatan
        finish();
    }
}