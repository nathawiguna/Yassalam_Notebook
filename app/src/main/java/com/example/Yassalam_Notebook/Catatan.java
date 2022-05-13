package com.example.Yassalam_Notebook;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Catatan {
        //mengset id catatan menjadi autoGenerate (dihasilkan secara otomatis)
        @PrimaryKey(autoGenerate = true)
        public int uid;

        //mengset atribut Judul catatan agar sesuai dengan variable Judul
        @ColumnInfo(name = "Judul")
        public String Judul;

        //mengset atribut Tanggal Catatan Dibuat menjadi sesuai dengan variabel TanggalDibuat
        @ColumnInfo(name = "TanggalDibuat")
        public String TanggalDibuat;

        //mengset atribut Isi menjadi sesuai dengan variabel Isi
        @ColumnInfo(name = "Isi")
        public String Isi;

        //membuat fungsi untuk memindahkan data yang disimpan di objek kelas CatatanClassParceable ke
        //objek catatan
        public Catatan moveDataFromParceableToCatatanObject(CatatanClassParceable catatanClassParceable) {
                //menyimpan nilai variabel uid pada objek parceabel ke variabel uid di objek catatan
                this.uid = catatanClassParceable.uid;
                //menyimpan nilai variabel Judul pada objek parceabel ke variabel Judul di objek catatan
                this.Judul = catatanClassParceable.judul;
                //menyimpan nilai variabel isi pada objek parceabel ke variabel Isi di objek catatan
                this.Isi = catatanClassParceable.isi;
                //menyimpan nilai variabel tanggalDibuat pada objek parceabel ke variabel TanggalDibuat di objek catatan
                this.TanggalDibuat = catatanClassParceable.tanggalDibuat;
                //mengembalikan objek catatan yang telah dimasukan nilai nilai variabel pada objek catatanClassParceabel
                return this;
        }

}

