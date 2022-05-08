package com.example.Yassalam_Notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

//membuat class EditCatatan untuk menjalankan halaman EditCatatan
public class EditCatatan extends AppCompatActivity {
    public static boolean isFromLihatCatatan;
    //membuat clazss atribut untuk menyimpan objek catatan yang ingin diedit
    Catatan catatan = new Catatan();
    //membuat class atribut untuk menyimpan objek EditText yang berguna untuk mengambil input user
    EditText editTextJudulFromEditCatatanAct;
    ///membuat class atribut untuk menyimpan objek EditText yang berguna untuk mengambil input user
    EditText editTextIsiFromEditCatatanAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //memanggil method onCreate di parent class
        super.onCreate(savedInstanceState);
        //menampilkan file xml activity_edit_catatan
        setContentView(R.layout.activity_edit_catatan);
        //menadapat data dari catatan yang dipilih yang dikirim oleh halaman lain atau Activity lain yang memanggil EditCatatan Acitvity
        CatatanClassParceable catatanClassParceable =  getIntent().getParcelableExtra("Data_Untuk_Activity_Edit_Catatan");
        //memindahkan data catatan yang dipilih ke atribut catatan
        catatan = catatan.moveDataFromParceableToCatatanObject(catatanClassParceable);
        //mendapatkan objek dari EditText View dengan id : judul_edit_catatan
        editTextJudulFromEditCatatanAct = findViewById(R.id.judul_edit_catatan);
        //mendapatkan objek dari EditText View dengan id : isi_edit_catatan
        editTextIsiFromEditCatatanAct = findViewById(R.id.isi_edit_catatan);
        //mengubah text pada EditText View yang telah tersimpan pada atribut editTextJudulFromEditCatatanAct
        //dengan kata lain mengubah bagian judul pada halaman Pengeditan Catatan menjadi judul dari catatan yang dipilih
        editTextJudulFromEditCatatanAct.setText(catatan.Judul);
        //mengubah text pada EditText View yang telah tersimpan pada atribut editTextIsiFromEditCatatanAct
        //dengan kata lain mengubah bagian isi pada halaman Pengeditan Catatan menjadi isi dari catatan yang dipilih
        editTextIsiFromEditCatatanAct.setText(catatan.Isi);
    }

    public void editCatatan(View finishButton) {
        //mendapatkan objek yang digunakan untuk mengakses penyimpanan atau database pada HandPhone
        NoteDatabase noteDatabase = NoteDatabase.getDaoClass(this.getApplicationContext());
        DaoClass noteDatabaseUserDao = noteDatabase.userDao();
        //menghapus data catatan yang dipilih yang berupa sebuah objek pada database
        noteDatabaseUserDao.delete(catatan);
        //membuat objek catatanBaru yang akan disimpan pada database
        Catatan catatanBaru = new Catatan();
        //mengubah atribut uid yang ada di dalam objek catatanBaru menjadi sama seperti catatan yang telah dihapus
        //pada database
        catatanBaru.uid = catatan.uid;
        //mendapatkan judul catatan yang telah diinput oleh user pada halaman pengeditan catatan
        //lalu mengubah nilai variable Judul pada objek catatanBaru menjadi judul catatan yang didapatkan
        catatanBaru.Judul = editTextJudulFromEditCatatanAct.getText().toString();
        //mendapatkan isi catatan yang telah diinput oleh user pada halaman pengeditan catatan
        //lalu mengubah nilai variable Isi pada objek catatanBaru menjadi isi catatan yang didapatkan
        catatanBaru.Isi = editTextIsiFromEditCatatanAct.getText().toString();
        //mendapatkan waktu sekarang dengan class Calendar
        //lalu disimpan ke dalam variable tanggalDibuat
        Date tanggalDibuat = Calendar.getInstance().getTime();
        DateFormat formaterObjekCalendar = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //mengatur format penulisan waktu sekarang dengan menggunakan kelas formatterObjekCalendar
        String tanggalYangTelahDibuat = formaterObjekCalendar.format(tanggalDibuat);
        //mengubah nilai variable TanggalDibuat pada objek catatanBaru menjadi sesuai dengan waktu sekarang
        catatanBaru.TanggalDibuat = tanggalYangTelahDibuat;
        //menambahkan objek catatanBaru pada database
        noteDatabaseUserDao.InsertOnce(catatanBaru);
        //membuat intent yang digunakan untuk berpindah halaman ke halaman utama
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        CatatanClassParceable catatanClassParceable = new CatatanClassParceable(catatanBaru);
        intentToMainActivity.putExtra("Data_Untuk_Activity_Lihat_Catatan", catatanClassParceable);
        noteDatabaseUserDao.delete(catatan); noteDatabaseUserDao.InsertOnce(catatanBaru);
        //berpindah pada halaman utam
        startActivity(intentToMainActivity);
        //menutup halaman pengeditan catatan
        finish();
    }
}