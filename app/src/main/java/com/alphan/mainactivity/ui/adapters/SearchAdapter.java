package com.alphan.mainactivity.ui.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.alphan.mainactivity.R;
import com.alphan.mainactivity.models.SearchPlaceModel;

import java.util.List;

import javax.inject.Inject;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    @Inject
    Application context;

    private List<SearchPlaceModel> mPlaces;
    private OnItemClicked onClick;

    public SearchAdapter(List<SearchPlaceModel> places, OnItemClicked listener) {
        this.mPlaces = places;
        this.onClick = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_place, viewGroup, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        SearchPlaceModel place = mPlaces.get(i);
        searchViewHolder.mainPlaceNameTv.setText(place.getMainPlaceName());
        searchViewHolder.placeInfoTv.setText(place.getPlaceInfo());

        searchViewHolder.parentConstraintLayout.setOnClickListener(v -> onClick.onItemClicked(place.getLat(), place.getLng()));
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onItemClicked(double lat, double lng);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout parentConstraintLayout;
        TextView mainPlaceNameTv, placeInfoTv;
        View grayLine;

        SearchViewHolder(View itemView) {
            super(itemView);
            parentConstraintLayout = itemView.findViewById(R.id.parent);
            grayLine = itemView.findViewById(R.id.grayLine);

            mainPlaceNameTv = itemView.findViewById(R.id.placeMainNameTv);
            placeInfoTv = itemView.findViewById(R.id.placeDescriptionTv);
        }
    }
}