package seedu.billboard.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Expense}.
 */
class JsonAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";
    private static final DateTimeFormatter dateStoragePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    private final String name;
    private final String description;
    private final String amount;
    private final String created;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String archiveName;

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("amount") String amount,
                              @JsonProperty("created") String created,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                              @JsonProperty("archive") String archiveName) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.created = created;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.archiveName = archiveName;
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        name = source.getName().name;
        description = source.getDescription().description;
        amount = source.getAmount().toString();
        created = source.getCreated().dateTime.format(dateStoragePattern);
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        archiveName = source.getArchiveName();
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (created == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CreatedDateTime.class.getSimpleName()));
        }
        LocalDateTime dateTime;
        try {
            dateTime = dateStoragePattern.parse(created, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(CreatedDateTime.MESSAGE_CONSTRAINTS);
        }
        final CreatedDateTime modelCreated = new CreatedDateTime(dateTime);

        if (archiveName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Archive name"));
        }
        final String modelArchiveName = archiveName;

        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Expense(modelName, modelDescription, modelAmount, modelCreated, modelTags, modelArchiveName);
    }

}
