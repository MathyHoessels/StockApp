package com.hoessels.stockapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoessels.stockapp.R;
import com.hoessels.stockapp.domain.Article;
import com.hoessels.stockapp.domain.Order;
import com.hoessels.stockapp.domain.Supplier;
import com.hoessels.stockapp.viewmodel.ArticleAdapter;
import com.hoessels.stockapp.viewmodel.ViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private TextView textView;
    private ListView listView;
    private Button btnSend;
    private Button btnScan;
    private Order order;
    private ArticleAdapter adapter;

    private ViewModel viewModel;

    public OrderFragment() {
        // Required empty public constructor
        Log.d(TAG, "created");
    }

    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_order, container, false);
        //build view
        textView = mView.findViewById(R.id.orderListTitle);
        listView = mView.findViewById(R.id.orderList);
        btnSend = mView.findViewById(R.id.btnSend);
        btnScan = mView.findViewById(R.id.btnScan);

        viewModel.setOrders();
        order = viewModel.getOrder().get(0);
        Log.d(TAG, "ordername: "+ order.getName());
        Log.d(TAG, "orderarticles: "+ order.getArticles().toString());
        adapter = new ArticleAdapter(getContext(), order);
        listView.setAdapter(adapter);

        // set listeners
        setListeners(mView);

        return mView;
    }

    private void setListeners(View mView) {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController nav = Navigation.findNavController(mView);
                nav.navigate(R.id.action_orderFragment_to_scanFragment);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String cc = "";
                String subject = "Bestelling";
                String body = "Beste\n\nIk had graag volgende artikels besteld:\n" +
                        order.getArticleNames() + "Mvg\n" + "Mathy";
                String recipient = "mathy.hoessels@gmail.com";
                String mailto = "mailto:"+ Uri.encode(recipient) + "?cc=" + Uri.encode(cc) + "&subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body);
                intent.setData(Uri.parse(mailto));
                try {
                    startActivity(Intent.createChooser(intent, "Send email..."));
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "No email client installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void fake() {
        viewModel.setArticles();
        ArrayList<Article> articles = viewModel.getArtikels();
        order = new Order("testOrder");
        for (Article a : articles) {
            order.addArticle(a);
        }
    }
}