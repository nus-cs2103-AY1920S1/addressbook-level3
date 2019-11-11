package seedu.deliverymans.model.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.customer.Address;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
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
            new Customer(new Name("AlexYeoh99"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("Burgers", "Western"), 1),
            new Customer(new Name("BigEaterYu"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("Indian"), 1),
            new Customer(new Name("PrettyCharlotte"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("Bar"), 1),
            new Customer(new Name("David"), new Name("David Li"), new Phone("91031282"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("Japanese"), 1),
            new Customer(new Name("ProphetIbrahim"), new Name("Ifran Ibrahim"), new Phone("92492021"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("Barbeque"), 1),
            new Customer(new Name("CallMeRightathaway"), new Name("Penny Hathaway"), new Phone("98372938"),
                    new Address("Blk 214 Punggol Avenue 21, #24-01"),
                    getTagSet("Seafood"), 1)
        };
    }

    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[]{
            new Restaurant(new Name("Burger Palace"), LocationMap.getLocation("Jurong").get(),
                        getTagSet("Burgers", "Western"),
                        getMenu(new Food(new Name("Ginger Burger"), new BigDecimal("6.5"),
                                        getTagSet("Recommended")),
                                new Food(new Name("Chicken Cheese Burger"), new BigDecimal("9.9"), getTagSet()),
                                new Food(new Name("Fish Fillet Burger"), new BigDecimal("7.9"), getTagSet()),
                                new Food(new Name("Crispy Duck Burger"), new BigDecimal("12.9"),
                                        getTagSet("Recommended")),
                                new Food(new Name("Shrooms Burger"), new BigDecimal("5"), getTagSet()),
                                new Food(new Name("Pickle Burger"), new BigDecimal("3"), getTagSet()))),

            new Restaurant(new Name("Prata House"), LocationMap.getLocation("Bishan").get(),
                        getTagSet("Indian"),
                        getMenu(new Food(new Name("Plain Prata"), new BigDecimal("0.7"), getTagSet()),
                                new Food(new Name("Egg Prata"), new BigDecimal("1"), getTagSet()),
                                new Food(new Name("Cheese Prata"), new BigDecimal("1.2"), getTagSet()),
                                new Food(new Name("Prata Bomb"), new BigDecimal("2"),
                                        getTagSet("Recommended")),
                                new Food(new Name("Curry Fountain"), new BigDecimal("5.5"), getTagSet()),
                                new Food(new Name("Curry Waterfall"), new BigDecimal("13.5"),
                                        getTagSet("Recommended")))),

            new Restaurant(new Name("SkyBar Bar and Restaurant"), LocationMap.getLocation("Marina").get(),
                        getTagSet("Bar"),
                        getMenu(new Food(new Name("Duck Confit"), new BigDecimal("10"), getTagSet()),
                                new Food(new Name("Foie gras"), new BigDecimal("15"),
                                        getTagSet("Recommended")),
                                new Food(new Name("Buffalo Wings"), new BigDecimal("15"), getTagSet()),
                                new Food(new Name("Rhinoceros Pizza"), new BigDecimal("25"),
                                        getTagSet("Recommended")),
                                new Food(new Name("Hippo Teeth"), new BigDecimal("30.5"), getTagSet()),
                                new Food(new Name("Rat with Caviar"), new BigDecimal("49.9"),
                                        getTagSet("Recommended")))),

            new Restaurant(new Name("Piggys Self Barbeque"), LocationMap.getLocation("Woodlands").get(),
                        getTagSet("Barbeque"),
                        getMenu(new Food(new Name("BBQ Head"), new BigDecimal("10"), getTagSet()),
                                new Food(new Name("BBQ Shank"), new BigDecimal("15"), getTagSet()),
                                new Food(new Name("BBQ Trotter"), new BigDecimal("15"), getTagSet("Recommended")),
                                new Food(new Name("BBQ Nails"), new BigDecimal("25"), getTagSet()),
                                new Food(new Name("BBQ Butt"), new BigDecimal("30.5"), getTagSet("Recommended")),
                                new Food(new Name("BBQ Tail"), new BigDecimal("49.9"), getTagSet()))),

            new Restaurant(new Name("Short Beach Seafood"), LocationMap.getLocation("Changi").get(),
                    getTagSet("Seafood"),
                    getMenu(new Food(new Name("Oysters with Cheese"), new BigDecimal("15"), getTagSet("Recommended")),
                            new Food(new Name("Seahorse Roe"), new BigDecimal("10"), getTagSet()),
                            new Food(new Name("Fried Anglerfish"), new BigDecimal("25"), getTagSet("Recommended")),
                            new Food(new Name("Turtle Stew"), new BigDecimal("25"), getTagSet()),
                            new Food(new Name("Shark Meatballs"), new BigDecimal("30.5"), getTagSet()),
                            new Food(new Name("Starfish Tartare"), new BigDecimal("49.9"), getTagSet()))),

            new Restaurant(new Name("IchiNiSan Ramen"), LocationMap.getLocation("City").get(),
                    getTagSet("Japanese"),
                    getMenu(new Food(new Name("Chicken Ramen"), new BigDecimal("10"), getTagSet()),
                            new Food(new Name("Pork Ramen"), new BigDecimal("10"), getTagSet()),
                            new Food(new Name("Sushi Ramen"), new BigDecimal("15"), getTagSet()),
                            new Food(new Name("Wasabi Ramen"), new BigDecimal("15"), getTagSet("Recommended")),
                            new Food(new Name("Seafood Ramen"), new BigDecimal("25"), getTagSet()),
                            new Food(new Name("Ramen Teppanyaki"), new BigDecimal("20"), getTagSet("Recommended"))))

        };
    }

    public static Order[] getSampleOrders() {
        return new Order[]{
            new Order.OrderBuilder().setOrderName(new Name("Order 1")).setCustomer(new Name("AlexYeoh99"))
                    .setRestaurant(new Name("Burger Palace")).setDeliveryman(new Name("Damith"))
                    .setFood(Map.ofEntries(Map.entry(new Name("Ginger Burger"), 2),
                            Map.entry(new Name("Crispy Duck Burger"), 4),
                            Map.entry(new Name("Pickle Burger"), 1)))
                    .completeOrder(),
            new Order.OrderBuilder().setOrderName(new Name("Order 2")).setCustomer(new Name("BigEaterYu"))
                    .setRestaurant(new Name("Prata House")).setDeliveryman(new Name("Donald Trump"))
                    .setFood(Map.ofEntries(Map.entry(new Name("Plain Prata"), 2),
                            Map.entry(new Name("Cheese Prata"), 4),
                            Map.entry(new Name("Prata Bomb"), 10),
                            Map.entry(new Name("Curry Waterfall"), 1)))
                    .completeOrder(),
            new Order.OrderBuilder().setOrderName(new Name("Order 3")).setCustomer(new Name("PrettyCharlotte"))
                    .setRestaurant(new Name("SkyBar Bar and Restaurant")).setDeliveryman(new Name("Charlie Choong"))
                    .setFood(Map.ofEntries(Map.entry(new Name("Buffalo Wings"), 15),
                            Map.entry(new Name("Rhinoceros Pizza"), 1),
                            Map.entry(new Name("Rat with Caviar"), 1)))
                    .completeOrder(),
            new Order.OrderBuilder().setOrderName(new Name("Order 4")).setCustomer(new Name("David"))
                    .setRestaurant(new Name("IchiNiSan Ramen")).setDeliveryman(new Name("Ethan Lim"))
                    .setFood(Map.ofEntries(Map.entry(new Name("Wasabi Ramen"), 1),
                            Map.entry(new Name("Ramen Teppanyaki"), 3)))
                    .completeOrder(),
            new Order.OrderBuilder().setOrderName(new Name("Order 5")).setCustomer(new Name("ProphetIbrahim"))
                    .setRestaurant(new Name("Piggys Self Barbeque")).setDeliveryman(new Name("Yuen Jun Rong"))
                    .setFood(Map.ofEntries(Map.entry(new Name("BBQ Head"), 1),
                                Map.entry(new Name("BBQ Trotter"), 2),
                                Map.entry(new Name("BBQ Nails"), 30),
                                Map.entry(new Name("BBQ Tail"), 10)))
                    .completeOrder(),
            new Order.OrderBuilder().setOrderName(new Name("Order 6")).setCustomer(new Name("CallMeRightathaway"))
                    .setRestaurant(new Name("Short Beach Seafood")).setDeliveryman(new Name("Jynn Shen"))
                    .setFood(Map.ofEntries(Map.entry(new Name("Oysters with Cheese"), 10),
                            Map.entry(new Name("Fried Anglerfish"), 3),
                            Map.entry(new Name("Turtle Stew"), 1)))
                    .completeOrder()
        };
    }

    public static Deliveryman[] getSampleDeliverymen() {
        return new Deliveryman[]{
            new Deliveryman(new Name("Damith"), new Phone("83412321"),
                    getTagSet("inactive", "giveCharlieAplus"), new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Donald Trump"), new Phone("91234567"),
                    getTagSet("buff", "powerful", "bestDeliveryman2019", "notFat", "cuteHairstyle"),
                    new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Charlie Choong"), new Phone("98887146"),
                getTagSet("active", "AisEnough"), new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Low Ee Ter"), new Phone("99367862"), getTagSet("inactive"),
                    new StatusTag("UNAVAILABLE")),
            new Deliveryman(new Name("Yuen Jun Rong"), new Phone("92345678"), getTagSet("veryactive"),
                    new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Jynn Shen"), new Phone("82632181"), getTagSet("veryactive"),
                new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Jackie Chan"), new Phone("82321242"), getTagSet("deliverymaster"),
                    new StatusTag("UNAVAILABLE")),
            new Deliveryman(new Name("Barry Allen"), new Phone("88547422"), getTagSet("lightningfast"),
                    new StatusTag("AVAILABLE")),
            new Deliveryman(new Name("Gloria Tan"), new Phone("83123111"), getTagSet()),
            new Deliveryman(new Name("Ethan Lim"), new Phone("83123875"), getTagSet("loyal"),
                    new StatusTag("DELIVERING")),
            new Deliveryman(new Name("Lee Hsien Kun"), new Phone("88887777"), getTagSet(),
                new StatusTag("AVAILABLE")),
            new Deliveryman(new Name("Lee Hsien Kong"), new Phone("88889999"), getTagSet(),
                    new StatusTag("AVAILABLE"))
        };
    }

    public static ReadOnlyCustomerDatabase getSampleCustomerDatabase() {
        CustomerDatabase sampleCd = new CustomerDatabase();
        Customer[] sampleCustomerArray = getSampleCustomers();
        for (Customer sampleCustomer : sampleCustomerArray) {
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

    public static ReadOnlyOrderDatabase getSampleOrderDatabase() {
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
