/*
 * Created by rogergcc
 * Copyright Ⓒ 2021 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites.ui.locations;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.rogergcc.sharedpreferencefavorites.R;
import com.rogergcc.sharedpreferencefavorites.databinding.DialogFragmentCharactersBinding;

public class CharactersDialogFragment extends DialogFragment {

    private View rootView;
    private TextView textView;
    private DialogFragmentCharactersBinding binding;
    private Toolbar toolbarConference;
    private Context mcontext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbarConference = (Toolbar) getActivity().findViewById(R.id.toolbarConference);
//        toolbarConference = rootView.findViewById(R.id.toolbarConference);
//        toolbarConference = view.findViewById(R.id.toolbarConference);
        if (toolbarConference != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarConference);
            toolbarConference.setNavigationIcon(R.drawable.ic_baseline_close_24);
            toolbarConference.setTitle(getArguments().getString("titulo"));
//            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//
//            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                    getActivity(), drawer, toolbarConference, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            drawer.setDrawerListener(toggle);
//
//            toggle.syncState();


            toolbarConference.setTitleTextColor(Color.WHITE);
            toolbarConference.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }


    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
////        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
////        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        return dialog;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mcontext = getContext();

        rootView = inflater.inflate(R.layout.dialog_fragment_characters, container, false);


        findViewsByIds();
        if (getArguments() != null) {
            textView.setText(getArguments().getString("personajesArg"));
            getDialog().setTitle(getArguments().getString("titulo"));


        }


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog);
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    private void findViewsByIds() {

        textView = rootView.findViewById(R.id.tvCharacters);


    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
//        layoutParams.dimAmount = 0;
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
//        getDialog().getWindow().setAttributes(layoutParams);
//    }

}
