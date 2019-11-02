package seedu.deliverymans.model.util;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.StatusTag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Customer[] getSampleCustomers() {
        return new Customer[]{
            new Customer(new Name("Alex Yeoh"), new Phone("87438807"), getTagSet("FastFood"),
                    getOrder(new Order(new Name("Alex Yeoh"), new Name("KFC"),
                            getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("3 Piece Chicken"), 1))))),
            new Customer(new Name("Bernice Yu"), new Phone("99272758"), getTagSet("Indian"),
                    getOrder(new Order(new Name("Bernice Yu"), new Name("Prata House"),
                            getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Curry Fountain"), 1))))),
            new Customer(new Name("Charlotte Oliveiro"), new Phone("93210283"), getTagSet("Bar"),
                    getOrder(new Order(new Name("Charlotte Oliveiro"), new Name("SkyBar Bar and Restaurant"),
                            getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Buffalo Wings"), 5))))),
            new Customer(new Name("David Li"), new Phone("91031282"), getTagSet("Japanese"),
                    getOrder(new Order(new Name("David Li"), new Name("IchiNiSan Ramen"),
                            getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Ramen C"), 1))))),
            new Customer(new Name("Ifran Ibrahim"), new Phone("92492021"), getTagSet("Barbeque"),
                    getOrder(new Order(new Name("Irfan Ibrahim"), new Name("Piggys Self Barbeque"),
                            getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Trotter"), 7),
                                    new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Shank"), 7),
                                    new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Tail"), 7)))))
        };
    }

    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[]{
            new Restaurant(new Name("KFC"), LocationMap.getLocation("Jurong").get(),
                        getTagSet("FastFood", "Western"),
                        getMenu(new Food(new Name("Ginger Burger"), new BigDecimal(6.5),
                                        getFoodTags("Recommended")),
                                new Food(new Name("Shrooms Burger"), new BigDecimal(5), getFoodTags()),
                                new Food(new Name("Fish Fillet Burger"), new BigDecimal(5.5), getFoodTags()),
                                new Food(new Name("2 Piece Chicken"), new BigDecimal(7.95), getFoodTags()),
                                new Food(new Name("3 Piece Chicken"), new BigDecimal(10.95),
                                        getFoodTags("Recommended")),
                                new Food(new Name("Chicken Nuggets"), new BigDecimal(3), getFoodTags()))),

            new Restaurant(new Name("Prata House"), LocationMap.getLocation("Bishan").get(),
                        getTagSet("Indian"),
                        getMenu(new Food(new Name("Plain Prata"), new BigDecimal(0.7), getFoodTags()),
                                new Food(new Name("Egg Prata"), new BigDecimal(1), getFoodTags()),
                                new Food(new Name("Cheese Prata"), new BigDecimal(1.2), getFoodTags()),
                                new Food(new Name("Prata Bomb"), new BigDecimal(2),
                                        getFoodTags("Recommended")),
                                new Food(new Name("Curry Fountain"), new BigDecimal(2), getFoodTags()),
                                new Food(new Name("Curry Waterfall"), new BigDecimal(3),
                                        getFoodTags("Recommended")))),

            new Restaurant(new Name("SkyBar Bar and Restaurant"), LocationMap.getLocation("Marina").get(),
                        getTagSet("Bar"),
                        getMenu(new Food(new Name("Duck Confit"), new BigDecimal(10), getFoodTags()),
                                new Food(new Name("Foie gras"), new BigDecimal(15),
                                        getFoodTags("Recommended")),
                                new Food(new Name("Buffalo Wings"), new BigDecimal(15), getFoodTags()),
                                new Food(new Name("Rhinoceros Pizza"), new BigDecimal(25),
                                        getFoodTags("Recommended")),
                                new Food(new Name("Hippo Teeth"), new BigDecimal(30.5), getFoodTags()),
                                new Food(new Name("Rat with Caviar"), new BigDecimal(49.9),
                                        getFoodTags("Recommended")))),

            new Restaurant(new Name("IchiNiSan Ramen"), LocationMap.getLocation("City").get(),
                        getTagSet("Japanese"),
                        getMenu(new Food(new Name("Ramen A"), new BigDecimal(10), getFoodTags()),
                                new Food(new Name("Ramen B"), new BigDecimal(10), getFoodTags()),
                                new Food(new Name("Ramen C"), new BigDecimal(10), getFoodTags()),
                                new Food(new Name("Ramen D"), new BigDecimal(15), getFoodTags()),
                                new Food(new Name("Ramen E"), new BigDecimal(15), getFoodTags()),
                                new Food(new Name("Ramen F"), new BigDecimal(15), getFoodTags()))),

            new Restaurant(new Name("Piggys Self Barbeque"), LocationMap.getLocation("Woodlands").get(),
                        getTagSet("Barbeque"),
                        getMenu(new Food(new Name("BBQ Head"), new BigDecimal(10), getFoodTags()),
                                new Food(new Name("BBQ Shank"), new BigDecimal(15), getFoodTags()),
                                new Food(new Name("BBQ Trotter"), new BigDecimal(15), getFoodTags()),
                                new Food(new Name("BBQ Nails"), new BigDecimal(25), getFoodTags()),
                                new Food(new Name("BBQ Butt"), new BigDecimal(30.5), getFoodTags()),
                                new Food(new Name("BBQ Tail"), new BigDecimal(49.9), getFoodTags())))
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[]{
            new Order(new Name("Alex Yeoh"), new Name("KFC"),
                        getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("3 Piece Chicken"), 1))),
            new Order(new Name("Bernice Yu"), new Name("Prata House"),
                        getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Curry Fountain"), 1))),
            new Order(new Name("Charlotte Oliveiro"), new Name("SkyBar Bar and Restaurant"),
                        getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Buffalo Wings"), 5))),
            new Order(new Name("David Li"), new Name("IchiNiSan Ramen"),
                        getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("Ramen C"), 1))),
            new Order(new Name("Irfan Ibrahim"), new Name("Piggys Self Barbeque"),
                        getFoodMap(new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Trotter"), 7),
                                new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Shank"), 7),
                                new AbstractMap.SimpleEntry<Name, Integer>(new Name("BBQ Tail"), 7)))
        };
    }

    public static Deliveryman[] getSampleDeliverymen() {
        return new Deliveryman[]{
            new Deliveryman(new Name("Damith"), new Phone("83412321"),
                    getTagSet("inactive", "giveCharlieAplus"), new StatusTag("AVAILABLE")),
            new Deliveryman(new Name("Donald Trump"), new Phone("91234567"),
                    getTagSet("buff", "powerful", "bestDeliveryman2019", "notFat", "cuteHairstyle"),
                    new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Charlie Choong"), new Phone("98887146"),
                    getTagSet("active", "AisEnough")),
            new Deliveryman(new Name("Low Ee Ter"), new Phone("99367862"), getTagSet("inactive"),
                    new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Yuen Jun Rong "), new Phone("12345678"), getTagSet("veryactive"))
        };
    }

    public static ReadOnlyCustomerDatabase getSampleCustomerDatabase() {
        CustomerDatabase sampleCd = new CustomerDatabase();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleCd.addCustomer(sampleCustomer);
        }
        return sampleCd;
    }

    public static ReadOnlyDeliverymenDatabase getSampleDeliverymenDatabase() {
        DeliverymenDatabase sampleDd = new DeliverymenDatabase();
        for (Deliveryman sampleDeliveryman : getSampleDeliverymen()) {
            sampleDd.addDeliveryman(sampleDeliveryman);
        }
        return sampleDd;
    }

    public static ReadOnlyRestaurantDatabase getSampleRestaurantDatabase() {
        RestaurantDatabase sampleRd = new RestaurantDatabase();
        for (Restaurant sampleRestaurant : getSampleRestaurants()) {
            sampleRd.addRestaurant(sampleRestaurant);
        }
        return sampleRd;
    }

    public static ReadOnlyOrderBook getSampleOrderBook() {
        OrderDatabase sampleOb = new OrderDatabase();
        for (Order sampleOrder : getSampleOrders()) {
            sampleOb.addOrder(sampleOrder);
        }
        return sampleOb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static ObservableList<Tag> getFoodTags(String... strings) {
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        tags.addAll(Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toList()));
        return tags;
    }

    public static Map<Name, Integer> getFoodMap(Map.Entry<Name, Integer>... strings) {
        HashMap<Name, Integer> toReturn = new HashMap<>();
        for (Map.Entry<Name, Integer> keyValuePair : strings) {
            toReturn.put(keyValuePair.getKey(), keyValuePair.getValue());
        }
        return toReturn;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static ObservableList<Food> getMenu(Food... foods) {
        ObservableList<Food> menu = FXCollections.observableArrayList();
        menu.addAll(foods);
        return menu;
    }

    public static ObservableList<Order> getOrder(Order... order) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.addAll(Arrays.asList(order));
        return orders;
    }
}
