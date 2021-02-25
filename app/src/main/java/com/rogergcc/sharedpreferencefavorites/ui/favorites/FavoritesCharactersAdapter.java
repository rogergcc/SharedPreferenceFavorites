/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.ui.helpers.MySharedPreference;

import java.util.List;


public class FavoritesCharactersAdapter extends RecyclerView.Adapter<FavoritesCharactersAdapter.ViewHolder> {

    private final List<RickMorty> mValues;
    private MySharedPreference sharedPreference;

    private List<RickMorty> mFavoritesList;

    public FavoritesCharactersAdapter(List<RickMorty> items) {
        mValues = items;
        this.mFavoritesList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
//        return mValues.size();
        return mValues == null ? 0 : mValues.size();
    }

    @Override
    public long getItemId(int position) {
        RickMorty rickMorty = mValues.get(position);
        return rickMorty.getId();
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

        holder.mselect_favorite.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
                R.color.color_select_favorite));


        holder.mselect_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();

                sharedPreference = new MySharedPreference(holder.itemView.getContext());

                mFavoritesList.remove(mValues.get(position));

                String addNewItem = gson.toJson(mFavoritesList);

                sharedPreference.saveFavoritesMarkers(addNewItem);
                notifyItemRemoved(position);

            }
        });
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CardView itemCardViewCharacter;
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
            itemCardViewCharacter = view.findViewById(R.id.itemCardViewCharacter);

            mimage_character = view.findViewById(R.id.image_character);
            mselect_favorite = view.findViewById(R.id.select_favorite);

            mname_character = view.findViewById(R.id.name_character);
            mdetails_status = view.findViewById(R.id.details_status);
            mdetails_species = view.findViewById(R.id.details_species);
            mdetails_gender = view.findViewById(R.id.details_gender);
            mdetails_origin = view.findViewById(R.id.details_origin);
            mdetails_last_location = view.findViewById(R.id.details_last_location);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mname_character.getText() + "'";
        }
    }
}
