package com.test.employeedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private   MyAdapter adapter;
    private MainActivityViewModel mainActivityViewModel;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    ArrayList<String> nameList = new ArrayList<>();
    EditText searchName;
    Button sortButtonAscendig;
    Button sortButtonDescendig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        searchName=findViewById(R.id.editTextSearch);
        sortButtonAscendig=findViewById(R.id.buttonAsc);
        sortButtonDescendig=findViewById(R.id.buttonDsc);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        mainActivityViewModel.getAllEMployees(this).observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                if(adapter==null){
                    setupAdapter();
                }

                if(adapter!=null){
                    nameList.clear();
                    for(Datum name : data){
                        nameList.add(name.getEmployeeName());
                        Log.d("NAMELIST --->",nameList.toString());
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mainActivityViewModel.getProgressValue().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else progressBar.setVisibility(View.GONE);
            }
        });


        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
                if(charSequence.toString().trim().contentEquals("")){
                    setupAdapter();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                ///filter(editable.toString());
            }
        });


        sortButtonAscendig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Collections.sort(nameList);
              if(adapter!=null){
                  adapter.sortList(nameList);
              }
            }
        });

        sortButtonDescendig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Collections.sort(nameList, new Comparator<String>(){
                    public int compare(String obj1, String obj2) {
                        // ## Descending order
                         return obj2.compareToIgnoreCase(obj1); // To compare string values
                    }
                });
                if(adapter!=null){
                    adapter.sortList(nameList);
                }
            }
        });
    }

    private void setupAdapter() {
        adapter=new MyAdapter(mainActivityViewModel.getAllEMployees(this).getValue());
        linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : nameList) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

}