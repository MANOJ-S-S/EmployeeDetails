package com.test.employeedetails;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {

    private static EmployeeRepository instance;
    private MutableLiveData<List<Datum>> employeeList =new MutableLiveData<>();
    private List<Datum>  responseList =new ArrayList<>();
    MutableLiveData<Boolean>  progressValue= new MutableLiveData<>();
    Context context;
    public RetrofitAPI retrofitAPI;


    public EmployeeRepository(Context context) {
        this.context = context;
    }

    public static EmployeeRepository getInstance(Context context){
        if(instance==null){
            instance= new EmployeeRepository(context);
        }
        return instance;
    }
    public LiveData<Boolean> getProgressValue() {
        return progressValue;
    }


    public MutableLiveData<List<Datum>> getEmployeeList(){
        progressValue.setValue(true);

        retrofitAPI = RetrofitAPI.Factory.getInstance(context);
        retrofitAPI.responseCall().enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if(response.body()!=null&&response.isSuccessful()){
                    progressValue.setValue(false);
                    if(responseList!=null){
                        responseList.clear();
                        responseList.addAll(response.body().getData());
                        employeeList.setValue(responseList);
                    }

                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                progressValue.setValue(false);
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR",t.toString());
            }
        });

        return employeeList;
    }

}
