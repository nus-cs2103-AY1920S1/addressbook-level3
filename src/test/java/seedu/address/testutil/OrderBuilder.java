package seedu.address.testutil;

import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final Customer DEFAULT_CUSTOMER = CUSTOMERONE;
    private static final Phone DEFAULT_PHONE = IPHONEONE;
    private static final String DEFAULT_PRICE = "$1212";
    private static final Status DEFAULT_STATUS = Status.UNSCHEDULED;
    private static final Optional<Schedule> DEFAULT_SCHEDULE = Optional.empty();

    private UUID id;
    private Customer customer;
    private Phone phone;
    private Price price;
    private Status status;
    private Optional<Schedule> schedule;
    private Set<Tag> tags;

    public OrderBuilder() {
        id = UUID.randomUUID();
        customer = new CustomerBuilder(CUSTOMERONE).build();
        phone = new PhoneBuilder(IPHONEONE).build();
        price = new Price(DEFAULT_PRICE);
        status = DEFAULT_STATUS;
        schedule = DEFAULT_SCHEDULE;
        tags = new HashSet<>();
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        id = orderToCopy.getId();
        customer = orderToCopy.getCustomer();
        phone = orderToCopy.getPhone();
        price = orderToCopy.getPrice();
        status = orderToCopy.getStatus();
        schedule = orderToCopy.getSchedule();
        tags = new HashSet<>(orderToCopy.getTags());
    }

    /**
     * Sets the {@code UUID} of the {@code Order} that we are building.
     */
    public OrderBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Customer} of the {@code Order} that we are building.
     */
    public OrderBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Order} that we are building.
     */
    public OrderBuilder withPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Order} that we are building.
     */
    public OrderBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Order} that we are building.
     */
    public OrderBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code Order} that we are building.
     */
    public OrderBuilder withSchedule(Optional<Schedule> schedule) {
        this.schedule = schedule;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Order} that we are building.
     */
    public OrderBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Order build() {
        return new Order(id, customer, phone, price, status, schedule, tags);
    }
}
