package com.main.sampleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MessageViewHolder> {

    private ArrayList<model> imageModelArrayList = new ArrayList<>();
    private Context context;

    public Adapter(ArrayList<model> horizontalList, Context context) {
        this.imageModelArrayList = horizontalList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final model model = imageModelArrayList.get(position);
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;

        messageViewHolder.imageView.setImageResource(model.getImage());
        messageViewHolder.textView.setText(model.getName());

        messageViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model.getName()+" - "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        private MessageViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageMain);
            textView = view.findViewById(R.id.name);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MessageViewHolder(itemView);
    }
}
