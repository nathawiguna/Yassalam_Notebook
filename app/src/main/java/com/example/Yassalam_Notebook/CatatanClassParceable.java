package com.example.Yassalam_Notebook;

import android.os.Parcel;
import android.os.Parcelable;

public class CatatanClassParceable implements Parcelable {
    public int uid;
    public String judul, isi, tanggalDibuat;

   public CatatanClassParceable(Catatan catatan) {
      uid = catatan.uid;
      judul = catatan.Judul;
      isi = catatan.Isi;
      tanggalDibuat = catatan.TanggalDibuat;
   }


    protected CatatanClassParceable(Parcel in) {
        uid = in.readInt();
        judul = in.readString();
        isi = in.readString();
        tanggalDibuat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(judul);
        dest.writeString(isi);
        dest.writeString(tanggalDibuat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CatatanClassParceable> CREATOR = new Creator<CatatanClassParceable>() {
        @Override
        public CatatanClassParceable createFromParcel(Parcel in) {
            return new CatatanClassParceable(in);
        }

        @Override
        public CatatanClassParceable[] newArray(int size) {
            return new CatatanClassParceable[size];
        }
    };
}
