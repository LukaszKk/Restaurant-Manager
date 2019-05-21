package app;

public class Dish {
    private String dishName;
    private Double price;
    private String category;
    private String[] ingredients;

    Dish(String name, Double price) {
        this.dishName = name;
        this.price = price;
    }

    public String getName() {
        return dishName;
    }

    public Double getPrice() {
        return price;
    }
}
