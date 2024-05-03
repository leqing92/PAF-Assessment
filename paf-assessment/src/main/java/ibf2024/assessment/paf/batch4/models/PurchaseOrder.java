package ibf2024.assessment.paf.batch4.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

public class PurchaseOrder {
    private String orderId;
    private LocalDateTime orderDate;
    private int breweryId;    
    private List<Order> orders;

    public PurchaseOrder() {
    }    

    public PurchaseOrder(String orderId, LocalDateTime orderDate, int breweryId, int beerId, String beerName, int quantity,
            List<Order> orders) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.breweryId = breweryId;        
        this.orders = orders;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }    

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [orderId=" + orderId + ", orderDate=" + orderDate + ", breweryId=" + breweryId
                + ", orders=" + orders
                + "]";
    }  

    public String toJSON(){
        JsonObjectBuilder jObjectBuilder = Json.createObjectBuilder();

        JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
        for(Order order : orders){
            jArrayBuilder.add(order.toJSON());
        }

        jObjectBuilder.add("orderId", getOrderId())
                    .add("date", getOrderDate().toString())
                    .add("breweryId", getBreweryId())
                    .add("orders", jArrayBuilder)
                    .build();

        return jObjectBuilder.toString();
    }
}
