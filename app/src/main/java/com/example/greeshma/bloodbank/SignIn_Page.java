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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by android on 4/16/2018.
 */

public class SignIn_Page extends Fragment {
    EditText etphone;
    Spinner spnrbloodGroup;
  //  static EditText etdob;
    Button btsignIn;
    String[] bloodGroup = {"Blood Group", "A+", "A-", "B+", "B-", "O+", "AB+", "AB-"};
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ValueEventListener listener;
     DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_signin, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spnrbloodGroup = (Spinner) v.findViewById(R.id.spnr_bloodGroup);
        etphone = (EditText) v.findViewById(R.id.et_phone);
      //  etdob = (EditText) v.findViewById(R.id.et_dob);
        btsignIn = (Button) v.findViewById(R.id.bt_signIn);

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, bloodGroup);
        adaptor.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnrbloodGroup.setAdapter(adaptor);

        firebase();
  /*      etdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }

            private void showTruitonDatePickerDialog(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }


        });*/


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Sign In");


    }


     void firebase() {


        pref =getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        btsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!spnrbloodGroup.getSelectedItem().toString().equals("Blood Group")) {
                    if (!etphone.getText().toString().equals("")) {
                        // if (!etdob.getText().toString().equals("")) {

                        // Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        myRef = database.getReference().child(spnrbloodGroup.getSelectedItem().toString());
                        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                                "Please wait...", true);


                        listener= myRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot FBdonor) {

                                for(DataSnapshot FBdonorDetais: FBdonor.getChildren())
                                {
                                    if(FBdonorDetais.getKey().equals(etphone.getText().toString())) {
                                        //  if( FBdonorDetais.getValue().equals(etdob.getText().toString())){
                                        // next page
                                        Toast.makeText(getActivity(),"You are successfully signed in",Toast.LENGTH_LONG).show();
                                        dialog.hide();


                                        callSignedFragment( FBdonorDetais);


                                        // }else{Toast.makeText(getActivity(),"Incorrect values",Toast.LENGTH_LONG).show();}
                                    }
                                    dialog.hide();
                                }
                                dialog.hide();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("TAG", "Failed to read value.", error.toException());
                            }
                        });

                        // } else {etdob.setError("Enter date of birth");}
                    } else {etphone.setError("Enter Phone number");}
                } else {Toast.makeText(getContext(),"Please select blood group. ",Toast.LENGTH_LONG).show();}
            }
        });



        boolean userLogin= pref.getBoolean("userSigned", false);
        if (userLogin){
            String details=pref.getString("details","No details saved");
            callFragment(details);
        }
    }



    public void callSignedFragment(DataSnapshot FBdonor) {

    editor.putBoolean("userSigned", true);
    editor.commit();

    ArrayList donorDetails = new ArrayList<String>();
    for (DataSnapshot FBdonorDetais : FBdonor.getChildren()) {
        donorDetails.add(FBdonorDetais.getKey());
        donorDetails.add(FBdonorDetais.getValue());
    }

    String details = "";
    for (int i = 0; i < donorDetails.size(); i++) {
        if (i % 2 == 0) {
            if(i==0){details =donorDetails.get(i).toString();}
            else{details = details + "\n" + donorDetails.get(i);}
        } else {
            details = details + ": " + donorDetails.get(i);
        }
    }

    editor.putString("details", details);
    editor.commit();
        myRef.removeEventListener(listener);
    callFragment(details);
}

void callFragment(String details){
    Fragment signedFragment=new signedUser();
    Bundle args = new Bundle();
    args.putString("userDetails",details);
    signedFragment.setArguments(args);

    if (signedFragment != null) {
        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, signedFragment);
        ft.commit();
    }
}


/*
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

        String date =day+"/"+ month+"/"+ year;
       // etdob.setText(date);
    }
}
*/




}
