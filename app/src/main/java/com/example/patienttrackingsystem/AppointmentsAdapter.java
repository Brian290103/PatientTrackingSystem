package com.example.patienttrackingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    ProgressDialog progressDialog,progressDialog1;

    AlertDialog dialog;

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

        AppointmentsModel currentItem = appointmentsModels.get(position);

        notificationId = appointmentsModels.get(position).getId() + "";
        String date = appointmentsModels.get(position).getApp_date();
        String time = appointmentsModels.get(position).getApp_time();

        holder.tvIndex.setText(position + 1 + "");
        holder.tvPatientName.setText(appointmentsModels.get(position).getUsername());
        holder.tvAppDateTime.setText(date + " " + time);


        holder.btnViewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                View view = LayoutInflater.from(context).inflate(R.layout.activity_view_appointment, null);

                TextView tvPatientName = view.findViewById(R.id.tvPatientName);
                TextView tvAppDateTime = view.findViewById(R.id.tvAppDateTime);
                TextView tvDesc = view.findViewById(R.id.tvDesc);

                tvPatientName.setText(currentItem.getUsername());
                tvAppDateTime.setText(currentItem.getApp_date() + " " + currentItem.getApp_time());
                tvDesc.setText(currentItem.getMessage());

                Button btnDecline = view.findViewById(R.id.btnDecline);
                Button btnApprove = view.findViewById(R.id.btnApprove);

                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.doctor_comment, null);

                        TextView txt_drComments = view.findViewById(R.id.drComments);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Declination message to be sent to the sever should be place here
                                        if (txt_drComments.getText().toString().equals("")) {
                                            Toast.makeText(context, "Aborted, Provide an Appropriate Message", Toast.LENGTH_SHORT).show();
                                        } else {

                                            progressDialog = new ProgressDialog(context);
                                            progressDialog.setMessage("Sending Message. Please Wait.");
                                            progressDialog.show();

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_MAKE_NOTIFICATION,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //Toast.makeText(Register.this, "Response", Toast.LENGTH_SHORT).show();

                                                            progressDialog.dismiss();
                                                            JSONObject jsonObject = null;
                                                            try {
                                                                jsonObject = new JSONObject(response);

                                                                String message = jsonObject.getString("message");

                                                                    Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
                                                                context.startActivity(new Intent(context,DashboardDr.class));



                                                            } catch (JSONException e) {
                                                                //e.printStackTrace();
                                                                //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                Toast.makeText(context, "An Error Occurred, Try Again Later", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    progressDialog.hide();
                                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            }) {
                                                @Nullable
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("user_id", Integer.toString(currentItem.getUser_id()));
                                                    params.put("reason", txt_drComments.getText().toString());

                                                    return params;
                                                }
                                            };

                                            // RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                                            // requestQueue.add(stringRequest);
                                            RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
                                            dialog.cancel();
                                        }
                                    }
                                })
                                .setView(view);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog1 = new ProgressDialog(context);
                        progressDialog1.setMessage("Sending Message. Please Wait.");
                        progressDialog1.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APPROVE_APPOINTMENT,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //Toast.makeText(Register.this, "Response", Toast.LENGTH_SHORT).show();

                                        progressDialog1.dismiss();
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);

                                            String message = jsonObject.getString("message");

                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                                context.startActivity(new Intent(context,DashboardDr.class));

                                        } catch (JSONException e) {
                                            //e.printStackTrace();
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                           // Toast.makeText(context, "An Error Occurred, Try Again Later", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog1.hide();
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", Integer.toString(currentItem.getId()));
                                params.put("user_id", Integer.toString(currentItem.getUser_id()));

                                return params;
                            }
                        };

                        // RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                        // requestQueue.add(stringRequest);
                        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
                        dialog.cancel();

                    }
                });


                builder.setView(view);

                dialog = builder.create();
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
        ImageView btnViewApp;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvIndex = itemView.findViewById(R.id.tv_index);
            tvAppDateTime = itemView.findViewById(R.id.tv_appdate_time);
            btnViewApp = itemView.findViewById(R.id.btnViewApp);


        }
    }
}

