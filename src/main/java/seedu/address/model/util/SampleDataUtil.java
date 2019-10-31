package seedu.address.model.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    /*public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }*/

    public static Customer[] getSampleCustomers() {
        return new Customer[] {
            new Customer(new CustomerName("Alex Yeoh"), new ContactNumber("87438807"),
                  new Email("alexyeoh@example.com"), getTagSet("friends")),
            new Customer(new CustomerName("Bernice Yu"), new ContactNumber("99272758"),
                  new Email("berniceyu@example.com"), getTagSet("colleagues", "friends")),
            new Customer(new CustomerName("Charlotte Oliveiro"), new ContactNumber("93210283"),
                new Email("charlotte@example.com"), getTagSet("neighbours")),
            new Customer(new CustomerName("David Li"), new ContactNumber("91031282"),
                new Email("lidavid@example.com"), getTagSet("family")),
            new Customer(new CustomerName("Irfan Ibrahim"), new ContactNumber("92492021"),
                new Email("irfan@example.com"), getTagSet("classmates")),
            new Customer(new CustomerName("Roy Balakrishnan"), new ContactNumber("92624417"),
                new Email("royb@example.com"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyDataBook<Customer> getSampleCustomerBook() {
        DataBook<Customer> sampleCustBook = new DataBook<>();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleCustBook.add(sampleCustomer);
        }
        return sampleCustBook;
    }

    public static Phone[] getSamplePhones() {
        return new Phone[] {
            new Phone(new IdentityNumber("990003709954321"),
                new SerialNumber("28xa9q2ns"), new PhoneName("iPhone 11"), new Brand("Apple"),
                Capacity.SIZE_128GB, new Colour("Jet Black"), new Cost("$1100"),
                getTagSet("New")),
            new Phone(new IdentityNumber("990001940227978"),
                new SerialNumber("1d27s9az"), new PhoneName("iPhone 8"), new Brand("Apple"),
                Capacity.SIZE_64GB, new Colour("White"), new Cost("$400"),
                getTagSet("Used")),
            new Phone(new IdentityNumber("352039075644270"),
                new SerialNumber("2ncs81ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$1000"),
                getTagSet("New")),
            new Phone(new IdentityNumber("013373005371667"),
                new SerialNumber("29asdn1mx"), new PhoneName("iPhone XR"), new Brand("Apple"),
                Capacity.SIZE_256GB, new Colour("Coral"), new Cost("$750"),
                getTagSet("Used")),
            new Phone(new IdentityNumber("358373060612446"),
                new SerialNumber("129zn28xa"), new PhoneName("Pixel 3"), new Brand("Google"),
                Capacity.SIZE_64GB, new Colour("White"), new Cost("$800"),
                getTagSet("New")),
            new Phone(new IdentityNumber("990002722150925"),
                new SerialNumber("x82n10zm1a"), new PhoneName("iPhone 11"), new Brand("Apple"),
                Capacity.SIZE_256GB, new Colour("Purple"), new Cost("$1300"),
                getTagSet("New"))
        };
    }

    public static ReadOnlyDataBook<Phone> getSamplePhoneBook() {
        DataBook<Phone> samplePhoneBook = new DataBook<>();
        for (Phone samplePhone : getSamplePhones()) {
            samplePhoneBook.add(samplePhone);
        }
        return samplePhoneBook;
    }

    public static Schedule[] getSampleSchedule() {
        return new Schedule[] {
            new Schedule(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"), Calendar.getInstance(),
                    new Venue("Kovan"), getTagSet("Rush")),
            new Schedule(UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66"), Calendar.getInstance(),
                    new Venue("Orchard"), getTagSet("Office"))
        };
    }

    public static ReadOnlyDataBook<Schedule> getSampleScheduleBook() {
        DataBook<Schedule> sampleScheduleBook = new DataBook<>();
        for (Schedule sampleSchedule : getSampleSchedule()) {
            sampleScheduleBook.add(sampleSchedule);
        }
        return sampleScheduleBook;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order(UUID.randomUUID(),
                   new Customer(new CustomerName("Charlotte Oliveiro"), new ContactNumber("93210283"),
                        new Email("charlotte@example.com"), getTagSet("neighbours")),
                    new Phone(new IdentityNumber("990003709954321"),
                            new SerialNumber("28xa9q2ns"), new PhoneName("iPhone 11"), new Brand("Apple"),
                            Capacity.SIZE_128GB, new Colour("Jet Black"), new Cost("$1100"),
                            getTagSet("New")),
                   new Price("$1000"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()),
                   getTagSet("Urgent")),
            new Order(UUID.randomUUID(),
                  new Customer(new CustomerName("Alex Yeoh"), new ContactNumber("87438807"),
                        new Email("alexyeoh@example.com"), getTagSet("friends")),
                  new Phone(new IdentityNumber("990001940227978"),
                        new SerialNumber("1d27s9az"), new PhoneName("iPhone 8"), new Brand("Apple"),
                        Capacity.SIZE_64GB, new Colour("White"), new Cost("$400"),
                        getTagSet("Used")),
                  new Price("$1500"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                  new Customer(new CustomerName("David Li"), new ContactNumber("91031282"),
                        new Email("lidavid@example.com"), getTagSet("family")),
                  new Phone(new IdentityNumber("352039075644270"),
                        new SerialNumber("2ncs81ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                        Capacity.SIZE_256GB, new Colour("Black"), new Cost("$540"),
                        getTagSet("New")),
                  new Price("$1200"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("David Li"), new ContactNumber("91031282"),
                                new Email("lidavid@example.com"), getTagSet("family")),
                        new Phone(new IdentityNumber("352039075644270"),
                                new SerialNumber("2ncs81ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$581"),
                                getTagSet("New")),
                        new Price("$1200"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule2()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("David Li"), new ContactNumber("91031282"),
                                new Email("lidavid@example.com"), getTagSet("family")),
                        new Phone(new IdentityNumber("352039075644270"),
                                new SerialNumber("2ncs81ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$95"),
                                getTagSet("New")),
                        new Price("$1200"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule3()), getTagSet("New"))

        };
    }

    /**
     *Return sample Schedule object
     */
    private static Schedule sampleSchedule() {
        return new Schedule(
                UUID.randomUUID(),
                new Calendar.Builder().setDate(2019, 10, 02).build(),
                new Venue("test venue"),
                getTagSet("New"));
    }

    /**
     *Return sample Schedule object
     */
    private static Schedule sampleSchedule2() {
        return new Schedule(
                UUID.randomUUID(),
                new Calendar.Builder().setDate(2019, 1, 31).build(),
                new Venue("test venue"),
                getTagSet("New"));
    }
    /**
     *Return sample Schedule object
     */
    private static Schedule sampleSchedule3() {
        return new Schedule(
                UUID.randomUUID(),
                new Calendar.Builder().setDate(2019, 4, 31).build(),
                new Venue("test venue"),
                getTagSet("New"));
    }



    public static ReadOnlyDataBook<Order> getSampleOrderBook() {
        DataBook<Order> sampleOrderBook = new DataBook<>();
        for (Order sampleOrder : getSampleOrders()) {
            sampleOrderBook.add(sampleOrder);
        }
        return sampleOrderBook;
    }

}
