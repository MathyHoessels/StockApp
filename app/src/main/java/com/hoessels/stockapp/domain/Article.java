package com.hoessels.stockapp.domain;

public class Article implements Comparable<Article> {
    private String name; // article name
    private int nr; // unique number this should be scannable
    private Double price; // article price
    private Long remaining; // the remaining amount of articles
    private Supplier supplier;

    public Article() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNr() {
        return nr;
    }

    //TODO: this is not necessary??
    public void setNr(int nr) {
        this.nr = nr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(Long remaining) {
        this.remaining = remaining;
    }

    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public Supplier getSupplier() { return supplier; }

    @Override
    public int compareTo(Article article) {
        if (this.nr>article.getNr()) {
            return 1;
        } else if (this.nr==article.getNr()) {
            return 0;
        } else {
            return -1;
        }
    }
}
