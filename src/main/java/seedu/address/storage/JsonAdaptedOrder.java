package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

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
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    // Identity fields
    private final UUID id;

    // Data fields
    private final Customer customer;
    private final Phone phone;
    private final Price price;
    private final Status status;
    private final Optional<Schedule> schedule;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("id") UUID id,
                            @JsonProperty("customer") Customer customer,
                            @JsonProperty("phone") Phone phone, @JsonProperty("price") Price price,
                            @JsonProperty("status") Status status, @JsonProperty("schedule") Schedule schedule,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.price = price;
        this.status = status;
        this.schedule = Optional.ofNullable(schedule);
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        id = source.getId();
        customer = source.getCustomer();
        phone = source.getPhone();
        price = source.getPrice();
        status = source.getStatus();
        schedule = source.getSchedule();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        final List<Tag> orderTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            orderTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UUID.class.getSimpleName()));
        }

        final UUID modelId = id;

        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Customer.class.getSimpleName()));
        }
        if (!customer.getCustomerName().isValidCustomerName(customer.getCustomerName().toString())) {
            throw new IllegalValueException(CustomerName.MESSAGE_CONSTRAINTS);
        }
        if (!customer.getContactNumber().isValidContactNumber(customer.getContactNumber().toString())) {
            throw new IllegalValueException(ContactNumber.MESSAGE_CONSTRAINTS);
        }
        if (!customer.getEmail().isValidEmail(customer.getEmail().toString())) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Customer modelCustomer = new Customer(customer.getCustomerName(), customer.getContactNumber(),
                customer.getEmail(), customer.getTags());

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!IdentityNumber.isValidIdentityNumber(phone.getIdentityNumber().toString())) {
            throw new IllegalValueException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        if (!SerialNumber.isValidSerialNumber(phone.getSerialNumber().toString())) {
            throw new IllegalValueException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        if (!PhoneName.isValidPhoneName(phone.getPhoneName().toString())) {
            throw new IllegalValueException(PhoneName.MESSAGE_CONSTRAINTS);
        }
        if (!Brand.isValidBrand(phone.getBrand().toString())) {
            throw new IllegalValueException(Brand.MESSAGE_CONSTRAINTS);
        }
        if (!Capacity.isValidCapacity(phone.getCapacity().toString())) {
            throw new IllegalValueException(Capacity.MESSAGE_CONSTRAINTS);
        }
        if (!Colour.isValidColour(phone.getColour().toString())) {
            throw new IllegalValueException(Colour.MESSAGE_CONSTRAINTS);
        }
        if (!Cost.isValidCost(phone.getCost().toString())) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone.getIdentityNumber(), phone.getSerialNumber(),
                phone.getPhoneName(), phone.getBrand(), phone.getCapacity(), phone.getColour(),
                phone.getCost(), phone.getTags());

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price.toString())) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price.toString());

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        final Status modelStatus = status;

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(schedule.get().getVenue().toString())) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }

        Optional<Schedule> modelSchedule = Optional.empty();
        if (!schedule.isEmpty()) {
            modelSchedule = Optional.of(new Schedule(schedule.get().getId(), schedule.get().getCalendar(),
                    schedule.get().getVenue(), schedule.get().getTags()));
        }

        final Set<Tag> modelTags = new HashSet<>(orderTags);
        return new Order(modelId, modelCustomer, modelPhone, modelPrice, modelStatus, modelSchedule,
                modelTags);
    }

}
