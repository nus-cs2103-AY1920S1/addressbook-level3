package seedu.deliverymans.model.order;

import java.util.HashMap;
import java.util.Map;

import seedu.deliverymans.model.Name;

public class OrderBuilder {
    private final Name customer;
    private final Name restaurant;
    private final Name deliveryman;
    private final Map<Name, Integer> foodList = new HashMap<>();

    public OrderBuilder(Name customer, Name restaurant, Name deliveryman) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
    }

    public OrderBuilder(Name customer, Name restaurant, Map<Name, Integer> foodList) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = new Name("Unassigned");
        this.foodList.putAll(foodList);
    }

    public OrderBuilder(Name customer, Name restaurant, Name deliveryman, Map<Name, Integer> foodList) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
        this.foodList.putAll(foodList);
    }

    public Name getDeliveryman() {
        return deliveryman;
    }

    public Name getRestaurant() {
        return restaurant;
    }

    public Name getCustomer() {
        return customer;
    }

    public Map<Name, Integer> getFoodList() {
        return foodList;
    }
}
