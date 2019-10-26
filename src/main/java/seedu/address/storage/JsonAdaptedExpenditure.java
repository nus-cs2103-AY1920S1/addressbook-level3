package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenditure.DayNumber;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Jackson friendly version of {@code Expenditure}.
 */
public class JsonAdaptedExpenditure {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expenditure's %s field is missing!";

    private final String name;
    private final Double budget;
    private final Optional<String> dayNumber;
    private final boolean isRemovable;

    /**
     * Constructs a {@code JsonAdaptedExpenditure} with the given Expenditure details.
     */
    @JsonCreator
    public JsonAdaptedExpenditure(@JsonProperty("name") String name,
                                  @JsonProperty("budget") Double budget,
                                  @JsonProperty("dayNumber") Optional<String> dayNumber,
                                  @JsonProperty("removable") boolean isRemovable) {
        this.name = name;
        this.budget = budget;
        this.dayNumber = dayNumber;
        this.isRemovable = isRemovable;

    }

    /**
     * Converts a given {@code Expenditure} into this class for Jackson use.
     */
    public JsonAdaptedExpenditure(Expenditure source) {
        this.name = source.getName().fullName;
        this.budget = source.getBudget().value;
        if (source.getDayNumber().isPresent()) {
            this.dayNumber = Optional.of(source.getDayNumber().get().value);
        } else {
            this.dayNumber = Optional.empty();
        }
        this.isRemovable = source.getRemovability();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Expenditure} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Expenditure toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (budget == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }

        if (!Budget.isValidBudget(budget.toString())) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Optional<DayNumber> modelDayNumber;

        if (dayNumber.isPresent()) {
            modelDayNumber = Optional.of(new DayNumber(dayNumber.get()));
        } else {
            modelDayNumber = Optional.empty();
        }

        final Name modelName = new Name(name);
        final Budget modelTotalBudget = new Budget(budget);

        return new Expenditure(modelName, modelTotalBudget, modelDayNumber, isRemovable);
    }
}
