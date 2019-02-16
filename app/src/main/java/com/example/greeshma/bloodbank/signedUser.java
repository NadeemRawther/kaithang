package com.example.greeshma.bloodbank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by android on 4/16/2018.
 */

public class signedUser extends Fragment {

    TextView tv_user;
    Button btn_sign_out;
    Button btn_delete_account;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String details;
    static EditText et_lastDonateUpdate;
    Button bt_lastDonateUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_signed_user, container, false);

        pref =getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        details = getArguments().getString("userDetails");

        tv_user=v.findViewById(R.id.tv_userDetails) ;
        btn_sign_out=v.findViewById(R.id.btn_sign_out) ;
        btn_delete_account=v.findViewById(R.id.btn_delete_account) ;
        et_lastDonateUpdate=v.findViewById(R.id.et_lastDonateUpdate) ;
        bt_lastDonateUpdate=v.findViewById(R.id.bt_lastDonateUpdate) ;
        tv_user.setText(details);


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Sign In");

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("userSigned", false);
                editor.commit();
                callSignInFragment();
            }
        });

        et_lastDonateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        bt_lastDonateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_lastDonateUpdate.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Select a date",Toast.LENGTH_LONG).show();
                    return;
                }

                String phone="",bloodGroup="";
                String lines[] = details.split("\n");
                for (int i=0;i<lines.length;i++){
                    if (lines[i].contains("Phone")){
                        phone=lines[i].replace("Phone: ","");
                    }

                    if (lines[i].contains("Blood group")){
                        bloodGroup=lines[i].replace("Blood group: ","");
                    }
                }


               // Toast.makeText(getActivity(),phone,Toast.LENGTH_LONG).show();
               // Toast.makeText(getActivity(),bloodGroup,Toast.LENGTH_LONG).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child(bloodGroup).child(phone).child("LastDonated");
                myRef.setValue(et_lastDonateUpdate.getText().toString());
                Toast.makeText(getActivity(),"Last Donated Date updated",Toast.LENGTH_LONG).show();

            }
        });


        btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               /// String firstline=details.substring(0,details.indexOf("\n"));
              //  String phone=firstline.replace("Phone: ","");

               // String DetailsFromSecondLine=details.replace(firstline+"\n","");
               // String SecondLine=DetailsFromSecondLine.substring(0,DetailsFromSecondLine.indexOf("\n"));
               // String bloodGroup =SecondLine.replace("Blood group: ","");
                String phone="",bloodGroup="";
                String lines[] = details.split("\n");
                for (int i=0;i<lines.length;i++){
                    if (lines[i].contains("Phone")){
                         phone=lines[i].replace("Phone: ","");
                    }

                    if (lines[i].contains("Blood group")){
                        bloodGroup=lines[i].replace("Blood group: ","");
                    }
                }


                Toast.makeText(getActivity(),phone,Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),bloodGroup,Toast.LENGTH_LONG).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child(bloodGroup).child(phone);
                myRef.removeValue();
                Toast.makeText(getActivity(),"User account deleted",Toast.LENGTH_LONG).show();

                editor.putBoolean("userSigned", false);
                editor.commit();
                callSignInFragment();
            }
        });

    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            et_lastDonateUpdate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    void callSignInFragment(){
        Fragment SignIn=new SignIn_Page();

        if (SignIn != null) {
            FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, SignIn);
            ft.commit();
        }
    }

}
