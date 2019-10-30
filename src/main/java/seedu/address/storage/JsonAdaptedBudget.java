package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.budget.Percentage;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.expense.UniqueIdentifier;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String description;
    private final String amount;
    private final String startDate;
    private final String endDate;
    private final String period;
    private final boolean isPrimary;
    private List<String> expenseIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("endDate") String endDate,
                             @JsonProperty("period") String period,
                             @JsonProperty("expenses") List<String> expenseIds,
                             @JsonProperty("isPrimary") boolean isPrimary) {
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        if (expenseIds != null) {
            this.expenseIds.addAll(expenseIds);
        }
        this.isPrimary = isPrimary;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        requireNonNull(source);
        description = source.getDescription().fullDescription;
        amount = source.getAmount().value;
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        period = source.getPeriod().toString();
        expenseIds.addAll(source.getExpenses().stream()
                .map(e -> e.getUniqueIdentifier().value)
                .collect(Collectors.toList()));
        isPrimary = source.isPrimary();
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budget.
     */
    public Budget toModelType(List<JsonAdaptedExpense> expenses) throws IllegalValueException {
        final ObservableList<Expense> expenseList = FXCollections.observableArrayList();
        for (String id : expenseIds) {
            for (JsonAdaptedExpense je : expenses) {
                Expense e = je.toModelType();
                if (e.isWithId(id)) {
                    expenseList.add(e);
                }
            }
        }

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(amount)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelAmount = new Price(amount);

        if (startDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName()));
        }

        Optional<Timestamp> potentialStartDate = Timestamp.createTimestampFromStorage(startDate);
        if (potentialStartDate.isEmpty()) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_DATE);
        }
        final Timestamp modelStartDate = potentialStartDate.get();

        if (endDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName()));
        }

        Optional<Timestamp> potentialEndDate = Timestamp.createTimestampFromStorage(endDate);
        if (potentialEndDate.isEmpty()) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_DATE);
        }
        final Timestamp modelEndDate = potentialEndDate.get();

        if (period == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Period.class.getSimpleName()));
        }
        try {
            ParserUtil.parsePeriod(period);
        } catch (ParseException e) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_PERIOD);
        }
        final BudgetPeriod modelPeriod = ParserUtil.parsePeriod(period);

        /*
        if (proportionUsed == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Percentage.class.getSimpleName()));
        }
        int proportionValue = ParserUtil.parsePercentage(proportionUsed);
        if (!Percentage.isValidPercentage(proportionValue)) {
            throw new IllegalValueException(Percentage.MESSAGE_CONSTRAINTS);
        }
        final Percentage modelProportionUsed = ParserUtil.parsePercentage(proportionUsed);
        */

        Budget budget = new Budget(modelDescription, modelAmount, modelStartDate,
                modelPeriod, expenseList, isPrimary);

        return budget;
    }
}
