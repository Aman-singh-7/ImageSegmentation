package com.example.imagesegmentation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagesegmentation.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link snapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class snapFragment extends Fragment {



    public snapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static snapFragment newInstance() {
        snapFragment fragment = new snapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snap, container, false);
    }
}