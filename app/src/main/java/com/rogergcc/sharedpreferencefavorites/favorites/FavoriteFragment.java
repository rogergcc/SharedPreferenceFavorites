/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.favorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FavoritesCharactersAdapter favoritesAdapter;
    private MySharedPreference mShared;
    private List<RickMorty> favoritesList;

    private RelativeLayout llEmptyList;

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        llEmptyList = view.findViewById(R.id.llEmptyList);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //favoritesAdapter = new FavoritesCharactersAdapter();

        mShared = new MySharedPreference(context);

        String favoriteCharacters = mShared.retrieveFavorites();

//        Toast.makeText(context, "ROGER" + productsFavorites, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<RickMorty>>() {
        }.getType();

        if (favoriteCharacters.length() != 0 && !favoriteCharacters.equals("[]")) {
            favoritesList = new Gson().fromJson(favoriteCharacters, type);
            FavoritesCharactersAdapter favoritesAdapter = new FavoritesCharactersAdapter(favoritesList);
            recyclerView.setAdapter(favoritesAdapter);
            llEmptyList.setVisibility(View.GONE);
        } else {
            llEmptyList.setVisibility(View.VISIBLE);
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}
