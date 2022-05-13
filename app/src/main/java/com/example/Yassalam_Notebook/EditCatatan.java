package com.example.Yassalam_Notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditCatatan extends AppCompatActivity {
    //membuat objek catatan baru
    //lalu disimpan di varaibel catatan
    Catatan catatan = new Catatan();
    //membuat variabel yang digunakan untuk mengambil Judul yang dimasukan user di halaman pengeditan catatan
    EditText editTextJudulFromEditCatatanAct;
    //membuat variabel yang digunakan untuk mengambil Isi Catatan yang dimasukan di halaman pengeditan catatan
    EditText editTextIsiFromEditCatatanAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mengset tampilan halaman pengeditan catatan menjadi sesuai dengan file xml activity_edit_catatan
        setContentView(R.layout.activity_edit_catatan);
        //mendapat objek parceabel yang berisi data - data catatan yang ingin diubah
        CatatanClassParceable catatanClassParceable =  getIntent().getParcelableExtra("Data_Untuk_Activity_Edit_Catatan");
        //memindahkan data - data catatan dari objek parceabel menuju ke objek catatan baru
        catatan = catatan.moveDataFromParceableToCatatanObject(catatanClassParceable);
        //memasukan variabel editTextJudulFromEditCatatanAct dengan objek yang dapat memodifikasi teks judul catatan pada halaman pengeditan catatan
        editTextJudulFromEditCatatanAct = findViewById(R.id.judul_edit_catatan);
        //memasukan variabel editTextIsiFromEditCatatanAct dengan objek yang dapat memodifikasi teks isi catatan pada halaman pengeditan catatan
        editTextIsiFromEditCatatanAct = findViewById(R.id.isi_edit_catatan);
        //mengubah teks judul catatan pada halaman pengeditan teks menjadi sesuai dengan judul catatan yang ingin diubah
        editTextJudulFromEditCatatanAct.setText(catatan.Judul);
        //mengubah teks isi catatan pada halaman pengeditan teks menjadi sesuai dengan isi catatan yang ingin diubah
        editTextIsiFromEditCatatanAct.setText(catatan.Isi);
    }

    public void editCatatan(View finishButton) {
        //membuat objek NoteDatabase
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this.getApplicationContext());
        //mendapatkan objek DAO dari objek NoteDatabse untuk berinteraksi dengan database
        DaoClass noteDatabaseUserDao = noteDatabase.userDao();
        //menghapus catatan yang lama pada database
        noteDatabaseUserDao.delete(catatan);
        //membuat objek catatan baru
        Catatan catatanBaru = new Catatan();
        //mengganti uid cobjek atatan yang baru dengan uid catatan yang lama
        //uid = id catatan pada database (id dapat disebut jugak primary key)
        catatanBaru.uid = catatan.uid;
        //mengganti judul objek catatan yang baru dengan judul catatan yang dimasukan user pada halmaan pengeditan catatan
        catatanBaru.Judul = editTextJudulFromEditCatatanAct.getText().toString();
        //mengganti isi objek catatan yang beru dengan isi catatan yang dimasukan user
        catatanBaru.Isi = editTextIsiFromEditCatatanAct.getText().toString();
        //membuat objek untuk mengakses tanggal hari ini
        Date tanggalDibuat = Calendar.getInstance().getTime();
        //mengakses objek dateFormat untuk memformat tanggal agar berbentuk string
        //lalu mengset format yang dipakai objek dateFormat menjadi medium (salah satu format bawaaan aplikasi)
        DateFormat formaterObjekCalendar = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //memformat tanggal hari ini hinggal menjadi string atau teks
        String tanggalYangTelahDibuat = formaterObjekCalendar.format(tanggalDibuat);
        //mengubah tanggal pada objek catatan baru menjadi tanggal hari ini
        catatanBaru.TanggalDibuat = tanggalYangTelahDibuat;
        //memasukan objek catatan yang baru ke database
        noteDatabaseUserDao.InsertOnce(catatanBaru);
        //membuat itent untuk berpindah ke halaman utama
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //berpindah ke halaman utama dengan menggunakan intent yang telah dibuat
        startActivity(intentToMainActivity);
        //menutup halaman pengeditan catatan
        finish();
    }
}