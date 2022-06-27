package com.hoessels.stockapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoessels.stockapp.domain.Article;
import com.hoessels.stockapp.domain.Order;

import java.util.ArrayList;
import java.util.Observable;

public class Database extends Observable {
    private final String TAG = this.getClass().getSimpleName();

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://stockhoessels-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference mRef = database.getReference();
    private DatabaseReference articleRef = database.getReference().child("Artikelen");

    public Database() {
        // add realtime updates
        getArticles();
        getOrders();
    }

    public void getArticles() {
        mRef.child("Artikelen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Article article = new Article();
                    for (DataSnapshot d : data.getChildren()) {
                        buildArticle(d, article);
                    }
                    setChanged();
                    notifyObservers(article);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void getOrders() {
        mRef.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Main Value is: " + snapshot.getValue() );
                for (DataSnapshot data : snapshot.getChildren()) {
                    for (DataSnapshot d : data.getChildren()) {
                        Order order = new Order();
                        Log.d(TAG, "Value is: " + snapshot.getValue() );
                        buildOrder(d, order);
                        setChanged();
                        notifyObservers(order);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void buildArticle(DataSnapshot d, Article a) {
        switch (d.getKey()) {
            case "Naam":
                a.setName((String) d.getValue());
                break;
            case "Nummer":
                a.setNr(((Long) d.getValue()).intValue());
                break;
            case "Prijs":
                a.setPrice((Double) d.getValue());
                break;
            case "Aantal":
                Log.d(TAG, "Aantal" + d.getValue().getClass());
                a.setRemaining((Long) d.getValue());
                break;
            case "LeverancierNr":
                //TODO: find and set supplier
                break;
            default:
                Log.d(TAG, "There is something wrong with the key value of an article");
                break;
        }
    }

    private void buildOrder(DataSnapshot d, Order o) {
        switch (d.getKey()) {
            case "Naam":
                o.setName((String) d.getValue());
                Log.d(TAG, "ordername received");
                break;
            case "Nummer":
                o.setNr((Long) d.getValue());
                Log.d(TAG, "ordernumber received");
                break;
            case "Artikels":
                for (DataSnapshot data : d.getChildren()) {
                    o.addArticleNumber(Integer.parseInt(data.getKey()), ((Long) data.getValue()).intValue());
                }
                Log.d(TAG, "orderarticles received");
                break;
            default:
                Log.d(TAG, "There is something wrong with the key value of an order");
                break;
        }
    }
}
