package com.hoessels.stockapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hoessels.stockapp.R;

public class MainFragment extends Fragment {
    private final String TAG = this.getTag();

    private Button scanButton;
    private Button bestelLijstButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "fuck off");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        scanButton = root.findViewById(R.id.scanButton);
        bestelLijstButton = root.findViewById(R.id.bestelLijstButton);

        addListeners(root);
        return root;
    }

    /**
     * this method adds the eventlisteners
     */
    public void addListeners(View root){
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController nav = Navigation.findNavController(root);
                nav.navigate(R.id.action_mainFragment_to_scanFragment);
            }
        });

        bestelLijstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController nav = Navigation.findNavController(root);
                Log.d(TAG, "main");
                nav.navigate(R.id.action_mainFragment_to_orderFragment); //TODO: add navigation to bestellijstfragment
            }
        });
    }
}