package models;

public class Product {
    private int idProduct;
    private String name;
    private double quantity;
    private double price;

    public Product(int idProduct, String name, double quantity, double price) {
        this.idProduct = idProduct;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        String result = "       ";
        result += idProduct + "       ";
        result += name + "      ";
        result += quantity + "        ";
        result += price + "         ";
        return  result;
    }
}
