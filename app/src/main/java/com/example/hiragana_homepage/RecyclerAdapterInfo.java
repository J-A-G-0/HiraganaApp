package com.example.hiragana_homepage;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for recyclerview used in Info Activity.
 */

public class RecyclerAdapterInfo extends RecyclerView.Adapter<RecyclerAdapterInfo.ViewHolder> {

    private static final String TAG = "InfoAdapter";
    private final List<InfoEntry> entriesList;

    public RecyclerAdapterInfo(List<InfoEntry> entriesList) {
        this.entriesList = entriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_entry,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfoEntry infoEntry = entriesList.get(position);
        holder.titleTextView.setText(infoEntry.getTitle());
        holder.contentsTextView.setText(infoEntry.getContents());

        boolean isExpanded = entriesList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "InfoVH";

        TextView titleTextView, contentsTextView;
        ConstraintLayout expandableLayout;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tv_entry_name);
            contentsTextView = itemView.findViewById(R.id.tv_entry_contents);
            contentsTextView.setMovementMethod(LinkMovementMethod.getInstance());
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InfoEntry entry = entriesList.get(getAdapterPosition());
                    entry.setExpanded(!entry.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
