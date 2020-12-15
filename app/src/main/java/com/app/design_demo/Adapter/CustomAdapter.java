package com.app.design_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.design_demo.Model.Model;
import com.app.design_demo.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.holder> {
    Context context;
    List<Model> modelList;

    public CustomAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override

    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( context ).inflate( R.layout.row_item,parent,false );

        return new holder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
    String Name=modelList.get( position ).getName();
    holder.name.setText( Name );

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class holder extends RecyclerView.ViewHolder{
        TextView name;
        public holder(@NonNull View itemView) {
            super( itemView );
            name=itemView.findViewById( R.id.name );
        }
    }
}
