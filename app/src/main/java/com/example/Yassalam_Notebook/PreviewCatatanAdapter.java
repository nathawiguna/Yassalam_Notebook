package com.example.Yassalam_Notebook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PreviewCatatanAdapter extends RecyclerView.Adapter<PreviewCatatanAdapter.ViewHolder> {
    List<Catatan> listCatatan;
    Context context;
    RelativeLayout bingkaiDalamPreviewCatatan;
    ArrayList<ViewHolder> viewHolderArrayList = new ArrayList<>();


    public PreviewCatatanAdapter(List<Catatan> listCatatanParams, Context context,
                                 RelativeLayout bingkaiDalamPreviewCatatan) {
        listCatatan = listCatatanParams;
        this.context = context;
        this.bingkaiDalamPreviewCatatan = bingkaiDalamPreviewCatatan;
    }

    @NonNull
    @Override
    //membuat view holder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //membuat view dari notebook yang akan dimasukan di dalam viewGroupParent (list dari notebooknya)
        View childViewOfParent = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        //membuat viewGroupParent yang telah terisi oleh childViewOfParent
        return new ViewHolder(childViewOfParent);
    }

    //memasukan data ke view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        TextView judul = holder.itemView.findViewById(R.id.Judul_Preview_Note);
        TextView tanggalDibuat = holder.itemView.findViewById(R.id.Isi_Preview_Note);
        TextView indexCatatan = holder.itemView.findViewById(R.id.note_index);
        judul.setText(listCatatan.get(position).Judul);
        tanggalDibuat.setText(listCatatan.get(position).TanggalDibuat);
        holder.itemView.findViewById(R.id.tombolBagi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catatan catatanYangInginDibagi = listCatatan.get(holder.getAdapterPosition());
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
                context.startActivity(Intent.createChooser(
                        intentUntukMembagiCatatan, "Pilih aplikasi")
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        holder.itemView.findViewById(R.id.tombolDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDatabase databaseCatatan = NoteDatabase.getDaoClass(v.getContext());
                DaoClass databaseCatatanDaoClass = databaseCatatan.userDao();
                databaseCatatanDaoClass.delete(listCatatan.get(holder.getAdapterPosition()));
                int indexHolderSebelumDihapus = holder.getAdapterPosition();
                listCatatan.remove(holder.getAdapterPosition());
                PreviewCatatanAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                viewHolderArrayList.remove(indexHolderSebelumDihapus);
                for (int posisiCatatanYangDieditNomorIndexnya = indexHolderSebelumDihapus;
                     posisiCatatanYangDieditNomorIndexnya <= (PreviewCatatanAdapter.this.getItemCount() - 1);
                     posisiCatatanYangDieditNomorIndexnya++) {
                    ViewHolder catatanDieditIndexViewHolder = viewHolderArrayList.get(posisiCatatanYangDieditNomorIndexnya);
                    TextView viewNoteIndexSetiapPreviewCatatan = catatanDieditIndexViewHolder.itemView.findViewById(R.id.note_index);
                    viewNoteIndexSetiapPreviewCatatan.setText(Integer.toString(posisiCatatanYangDieditNomorIndexnya + 1));
                }
                LayoutInflater layoutInflater = (LayoutInflater) (v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                View hasilLayoutInflater = layoutInflater.inflate(R.layout.text_tidak_ada_catatan,bingkaiDalamPreviewCatatan, false);
                if (listCatatan.isEmpty()){
                    //Toast.makeText(context, "catatan habis", Toast.LENGTH_SHORT).show();
                    bingkaiDalamPreviewCatatan.addView(hasilLayoutInflater);

                } else {
                    //Toast.makeText(context, "catatan masih ada", Toast.LENGTH_SHORT).show();
                    hasilLayoutInflater.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewHolderArrayList.add(holder);
        indexCatatan.setText(Integer.toString(position + 1));
    }

    //mendapat jumlah item di list
    @Override
    public int getItemCount() {
        return listCatatan.size();
    }

    //membuat class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView indexCatatanView = itemView.findViewById(R.id.note_index);
                    String indexCatatanString = (String) indexCatatanView.getText();
                    Catatan catatanDipilih = listCatatan.get(Integer.parseInt(indexCatatanString) - 1);
                    CatatanClassParceable catatanClassParceable = new CatatanClassParceable(catatanDipilih);
                    Intent intentToLihatCatatanAct = new Intent(itemView.getContext(), LihatCatatan.class);
                    intentToLihatCatatanAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentToLihatCatatanAct.putExtra("Data_Untuk_Activity_Lihat_Catatan", catatanClassParceable);
                    context.startActivity(intentToLihatCatatanAct);
                }
            });
        }
    }
}
