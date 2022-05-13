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
    Catatan catatan = new Catatan();
    EditText editTextJudulFromEditCatatanAct;
    EditText editTextIsiFromEditCatatanAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catatan);
        CatatanClassParceable catatanClassParceable =  getIntent().getParcelableExtra("Data_Untuk_Activity_Edit_Catatan");
        catatan = catatan.moveDataFromParceableToCatatanObject(catatanClassParceable);
        editTextJudulFromEditCatatanAct = findViewById(R.id.judul_edit_catatan);
        editTextIsiFromEditCatatanAct = findViewById(R.id.isi_edit_catatan);
        editTextJudulFromEditCatatanAct.setText(catatan.Judul);
        editTextIsiFromEditCatatanAct.setText(catatan.Isi);
    }

    public void editCatatan(View finishButton) {
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this.getApplicationContext());
        DaoClass noteDatabaseUserDao = noteDatabase.userDao();
        noteDatabaseUserDao.delete(catatan);
        Catatan catatanBaru = new Catatan();
        catatanBaru.uid = catatan.uid;
        catatanBaru.Judul = editTextJudulFromEditCatatanAct.getText().toString();
        catatanBaru.Isi = editTextIsiFromEditCatatanAct.getText().toString();
        Date tanggalDibuat = Calendar.getInstance().getTime();
        DateFormat formaterObjekCalendar = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String tanggalYangTelahDibuat = formaterObjekCalendar.format(tanggalDibuat);
        catatanBaru.TanggalDibuat = tanggalYangTelahDibuat;
        noteDatabaseUserDao.InsertOnce(catatanBaru);
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentToMainActivity);
        finish();
    }
}