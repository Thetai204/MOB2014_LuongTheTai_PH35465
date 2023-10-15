package com.example.mob2014_luongthetai_ph35465;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.mob2014_luongthetai_ph35465.dao.ThuThuDAO;
import com.example.mob2014_luongthetai_ph35465.fragment.DoanhThuFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.DoiMatKhauFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.LoaiSachFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.PhieuMuonFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.SachFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.TaoTaiKhoanFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.ThanhVienFragment;
import com.example.mob2014_luongthetai_ph35465.fragment.Top10Fragment;
import com.example.mob2014_luongthetai_ph35465.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_PHIEUMUON = 0;
    private static final int FRAGMENT_LOAISACH = 1;
    private static final int FRAGMENT_SACH = 2;
    private static final int FRAGMENT_THANHVIEN = 3;
    private static final int FRAGMENT_TOP10 = 4;
    private static final int FRAGMENT_DOANHTHU = 5;
    private static final int FRAGMENT_TAOTAIKHOAN = 6;
    private static final int FRAGMENT_DOIMATKHAU = 7;

    private int mCurrentFragment = FRAGMENT_PHIEUMUON;
    Toolbar toolbar;
    DrawerLayout drawerLayout_home;
    NavigationView navigationView_home;
    String[] title = {"Quản lý Phiếu mượn",
            "Quản lý Loại sách",
            "Quản lý Sách",
            "Quản lý Thành viên",
            "Top 10 Sách mượn nhiều nhất",
            "Doanh thu",
            "Tạo tài khoản",
            "Đổi mật khẩu"};
    TextView tv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        navigationView_home();
        navigationView_home.setItemTextColor(ColorStateList.valueOf(getColor(R.color.black)));
        navigationView_home.setItemIconTintList(ColorStateList.valueOf(getColor(R.color.black)));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title[0]);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,drawerLayout_home,toolbar,R.string.open,R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout_home.addDrawerListener(drawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.group));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,new PhieuMuonFragment());
        fragmentTransaction.commit();
        drawerLayout_home.close();
        showInf();
    }

    private void showInf() {
        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if (!username.equals("admin")) {
            ThuThu thuThu = thuThuDAO.getID(username);
            tv_username.setText(thuThu.getHoTen());
            // ẩn chức năng thêm thu thu
            navigationView_home.getMenu().findItem(R.id.menuthemThanhvien).setVisible(false);
        }
    }

    private void navigationView_home() {
        navigationView_home.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuQLPM) {
                    toolbar.setTitle(title[0]);
                    if (mCurrentFragment != FRAGMENT_PHIEUMUON) {
                        replaceFragment(new PhieuMuonFragment());
                        mCurrentFragment = FRAGMENT_PHIEUMUON;
                    }
                } else if(item.getItemId() == R.id.menuQLLS) {
                    toolbar.setTitle(title[1]);
                    if (mCurrentFragment != FRAGMENT_LOAISACH) {
                        replaceFragment(new LoaiSachFragment());
                        mCurrentFragment = FRAGMENT_LOAISACH;
                    }
                } else if (item.getItemId() == R.id.menuQLS) {
                    toolbar.setTitle(title[2]);
                    if (mCurrentFragment != FRAGMENT_SACH) {
                        replaceFragment(new SachFragment());
                        mCurrentFragment = FRAGMENT_SACH;
                    }
                } else if (item.getItemId() == R.id.menuQLTV) {
                    toolbar.setTitle(title[3]);
                    if (mCurrentFragment != FRAGMENT_THANHVIEN) {
                        replaceFragment(new ThanhVienFragment());
                        mCurrentFragment = FRAGMENT_THANHVIEN;
                    }
                } else if (item.getItemId() == R.id.menutop) {
                    toolbar.setTitle(title[4]);
                    if (mCurrentFragment != FRAGMENT_TOP10) {
                        replaceFragment(new Top10Fragment());
                        mCurrentFragment = FRAGMENT_TOP10;
                    }
                } else if (item.getItemId() == R.id.menuTKDS) {
                    toolbar.setTitle(title[5]);
                    if (mCurrentFragment != FRAGMENT_DOANHTHU) {
                        replaceFragment(new DoanhThuFragment());
                        mCurrentFragment = FRAGMENT_DOANHTHU;
                    }
                }
                // chức năng tạo tài khoản
                else if (item.getItemId() == R.id.menuthemThanhvien) {
                    toolbar.setTitle(title[6]);
                    if (mCurrentFragment != FRAGMENT_TAOTAIKHOAN) {
                        replaceFragment(new TaoTaiKhoanFragment());
                        mCurrentFragment = FRAGMENT_TAOTAIKHOAN;
                    }
                }
                 else if (item.getItemId() == R.id.menudoiMK) {
                    toolbar.setTitle(title[7]);
                    if (mCurrentFragment != FRAGMENT_DOIMATKHAU) {
                        replaceFragment(new DoiMatKhauFragment());
                        mCurrentFragment = FRAGMENT_DOIMATKHAU;
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Bạn có chắc chắn muốn đăng xuất tài khoản này không ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this,DangNhapActivity.class));
                            finishAffinity();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return true;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
        drawerLayout_home.close();
    }
    private void initUI() {
        toolbar = findViewById(R.id.toolbar);

        drawerLayout_home = findViewById(R.id.drawer_layout);

        navigationView_home = findViewById(R.id.navigationView);

        tv_username = navigationView_home.getHeaderView(0).findViewById(R.id.lblUsername);
    }
}