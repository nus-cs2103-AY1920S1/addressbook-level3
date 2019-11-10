package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.Diary;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseList;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.InventoryList;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.Photo;
import seedu.address.model.trip.Trip;
import seedu.address.storage.diary.JsonAdaptedDiary;

/**
 * Jackson friendly version of {@code Trip}.
 */
class JsonAdaptedTrip {
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Trip's %s field is missing!";

    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String destination;
    private final Double totalBudget;
    private final JsonAdaptedDiary diary;
    private final List<JsonAdaptedDay> dayList = new ArrayList<>();
    private final List<JsonAdaptedExpense> expenseList = new ArrayList<>();
    private final Optional<JsonAdaptedTripPhoto> photo;

    //Added by Karan Dev Sapra
    private final List<JsonAdaptedInventory> inventoryList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTrip} with the given trip details.
     */
    @JsonCreator
    public JsonAdaptedTrip(
            @JsonProperty("name") String name,
            @JsonProperty("startDate")LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("destination")String destination,
            @JsonProperty("totalBudget") Double totalBudget,
            @JsonProperty("dayList")List<JsonAdaptedDay> dayList,
            @JsonProperty("expenseList")List<JsonAdaptedExpense> expenseList,
            @JsonProperty("diary") JsonAdaptedDiary diary,
            @JsonProperty("photo") Optional<JsonAdaptedTripPhoto> photo,
            @JsonProperty("inventoryList") List<JsonAdaptedInventory> inventoryList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.totalBudget = totalBudget;
        if (dayList != null) {
            this.dayList.addAll(dayList);
        }
        if (expenseList != null) {
            this.expenseList.addAll(expenseList);
        }
        this.diary = diary;
        this.photo = photo;
        if (inventoryList != null) {
            this.inventoryList.addAll(inventoryList);
        }
    }

    /**
     * Converts a given {@code Trip} into this class for Jackson use.
     */
    public JsonAdaptedTrip(Trip source) {
        this.name = source.getName().fullName;
        this.startDate = source.getStartDate();
        this.endDate = source.getEndDate();
        this.destination = source.getDestination().value;
        this.totalBudget = source.getBudget().getValue();
        this.dayList.addAll(source.getDayList()
                .asUnmodifiableObservableList()
                .stream().map(JsonAdaptedDay::new)
                .collect(Collectors.toList())
        );
        this.expenseList.addAll(source.getExpenseList()
                .asUnmodifiableObservableList()
                .stream().map(JsonAdaptedExpense::new)
                .collect(Collectors.toList())
        );
        this.diary = new JsonAdaptedDiary(source.getDiary());
        if (source.getPhoto().isPresent()) {
            this.photo = Optional.of(new JsonAdaptedTripPhoto(source.getPhoto().get()));
        } else {
            this.photo = Optional.empty();
        }

        this.inventoryList.addAll(source.getInventoryList()
                .asUnmodifiableObservableList()
                .stream().map(JsonAdaptedInventory::new)
                .collect(Collectors.toList())
        );
    }

    /**
     * Converts this Jackson-friendly adapted trip object into the model's {@code Trip} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted trip.
     */
    public Trip toModelType() throws IllegalValueException {
        final List<Day> days = new ArrayList<>();
        final List<Expense> expenses = new ArrayList<>();
        final List<Inventory> inventories = new ArrayList<>();

        for (JsonAdaptedDay day : dayList) {
            days.add(day.toModelType());
        }

        for (JsonAdaptedExpense expense : expenseList) {
            expenses.add(expense.toModelType());
        }

        for (JsonAdaptedInventory inventory : inventoryList) {
            inventories.add(inventory.toModelType());
        }

        Diary diary = this.diary.toModelType();

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Date"));
        }

        final LocalDateTime modelStartDate = startDate;

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Date"));
        }

        final LocalDateTime modelEndDate = endDate;

        if (destination == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }

        if (!Location.isValidLocation(destination)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }

        final Location modelDestination = new Location(destination);

        //No check for TotalBudget (defaults to 0)
        Budget modelTotalBudget = new Budget(totalBudget);

        Optional<Photo> modelPhoto;
        if (photo.isPresent()) {
            modelPhoto = Optional.of(photo.get().toModelType());
        } else {
            modelPhoto = Optional.empty();
        }

        DayList modelDayList = new DayList(startDate, endDate);
        modelDayList.set(days);

        ExpenseList modelExpenseList = new ExpenseList();
        modelExpenseList.set(expenses);

        InventoryList modelInventoryList = new InventoryList();
        modelInventoryList.set(inventories);

        return new Trip(modelName, modelStartDate, modelEndDate,
                modelDestination, modelTotalBudget, modelDayList, modelExpenseList,
                        diary, modelInventoryList, modelPhoto);
    }
}
