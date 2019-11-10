package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";
    private final String uniqueId;
    private final String category;
    private final String desc;
    private final String date;
    private final String amt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("uniqueId") String uniqueId, @JsonProperty("category") String category,
                              @JsonProperty("desc") String desc, @JsonProperty("amt") String amt,
                              @JsonProperty("time") String time, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.uniqueId = uniqueId;
        this.category = category;
        this.desc = desc;
        this.amt = amt;
        this.date = time;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        uniqueId = source.getUniqueId();
        category = source.getCategory().categoryName;
        desc = source.getDesc().fullDesc;
        amt = source.getAmount().toString();
        date = source.getDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Expense toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }

        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        if (!Amount.isValidAmount(amt)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }

        if (!Date.parseIfValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS_FOR_ENTRIES);
        }

        final Category modelCategory = new Category(category, "Expense");

        final Description modelDesc = new Description(desc);

        final Date modelTime = new Date(date);

        final Amount modelAmt = new Amount(amt);

        final Set<Tag> modelTags = new HashSet<>(entryTags);
        Expense modelExpense = new Expense(modelCategory, modelDesc, modelTime, modelAmt, modelTags);
        if (uniqueId != null) {
            modelExpense.setUniqueId(uniqueId);
            modelExpense.setHasReminder(true);
        }
        return modelExpense;
    }

}
