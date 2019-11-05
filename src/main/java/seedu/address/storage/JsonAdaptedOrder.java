package seedu.address.storage;

import java.text.ParseException;
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
    private final String id;

    // Data fields
    private final JsonAdaptedCustomer customer;
    private final JsonAdaptedPhone phone;
    private final String price;
    private final String status;
    private JsonAdaptedSchedule schedule;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("id") String id,
                            @JsonProperty("customer") JsonAdaptedCustomer customer,
                            @JsonProperty("phone") JsonAdaptedPhone phone, @JsonProperty("price") String price,
                            @JsonProperty("status") String status,
                            @JsonProperty("schedule") JsonAdaptedSchedule schedule,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.price = price;
        this.status = status;
        this.schedule = schedule;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        id = source.getId().toString();
        customer = new JsonAdaptedCustomer(source.getCustomer());
        phone = new JsonAdaptedPhone(source.getPhone());
        price = source.getPrice().value;
        status = source.getStatus().toString();
        if (source.getSchedule().isPresent()) {
            schedule = new JsonAdaptedSchedule(source.getSchedule().get());
        }
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException, ParseException {
        final List<Tag> orderTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            orderTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UUID.class.getSimpleName()));
        }

        UUID modelId;
        try {
            modelId = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UUID.class.getSimpleName()));
        }

        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Customer.class.getSimpleName()));
        }
        if (!CustomerName.isValidCustomerName(customer.toModelType().getCustomerName()
                .toString())) {
            throw new IllegalValueException(CustomerName.MESSAGE_CONSTRAINTS);
        }
        if (!ContactNumber.isValidContactNumber(customer.toModelType().getContactNumber()
                .toString())) {
            throw new IllegalValueException(ContactNumber.MESSAGE_CONSTRAINTS);
        }
        if (!Email.isValidEmail(customer.toModelType().getEmail().toString())) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Customer modelCustomer = customer.toModelType();

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!IdentityNumber.isValidIdentityNumber(phone.toModelType().getIdentityNumber().toString())) {
            throw new IllegalValueException(IdentityNumber.MESSAGE_CONSTRAINTS);
        }
        if (!SerialNumber.isValidSerialNumber(phone.toModelType().getSerialNumber().toString())) {
            throw new IllegalValueException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        if (!PhoneName.isValidPhoneName(phone.toModelType().getPhoneName().toString())) {
            throw new IllegalValueException(PhoneName.MESSAGE_CONSTRAINTS);
        }
        if (!Brand.isValidBrand(phone.toModelType().getBrand().toString())) {
            throw new IllegalValueException(Brand.MESSAGE_CONSTRAINTS);
        }
        if (!Capacity.isValidCapacity(phone.toModelType().getCapacity().toString().split("GB")[0])) {
            throw new IllegalValueException(Capacity.MESSAGE_CONSTRAINTS);
        }
        if (!Colour.isValidColour(phone.toModelType().getColour().toString())) {
            throw new IllegalValueException(Colour.MESSAGE_CONSTRAINTS);
        }
        if (!Cost.isValidCost(phone.toModelType().getCost().toString())) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = phone.toModelType();

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        Status temp;

        if (status.equals("Unscheduled")) {
            temp = Status.UNSCHEDULED;
        } else if (status.equals("Scheduled")) {
            temp = Status.SCHEDULED;
        } else if (status.equals("Completed")) {
            temp = Status.COMPLETED;
        } else {
            temp = Status.CANCELLED;
        }
        final Status modelStatus = temp;

        if (schedule == null && !status.equals("Unscheduled")) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        if (schedule != null && !Venue.isValidVenue(schedule.toModelType().getVenue().toString())) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }

        final Optional<Schedule> modelSchedule;
        if (schedule != null) {
            modelSchedule = Optional.of(schedule.toModelType());
        } else {
            modelSchedule = Optional.ofNullable(null);
        }

        final Set<Tag> modelTags = new HashSet<>(orderTags);
        return new Order(modelId, modelCustomer, modelPhone, modelPrice, modelStatus, modelSchedule,
                modelTags);
    }

}
