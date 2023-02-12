package com.example.projekt;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class prisutnostViewAdapter  extends ArrayAdapter<PrisutnostView> {
    // invoke the suitable constructor of the ArrayAdapter class
    public prisutnostViewAdapter(@NonNull Context context, ArrayList<PrisutnostView> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.prisutnost, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        PrisutnostView currentPrisutnostPosition = getItem(position);

        TextView subjectNameTextView = currentItemView.findViewById(R.id.subjectName);
        TextView statsTextView = currentItemView.findViewById(R.id.stats);
        subjectNameTextView.setText(currentPrisutnostPosition.getName());
        statsTextView.setText(String.valueOf(currentPrisutnostPosition.getScore()) + "/" + String.valueOf(currentPrisutnostPosition.getTotal()));
        // then return the recyclable view
        return currentItemView;
    }
}
