package ru.lab10;


import java.util.Scanner;

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

    public String[] toStringArray() {
        String[] product = new String[7];
        product[0] = articul;
        product[1] = name;
        product[2] = Integer.toString(count);
        product[3] = Integer.toString(price_buy);
        product[4] = Integer.toString(price_sell);
        product[5] = shelf_life;
        return product;
    }

    @Override
    public String toString() {
        return String.format("%10s%15s%15d%15d%15d%15s", articul, name, count, price_buy, price_sell, shelf_life);
    }

    public static Product addProduct() {
        Scanner scanner = new Scanner(System.in);
        String articul;
        String name;
        int count;
        int price_buy;
        int price_sell;
        String shelf_life;
        System.out.println("Введите articul:");
        articul = scanner.nextLine();
        System.out.println("Введите name:");
        name = scanner.nextLine();
        System.out.println("Введите count:");
        count = scanner.nextInt();
        System.out.println("Введите price_buy:");
        price_buy = scanner.nextInt();
        System.out.println("Введите price_sell:");
        price_sell = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите shelf_life:");
        shelf_life = scanner.nextLine();
        return new Product(articul, name, count, price_buy, price_sell, shelf_life);
    }

    public String getArticul() {
        return this.articul;
    }

    public void edit() {
        System.out.println("Введите поле, которое надо изменить:");
        String[] fields = new String[]{"Артикул", "Название", "Количество", "Цена покупки", "Цена продажи", "Срок годности"};
        int i = 1;
        for(String field : fields) {
            System.out.println(Integer.toString(i) + " " + field);
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        String newStr = "";
        int newInt = 0;
        System.out.println("Введите значение:");
        scanner.nextLine();
        switch (action) {
            case 1:
            case 2:
            case 6:
                newStr = scanner.nextLine();
                break;
            case 3:
            case 4:
            case 5:
                newInt = scanner.nextInt();
                break;
        }
        switch (action) {
            case 1:
                this.articul = newStr;
                break;
            case 2:
                this.name = newStr;
                break;
            case 3:
                this.count = newInt;
                break;
            case 4:
                this.price_buy = newInt;
                break;
            case 5:
                this.price_sell = newInt;
                break;
            case 6:
                this.shelf_life = newStr;
                break;
        }
    }
}
