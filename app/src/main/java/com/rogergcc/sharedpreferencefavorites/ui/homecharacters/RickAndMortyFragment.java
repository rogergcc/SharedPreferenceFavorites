/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.homecharacters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rogergcc.sharedpreferencefavorites.databinding.FragmentRickandmortyListBinding;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.ui.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.ui.utils.AppLogger;
import com.rogergcc.sharedpreferencefavorites.ui.utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.rogergcc.sharedpreferencefavorites.ui.helpers.PaginationListener.PAGE_START;


public class RickAndMortyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Customize parameters
    private LinearLayoutManager linearLayoutManager;
    private List<RickMorty> rickMortyCharactersList;
    private ListCharactersAdapter adapterapi;
    private ArrayList<RickMorty> mFavoritesList;
    private int currentPage;
    private int totalPage = 2;
    private FragmentRickandmortyListBinding binding;
    private Context mcontext;
    private ProgressDialog mProgressDialog;
    private HomeCharactersViewModel viewModel;
    private Parcelable mystatee;

//    private HomeCharactersViewModel viewModel= new ViewModelProvider(this).get(HomeCharactersViewModel.class);

    public RickAndMortyFragment() {
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

    public void toggleLoadingList() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.showLoadingDialog(mcontext);
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            } else {
                mProgressDialog.show();
            }
        }
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        val state = recyclerview.layoumanager.on SaveInatanceSate()
//
////        mystatee= binding.list.getLayoutManager().onSaveInstanceState();
//        mystatee= linearLayoutManager.onSaveInstanceState();
//        state.putParcelable(LIST_STATE_KEY, mListState);
//    }

    @Override
    public void onPause() {
        super.onPause();
        AppLogger.e("Fragment Resume");
//        mystatee= binding.list.getLayoutManager().onSaveInstanceState();
        mystatee = linearLayoutManager.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mystatee != null) {
//            linearLayoutManager.onRestoreInstanceState(mystatee);
//        }
        AppLogger.e("Fragment Resume");
//        binding.list.getLayoutManager().onRestoreInstanceState(mystatee);
        linearLayoutManager.onRestoreInstanceState(mystatee);
    }


//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        // Retrieve list state and list/item positions
//        if(mystatee != null)
//            mListState = state.getParcelable(LIST_STATE_KEY);
//    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.e("RickAndMortyFragment" + "=>onCreate");


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLogger.e("RickAndMortyFragment" + "=>onViewCreated");

        //binding = FragmentRickandmortyListBinding.inflate(getLayoutInflater());
        mcontext = this.getActivity();


        init();
    }

    public void init() {
        binding.list.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.list.setLayoutManager(linearLayoutManager);

        rickMortyCharactersList = new ArrayList<>();

        loadFavoritesData();

        adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
        binding.list.setAdapter(adapterapi);

        currentPage = PAGE_START;

        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.list.canScrollVertically(1)) {
                    if (currentPage <= totalPage) {
                        currentPage++;
//                        getCharacters();
//                        getMyListCharactersViewModel();
                        viewModel.apiCallCharacters(currentPage);
                    }
                }
            }
        });
        observeListCharactersViewMode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_rickandmorty_list, container, false);

        binding = FragmentRickandmortyListBinding.inflate(getLayoutInflater());
        mcontext = this.getActivity();
        AppLogger.e("RickAndMortyFragment" + "=>oncreateView");
        AppLogger.e("RickAndMortyFragment" + "Page=>" + currentPage);
        View view = binding.getRoot();

//        init();
        return view;
    }

    private void observeListCharactersViewMode() {
        viewModel = new ViewModelProvider(this).get(HomeCharactersViewModel.class);

        if (currentPage == 1)
            showLoading();
        else
            showProgressLoadingMore();

        viewModel.getRMCharactersListObserver().observe(getViewLifecycleOwner(), rickMortyResponse -> {
            Toast.makeText(mcontext, "get Characters", Toast.LENGTH_SHORT).show();

            if (currentPage == 1)
                hideLoading();
            else
                hideProgressLoadingMore();

            if (rickMortyResponse == null) return;
//                    if (rickMortyResponse.getResults()==null)return;

            totalPage = rickMortyResponse.getInfo().getPages();
            int oldCount = rickMortyCharactersList.size();
            rickMortyCharactersList.addAll(rickMortyResponse.getResults());
            adapterapi.notifyItemRangeInserted(oldCount, rickMortyCharactersList.size());


        });
        viewModel.apiCallCharacters(currentPage);

    }

    public void showProgressLoadingMore() {

        binding.pbLoadMore.setVisibility(View.VISIBLE);
    }

    public void hideProgressLoadingMore() {

        binding.pbLoadMore.setVisibility(View.GONE);
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
