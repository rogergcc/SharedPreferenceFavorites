/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.adapters;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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


import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

import java.util.List;

public class RickAndMortyRecyclerViewAdapter extends RecyclerView.Adapter<RickAndMortyRecyclerViewAdapter.ViewHolder> {

    private MySharedPreference sharedPreference;

    private final List<RickMorty> mValues;


    private List<RickMorty> mFavoritesList;

    public RickAndMortyRecyclerViewAdapter(List<RickMorty> mFavorites, List<RickMorty> items) {
        this.mFavoritesList = mFavorites;
        this.mValues = items;

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
        final int pos = holder.getAdapterPosition();
        //Set ViewTag
        holder.itemView.setTag(pos);

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

        if (mFavoritesList.size() != 0) {
            boolean itemExists = checkAvailability(mFavoritesList, mValues.get(position));

            if (itemExists) {
                holder.mselect_favorite.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
                        R.color.color_select_favorite));

            } else {
                holder.mselect_favorite.clearColorFilter();
            }
        }


        holder.mselect_favorite.setOnClickListener(new View.OnClickListener() {
            int button01pos = 0;

            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                sharedPreference = new MySharedPreference(holder.itemView.getContext());
                if (holder.mselect_favorite.getColorFilter() != null) {
                    holder.mselect_favorite.clearColorFilter();
                    mFavoritesList.remove(mValues.get(pos));

                    String addNewItem = gson.toJson(mFavoritesList);

                    sharedPreference.saveFavoritesMarkers(addNewItem);
                } else {
                    holder.mselect_favorite.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
                            R.color.color_select_favorite));
                    mFavoritesList.add(mValues.get(pos));

                    String addNewItem = gson.toJson(mFavoritesList);
                    sharedPreference.saveFavoritesMarkers(addNewItem);

                }


            }
        });
    }


    public static boolean checkAvailability(List<RickMorty> rickMortyList, RickMorty rickMorty) {
        for (RickMorty p : rickMortyList) {
            if (p.equals(rickMorty)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public long getItemId(int position) {
        RickMorty rickMorty = mValues.get(position);
        return rickMorty.getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        private final ImageView mselect_favorite;
        public RickMorty mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mimage_character = view.findViewById(R.id.image_character);
            mselect_favorite = view.findViewById(R.id.select_favorite);

            mname_character = view.findViewById(R.id.name_character);
            mdetails_status = view.findViewById(R.id.details_status);
            mdetails_species = view.findViewById(R.id.details_species);
            mdetails_gender = view.findViewById(R.id.details_gender);
            mdetails_origin = view.findViewById(R.id.details_origin);
            mdetails_last_location = view.findViewById(R.id.details_last_location);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mname_character.getText() + "'";
        }
    }
}
