package com.example.mob2014_luongthetai_ph35465.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mob2014_luongthetai_ph35465.IClickItemRCV;
import com.example.mob2014_luongthetai_ph35465.R;
import com.example.mob2014_luongthetai_ph35465.dao.LoaiSachDAO;
import com.example.mob2014_luongthetai_ph35465.model.LoaiSach;
import com.example.mob2014_luongthetai_ph35465.model.Sach;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private List<Sach> lstSach;
    IClickItemRCV clickItemRCV;
    public SachAdapter(Context context, List<Sach> lstSach,IClickItemRCV itemRCV) {
        this.context = context;
        this.lstSach = lstSach;
        this.clickItemRCV = itemRCV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        Sach sach = lstSach.get(position);

        holder.tv_maSach.setText("Mã Sách: " + sach.getMaSach());
        holder.tv_tenSach.setText("Tên Sách: " + sach.getTenSach());
        holder.tv_giaThue.setText("Giá thuê: " + sach.getGiaThue());
//        holder.tv_nam.setText("Năm xuất bản: "+sach.getNam());

        LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMaLoai()));
        holder.tv_tenLoai.setText("Loại sách: " + loaiSach.getTenLoai());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder, sach.getMaSach(), 1);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder, holder.getAdapterPosition(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstSach != null ? lstSach.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_maSach,tv_tenSach,tv_giaThue,tv_tenLoai,tv_nam;
        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_idsach);
            tv_tenSach = itemView.findViewById(R.id.tv_tensach);
            tv_giaThue = itemView.findViewById(R.id.tv_gia);
            tv_tenLoai = itemView.findViewById(R.id.tv_loai);


            btn_delete = itemView.findViewById(R.id.ibtn_xoaSach);
        }
    }
}
