package seedu.moolah.storage;

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
import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

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
                             @JsonProperty("expenses") List<String> expenseIds) {
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        if (expenseIds != null) {
            this.expenseIds.addAll(expenseIds);
        }
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        requireNonNull(source);
        description = source.getDescription().fullDescription;
        amount = source.getAmount().value;
        startDate = source.getWindowStartDate().toString();
        endDate = source.getWindowEndDate().toString();
        period = source.getBudgetPeriod().toString();
        expenseIds.addAll(source.getExpenses().stream()
                .map(e -> e.getUniqueIdentifier().value)
                .collect(Collectors.toList()));
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
                if (e.uniqueIdIs(id)) {
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

        Optional<Timestamp> potentialStartDate = Timestamp.createGeneralTimestampFromStorage(startDate);
        if (potentialStartDate.isEmpty()) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        }
        final Timestamp modelStartDate = potentialStartDate.get();

        if (endDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName()));
        }

        Optional<Timestamp> potentialEndDate = Timestamp.createGeneralTimestampFromStorage(endDate);
        if (potentialEndDate.isEmpty()) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
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

        Budget budget = new Budget(modelDescription, modelAmount, modelStartDate,
                modelPeriod, expenseList, false);

        return budget;
    }
}
