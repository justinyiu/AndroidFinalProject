package com.cst2335.androidfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList drinkName, drinkInstructions, drinkIng1, drinkIng2, drinkIng3;

    public MyAdapter(Context context, ArrayList drinkName, ArrayList drinkInstructions, ArrayList drinkIng1, ArrayList drinkIng2, ArrayList drinkIng3) {
        this.context = context;
        this.drinkName = drinkName;
        this.drinkInstructions = drinkInstructions;
        this.drinkIng1 = drinkIng1;
        this.drinkIng2 = drinkIng2;
        this.drinkIng3 = drinkIng3;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cocktailentry, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(drinkName.get(position)));
        holder.instruction_id.setText(String.valueOf(drinkInstructions.get(position)));
        holder.ing1_id.setText(String.valueOf(drinkIng1.get(position)));
        holder.ing2_id.setText(String.valueOf(drinkIng2.get(position)));
        holder.ing3_id.setText(String.valueOf(drinkIng3.get(position)));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return drinkName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, instruction_id, ing1_id, ing2_id, ing3_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textname);
            instruction_id = itemView.findViewById(R.id.textinstructions);
            ing1_id = itemView.findViewById(R.id.textingredient1);
            ing2_id = itemView.findViewById(R.id.textingredient2);
            ing3_id = itemView.findViewById(R.id.textingredient3);
        }
    }
}
