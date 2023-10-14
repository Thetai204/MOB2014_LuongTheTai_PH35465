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
import com.example.mob2014_luongthetai_ph35465.dao.SachDAO;
import com.example.mob2014_luongthetai_ph35465.dao.ThanhVienDAO;
import com.example.mob2014_luongthetai_ph35465.model.PhieuMuon;
import com.example.mob2014_luongthetai_ph35465.model.Sach;
import com.example.mob2014_luongthetai_ph35465.model.ThanhVien;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PhieuMuon> lstPM;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    IClickItemRCV clickItemRCV;
    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> lstPM,IClickItemRCV itemRCV) {
        this.context = context;
        this.lstPM = lstPM;
        this.clickItemRCV = itemRCV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieumuon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = lstPM.get(position);

        thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));
        sachDAO = new SachDAO(context);
        Sach sach = sachDAO.getID(String.valueOf(phieuMuon.getMaSach()));

        holder.tv_maphieumuon.setText("Mã PM: " + String.valueOf(phieuMuon.getMaPM()));
        holder.tv_thanhvien.setText("Thành viên: " + thanhVien.getHoTen());
        holder.tv_tensach.setText("Sách: " + sach.getTenSach());
        holder.tv_giathue.setText("Giá thuê: " + String.valueOf(sach.getGiaThue()));
        if(phieuMuon.getTraSach() == 1) {
            holder.tv_trangthai.setText("Đã trả sách");
            holder.tv_trangthai.setTextColor(context.getResources().getColor(R.color.blue_A400));
        } else {
            holder.tv_trangthai.setText("Chưa trả sách");
            holder.tv_trangthai.setTextColor(context.getResources().getColor(R.color.red_A400));

        }
        holder.tv_ngay.setText("Ngày thuê: " + phieuMuon.getNgay());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder,holder.getAdapterPosition(),0);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder, phieuMuon.getMaPM(), 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstPM != null ? lstPM.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_maphieumuon,tv_thanhvien,tv_tensach,tv_giathue,tv_trangthai,tv_ngay;
        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maphieumuon = itemView.findViewById(R.id.tv_idphieu);
            tv_thanhvien = itemView.findViewById(R.id.tv_tenthanhVienPhieu);
            tv_tensach = itemView.findViewById(R.id.tv_tenSachPhieu);
            tv_giathue = itemView.findViewById(R.id.tv_giaThuePhieu);
            tv_trangthai = itemView.findViewById(R.id.tv_traSach);
            tv_ngay = itemView.findViewById(R.id.tv_ngayTra);

            btn_delete = itemView.findViewById(R.id.ibtn_xoaphie);
        }
    }
}
