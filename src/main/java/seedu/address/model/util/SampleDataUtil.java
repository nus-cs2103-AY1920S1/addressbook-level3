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
 * Contains utility methods for populating all {@code DataBook} with sample data.
 */
public class SampleDataUtil {

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
                new Email("royb@example.com"), getTagSet("colleagues")),
            new Customer(new CustomerName("Roy tan"), new ContactNumber("92626617"),
                new Email("roy2@example.com"), getTagSet("Friends")),
            new Customer(new CustomerName("albert Oon"), new ContactNumber("88626617"),
                new Email("albert2@example.com"), getTagSet("family")),

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
                            Capacity.SIZE_128GB, new Colour("Jet Black"), new Cost("$98.23"),
                            getTagSet("New")),
                   new Price("$99.89"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()),
                   getTagSet("Urgent")),
            new Order(UUID.randomUUID(),
                  new Customer(new CustomerName("Alex Yeoh"), new ContactNumber("87438807"),
                        new Email("alexyeoh@example.com"), getTagSet("friends")),
                  new Phone(new IdentityNumber("990001940227978"),
                        new SerialNumber("1d27s9az"), new PhoneName("iPhone 8"), new Brand("Apple"),
                        Capacity.SIZE_64GB, new Colour("White"), new Cost("$99.12"),
                        getTagSet("Used")),
                  new Price("$100.12"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                  new Customer(new CustomerName("David Li"), new ContactNumber("91031283"),
                        new Email("lidavid3@example.com"), getTagSet("family")),
                  new Phone(new IdentityNumber("352039075644275"),
                        new SerialNumber("2ncs86ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                        Capacity.SIZE_256GB, new Colour("Black"), new Cost("$104.32"),
                        getTagSet("New")),
                  new Price("$105.12"),
                    Status.SCHEDULED,
                    Optional.of(sampleSchedule()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("David Li"), new ContactNumber("91031281"),
                                new Email("lidavid1@example.com"), getTagSet("family")),
                        new Phone(new IdentityNumber("352039075644273"),
                                new SerialNumber("2ncs82ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$110.50"),
                                getTagSet("New")),
                        new Price("$104.52"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule2()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("David Li"), new ContactNumber("91031282"),
                                new Email("lidavid2@example.com"), getTagSet("family")),
                        new Phone(new IdentityNumber("352039075644271"),
                                new SerialNumber("2ncs81ma"), new PhoneName("Galaxy S11"), new Brand("Samsung"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$99.65"),
                                getTagSet("New")),
                        new Price("$101.67"),
                        Status.CANCELLED,
                        Optional.of(sampleSchedule3()), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("Tao Nan"), new ContactNumber("12345678"),
                                new Email("TaoNan@example.com"), getTagSet("bigCustomer")),
                        new Phone(new IdentityNumber("352039475644270"),
                                new SerialNumber("2ncs89ma"), new PhoneName("Galaxy apollo 20"), new Brand("NotApple"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$101.00"),
                                getTagSet("New")),
                        new Price("$101.45"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(01, 23)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("Tao tan"), new ContactNumber("01234323"),
                                new Email("Taotan@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("182043475644270"),
                                new SerialNumber("2ncs90ma"), new PhoneName("Galaxy apollo 19"), new Brand("NotApple"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$99.21"),
                                getTagSet("New")),
                        new Price("$100.32"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(04,
                                15)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("albert"), new ContactNumber("01234343"),
                                new Email("albert@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("181043475644270"),
                                new SerialNumber("2ncs90b2"), new PhoneName("Galaxy apollo 20"), new Brand("Applle"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$450.21"),
                                getTagSet("New")),
                        new Price("$600.32"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(03,
                                16)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("ming tan"), new ContactNumber("01234543"),
                                new Email("MING@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("231031475644270"),
                                new SerialNumber("2bnd20b2"), new PhoneName("the Flexi"), new Brand("FlexPh"),
                                Capacity.SIZE_256GB, new Colour("Black"), new Cost("$600.35"),
                                getTagSet("New")),
                        new Price("$800.76"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(02,
                                15)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("mindYag"), new ContactNumber("05234543"),
                                new Email("theYag@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("231043487644270"),
                                new SerialNumber("2bndk592"), new PhoneName("the Flexi"), new Brand("FlexPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$900.35"),
                                getTagSet("New")),
                        new Price("$1200.23"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(7,
                                23)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("Valudh"), new ContactNumber("05234546"),
                                new Email("valu@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("251044575644270"),
                                new SerialNumber("2gnd4553"), new PhoneName("the FlexiUPZ"), new Brand("FlexPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$1200.35"),
                                getTagSet("New")),
                        new Price("$1500.23"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(8,
                                24)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("muth"), new ContactNumber("12234546"),
                                new Email("MUH@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("254124575644270"),
                                new SerialNumber("2gnd4872"), new PhoneName("the Flexi"), new Brand("Flex2"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$1300.35"),
                                getTagSet("New")),
                        new Price("$1600.98"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(9,
                                14)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("Valudh"), new ContactNumber("05234546"),
                                new Email("valu@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("251044599644270"),
                                new SerialNumber("2gnd4553"), new PhoneName("the Flexi"), new Brand("FlexPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$1400.35"),
                                getTagSet("New")),
                        new Price("$1800.23"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(11,
                                21)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("Val"), new ContactNumber("05235346"),
                                new Email("vae@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("251044591244270"),
                                new SerialNumber("34fd4553"), new PhoneName("the Flex3"), new Brand("FlexPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$1300.35"),
                                getTagSet("New")),
                        new Price("$1800.23"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(0,
                                14)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("eba"), new ContactNumber("07535346"),
                                new Email("bae@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("223044591244270"),
                                new SerialNumber("34f5d553"), new PhoneName("the Flzcxx3"), new Brand("FlexPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$1500.35"),
                                getTagSet("New")),
                        new Price("$2000.23"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(5,
                                18)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("miacer"), new ContactNumber("07535526"),
                                new Email("miead@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("243044591244270"),
                                new SerialNumber("34f5d953"), new PhoneName("the Flzcxx3"), new Brand("FledxPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$900.35"),
                                getTagSet("New")),
                        new Price("$1700.64"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(8,
                                28)), getTagSet("New")),
            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("miyyr"), new ContactNumber("05235526"),
                                new Email("m2ad@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("243044591249850"),
                                new SerialNumber("34gdd953"), new PhoneName("the Flzcxx3"), new Brand("FledxPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$800.35"),
                                getTagSet("New")),
                        new Price("$1700.64"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(6,
                                28)), getTagSet("New")),

            new Order(UUID.randomUUID(),
                        new Customer(new CustomerName("mto"), new ContactNumber("06415526"),
                                new Email("1ad@example.com"), getTagSet("smallCustomer")),
                        new Phone(new IdentityNumber("251044591249850"),
                                new SerialNumber("34g53353"), new PhoneName("the Fdlzcxx3"), new Brand("FledxPh"),
                                Capacity.SIZE_128GB, new Colour("Black"), new Cost("$700.35"),
                                getTagSet("New")),
                        new Price("$1800.64"),
                        Status.COMPLETED,
                        Optional.of(sampleSchedule4(10,
                                12)), getTagSet("New"))
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

    /**
     *Return sample Schedule object
     */
    private static Schedule sampleSchedule4(int month, int day) {
        return new Schedule(
                UUID.randomUUID(),
                new Calendar.Builder().setDate(2019, month, day).build(),
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
