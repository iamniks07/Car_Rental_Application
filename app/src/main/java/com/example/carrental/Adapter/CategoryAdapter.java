package com.example.carrental.Adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrental.R;
import com.example.carrental.Model.VehicleCategory;
import com.example.carrental.VehicleFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CategoryAdapter extends FirebaseRecyclerAdapter<VehicleCategory,CategoryAdapter.categoryholder>
{

    String customerID;
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<VehicleCategory> options, String customerID) {
        super(options);
        this.customerID = customerID;
    }


//    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<VehicleCategory> options) {
//        super(options);
//    }

    @Override
    protected void onBindViewHolder(@NonNull categoryholder holder, int position, @NonNull VehicleCategory model) {
        holder.vehicleCategory.setText(model.getCategory());
        holder.quantity.setText("Total: " + model.getQuantity());
        holder.card.setBackgroundTintList(ColorStateList.valueOf(model.getColorCard()));
        Glide.with(holder.categoryImage.getContext()).load(model.getUrl()).into(holder.categoryImage);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,
                        new VehicleFragment(model.getCategory(), customerID)).addToBackStack(null).commit();
//                Toast.makeText((Context) activity, (CharSequence) holder.vehicleCategory, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public categoryholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_category_card,parent,false);
        return new categoryholder(view);
    }

    public class categoryholder extends RecyclerView.ViewHolder{

        TextView vehicleCategory, quantity;
        ImageView categoryImage;
        Button select;
        ConstraintLayout card;
//        Button select;
        public categoryholder(@NonNull View itemView) {
            super(itemView);

            vehicleCategory = itemView.findViewById(R.id.vehicleCategory);
            quantity = itemView.findViewById(R.id.quantity);
            card = itemView.findViewById(R.id.card);
            select = itemView.findViewById(R.id.select);
            categoryImage = itemView.findViewById(R.id.categoryImage);


        }
    }
}
