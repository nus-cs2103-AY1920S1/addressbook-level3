package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.Amount;
import seedu.address.model.entry.Category;
import seedu.address.model.entry.Date;
import seedu.address.model.entry.Description;
import seedu.address.model.entry.Expense;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";
    private final String category;
    private final String desc;
    private final String date;
    private final String amt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("category") String category, @JsonProperty("desc") String desc,
                              @JsonProperty("amt") String amt, @JsonProperty("time") String time,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
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

        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category, "Expense");

        final Description modelDesc = new Description(desc);

        final Date modelTime = new Date(date);

        final Amount modelAmt = new Amount(amt);

        final Set<Tag> modelTags = new HashSet<>(entryTags);
        return new Expense(modelCategory, modelDesc, modelTime, modelAmt, modelTags);
    }

}
