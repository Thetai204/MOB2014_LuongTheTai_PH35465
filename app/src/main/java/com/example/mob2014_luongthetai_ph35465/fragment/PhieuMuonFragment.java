package com.example.mob2014_luongthetai_ph35465.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mob2014_luongthetai_ph35465.IClickItemRCV;
import com.example.mob2014_luongthetai_ph35465.R;
import com.example.mob2014_luongthetai_ph35465.adapter.PhieuMuonAdapter;
import com.example.mob2014_luongthetai_ph35465.adapter.SachSpinnerAdapter;
import com.example.mob2014_luongthetai_ph35465.adapter.ThanhVienSpinnerAdapter;
import com.example.mob2014_luongthetai_ph35465.dao.PhieuMuonDAO;
import com.example.mob2014_luongthetai_ph35465.dao.SachDAO;
import com.example.mob2014_luongthetai_ph35465.dao.ThanhVienDAO;
import com.example.mob2014_luongthetai_ph35465.model.PhieuMuon;
import com.example.mob2014_luongthetai_ph35465.model.Sach;
import com.example.mob2014_luongthetai_ph35465.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhieuMuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhieuMuonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhieuMuonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhieuMuonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhieuMuonFragment newInstance(String param1, String param2) {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Dialog dialog;
    ArrayList<PhieuMuon> lstPM;
    ArrayList<ThanhVien>lstTV;
    ArrayList<Sach> lstSach;
    PhieuMuon phieuMuon;
    PhieuMuonDAO phieuMuonDAO;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    RecyclerView rcv_phieumuon;
    FloatingActionButton btn_add;
    EditText edt_maPM,edt_ngay,edt_tienThue;
    Spinner spinner_thanhvien,spinner_sach;
    int maTV = 0,maSach = 0,getPosition = 0,tienThue;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ql_phieu_muon, container, false);

        lstPM = new ArrayList<>();
        lstTV = new ArrayList<>();
        lstSach = new ArrayList<>();
        phieuMuon = new PhieuMuon();
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        thanhVienDAO = new ThanhVienDAO(getContext());
        sachDAO = new SachDAO(getContext());

        lstTV = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        lstSach = (ArrayList<Sach>) sachDAO.getAll();
        initUI(view);
        fillRCV();
        btnAdd();

        return view;
    }

    private void btnAdd() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
    }


    private void fillRCV() {
        lstPM = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), lstPM, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getPosition = position;
                if (type == 0) {
                    openDialog(getContext(),1);
                } else {
                    xoa_phieumuon(String.valueOf(position));
                }
            }
        });
        rcv_phieumuon.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_phieumuon.setAdapter(phieuMuonAdapter);
    }
    protected void openDialog(final Context context, final int type) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_phieu);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        edt_maPM = dialog.findViewById(R.id.txtMaphieu);
        edt_ngay = dialog.findViewById(R.id.txtngayThue);
        edt_tienThue = dialog.findViewById(R.id.txtgiaThue);
        spinner_sach = dialog.findViewById(R.id.sp_tensach);
        spinner_thanhvien = dialog.findViewById(R.id.sp_thanhvien);
        CheckBox chk_status = dialog.findViewById(R.id.cbk_tra);
        edt_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay(edt_ngay);
            }
        });

        Button btn_cancel = dialog.findViewById(R.id.btnCancelx);
        Button btn_save = dialog.findViewById(R.id.btnSavex);

        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(),R.layout.item_thanhvien,lstTV);
        spinner_thanhvien.setAdapter(thanhVienSpinnerAdapter);
        spinner_thanhvien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = lstTV.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(),R.layout.item_sach,lstSach);
        spinner_sach.setAdapter(sachSpinnerAdapter);
        spinner_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = lstSach.get(position).getMaSach();
                tienThue = lstSach.get(position).getGiaThue();
                Log.e("TAG", "onItemSelected: "+tienThue);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        chk_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("Đã trả sách");
                    buttonView.setTextColor(getResources().getColor(R.color.blue_A400));
                } else {
                    buttonView.setText("Chưa trả sách");
                    buttonView.setTextColor(getResources().getColor(R.color.red_A400));
                }
            }
        });

        if (type != 0) {
            phieuMuon = lstPM.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edt_maPM.setText(String.valueOf(phieuMuon.getMaPM()));

            for (int i = 0;i < lstTV.size(); i++) {
                if (phieuMuon.getMaTV() == lstTV.get(i).getMaTV()) {
                    maTV = i;
                }
            }
            spinner_thanhvien.setSelection(maTV);

            for (int i = 0;i < lstSach.size(); i++) {
                if (phieuMuon.getMaSach() == lstSach.get(i).getMaSach()) {
                    maSach = i;
                }
            }
            spinner_sach.setSelection(maSach);

            edt_ngay.setText(phieuMuon.getNgay());
            edt_tienThue.setText(String.valueOf(phieuMuon.getTienThue()));

            if (phieuMuon.getTraSach() == 1) {
                chk_status.setChecked(true);
                chk_status.setText("Đã trả sách");
                chk_status.setTextColor(getResources().getColor(R.color.blue_A400));
            } else {
                chk_status.setChecked(false);
                chk_status.setText("Chưa trả sách");
                chk_status.setTextColor(getResources().getColor(R.color.red_A400));
            }
        } else {
            tv_title.setText("Thêm Phiếu mượn");
            edt_maPM.setText("Mã PM");
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                phieuMuon = new PhieuMuon();
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTV(maTV);
                phieuMuon.setNgay(edt_ngay.getText().toString());


                SharedPreferences preferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
                String user = preferences.getString("username","");
                phieuMuon.setMaTT(user);

                phieuMuon.setTienThue(tienThue);
                if (chk_status.isChecked()) {
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);

                }
                if (type == 0) {
                    if (phieuMuonDAO.insert(phieuMuon) > 0) {
                        Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (phieuMuonDAO.update(phieuMuon) > 0) {
                        Toast.makeText(context, "Chỉnh sửa thông tin thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Chỉnh sửa thông tin thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
                fillRCV();
            }
        });
        dialog.show();
    }
    private void initUI(View view) {
        rcv_phieumuon = view.findViewById(R.id.rcv_phieu);

        btn_add = view.findViewById(R.id.flb_themphieu);
    }
    public void xoa_phieumuon(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Phiếu mượn");
        builder.setMessage("Bạn có chắc muốn xóa Phiếu mượn này không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                phieuMuonDAO.delete(id);
                fillRCV();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    int mYear;
    int mMonth;
    int mDay;
    private void chonNgay(EditText edt_tungay) {
        DatePickerDialog.OnDateSetListener mDateTungay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay);
                edt_tungay.setText(dateFormat.format(calendar.getTime()));
            }
        };
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mDateTungay,mYear,mMonth,mDay);
        dialog.show();
    }
}