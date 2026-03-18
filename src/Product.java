public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double calculateTotal(int qty) {
        return this.price * qty;
    }

    public String getInfo() {
        return name + "@ $" + price;
    }
}