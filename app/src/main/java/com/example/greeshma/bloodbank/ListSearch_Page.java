package com.example.greeshma.bloodbank;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Greeshma on 28-02-2018.
 */

public class ListSearch_Page extends Fragment {


    EditText search;

    List<ListSearch_Item> donorList;
    RecyclerView recyclerView;
    ListSearch_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        String bloodGroup = getArguments().getString("BloodGroup");

        final View v = inflater.inflate(R.layout.list_search, container, false);

        search = (EditText) v.findViewById(R.id.eT_listSearch_search);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_list_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        donorList = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(bloodGroup);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Loading donors in "+bloodGroup+" Group Please wait...", true);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot FBdonorList) {
                donorList.clear();

                for(DataSnapshot FBdonor: FBdonorList.getChildren()) {

                    String name = "", phone = "", place = "", lastDonated = "";

                    for (DataSnapshot Details : FBdonor.getChildren()) {

                        if (Details.getKey().contains("Name")) {
                            name = Details.getValue().toString();
                        }
                        if (Details.getKey().contains("Phone")) {
                            phone = Details.getValue().toString();
                        }
                        if (Details.getKey().contains("Place")) {
                            place = Details.getValue().toString();
                        }
                        if (Details.getKey().contains("LastDonated")) {
                            lastDonated = Details.getValue().toString();
                        }

                    }


                    int numOfDays;

                    if(!lastDonated.equals("Nill")) {
                        Date ld = null;
                        long diff = 0;
                        try {
                            ld = new SimpleDateFormat("dd/MM/yyyy").parse(lastDonated);
                            Date today = new Date();
                            diff = today.getTime() - ld.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                    }else{numOfDays=91;}


                 if (numOfDays>90){donorList.add(new ListSearch_Item(name,phone,place));}
                }

                adapter = new ListSearch_Adapter(getContext(), donorList);
                recyclerView.setAdapter(adapter);
                dialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


       /* donorList.add(new ListSearch_Item("n1","111","p1"));
        donorList.add(new ListSearch_Item("n2","222","p2"));
        donorList.add(new ListSearch_Item("n2","333","p3"));  */


       // final ListSearch_Adapter adapter = new ListSearch_Adapter(getContext(), donorList);
        recyclerView.setAdapter(adapter);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
}
