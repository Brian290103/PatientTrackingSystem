package com.example.patienttrackingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentsModel> appointmentsModels;

    String notificationId;
    ProgressDialog progressDialog;

    public void setSearchList(ArrayList<AppointmentsModel> appointmentsModels) {
        this.appointmentsModels = appointmentsModels;
        notifyDataSetChanged();
    }

    public AppointmentsAdapter(Context context, ArrayList<AppointmentsModel> appointmentsModels) {
        this.context = context;
        this.appointmentsModels = appointmentsModels;
    }

    @NonNull
    @Override
    public AppointmentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_appointments_row, parent, false);

        return new AppointmentsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsAdapter.MyViewHolder holder, int position) {


        notificationId=appointmentsModels.get(position).getId()+"";
        String date=appointmentsModels.get(position).getApp_date();
        String time=appointmentsModels.get(position).getApp_time();

        holder.tvIndex.setText(position + 1 + "");
        holder.tvPatientName.setText(appointmentsModels.get(position).getUsername());
        holder.tvAppDateTime.setText(date+" "+time);


        holder.btnViewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View view = LayoutInflater.from(context).inflate(R.layout.activity_view_appointment, null);

                builder.setView(view);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPatientName;
        TextView tvIndex;
        TextView tvAppDateTime;
        Button btnViewApp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvIndex = itemView.findViewById(R.id.tv_index);
            tvAppDateTime = itemView.findViewById(R.id.tv_appdate_time);
            btnViewApp = itemView.findViewById(R.id.btnViewApp);


        }
    }
}

