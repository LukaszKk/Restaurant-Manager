package app.manager.orders;

public class Order {
    private String numberOrder;
    private String date;
    private String numberPeople;
    private String time;
    private String table;

    Order(String numberOrder, String date,String numberPeople, String time, String table) {
        this.numberOrder = numberOrder;
        this.date = date;
        this.numberPeople = numberPeople;
        this.time = time;
        this.table = table;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public String getDate() {
        return date;
    }

    public String getNumberPeople() {
        return numberPeople;
    }
    public String getTime() {
        return time;
    }
    public String getTable() {
        return table;
    }
}
