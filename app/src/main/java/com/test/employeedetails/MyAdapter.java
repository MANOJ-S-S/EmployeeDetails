package com.test.employeedetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Datum> employeeList;

    public MyAdapter(List<Datum> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.namelist_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewName.setText(employeeList.get(position).getEmployeeName());
        holder.textViewSalary.setText(employeeList.get(position).getEmployeeSalary());
        holder.textViewAge.setText(employeeList.get(position).getEmployeeAge());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewSalary;
        TextView textViewAge;

        MyViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewSalary = (TextView) itemView.findViewById(R.id.textViewSalary);
            textViewAge = (TextView) itemView.findViewById(R.id.age);
        }
    }

    public void filterList(ArrayList<String> filterdNames) {
        for(String st: filterdNames){
            Datum employeeModel = new Datum();
            employeeModel.setEmployeeName(st);
            employeeList.clear();
            employeeList.add(employeeModel);
        }
        notifyDataSetChanged();
    }

    public void sortList(ArrayList<String> sortedNames){
        employeeList.clear();
        for(String st: sortedNames){
            Datum employeeModel = new Datum();
            employeeModel.setEmployeeName(st);
            employeeList.add(employeeModel);
        }
        notifyDataSetChanged();
    }



}
