package ru.lab10;


public class Product {
    private String articul;
    private String name;
    private int count;
    private int price_buy;
    private int price_sell;
    private String shelf_life;

    Product(String articul, String name, int count, int price_buy, int price_sell, String shelf_life) {
        this.articul = articul;
        this.name = name;
        this.count = count;
        this.price_buy = price_buy;
        this.price_sell = price_sell;
        this.shelf_life = shelf_life;
    }
}
