package com.example.bookingcare.ui.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.schedules.Calendar;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class DayWeekAdaptor extends RecyclerView.Adapter<DayWeekAdaptor.DayWeekViewHolder> {

    private Map<String, List<Calendar>> mListCalendar;
    Context context;

    public DayWeekAdaptor(Context context, Map<String, List<Calendar>> listCalendar) {
        this.context = context;
        this.mListCalendar = listCalendar;
    }

    @NonNull
    @Override
    public DayWeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_week, parent, false);
        return new DayWeekViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull DayWeekViewHolder holder, int position) {
        String day = DayOfWeek.values()[position].toString();
        holder.dayWeekName.setText(day);

        TimeSlotAdapter itemInnerRecyclerView = new TimeSlotAdapter(context, mListCalendar.get(day), day);

        holder.innerRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        final Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        final Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.silde_up);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an animation

                if (holder.expandButton.isChecked()) {
                    holder.innerRecyclerView.startAnimation(slideDown);
                    holder.innerRecyclerView.setVisibility(View.VISIBLE);
                    holder.expandButton.setChecked(false);
                } else {
                    holder.innerRecyclerView.startAnimation(slideUp);
                    holder.innerRecyclerView.setVisibility(View.GONE);
                    holder.expandButton.setChecked(true);
                }
            }
        });

        holder.expandButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.innerRecyclerView.startAnimation(slideUp);
                    holder.innerRecyclerView.setVisibility(View.GONE);
                } else{
                    holder.innerRecyclerView.startAnimation(slideDown);
                    holder.innerRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.innerRecyclerView.setAdapter(itemInnerRecyclerView);
    }

    @Override
    public int getItemCount() {
        return mListCalendar.size();
    }

    public class DayWeekViewHolder extends RecyclerView.ViewHolder {
        TextView dayWeekName;
        ToggleButton expandButton;
        RecyclerView innerRecyclerView;
        CardView cardView;

        public DayWeekViewHolder(View itemView) {
            super(itemView);
            dayWeekName = itemView.findViewById(R.id.day_week_name);
            innerRecyclerView = itemView.findViewById(R.id.inner_rcv);
            cardView = itemView.findViewById(R.id.day_week_cv);
            expandButton = itemView.findViewById(R.id.expand_button);
        }
    }
}
