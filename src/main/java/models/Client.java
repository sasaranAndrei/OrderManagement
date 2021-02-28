package models;

import validators.ClientValidator;

public class Client {
    private int idClient;
    private String name;
    private String address;

    public Client(int idClient, String name, String address) {
        this.idClient = idClient;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        String result = "   ";
        result += idClient + "        ";
        result += name + "      ";
        result += address + " ";
        return  result;
    }
}
