package com.example.greeshma.bloodbank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by Greeshma on 27-02-2018.
 */

public class RegisterFragment extends Fragment {

    Spinner blood, gender;
    Button register;
    TextView place;

    static EditText name, mobile, dob, date, time,eT_lastDonated,area;
    String[] bloodGroup = {"Blood Group", "A+", "A-", "B+", "B-", "O+","O-","AB+", "AB-"};
    String[] genderSpin = {"Select Gender","Male", "Female","Other"};
    String[] areaSpin = {"Select Area", "Palayam", "Pattom"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        blood = (Spinner) v.findViewById(R.id.spin_signin_blooggrp);
        gender = (Spinner) v.findViewById(R.id.spin_signin_gender);
        area = (EditText) v.findViewById(R.id.spin_signin_place);
        register = (Button) v.findViewById(R.id.bTN_signin_rwegister);
        name = (EditText) v.findViewById(R.id.eT_signin_name);
        mobile = (EditText) v.findViewById(R.id.eT_signin_mobile);
        dob = (EditText) v.findViewById(R.id.eT_signin_DOB);
        eT_lastDonated = (EditText) v.findViewById(R.id.eT_lastDonated);
        place = (TextView) v.findViewById(R.id.tV_signin_palce);

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, bloodGroup);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood.setAdapter(adapter_category);


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, genderSpin);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);


        /*ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, areaSpin);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(areaAdapter);*/


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!blood.getSelectedItem().toString().equals("Blood Group")) {
                if (!name.getText().toString().equals("")) {
                    if (!mobile.getText().toString().equals("")) {
                        if (!dob.getText().toString().equals("")) {
                            if (!eT_lastDonated.getText().toString().equals("")) {

                            // Write a message to the database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child(blood.getSelectedItem().toString()).child(mobile.getText().toString());
                            myRef.child("Phone").setValue(mobile.getText().toString());
                            myRef.child("Blood group").setValue(blood.getSelectedItem().toString());
                            myRef.child("Name").setValue(name.getText().toString());
                            myRef.child("Place").setValue(area.getText().toString());
                            myRef.child("dateOfBirth").setValue(dob.getText().toString());
                            myRef.child("LastDonated").setValue(eT_lastDonated.getText().toString());
                            Toast.makeText(getActivity(),"You are successfully registred",Toast.LENGTH_LONG).show();
                            SaveSharedPreference.setRegister(getActivity(), "1");
                            Log.e("Signin", "Signin" + SaveSharedPreference.getRegister(getActivity()));
                            SaveSharedPreference.setName(getActivity(), name.getText().toString());
                            SaveSharedPreference.setMobile(getActivity(), mobile.getText().toString());
                            SaveSharedPreference.setDob(getActivity(), dob.getText().toString());





                        /*    ApiClient.getAlertPolceInterface().getBloodSignup(name.getText().toString(), mobile.getText().toString(),
                                    blood.getSelectedItem().toString(), dob.getText().toString(), gender.getSelectedItem().toString(), place.getText().toString(),
                                    area.getSelectedItem().toString(), new Callback<SigninModel>() {
                                        @Override
                                        public void success(SigninModel signinModel, Response response) {

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    }
                            );*/
                            } else {
                                eT_lastDonated.setError("select last donated date");
                            }
                        } else {
                            dob.setError("Enter DOB");
                        }
                    } else {
                        mobile.setError("Enter Mobile Number");
                    }

                } else {
                    name.setError("Enter Name");
                }
            } else {
                Toast.makeText(getContext(),"Please select blood group. ",Toast.LENGTH_LONG).show();
            }


            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }

            private void showTruitonDatePickerDialog(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }


        });

        eT_lastDonated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Last donate");
                alert.setMessage("Do you donated blood before..??");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showTruitonDatePickerDialog(v);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eT_lastDonated.setText("Nill");
                    }
                });
                alert.show();



            }

            private void showTruitonDatePickerDialog(View v) {

                DialogFragment newFragment = new DatePickerFragmentLastDonate();
                newFragment.show(getFragmentManager(), "datePicker");
            }


        });

        return v;
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

            String date =day+"/"+ (month+1)+"/"+ year;
            dob.setText(date);
        }
    }

    public static class DatePickerFragmentLastDonate extends DialogFragment implements
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

            String date =day+"/"+ (month+1)+"/"+ year;
            eT_lastDonated.setText(date);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add donor");
    }
}
