package com.example.Yassalam_Notebook;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Catatan {
        @PrimaryKey(autoGenerate = true)
        public int uid;

        @ColumnInfo(name = "Judul")
        public String Judul;

        @ColumnInfo(name = "TanggalDibuat")
        public String TanggalDibuat;

        @ColumnInfo(name = "Isi")
        public String Isi;

        public Catatan moveDataFromParceableToCatatanObject(CatatanClassParceable catatanClassParceable) {
                this.uid = catatanClassParceable.uid;
                this.Judul = catatanClassParceable.judul;
                this.Isi = catatanClassParceable.isi;
                this.TanggalDibuat = catatanClassParceable.tanggalDibuat;
                return this;
        }

}

