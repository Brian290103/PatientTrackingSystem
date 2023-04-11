package com.example.patienttrackingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    ArrayList<NotificationModel> notificationModels;

    ProgressDialog progressDialog;

    public void setSearchList(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        notifyDataSetChanged();
    }

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationModels) {
        this.context = context;
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_row, parent, false);

        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {



        NotificationModel currentItem = notificationModels.get(position);

        holder.tvId.setText(position + 1 + "");
        holder.tvReason.setText(notificationModels.get(position).getReason());
        holder.tvTime.setText(notificationModels.get(position).date);

        //int colorRes = R.color.soft_green;

        if (notificationModels.get(position).isRead) {
            // holder.btn_markAsRead.setBackgroundColor(colorRes);
            holder.btn_markAsRead.setBackgroundColor(Color.GREEN);
        }


        holder.btn_markAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Marked as Read", Toast.LENGTH_SHORT).show();

                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Updating...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_NOTIFICATIONS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String message = jsonObject.getString("message");

                                    if (!jsonObject.getBoolean("error")) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context, Dashboard.class));
                                    } else {
                                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        params.put("id", Integer.toString(currentItem.getId()));

                        return params;
                    }
                };

                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvReason;
        TextView tvTime;
        Button btn_markAsRead;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvReason = itemView.findViewById(R.id.txt_reason);
            tvId = itemView.findViewById(R.id.txt_id);
            tvTime = itemView.findViewById(R.id.txt_notTime);
            btn_markAsRead = itemView.findViewById(R.id.btn_markAsRead);


        }
    }
}
