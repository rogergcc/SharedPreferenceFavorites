/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.fragments;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rogergcc.sharedpreferencefavorites.R;


import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

import java.util.List;

public class RickAndMortyRecyclerViewAdapter extends RecyclerView.Adapter<RickAndMortyRecyclerViewAdapter.ViewHolder> {

    private final List<RickMorty> mValues;


    public RickAndMortyRecyclerViewAdapter(List<RickMorty> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_rickandmorty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mname_character.setText(mValues.get(position).getName());
        holder.mdetails_status.setText(mValues.get(position).getStatus());
        holder.mdetails_species.setText(mValues.get(position).getSpecies());
        holder.mdetails_gender.setText(mValues.get(position).getGender());
        holder.mdetails_origin.setText(mValues.get(position).getOrigin().getName());
        holder.mdetails_last_location.setText(mValues.get(position).getLocation().getName());

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.imagesloading)
                .centerInside()
//                .error(R.drawable.cinema_filled_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(holder.itemView.getContext())
                .load(mValues.get(position).getImage())
                .apply(requestOptions)
                .into(holder.mimage_character);

        holder.mselect_favorit.setOnClickListener(new View.OnClickListener() {
            int button01pos = 0;

            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                //sharedPreference = new MySharedPreference(context);
                if (holder.mselect_favorit.getColorFilter() != null) {
                    holder.mselect_favorit.clearColorFilter();
                    //mFavList.remove(mProductObject.get(position));

                    //String addNuevoItem = gson.toJson(mFavList);

                    //sharedPreference.addProductToTheCart(addNuevoItem);
                } else {
                    holder.mselect_favorit.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
                            R.color.color_select_favorite));
                    //mFavList.add(mProductObject.get(position));

                    //String addNuevoItem = gson.toJson(mFavList);
                    //sharedPreference.addProductToTheCart(addNuevoItem);

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mname_character;

        private final TextView mdetails_status;
        private final TextView mdetails_species;
        private final TextView mdetails_gender;
        private final TextView mdetails_origin;
        private final TextView mdetails_last_location;
        private final ImageView mimage_character;
        private final ImageView mselect_favorit;
        public RickMorty mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mimage_character =  view.findViewById(R.id.image_character);
            mselect_favorit =  view.findViewById(R.id.select_favorit);

            mname_character =  view.findViewById(R.id.name_character);
            mdetails_status =  view.findViewById(R.id.details_status);
            mdetails_species =  view.findViewById(R.id.details_species);
            mdetails_gender=  view.findViewById(R.id.details_gender);
            mdetails_origin=  view.findViewById(R.id.details_origin);
            mdetails_last_location=  view.findViewById(R.id.details_last_location);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mname_character.getText() + "'";
        }
    }
}
