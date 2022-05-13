package com.example.Yassalam_Notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LihatCatatan extends AppCompatActivity {
    Catatan catatanDilihat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_catatatan);
        CatatanClassParceable catatanClassParceable = getIntent().getParcelableExtra("Data_Untuk_Activity_Lihat_Catatan");
        catatanDilihat = new Catatan().moveDataFromParceableToCatatanObject(catatanClassParceable);
        TextView judulCatatan = findViewById(R.id.judul_lihat_catatan);
        TextView isiCatatan = findViewById(R.id.isi_lihat_catatan);
        judulCatatan.setText(catatanDilihat.Judul);
        isiCatatan.setText(catatanDilihat.Isi);
    }

    public void bagiCatatan (View view){
        Catatan catatanYangInginDibagi = catatanDilihat;
        String judulCatatanYangDibagi = catatanYangInginDibagi.Judul;
        String tanggalDibuatCatatanYangDibagi = catatanYangInginDibagi.TanggalDibuat;
        String isiCatatanYangDibagi = catatanYangInginDibagi.Isi;
        String catatanYangDibagiString =
                "====================" + "\n" +
                        judulCatatanYangDibagi + "\n" +
                        "====================" + "\n\n" +
                        "====================" + "\n" +
                        "Dibuat : " + tanggalDibuatCatatanYangDibagi + "\n" +
                        "====================" + "\n\n" +
                        "====================" + "\n" +
                        isiCatatanYangDibagi + "\n" +
                        "====================";
        Intent intentUntukMembagiCatatan = new Intent(Intent.ACTION_SEND);
        intentUntukMembagiCatatan.putExtra(Intent.EXTRA_TEXT, catatanYangDibagiString);
        intentUntukMembagiCatatan.setType("text/plain");
        this.startActivity(Intent.createChooser(
                intentUntukMembagiCatatan, "Pilih aplikasi")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void editCatatan (View view) {
        Intent intentToEditCatatanActivity = new Intent(this, EditCatatan.class);
        CatatanClassParceable catatanClassParceable = new CatatanClassParceable(catatanDilihat);
        intentToEditCatatanActivity.putExtra("Data_Untuk_Activity_Edit_Catatan", catatanClassParceable);
        startActivity(intentToEditCatatanActivity);
    }

    public void hapusCatatan (View view) {
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this);
        DaoClass daoClassCatatan = noteDatabase.getDaoClass(this).userDao();
        daoClassCatatan.delete(catatanDilihat);
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(this, "Catatan Berhasil Di Delete", Toast.LENGTH_SHORT).show();
        startActivity(intentToMainActivity);
    }

}