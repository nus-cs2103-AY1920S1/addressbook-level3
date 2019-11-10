package budgetbuddy.storage.transactions;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;


/**
 * Jackson-friendly version of {@link budgetbuddy.model.transaction.Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String direction;
    private final Long amount;
    private final String description;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    public JsonAdaptedTransaction(@JsonProperty("direction") String direction,
                              @JsonProperty("amount") Long amount,
                              @JsonProperty("description") String description,
                              @JsonProperty("categories") List<JsonAdaptedCategory> categories,
                              @JsonProperty("date") String date) {
        requireAllNonNull(date, amount, direction, categories, description);
        this.direction = direction;
        this.amount = amount;
        this.description = description;
        this.categories.addAll(categories);
        this.date = date;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedTransaction(Transaction source) {
        direction = source.getDirection().toString();
        amount = source.getAmount().toLong();
        description = source.getDescription().toString();
        categories.addAll(source.getCategories().stream().map(JsonAdaptedCategory::new).collect(Collectors.toList()));
        date = source.getLocalDate().format(getDateFormatter());
    }

    /**
     * Converts this Jackson-friendly adapted account object into the model's {@code Transaction} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted account.
     */
    public Transaction toModelType() throws IllegalValueException {
        return new Transaction(getValidatedLocalDate(), getValidatedAmount(), getValidatedDirection(),
                getValidatedDescription(), getValidatedCategoryList());
    }

    /**
     * Checks that adapted direction can be converted into model's {@code Direction} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Direction getValidatedDirection() throws IllegalValueException {
        if (direction == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Direction.class.getSimpleName()));
        }
        if (!Direction.isValidDirection(direction)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return Direction.valueOf(direction);
    }

    /**
     * Checks that adapted amount can be converted into model's {@code Amount} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Amount getValidatedAmount() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(amount);
    }

    /**
     * Checks that adapted description can be converted into model's {@code Description} object.
     * @throws IllegalValueException If adapted object cannot be converted.
     */
    private Description getValidatedDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Checks that adapted categories can be converted into model's @code{@literal Set<Category>} object.
     * @throws IllegalValueException If any category within the categories are invalid.
     */
    private Set<Category> getValidatedCategoryList() throws IllegalValueException {
        if (categories == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "Categories"));
        }
        Set<Category> categoryList = new HashSet<>();
        for (JsonAdaptedCategory jsonAdaptedCategory : categories) {
            categoryList.add(jsonAdaptedCategory.toModelType());
        }
        return categoryList;
    }

    /**
     * Validates the adapted date as a {@code LocalDate} object.
     * @return The validated date.
     * @throws IllegalValueException If validation fails.
     */
    private LocalDate getValidatedLocalDate() throws IllegalValueException {
        try {
            return LocalDate.parse(date, getDateFormatter());
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Error reading stored date.");
        }
    }
}
