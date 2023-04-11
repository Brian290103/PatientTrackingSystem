package com.example.patienttrackingsystem;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> doctorModels;
    TextView select_date, select_time, txt_message;
    String doctor_id;
    String speciality_id;
    ProgressDialog progressDialog;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    AlertDialog alertDialog1;

   private final String CHANNEL_ID="Notification";
   private final int NOTIFICATION_ID=01;


    public void setSearchList(ArrayList<DoctorModel> doctorModels) {
        this.doctorModels = doctorModels;
        notifyDataSetChanged();
    }

    public DoctorAdapter(Context context, ArrayList<DoctorModel> doctorModels) {
        this.context = context;
        this.doctorModels = doctorModels;
    }

    @NonNull
    @Override
    public DoctorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_doctor_row, parent, false);

        return new DoctorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.MyViewHolder holder, int position) {


        String fname = doctorModels.get(position).getFname();
        String lname = doctorModels.get(position).getLname();
        doctor_id = Integer.toString(doctorModels.get(position).getUser_id());
        speciality_id = Integer.toString(doctorModels.get(position).getSpeciality_id());

        holder.tvId.setText(position + 1 + "");
        holder.tvName.setText("Doctor " + fname + " " + lname);
        holder.txAvailableTimes.setText(doctorModels.get(position).getAvailable_times());

        if (doctorModels.get(position).getIsPresent() == 1) {
            holder.imgStatus.setImageResource(R.drawable.presnt_icon);
            //holder.btn_bookNow.setVisibility(View.VISIBLE);
            holder.btn_bookNow.setEnabled(true);
        } else if (doctorModels.get(position).getIsPresent() == 2) {
            holder.imgStatus.setImageResource(R.drawable.absent_icon);
        }

        holder.btn_bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Doctor Id.: "+doctorModels.get(position).getUser_id(), Toast.LENGTH_SHORT).show();

                builder = new AlertDialog.Builder(context);

                View view = LayoutInflater.from(context).inflate(R.layout.single_book_doctor, null);

                select_date = view.findViewById(R.id.txt_date);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DateListener(), year, month, day);
                datePickerDialog.setCancelable(true);
                select_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                select_time = view.findViewById(R.id.txt_time);

                Calendar calendar1 = Calendar.getInstance();
                int hour = calendar1.get(Calendar.HOUR_OF_DAY);
                int minute = calendar1.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new DateListener(), hour, minute, false);
                timePickerDialog.setCancelable(true);
                select_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerDialog.show();
                    }
                });

                txt_message = view.findViewById(R.id.txt_message);

                progressDialog = new ProgressDialog(context);
                // Toast.makeText(context, "User ID: "+SharedPrefManager.getInstance(view.getContext()).getUserId(), Toast.LENGTH_SHORT).show();
                view.findViewById(R.id.btn_appoint).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        progressDialog.setMessage("Making Appointment. Please Wait.");
                        progressDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_MAKE_APPOINTMENT,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //Toast.makeText(Register.this, "Response", Toast.LENGTH_SHORT).show();

                                        progressDialog.dismiss();
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);

                                            String message = jsonObject.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                            if (message.equals("Appointment Made Successfully")) {

                                                dialog.cancel();
                                                //Toast.makeText(context, "Appointment Made Successfully", Toast.LENGTH_SHORT).show();


                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                                builder1.setMessage("Appointment Made Successfully")
                                                        .setTitle("Appointment Made")
                                                        .setIcon(R.mipmap.ic_launcher_round)
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                alertDialog1.cancel();
                                                                goToHome();

                                                          //    createNotificationChannel();

                                                             /*   NotificationCompat.Builder builder2=new NotificationCompat.Builder(context,CHANNEL_ID)
                                                                        .setSmallIcon(R.mipmap.ic_launcher_round)
                                                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round))
                                                                        .setContentTitle("KNH Patient Tracker")
                                                                        .setContentText("A New Appointment has been made")
                                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

                                                                //notification id is unique int for each notification that u must declare
                                                                notificationManagerCompat.notify(ID);

                                                              */

                                                            }
                                                        });
                                                alertDialog1 = builder1.create();
                                                alertDialog1.show();

                                            }

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
                                params.put("user_id", Integer.toString(SharedPrefManager.getInstance(view.getContext()).getUserId()));
                                params.put("doctor_id", doctor_id);
                                params.put("speciality_id", speciality_id);
                                params.put("message", txt_message.getText() + "");
                                params.put("app_date", select_date.getText() + "");
                                params.put("app_time", select_time.getText() + "");


                                return params;
                            }
                        };

                        // RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                        // requestQueue.add(stringRequest);
                        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
                    }
                });


                builder.setView(view);

                dialog = builder.create();
                dialog.show();
            }
        });
       /* holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BookDoctor.class);
                intent.putExtra("ID",doctorModels.get(holder.getAdapterPosition()).getId());

                context.startActivity(intent);
            }
        });

        */
    }

    private void goToHome() {
       context.startActivity(new Intent(context,Dashboard.class));
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name="Notification";
            String description="Simple Notification";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            //NotificationManager notificationManager=getSystemService(NotificationManager.class);
            //notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int getItemCount() {
        return doctorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvName, txAvailableTimes;
        ImageView imgStatus;
        Button btn_bookNow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.txt_id);
            tvName = itemView.findViewById(R.id.txt_reason);
            txAvailableTimes = itemView.findViewById(R.id.txt_notTime);

            imgStatus = itemView.findViewById(R.id.img_status);
            btn_bookNow = itemView.findViewById(R.id.btn_markAsRead);
        }
    }

    public class DateListener implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            String date = year + "/" + month + "/" + dayOfMonth;
            select_date.setText(date);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = hourOfDay + ":" + minute;

            select_time.setText(time);
        }
    }
}
