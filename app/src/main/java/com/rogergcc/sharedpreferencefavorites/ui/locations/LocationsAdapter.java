/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.locations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rogergcc.sharedpreferencefavorites.databinding.ItemLoadingBinding;
import com.rogergcc.sharedpreferencefavorites.databinding.ItemLocationBinding;
import com.rogergcc.sharedpreferencefavorites.remote.model.LocationResponse;
import com.rogergcc.sharedpreferencefavorites.ui.helpers.BaseViewHolder;
import com.rogergcc.sharedpreferencefavorites.ui.helpers.MySharedPreference;

import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private List<LocationResponse.Location> mValues;
    private MySharedPreference sharedPreference;
    private List<LocationResponse.Location> mFavoritesList;
    private boolean isLoaderVisible = false;

    private ILocationListener iLocationListener;

    public LocationsAdapter( List<LocationResponse.Location> items,ILocationListener iLocationListener) {

        this.mValues = items;
        this.iLocationListener= iLocationListener;
    }

    public static boolean checkAvailability(List<LocationResponse.Location> rickMortyList, LocationResponse.Location rickMorty) {
        for (LocationResponse.Location p : rickMortyList) {
            if (p.equals(rickMorty)) {
                return true;
            }
        }

        return false;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemLocationBinding binding = ItemLocationBinding.inflate(layoutInflater, parent, false);
//        ItemLoadingBinding itemLoadingBinding = ItemLoadingBinding.inflate(layoutInflater, parent, false);

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.items_character, parent, false);
//        return new ListViewHolder(view);
        return new ListViewHolder(
                binding);

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new ListViewHolder(
//                        binding);
//            case VIEW_TYPE_LOADING:
//                return new ProgressHolder(
//                        itemLoadingBinding);
//            default:
//
//                return null;
//        }


    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        final int pos = holder.getAdapterPosition();
        //Set ViewTag
        holder.itemView.setTag(pos);

        holder.bindViews(position);
    }

    @Override
    public int getItemCount() {
//        return mValues.size();
        return mValues == null ? 0 : mValues.size();
    }

//    @Override
//    public long getItemId(int position) {
//        LocationResponse.Location rickMorty = mValues.get(position);
//        return rickMorty.getId();
//    }

    @Override
    public int getItemViewType(int position) {
//        return position;
        if (isLoaderVisible) {
            return position == mValues.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    public static class ProgressHolder extends BaseViewHolder {
        ItemLoadingBinding itemLoadingBinding;

        public ProgressHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ProgressHolder(@NonNull ItemLoadingBinding binding) {
            super(binding.getRoot());
            this.itemLoadingBinding = binding;
            //this.itemRecyclerMealBinding.
        }

        @Override
        public void bindViews(int position) {
            super.bindViews(position);
        }

        @Override
        public void clear() {

        }
    }

    public class ListViewHolder extends BaseViewHolder {
        ItemLocationBinding binding;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

//            titleTxtView = itemView.findViewById(R.id.title_tv);
//            contentImgView = itemView.findViewById(R.id.content_iv);
        }


        public ListViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            //this.itemRecyclerMealBinding.
        }

        @Override
        public void bindViews(final int position) {
            super.bindViews(position);
            LocationResponse.Location location = mValues.get(position);


            binding.tvNameLocation.setText(mValues.get(position).getName());
            binding.tvDimensionLocation.setText(mValues.get(position).getDimension());
            binding.tvTypeLocation.setText(mValues.get(position).getType());

            binding.itemCardViewCharacter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iLocationListener.ondLocationClick(mValues.get(position));
                }
            });
        }

        @Override
        public void clear() {

        }
    }
}
