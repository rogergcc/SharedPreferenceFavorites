/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.rogergcc.sharedpreferencefavorites.adapters.ListCharactersAdapter;
import com.rogergcc.sharedpreferencefavorites.databinding.FragmentRickandmortyListBinding;
import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.helpers.PaginationListener;
import com.rogergcc.sharedpreferencefavorites.helpers.VolleySingleton;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rogergcc.sharedpreferencefavorites.helpers.PaginationListener.PAGE_START;


public class RickAndMortyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private LinearLayoutManager linearLayoutManager;


    private List<RickMorty> rickMortyCharactersList;
    private ProgressDialog pd;
    private ListCharactersAdapter adapterapi;
    private ArrayList<RickMorty> mFavoritesList;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 2;
    private boolean isLoading = false;
    private FragmentRickandmortyListBinding binding;
    private Context mcontext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RickAndMortyFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_rickandmorty_list, container, false);

        binding = FragmentRickandmortyListBinding.inflate(getLayoutInflater());
        mcontext = this.getActivity();

        View view = binding.getRoot();

//        binding.list = view.findViewById(R.id.list);

        pd = new ProgressDialog(getContext());

        pd.setTitle("Obtain Characters");
        pd.setMessage("loading...");
        pd.setCancelable(false);
        pd.show();


        binding.list.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        binding.list.setLayoutManager(linearLayoutManager);

        rickMortyCharactersList = new ArrayList<>();

        loadFavoritesData();

        adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
        binding.list.setAdapter(adapterapi);

        getCharacters();

        binding.list.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getCharacters();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        return view;
    }

    /*
     * Get Characters of api Rick & Morty
     * */
    public void getCharacters() {

        rickMortyCharactersList.clear();
        String url = "https://rickandmortyapi.com/api/character/?page="+currentPage;
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
                            totalPage= rickMortyResponse.getInfo().getPages();
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.list.getContext(),
//                                    linearLayoutManager.getOrientation());
//                            binding.list.addItemDecoration(dividerItemDecoration);

//                            adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
//                            binding.list.setAdapter(adapterapi);

                            if (currentPage != PAGE_START) adapterapi.removeLoading();
                            adapterapi.addItems(rickMortyCharactersList);
                            //swipeRefresh.setRefreshing(false);
                            // check weather is last page or not

                            if (currentPage < totalPage) {
                                adapterapi.addLoading();
                            } else {
                                isLastPage = true;
                            }
                            isLoading = false;

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
        MySharedPreference sharedPreference = new MySharedPreference(mcontext);
        String productsFromCart = sharedPreference.retrieveFavorites();
        Type type = new TypeToken<ArrayList<RickMorty>>() {
        }.getType();

        mFavoritesList = new Gson().fromJson(productsFromCart, type);
        if (mFavoritesList == null) {
            mFavoritesList = new ArrayList<>();
        }
    }

}
