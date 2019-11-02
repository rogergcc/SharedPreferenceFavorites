/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.adapters.RickAndMortyRecyclerViewAdapter;
import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.helpers.VolleySingleton;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RickAndMortyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private List<RickMorty> rickMortyCharactersList;
    private ProgressDialog pd;
    private RickAndMortyRecyclerViewAdapter adapterapi;
    private ArrayList<RickMorty> mFavoritesList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RickAndMortyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RickAndMortyFragment newInstance(int columnCount) {
        RickAndMortyFragment fragment = new RickAndMortyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rickandmorty_list, container, false);

        recyclerView = view.findViewById(R.id.list);


        linearLayoutManager = new LinearLayoutManager(getContext());
        pd = new ProgressDialog(getContext());

        pd.setTitle("Obtain Characters");
        pd.setMessage("Api Data");
        pd.setCancelable(false);
        pd.show();

        rickMortyCharactersList = new ArrayList<>();


        loadFavoritesData();

        getCharacters();


        return view;
    }

    /*
     * Get Characters of api Rick & Morty
     * */
    public void getCharacters() {

        rickMortyCharactersList.clear();
        String url = "https://rickandmortyapi.com/api/character/?page=2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.dismiss();
                        if (response != null) {

//                            try {
                            RickMortyResponse rickMortyResponse;

                            Type type = new TypeToken<RickMortyResponse>() {
                            }.getType();

                            rickMortyResponse = new Gson().fromJson(response, type);
                            rickMortyCharactersList = rickMortyResponse.getResults();

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    linearLayoutManager.getOrientation());
                            recyclerView.addItemDecoration(dividerItemDecoration);

                            adapterapi = new RickAndMortyRecyclerViewAdapter(mFavoritesList, rickMortyCharactersList);
                            recyclerView.setAdapter(adapterapi);
                            adapterapi.notifyDataSetChanged();

                        }

                        //if no error in response

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Log.e("Error", "Error Volley");
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            DynamicToast.makeWarning(getBaseContext(), "Error Tiempo de Respuesta Inicio de Sesión", Toast.LENGTH_LONG).show();
                            Log.e("Error", "Error Tiempo Respuest");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("nr", nr_ticket);

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void loadFavoritesData() {
        MySharedPreference sharedPreference = new MySharedPreference(getContext());
        String productsFromCart = sharedPreference.retrieveFavorites();
        Type type = new TypeToken<ArrayList<RickMorty>>() {
        }.getType();

        mFavoritesList = new Gson().fromJson(productsFromCart, type);
        if (mFavoritesList == null) {
            mFavoritesList = new ArrayList<>();
        }
    }

}
