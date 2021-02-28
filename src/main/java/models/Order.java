package models;

public class Order {
    private int idOrder;
    private int idClient;
    private int idProduct;

    private double quantity;

    private double totalPrice = -1.0;

    public Order(int idOrder, int idClient, int idProduct, double quantity) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public Order(int idOrder, int idClient, int idProduct, double quantity, double totalPrice) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String toString() {

        String result = "   ";
        result += "   " + idOrder + "        ";
        result += idClient + "              ";
        result += idProduct + "           ";
        result += quantity + "             ";
        result += totalPrice + "     ";

        return  result;
    }
}
