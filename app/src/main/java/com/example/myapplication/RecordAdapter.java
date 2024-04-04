package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private List<RecordModel> recordList;

    public RecordAdapter(List<RecordModel>recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        RecordModel record = recordList.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView causeOfBDTextView, sparesTextView, timetextTextView, textView1TextView,
                hrstextTextView, datetextTextView, natureOfProblemTextView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            causeOfBDTextView = itemView.findViewById(R.id.causeOfBDTextView);
            sparesTextView = itemView.findViewById(R.id.sparesTextView);
            timetextTextView = itemView.findViewById(R.id.timetextTextView);
            textView1TextView = itemView.findViewById(R.id.textView1TextView);
            hrstextTextView = itemView.findViewById(R.id.hrstextTextView);
            datetextTextView = itemView.findViewById(R.id.datetextTextView);
            natureOfProblemTextView = itemView.findViewById(R.id.natureOfProblemTextView);
        }

        public void bind(RecordModel record) {
            causeOfBDTextView.setText(record.getA2());
            sparesTextView.setText(record.getA3());
            timetextTextView.setText(record.getA4());
            textView1TextView.setText(record.getA5());
            hrstextTextView.setText(record.getA6());
            datetextTextView.setText(record.getA7());
            natureOfProblemTextView.setText(record.getA8());
        }
    }
}