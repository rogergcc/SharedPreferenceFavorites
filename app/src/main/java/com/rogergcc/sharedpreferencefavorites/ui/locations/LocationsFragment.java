/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.locations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.databinding.FragmentLocationsBinding;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.remote.CommonApiUrl;
import com.rogergcc.sharedpreferencefavorites.remote.model.LocationResponse;
import com.rogergcc.sharedpreferencefavorites.ui.homecharacters.HomeCharactersViewModel;
import com.rogergcc.sharedpreferencefavorites.ui.utils.AppLogger;
import com.rogergcc.sharedpreferencefavorites.ui.utils.CommonUtils;
import com.rogergcc.sharedpreferencefavorites.ui.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rogergcc.sharedpreferencefavorites.ui.helpers.PaginationListener.PAGE_START;


public class LocationsFragment extends Fragment implements ILocationListener {


    FragmentLocationsBinding binding;
    List<String> personajes = new ArrayList<>();
    private Context mcontext;
    private LinearLayoutManager linearLayoutManager;
    private List<LocationResponse.Location> rickMortyCharactersList;
    private ProgressDialog pd;
    private LocationsAdapter adapterapi;
    private int currentPage;
    private ProgressDialog mProgressDialog;
    private int totalPage = 2;
    private HomeCharactersViewModel viewModel;
    private NavController navController;

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


        adapterapi = new LocationsAdapter(rickMortyCharactersList, this);
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
        mProgressDialog = CommonUtils.showLoadingDialogMessage(mcontext,"loading Characters");
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

    @Override
    public void onDashboardCourseClick(LocationResponse locationResponse, ImageView imageView) {

    }

    @Override
    public void ondLocationClick(LocationResponse.Location locationResponse) {
//        Toast.makeText(mcontext, locationResponse.getResidents().toString(), Toast.LENGTH_SHORT).show();

        List<String> personajesDetail = new ArrayList<>();
        showLoading();
        String lastValue= ListUtils.getLast(locationResponse.getResidents());
        for (String resident : locationResponse.getResidents()) {
            int p = resident.lastIndexOf("/");
            String codigo = resident.substring(p + 1);

            CommonApiUrl.getGeoJsonData().getCharacterDetails(Integer.parseInt(codigo)).enqueue(new Callback<RickMorty>() {
                @Override
                public void onResponse(Call<RickMorty> call, Response<RickMorty> response) {
                    if (!response.isSuccessful()){
                        hideLoading();
                        return;
                    }
                    if (response.body()==null){
                        hideLoading();
                        return;
                    }

                    personajesDetail.add(response.body().getName());

                    if (resident.equals(lastValue)){
                        hideLoading();
                        //Toast.makeText(mcontext, personajesDetail.toString(), Toast.LENGTH_SHORT).show();

                        showDialogFragment(locationResponse.getName(),personajesDetail.toString(),1);
                    }

                    //Toast.makeText(mcontext, personajesDetail.toString(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<RickMorty> call, Throwable t) {
                    hideLoading();
                    AppLogger.e("Error: " + t.getMessage());

                }
            });
//                viewModel.apiCallDetailsCharacter(codigo);

        }
        personajes.clear();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void showDialogFragment(String titulo, String mensaje, int tipoDialog){

//        ListFragmentDirections.ActionListFragmentToDetailsFragment action = ListFragmentDirections.actionListFragmentToDetailsFragment();
//        action.setPosition(position);
//        navController.navigate(action);
        Bundle bundle = new Bundle();
        bundle.putString("titulo", titulo);
        bundle.putString("personajesArg", mensaje);
        navController.navigate(R.id.action_nav_locations_to_charactersDialogFragment, bundle);


    }

//    private void observeListCharactersViewMode() {
//        viewModel = new ViewModelProvider(this).get(HomeCharactersViewModel.class);
//        showLoading();
//        List<String> mpersonajes = new ArrayList<>();
//        viewModel.getDetailsListOserver().observe(getViewLifecycleOwner(), rickMortyResponse -> {
//
//            hideLoading();
//            if (rickMortyResponse == null) return;
//            mpersonajes.add(rickMortyResponse.getName());
//        });
//
//        Toast.makeText(mcontext, mpersonajes.toString(), Toast.LENGTH_SHORT).show();
////        mpersonajes.clear();
//    }

}