package app.manager;

public class Dish {
    private String nameDish;
    private String price;
    private String category;

    public Dish(String nameDish, String price,String category) {
        this.nameDish = nameDish;
        this.price = price;
        this.category = category;
    }

    public String getName() {
       return nameDish;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
