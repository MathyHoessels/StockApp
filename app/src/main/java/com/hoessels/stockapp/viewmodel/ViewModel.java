package com.hoessels.stockapp.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hoessels.stockapp.domain.Article;
import com.hoessels.stockapp.domain.Order;
import com.hoessels.stockapp.domain.StockApp;

import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private MutableLiveData<ArrayList<Article>> articles = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> orders = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Article>> articlesInOrder = new MutableLiveData<>();

    private StockApp stockApp = new StockApp();

    public ViewModel() {

    }

    public ArrayList<Article> getArtikels() {
        return articles.getValue();
    }

    public void setArticles() {
        articles.setValue(stockApp.getArticles());
    }

    public ArrayList<Order> getOrder() {
        return orders.getValue();
    }

    public void setOrders() {
        orders.setValue(stockApp.getOrders());
    }

    public void addArticle(int number) {
        //TODO: make different orders
        if (orders!=null) {
            orders.getValue().get(0).addArticleNumber(number);
            stockApp.setOrders();
        }
    }
    public ArrayList<Article> getArticlesInOrder() {
        return articlesInOrder.getValue();
    }
    public void setArticlesInOrder() {
        //articlesInOrder.setValue(stockApp.getArticlesInOrder());
    }

}
