package com.hoessels.stockapp.domain;

import java.util.Map;
import java.util.TreeMap;

//TODO: get unique number for each order!!!

public class Order {
    private String name; //name of the order
    private Long nr; // unique nr of the order
    private float price; // total price of the order
    private TreeMap<Article, Integer> articles; // Map of article with the amount.
    private TreeMap<Integer, Integer> articleNumbers; // Map of articlenumber with the amount.
    private StockApp stockApp;

    /**
     * public constructor without a name.
     */
    public Order() {
        //empty constructor
        this("No name entered", 1L);
        this.articleNumbers = new TreeMap<>();
    }

    /**
     * public constructor used to specify the name of the order
     * @param name
     */
    public Order(String name) {
        this(name, 1L);
    }

    /**
     * private constructor to make sure the nr is unique
     * @param name String representation of the name.
     * @param number int of the unique number.
     */
    private Order(String name, Long number) {
        this.name = name;
        this.nr = number;
        this.articles = new TreeMap<>();
    }

    public void addArticle(Article article) {
        if (articles.containsKey(article)) { // add 1 to amount if article already exists
            articles.put(article, articles.get(article)+1);
        } else {
            articles.put(article, 1);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TreeMap<Article, Integer> getArticles() {
        return articles;
    }

    public void setArticles(TreeMap<Article, Integer> articles) {
        this.articles = articles;
    }

    public String getArticleNames() {
        String names = "";
        for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
            names = names + entry.getKey().getName() + ", aantal: " + entry.getValue().toString() + "\n";
        }
        return names;
    }

    public void addArticleNumber(Integer articleNumber, Integer amount) {
        this.articleNumbers.put(articleNumber, amount);
    }
    public void addArticleNumber(Integer articleNumber) {
        if (articleNumbers.containsKey(articleNumber)) {
            this.articleNumbers.put(articleNumber, articleNumbers.get(articleNumber)+1);
        } else {
            this.articleNumbers.put(articleNumber, 1);
        }
    }

    public TreeMap<Integer, Integer> getArticleNumbers() {
        return articleNumbers;
    }
}
