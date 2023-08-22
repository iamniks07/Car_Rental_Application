package com.example.carrental.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrental.Model.Booking2;
import com.example.carrental.BookingSummaryActivity;
import com.example.carrental.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BookingAdapter extends FirebaseRecyclerAdapter<Booking2,BookingAdapter.myviewholder>
{
    String customerID;
    public BookingAdapter(@NonNull FirebaseRecyclerOptions<Booking2> options, String customerID) {
        super(options);
        this.customerID = customerID;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Booking2 model) {

        holder.bookingID.setText(model.getBookingID()+"");
        holder.bookingStatus.setText(model.getBookingStatus());
        holder.pickupDate.setText(model.getPickupTime());
        holder.returnDate.setText(model.getReturnTime());
        holder.vehicleName.setText(model.getVehicleName());
        holder.customerName.setText(model.getCustomerName());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_card,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView vehicleName, bookingID, customerName,
                pickupDate, returnDate, bookingStatus;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            bookingID = itemView.findViewById(R.id.bookingId);
            bookingStatus = itemView.findViewById(R.id.bookingStatus);
            pickupDate = itemView.findViewById(R.id.pickDate);
            returnDate = itemView.findViewById(R.id.retDate);
            vehicleName = itemView.findViewById(R.id.vehName);
            customerName = itemView.findViewById(R.id.customerName);
        }
    }
}