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
import com.example.mob2014_luongthetai_ph35465.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> lstTV;

    IClickItemRCV clickItemRCV;
    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> lstTV,IClickItemRCV itemRCV) {
        this.context = context;
        this.lstTV = lstTV;
        this.clickItemRCV = itemRCV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = lstTV.get(position);
        holder.tv_matv.setText("Mã TV: " + String.valueOf(thanhVien.getMaTV()));
        holder.tv_hoten.setText("Tên TV: " + thanhVien.getHoTen());
        holder.tv_namsinh.setText("Năm Sinh: " + String.valueOf(thanhVien.getNamSinh()));

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder,thanhVien.getMaTV(),1);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder,holder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstTV.size() != 0 ? lstTV.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matv,tv_hoten,tv_namsinh, tvCccd;
        ImageButton btn_delete;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tv_matv = itemView.findViewById(R.id.tv_idThanhVien);
            tv_hoten = itemView.findViewById(R.id.tv_tenthanhVien);
            tv_namsinh = itemView.findViewById(R.id.tv_namSinhThanhvien);


            btn_delete = itemView.findViewById(R.id.ibtn_xoaThanhvien);
        }
    }
}
