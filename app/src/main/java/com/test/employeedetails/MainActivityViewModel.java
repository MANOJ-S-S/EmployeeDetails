package com.test.employeedetails;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel  extends ViewModel {

    private EmployeeRepository employeeRepository;
    private MutableLiveData<List<Datum>> myEmployeeList;
    private LiveData<Boolean> progressValue;

/*
    public void init(Context context){

    }*/

    public LiveData<List<Datum>> getAllEMployees(Context context){
        myEmployeeList= new MutableLiveData<>();
        employeeRepository= EmployeeRepository.getInstance(context);
        myEmployeeList = employeeRepository.getEmployeeList();
        return myEmployeeList;
    }


    public LiveData<Boolean> getProgressValue(){
        if(progressValue==null){
            progressValue= employeeRepository.getProgressValue();
            return progressValue;
        }
        else {
            return progressValue;
        }
    }

}
