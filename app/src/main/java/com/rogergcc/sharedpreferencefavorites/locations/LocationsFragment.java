/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.locations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.databinding.FragmentLocationsBinding;
import com.rogergcc.sharedpreferencefavorites.remote.CommonApiUrl;
import com.rogergcc.sharedpreferencefavorites.remote.model.LocationResponse;
import com.rogergcc.sharedpreferencefavorites.utils.AppLogger;
import com.rogergcc.sharedpreferencefavorites.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.rogergcc.sharedpreferencefavorites.helpers.PaginationListener.PAGE_START;


public class LocationsFragment extends Fragment {


    FragmentLocationsBinding binding;
    private Context mcontext;

    private LinearLayoutManager linearLayoutManager;


    private List<LocationResponse.Location> rickMortyCharactersList;
    private ProgressDialog pd;
    private LocationsAdapter adapterapi;
    private int currentPage;

    private ProgressDialog mProgressDialog;
    private int totalPage = 2;

    public LocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationsBinding.inflate(getLayoutInflater());

//        binding = FragmentRickandmortyListBinding.inflate(getLayoutInflater());
        mcontext = this.getActivity();
        View view = binding.getRoot();

        binding.list.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.list.setLayoutManager(linearLayoutManager);

        rickMortyCharactersList = new ArrayList<>();


        adapterapi = new LocationsAdapter(rickMortyCharactersList);
        binding.list.setAdapter(adapterapi);

        currentPage = PAGE_START;
        getLocationsCharacters();

//        return inflater.inflate(R.layout.fragment_locations, container, false);
        return view;

    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showLoading() {
        //hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(mcontext);
    }

    public void getLocationsCharacters() {
        showLoading();

        //region REGION SEND POST RETROFIT
        Call<LocationResponse> call = CommonApiUrl.getGeoJsonData().getLocations(currentPage);
        call.enqueue(new Callback<LocationResponse>() {

            @Override
            public void onResponse(@NonNull Call<LocationResponse> call, retrofit2.Response<LocationResponse> response) {
                hideLoading();
                if (response.body() == null) return;

//                            try {
                LocationResponse rickMortyResponse = response.body();

                //rickMortyCharactersList = rickMortyResponse.getResults();
                totalPage = rickMortyResponse.getInfo().getPages();
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.list.getContext(),
//                                    linearLayoutManager.getOrientation());
//                            binding.list.addItemDecoration(dividerItemDecoration);

//                            adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
//                            binding.list.setAdapter(adapterapi);


                int oldCount = rickMortyCharactersList.size();
                rickMortyCharactersList.addAll(rickMortyResponse.getResults());
                adapterapi.notifyItemRangeInserted(oldCount, rickMortyCharactersList.size());

            }

            @Override
            public void onFailure(@NonNull Call<LocationResponse> call, Throwable t) {

                hideLoading();
                Toast.makeText(mcontext, getString(R.string.message_somethin_wrong), Toast.LENGTH_SHORT).show();
                AppLogger.e("LISTA_ERROR: " + t.getMessage());
            }
        });
        //endregion


    }

}