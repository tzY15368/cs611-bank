package bankBackend;

import Utils.Result;

public class Stock {
    String name;
    double price;

    public Stock(String name, double price){
        this.name=name;
        this.price=price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Result<Void> changePrice(double change){
        price+=change;
        return null;
    }
}
