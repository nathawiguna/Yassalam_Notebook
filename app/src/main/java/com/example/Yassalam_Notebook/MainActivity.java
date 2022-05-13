package com.example.Yassalam_Notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        NoteDatabase noteDatabaseObj;
        List<Catatan> listSemuaCatatanDariDatabase;
        PreviewCatatanAdapter previewCatatanAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tampilkanCatatan(findViewById(R.id.bingkai_dalam_preview_note));
        }

    public void tampilkanCatatan (RelativeLayout objekViewBingkaiDalamPreviewNote) {
        noteDatabaseObj = NoteDatabase.getDaoClass(this);
        listSemuaCatatanDariDatabase = noteDatabaseObj.userDao().getAll();
        if (!listSemuaCatatanDariDatabase.isEmpty()) {
            TextView teksTidakAdaCatatan = findViewById(R.id.teksTidakAdaCatatan);
            teksTidakAdaCatatan.setVisibility(View.INVISIBLE);
            RecyclerView recyclerView = findViewById(R.id.list_catatan);
            previewCatatanAdapter = new PreviewCatatanAdapter(listSemuaCatatanDariDatabase, this, objekViewBingkaiDalamPreviewNote);
            recyclerView.setAdapter(previewCatatanAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void buatCatatanBaru (View pressedButton) {
            Intent intentToAnotherActivity = new Intent(this, BuatCatatan.class);
            startActivity(intentToAnotherActivity);
    }

    public void deleteCatatan (View pressedButton) {
        NoteDatabase databaseCatatan = NoteDatabase.getDaoClass(pressedButton.getContext());
        DaoClass databaseCatatanDaoClass = databaseCatatan.userDao();
        TextView indexCatatan = pressedButton.findViewById(R.id.note_index);
        int indexCatatanInteger = Integer.parseInt(indexCatatan.getText().toString());
        databaseCatatanDaoClass.delete(listSemuaCatatanDariDatabase.get(indexCatatanInteger));
        listSemuaCatatanDariDatabase.remove(indexCatatanInteger);
        previewCatatanAdapter.notifyItemRemoved(indexCatatanInteger);
    }

}