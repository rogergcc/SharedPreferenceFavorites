/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.databinding.ItemLoadingBinding;
import com.rogergcc.sharedpreferencefavorites.databinding.ItemsCharacterBinding;
import com.rogergcc.sharedpreferencefavorites.helpers.BaseViewHolder;
import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

import java.util.List;

public class ListCharactersAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private final List<RickMorty> mValues;
    private MySharedPreference sharedPreference;
    private List<RickMorty> mFavoritesList;
    private boolean isLoaderVisible = false;


    public ListCharactersAdapter(List<RickMorty> mFavorites, List<RickMorty> items) {
        this.mFavoritesList = mFavorites;
        this.mValues = items;

    }

    public static boolean checkAvailability(List<RickMorty> rickMortyList, RickMorty rickMorty) {
        for (RickMorty p : rickMortyList) {
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

        ItemsCharacterBinding binding = ItemsCharacterBinding.inflate(layoutInflater, parent, false);
        ItemLoadingBinding itemLoadingBinding = ItemLoadingBinding.inflate(layoutInflater, parent, false);

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.items_character, parent, false);
//        return new ViewHolder(view);

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        binding);
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        itemLoadingBinding);
            default:
                return null;
        }

    }

//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        final int pos = holder.getAdapterPosition();
//        //Set ViewTag
//        holder.itemView.setTag(pos);
//
//        holder.mname_character.setText(mValues.get(position).getName());
//        holder.mdetails_status.setText(mValues.get(position).getStatus());
//        holder.mdetails_species.setText(mValues.get(position).getSpecies());
//        holder.mdetails_gender.setText(mValues.get(position).getGender());
//        holder.mdetails_origin.setText(mValues.get(position).getOrigin().getName());
//        holder.mdetails_last_location.setText(mValues.get(position).getLocation().getName());
//
//
//        RequestOptions requestOptions = new RequestOptions()
//                .fitCenter()
//                .placeholder(R.drawable.imagesloading)
//                .centerInside()
////                .error(R.drawable.cinema_filled_error)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH)
//                .dontAnimate()
//                .dontTransform();
//
//        Glide.with(holder.itemView.getContext())
//                .load(mValues.get(position).getImage())
//                .apply(requestOptions)
//                .into(holder.mimage_character);
//
//        if (mFavoritesList.size() != 0) {
//            boolean itemExists = checkAvailability(mFavoritesList, mValues.get(position));
//
//            if (itemExists) {
//                holder.mselect_favorite.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
//                        R.color.color_select_favorite));
//
//                holder.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(holder.mView.getContext(), R.color.colorPrimaryDark));
//
//            } else {
//                holder.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(holder.mView.getContext(), R.color.color_details));
//                holder.mselect_favorite.clearColorFilter();
//            }
//        }
//
//
//        holder.mselect_favorite.setOnClickListener(new View.OnClickListener() {
//            int button01pos = 0;
//
//            @Override
//            public void onClick(View v) {
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//
//                sharedPreference = new MySharedPreference(holder.itemView.getContext());
//                if (holder.mselect_favorite.getColorFilter() != null) {
//                    holder.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(holder.mView.getContext(), R.color.color_details));
//                    holder.mselect_favorite.clearColorFilter();
//                    mFavoritesList.remove(mValues.get(pos));
//
//                    String addNewItem = gson.toJson(mFavoritesList);
//
//                    sharedPreference.saveFavoritesMarkers(addNewItem);
//                } else {
//                    holder.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(holder.mView.getContext(), R.color.colorPrimaryDark));
//                    holder.mselect_favorite.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(),
//                            R.color.color_select_favorite));
//                    mFavoritesList.add(mValues.get(pos));
//
//                    String addNewItem = gson.toJson(mFavoritesList);
//                    sharedPreference.saveFavoritesMarkers(addNewItem);
//
//                }
//
//
//            }
//        });
//    }

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
//        RickMorty rickMorty = mValues.get(position);
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

    //region METHOD FOR ADDING MORE ITEMS IN SCROLL
    public void addItems(List<RickMorty> postItems) {
        mValues.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        mValues.add(new RickMorty());
        notifyItemInserted(mValues.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mValues.size() - 1;
        RickMorty item = getItem(position);
        if (item != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }

    RickMorty getItem(int position) {
        return mValues.get(position);
    }
    //endregion

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final CardView itemCardViewCharacter;
//        public final TextView mname_character;
//
//        private final TextView mdetails_status;
//        private final TextView mdetails_species;
//        private final TextView mdetails_gender;
//        private final TextView mdetails_origin;
//        private final TextView mdetails_last_location;
//        private final ImageView mimage_character;
//        private final ImageView mselect_favorite;
//        public RickMorty mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            itemCardViewCharacter = view.findViewById(R.id.itemCardViewCharacter);
//            mimage_character = view.findViewById(R.id.image_character);
//            mselect_favorite = view.findViewById(R.id.select_favorite);
//
//            mname_character = view.findViewById(R.id.name_character);
//            mdetails_status = view.findViewById(R.id.details_status);
//            mdetails_species = view.findViewById(R.id.details_species);
//            mdetails_gender = view.findViewById(R.id.details_gender);
//            mdetails_origin = view.findViewById(R.id.details_origin);
//            mdetails_last_location = view.findViewById(R.id.details_last_location);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mname_character.getText() + "'";
//        }
//    }

    public class ViewHolder extends BaseViewHolder {
        ItemsCharacterBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            titleTxtView = itemView.findViewById(R.id.title_tv);
//            contentImgView = itemView.findViewById(R.id.content_iv);
        }


        public ViewHolder(@NonNull ItemsCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            //this.itemRecyclerMealBinding.
        }

        @Override
        public void bindViews(final int position) {
            super.bindViews(position);
            RickMorty playerPosition = mValues.get(position);




        binding.nameCharacter.setText(mValues.get(position).getName());
            binding.detailsStatus.setText(mValues.get(position).getStatus());
            binding.detailsSpecies.setText(mValues.get(position).getSpecies());
            binding.detailsGender.setText(mValues.get(position).getGender());
            binding.detailsOrigin.setText(mValues.get(position).getOrigin().getName());
            binding.detailsLastLocation.setText(mValues.get(position).getLocation().getName());


        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.imagesloading)
                .centerInside()
//                .error(R.drawable.cinema_filled_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(itemView.getContext())
                .load(mValues.get(position).getImage())
                .apply(requestOptions)
                .into(binding.imageCharacter);

        if (mFavoritesList.size() != 0) {
            boolean itemExists = checkAvailability(mFavoritesList, mValues.get(position));

            if (itemExists) {
                binding.selectFavorite.setColorFilter(ContextCompat.getColor(itemView.getContext(),
                        R.color.color_select_favorite));

                binding.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark));

            } else {
                binding.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.color_details));
                binding.selectFavorite.clearColorFilter();
            }
        }


        binding.selectFavorite.setOnClickListener(new View.OnClickListener() {
            int button01pos = 0;

            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();

                sharedPreference = new MySharedPreference(itemView.getContext());
                if (binding.selectFavorite.getColorFilter() != null) {
                    binding.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.color_details));
                    binding.selectFavorite.clearColorFilter();
                    mFavoritesList.remove(mValues.get(position));

                    String addNewItem = gson.toJson(mFavoritesList);

                    sharedPreference.saveFavoritesMarkers(addNewItem);
                } else {
                    binding.itemCardViewCharacter.setCardBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark));
                    binding.selectFavorite.setColorFilter(ContextCompat.getColor(itemView.getContext(),
                            R.color.color_select_favorite));
                    mFavoritesList.add(mValues.get(position));

                    String addNewItem = gson.toJson(mFavoritesList);
                    sharedPreference.saveFavoritesMarkers(addNewItem);

                }


            }
        });
        }

        @Override
        public void clear() {

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
}
