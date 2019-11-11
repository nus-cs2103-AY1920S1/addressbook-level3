package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.expense.Expense;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;


/**
 * Jackson friendly version of {@code Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String destination;
    //private final Optional<Booking> booking;
    private final Optional<JsonAdaptedExpense> expense;
    //private final Optional<Inventory> inventory;
    private final Optional<String> description;

    //Added by Karan Dev Sapra
    private final Optional<List<JsonAdaptedInventory>> inventoryList;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
            @JsonProperty("startTime") LocalDateTime from,
            @JsonProperty("endTime") LocalDateTime to,
            @JsonProperty("destination") String destination, @JsonProperty("description") Optional<String> description,
                            @JsonProperty("expense") Optional<JsonAdaptedExpense> expense,
                            @JsonProperty("inventoryList") Optional<List<JsonAdaptedInventory>> inventoryList) {
        //, @JsonProperty("booking")Optional<Booking> booking
        this.name = name;
        this.startTime = from;
        this.endTime = to;
        this.destination = destination;

        this.inventoryList = inventoryList;

        this.description = description;
        this.expense = expense;

    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {

        System.out.println("PRESENCE OF MIND");

        this.name = source.getName().fullName;
        this.startTime = source.getStartDate();
        this.endTime = source.getEndDate();
        this.destination = source.getDestination().value;
        if (source.getExpense().isPresent()) {
            this.expense = Optional.of(new JsonAdaptedExpense(source.getExpense().get()));
        } else {
            this.expense = Optional.empty();
        }
        if (source.getDescription().isPresent()) {
            this.description = Optional.of(source.getDescription().get().description);
        } else {
            this.description = Optional.empty();
        }

        //System.out.println("BEFORE ENTERING THE PRESENCE with inventoryList " + source.getInventoryList());

        if (source.getInventoryList().isPresent()) {

            this.inventoryList = Optional.of(new ArrayList<>());

            this.inventoryList.get().addAll(source.getInventoryList().get()
                    .stream().map(JsonAdaptedInventory::new)
                    .collect(Collectors.toList())
            );


            //this.inventoryList = Optional.of(source.getInventoryList().get().getList()));
        } else {
            this.inventoryList = Optional.empty();
        }
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if (startTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelStartTime = startTime;

        if (endTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelEndTime = endTime;

        if (destination == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }

        if (!Location.isValidLocation(destination)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }

        final Location modelDestination = new Location(destination);

        //No check for TotalBudget (defaults endTime 0)
        final Optional<Expense> modelExpense;

        if (expense.isPresent()) {

            modelExpense = Optional.of(expense.get().toModelType());
        } else {
            modelExpense = Optional.empty();
        }

        Optional<List<Inventory>> modelInventoryList;

        if (inventoryList.isPresent()) {

            final List<Inventory> inventories = new ArrayList<>();

            for (JsonAdaptedInventory inventory : inventoryList.get()) {
                inventories.add(inventory.toModelType());
            }

            modelInventoryList = Optional.of(new ArrayList<>());
            modelInventoryList.get().addAll(inventories);

        } else {
            modelInventoryList = Optional.empty();
        }


        final Optional<Description> modelDescription;

        if (description.isPresent()) {
            if (!Description.isValidDescription(description.get())) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
            }
            modelDescription = Optional.of(new Description(description.get()));
        } else {
            modelDescription = Optional.empty();
        }

        return new Event(modelName, modelStartTime, modelEndTime, modelExpense, modelDestination,
                modelDescription, modelInventoryList);

    }
}


