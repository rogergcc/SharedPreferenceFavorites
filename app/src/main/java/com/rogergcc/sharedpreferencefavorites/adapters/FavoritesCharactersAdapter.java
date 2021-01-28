/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.fragments.FavoriteFragment.OnListFragmentInteractionListener;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

import java.util.List;


public class FavoritesCharactersAdapter extends RecyclerView.Adapter<FavoritesCharactersAdapter.ViewHolder> {

    private final List<RickMorty> mValues;
    private final OnListFragmentInteractionListener mListener;

    public FavoritesCharactersAdapter(List<RickMorty> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_character, parent, false);
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


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
