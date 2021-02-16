/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.homecharacters;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.databinding.FragmentRickandmortyListBinding;
import com.rogergcc.sharedpreferencefavorites.helpers.MySharedPreference;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;
import com.rogergcc.sharedpreferencefavorites.model.RickMortyResponse;
import com.rogergcc.sharedpreferencefavorites.remote.CommonApiUrl;
import com.rogergcc.sharedpreferencefavorites.utils.AppLogger;
import com.rogergcc.sharedpreferencefavorites.utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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

    private int currentPage;
    private boolean isLastPage = false;
    private int totalPage = 2;
    private boolean isLoading = false;
    private FragmentRickandmortyListBinding binding;
    private Context mcontext;
    private ProgressDialog mProgressDialog;
    private ProgressDialog progressDialog;

    int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean loading = true;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.e("RickAndMortyFragment"+"=>onCreate");

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
        AppLogger.e("RickAndMortyFragment"+"=>oncreateView");
        AppLogger.e("RickAndMortyFragment"+"Page=>"+currentPage);


        View view = binding.getRoot();

//        binding.list = view.findViewById(R.id.list);

//        pd = new ProgressDialog(getContext());
//        pd.setTitle("Obtain Characters");
//        pd.setMessage("loading...");
//        pd.setCancelable(false);
//        pd.show();


        binding.list.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.list.setLayoutManager(linearLayoutManager);

        rickMortyCharactersList = new ArrayList<>();

        loadFavoritesData();

        adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
        binding.list.setAdapter(adapterapi);

        currentPage = PAGE_START;
        getCharacters();

//        binding.list.addOnScrollListener(new PaginationListener(linearLayoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage++;
//                getCharacters();
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });

        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.list.canScrollVertically(1)){
                    if (currentPage <= totalPage) {
                        currentPage++;
                        getCharacters();
                    }
                }
            }
        });

        return view;
    }

    public void getCharacters() {

        if (currentPage==1) showLoading();
        else  showProgressLoadingMore();
//        toggleLoadingMore();

        //rickMortyCharactersList.clear();
        //region REGION SEND POST RETROFIT
        Call<RickMortyResponse> call = CommonApiUrl.getGeoJsonData().getCharacters(currentPage);
        call.enqueue(new Callback<RickMortyResponse>() {

            @Override
            public void onResponse(@NonNull Call<RickMortyResponse> call, retrofit2.Response<RickMortyResponse> response) {
                if (currentPage==1) hideLoading();
                else hideProgressLoadingMore();
//                toggleLoadingMore();

                if (response.body() == null) return;
//                if(!response.isSuccessful()) return;
                //AppLogger.d("LISTA_: " + response.body().toString());
//                            try {
                RickMortyResponse rickMortyResponse = response.body();

                //rickMortyCharactersList = rickMortyResponse.getResults();
                totalPage = rickMortyResponse.getInfo().getPages();
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.list.getContext(),
//                                    linearLayoutManager.getOrientation());
//                            binding.list.addItemDecoration(dividerItemDecoration);

//                            adapterapi = new ListCharactersAdapter(mFavoritesList, rickMortyCharactersList);
//                            binding.list.setAdapter(adapterapi);


//                if (currentPage != PAGE_START) adapterapi.removeLoading();
//                adapterapi.addItems(rickMortyCharactersList);
//                //swipeRefresh.setRefreshing(false);
//                // check weather is last page or not
//
//                if (currentPage < totalPage) {
//                    adapterapi.addLoading();
//                } else {
//                    isLastPage = true;
//                }
//                isLoading = false;

//                adapterapi.notifyDataSetChanged();
                int oldCount = rickMortyCharactersList.size();
                rickMortyCharactersList.addAll(rickMortyResponse.getResults());
                adapterapi.notifyItemRangeInserted(oldCount,rickMortyCharactersList.size());

            }

            @Override
            public void onFailure(@NonNull Call<RickMortyResponse> call, Throwable t) {

                hideLoading();
                Toast.makeText(mcontext, getString(R.string.message_somethin_wrong), Toast.LENGTH_SHORT).show();
                AppLogger.e("LISTA_ERROR: " + t.getMessage());
            }
        });
        //endregion


    }
//    private void toggleLoading() {
//        //https://github.com/Groestlcoin/groestlcoin-samourai-wallet-android/blob/master/app/src/main/java/com/samourai/wallet/CreateWalletActivity.java
//    }

    private void toggleLoadingMore() {

        int isLoadingMore = binding.pbLoadMore.getVisibility();

        if (currentPage!=1){
            if (binding.pbLoadMore.getVisibility()==View.GONE){
                hideProgressLoadingMore();
            }else {
                showProgressLoadingMore();
            }
        }

    }

    /*
     * Get Characters of api Rick & Morty
     * */

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
