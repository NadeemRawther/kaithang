package com.example.greeshma.bloodbank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Greeshma on 27-02-2018.
 */

public class Profile_Page extends Fragment {

    EditText name,mobile,dob,lastDonated;
    Spinner gender,palce;
    Button save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.profile,container,false);

        name=(EditText)v.findViewById(R.id.eT_profile_name);
        mobile=(EditText)v.findViewById(R.id.eT_profile_mobile);
        dob=(EditText)v.findViewById(R.id.eT_profile_dob);
        lastDonated=(EditText)v.findViewById(R.id.eT_profile_lastDonated);
        gender=(Spinner)v.findViewById(R.id.spin_profile_gender);
        palce=(Spinner)v.findViewById(R.id.spin_profile_place);
        save=(Button)v.findViewById(R.id.bTN_profile_save);

        name.setText(SaveSharedPreference.getName(getActivity()));
        mobile.setText(SaveSharedPreference.getMobile(getActivity()));
        dob.setText(SaveSharedPreference.getDob(getActivity()));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().equals(""))

                {
                    if(!mobile.getText().toString().equals(""))
                    {
                        if(!dob.getText().toString().equals(""))
                        {

                            if(!lastDonated.getText().toString().equals(""))
                            {


                            }
                            else {
                                lastDonated.setError("Enter Last Donated Date");
                            }
                        }
                        else
                        {
                            dob.setError("Enter Date Of Birth");
                        }

                    }

                    else {

                        mobile.setError("Enter Mobile Number");
                    }
                }
                else
                {
                    name.setError("Enter Name");
                }


            }
        });




        return v;
    }
}
