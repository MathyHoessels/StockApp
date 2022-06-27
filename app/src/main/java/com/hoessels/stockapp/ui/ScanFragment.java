package com.hoessels.stockapp.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.hoessels.stockapp.R;
import com.hoessels.stockapp.viewmodel.ViewModel;

//TODO: when item is scanned, switch button should dissapear till screen is ticked
public class ScanFragment extends Fragment {
    private final String TAG = this.getTag();

    private ViewModel viewModel;
    private CodeScanner mCodeScanner;
    private boolean batch;
    private Switch mSwitch;

    // check for permissions, otherwise ask
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(getActivity(), "Het is niet mogelijk de scanner te gebruiken zonder" +
                            " toegang te geven tot de camera", Toast.LENGTH_LONG).show();
                    //TODO: go back to previous fragment
                }
            });

    public ScanFragment() {
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
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        setupPermissions();

        // setup switch
        mSwitch = root.findViewById(R.id.batchScanSwitch);
        // make scanner
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scan(result, root);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public void setupPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA);
        }
    }

    public void scan(Result result, View root) {

        if (mSwitch.isChecked()) {
            // should show a toast with the number and send the data to orderlist
            viewModel.addArticle(Integer.parseInt(result.getText()));
            Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
        } else if (!mSwitch.isChecked()) {
            viewModel.addArticle(Integer.parseInt(result.getText()));
            // should show a toast with the number and send the data to orderlist
            Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
            // then go to next fragment (orderlist)
            NavController nav = Navigation.findNavController(root);
            nav.navigate(R.id.action_scanFragment_to_orderFragment);
        } else {
            // something has gone terribly wrong
        }
    }
}