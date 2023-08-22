package com.example.carrental.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrental.R;
import com.example.carrental.Model.Vehicle;
import com.example.carrental.UserViewActivity;
import com.example.carrental.VehicleInfoActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VehicleAdapter extends FirebaseRecyclerAdapter<Vehicle,VehicleAdapter.vehicleholder>
{

    public static String title = "";
    Context context;
    String ID;
    public VehicleAdapter(@NonNull FirebaseRecyclerOptions<Vehicle> options,String customerId) {
        super(options);
        this.ID = customerId;
    }

    @Override
    protected void onBindViewHolder(@NonNull vehicleholder holder, int position, @NonNull Vehicle model) {
        holder.vehicle.setText(model.getYear() + " " + model.getManufacturer() + " " + model.getModel());
        holder.price.setText("Rs." + model.getPrice() + "/Day");
        Glide.with(holder.imageView.getContext()).load(model.getPurl()).into(holder.imageView);

        title = model.fullTitle();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , VehicleInfoActivity.class);
                intent.putExtra("title",model.fullTitle());
                intent.putExtra("id",model.getId());
                intent.putExtra("price",model.getPrice());
                intent.putExtra("seats",model.getSeats());
                intent.putExtra("mileage",model.getMileage());
                intent.putExtra("manufacturer",model.getManufacturer());
                intent.putExtra("model",model.getModel());
                intent.putExtra("year",model.getYear());
                intent.putExtra("category",model.getCategory());
                intent.putExtra("available",model.getAvailability());
                intent.putExtra("url",model.getPurl());
                intent.putExtra("customerID",ID);
                context.startActivity(intent);
//                Toast.makeText(context, model.getVehicleID(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public vehicleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_card,parent,false);
        return new vehicleholder(view);
    }

    public class vehicleholder extends RecyclerView.ViewHolder
    {
        TextView vehicle, detail, price;
        ImageView imageView;
        ConstraintLayout card;
        public vehicleholder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            vehicle = itemView.findViewById(R.id.vehicle);
            detail = itemView.findViewById(R.id.detail);
            card = itemView.findViewById(R.id.card);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.vehicleImage);


        }
    }
}
