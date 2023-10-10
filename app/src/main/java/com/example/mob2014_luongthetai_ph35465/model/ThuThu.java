package com.example.mob2014_luongthetai_ph35465.model;

public class ThuThu {
    private String maTT;
    private String hoTen;
    private String matKhau;
    private int chucVu;

    public ThuThu(){}

    public ThuThu(String maTT, String hoTen, String matKhau) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public ThuThu(String maTT, String hoTen, String matKhau, int chucVu) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
