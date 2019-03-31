package com.example.greeshma.bloodbank;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Greeshma on 26-02-2018.
 */

public class DonorsPage extends Fragment implements DatePickerDialog.OnDateSetListener {

    static EditText date, time;
    EditText pname;
    EditText pnumber ,area;
    Spinner  bloodGroup;
    Button register;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";


    String[] SbloodGroup = {"Blood Group", "A+", "A-", "B+", "B-", "O+","O-","AB+", "AB-"};



   /* public DonorsPage(Context context, int editTextViewID)
    {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_donor, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        _context = getActivity();
        bloodGroup = (Spinner) v.findViewById(R.id.spin_donor_blooggrp);
        area = (EditText) v.findViewById(R.id.Spin_donorPage_Area);
        date = (EditText) v.findViewById(R.id.eT_donorPage_date);
        time = (EditText) v.findViewById(R.id.eT_donorPage_Time);

        pname = (EditText) v.findViewById(R.id.eT_donorPage_patientName);
        pnumber = (EditText) v.findViewById(R.id.eT_donorPage_patientPhone);
        register = (Button) v.findViewById(R.id.bTn_donorPage_search);

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, SbloodGroup);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter_category);

       /* ArrayAdapter<String> adapter_category2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, Sarea);
        adapter_category2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(adapter_category2);
*/

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);


            }


            private void showTruitonDatePickerDialog(View v) {


                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }


        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showTruitonTimePickerDialog(v);

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }


           /* private void showTruitonTimePickerDialog(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }*/

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!date.getText().toString().equals("")) {
                    if (!time.getText().toString().equals("")) {
                        if (!pname.getText().toString().equals("")) {
                           // Toast.makeText(getActivity(),"Not Empty",Toast.LENGTH_SHORT).show();
                            if (!pnumber.getText().toString().equals("")) {

                                //send SMS
                                String msg = "BLOOD BANK"
                                        +"\n"+"Patient name: "+pname.getText().toString()
                                        +"\n"+"Blood group: "+bloodGroup.getSelectedItem().toString()
                                        +"\n"+"Required date: "+date.getText().toString()
                                        +"\n"+"Required time: "+time.getText().toString()
                                        +"\n"+"Area: "+area.getText().toString()
                                        +"\n"+"Phone No: "+pnumber.getText().toString();
                                String phNo="+919207663879";
                                sendSMS(phNo, msg);






                                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();


                                Fragment fr=new ListSearch_Page();
                                Bundle args = new Bundle();
                                args.putString("BloodGroup", bloodGroup.getSelectedItem().toString());
                                fr.setArguments(args);

                                //  Bundle bndle = new Bundle();
                                //  addFragment(new ListSearch_Page(), true, FragmentTransaction.TRANSIT_NONE, "Donor", bndle);
                                  addFragment(fr, true, FragmentTransaction.TRANSIT_NONE, "Donor", args);


                            } else {

                                pnumber.setError("Enter Patient Number");
                            }

                        } else {
                            pname.setError("Enter Patient Name");
                         //   Toast.makeText(getActivity(),"Empty",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        time.setError("Enter Required Time");
                    }
                }

                else {
                    date.setError("Enter Required Date");
                }
            }
        });


        return v;
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> messageParts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, messageParts, null, null);
            Toast.makeText(getContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
           Toast.makeText(getContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
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
            date.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            // time.setText(time.getText() + " -" + hourOfDay + ":" + minute);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        _birthYear = year;
        _month = month;
        _day = dayOfMonth;
    }


    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, String name, Bundle bndle) {
        FragmentTransaction ft = getActivity()
                .getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.setTransition(transition);
        fragment.setArguments(bndle);
        if (addToBackStack)
            ft.addToBackStack(name);
        ft.commit();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Search donors");
    }

}
