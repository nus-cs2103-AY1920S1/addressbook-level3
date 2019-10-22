package seedu.deliverymans.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.addressbook.AddressBook;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.addressbook.person.Remark;
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
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[]{
        };
    }


    public static Customer[] getSampleCustomers() {
        return new Customer[]{
                new Customer(new Name("Alex Yeoh"), new Phone("87438807"), getTagSet("FastFood")),
                new Customer(new Name("Bernice Yu"), new Phone("99272758"), getTagSet("Indian")),
                new Customer(new Name("Charlotte Oliveiro"), new Phone("93210283"), getTagSet("Bar")),
                new Customer(new Name("David Li"), new Phone("91031282"), getTagSet("Japanese")),
                new Customer(new Name("Ifran Ibrahim"), new Phone("92492021"), getTagSet("Barbeque"))
        };
    }

    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[]{
                new Restaurant(new Name("KFC"), LocationMap.getLocation("Jurong").get(),
                        getTagSet("FastFood", "Western")),
                new Restaurant(new Name("Prata House"), LocationMap.getLocation("Bishan").get(),
                        getTagSet("Indian")),
                new Restaurant(new Name("SkyBar Bar and Restaurant"), LocationMap.getLocation("Marina").get(),
                        getTagSet("Bar")),
                new Restaurant(new Name("IchiNiSan Ramen"), LocationMap.getLocation("City").get(),
                        getTagSet("Japanese")),
                new Restaurant(new Name("Piggys Self Barbeque"), LocationMap.getLocation("Woodlands").get(),
                        getTagSet("Barbeque"))
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[]{
                        new Order(new Name("Alex Yeoh"), new Name("KFC"), getTagSet("3PCChickenMeal")),
                        new Order(new Name("Bernice Yu"), new Name("Prata House"), getTagSet("RotiPrata")),
                        new Order(new Name("Charlotte Oliveiro"), new Name("SkyBar Bar and Restaurant"),
                                getTagSet("SushiandBurger")),
                        new Order(new Name("David Li"), new Name("IchiNiSan Ramen"),
                                getTagSet("a", "b", "c", "d", "e")),
                        new Order(new Name("Irfan Ibrahim"), new Name("Piggys Self Barbeque"),
                getTagSet("chillicrab", "sushi"))
        };
    }

    public static Deliveryman[] getSampleDeliverymen() {
        return new Deliveryman[]{
                new Deliveryman(new Name("Damith"), new Phone("99999999"), getTagSet("inactive")),
                new Deliveryman(new Name("Donald Trump"), new Phone("91234567"), getTagSet("buff", "powerful")),
                new Deliveryman(new Name("Charlie Choong"), new Phone("98887146"), getTagSet("active")),
                new Deliveryman(new Name("Low ee ter"), new Phone("99367862"), getTagSet("inactive")),
                new Deliveryman(new Name("Yuen Jun rong "), new Phone("12345678"), getTagSet("veryactive"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
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
}
