package com.hoessels.stockapp.domain;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hoessels.stockapp.data.Database;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

public class StockApp implements Observer {
    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<Order> orders;
    private ArrayList<Article> articles;
    private ArrayList<Supplier> suppliers;
    private User user;

    private Database database;

    public StockApp() {
        database = new Database();
        database.addObserver(this);
        articles = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders() {
        for (Order o : orders) {
            TreeMap<Integer, Integer> numbers = o.getArticleNumbers();
            TreeMap<Article, Integer> articleAmounts = new TreeMap<>();
            for (Map.Entry<Integer, Integer> entry : numbers.entrySet()) {
                for (Article article : articles) {
                    if (article.getNr() == entry.getKey()) {
                        articleAmounts.put(article, entry.getValue());
                    }
                }
            }
            o.setArticles(articleAmounts);
        }
    }

    @Override
    public void update(Observable o, Object object) {
        if (object instanceof Article) {
            Log.d(TAG, "Article received");
            if (!articles.contains(object)) {
                articles.add((Article) object);
            }
        } else if (object instanceof Order) {
            Log.d(TAG, "Order received");
            if (!orders.contains(object)) {
                orders.add((Order) object);
            }
            setOrders();
        } else {
            Log.d(TAG, "BAD");
        }

    }
}
