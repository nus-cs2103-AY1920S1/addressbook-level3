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
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedIncome {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";
    private final String uniqueId;
    private final String category;
    private final String desc;
    private final String time;
    private final String amt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedIncome(@JsonProperty("uniqueId") String uniqueId, @JsonProperty("category") String category,
                             @JsonProperty("desc") String desc, @JsonProperty("amt") String amt,
                             @JsonProperty("time") String time, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.uniqueId = uniqueId;
        this.category = category;
        this.desc = desc;
        this.amt = amt;
        this.time = time;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedIncome(Income source) {
        uniqueId = source.getUniqueId();
        category = source.getCategory().categoryName;
        desc = source.getDesc().fullDesc;
        amt = source.getAmount().toString();
        time = source.getDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public Income toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category, "Income");
        final Description modelDesc = new Description(desc);

        final Date modelTime = new Date(time);

        final Amount modelAmt = new Amount(amt);

        final Set<Tag> modelTags = new HashSet<>(entryTags);
        Income modelIncome = new Income(modelCategory, modelDesc, modelTime, modelAmt, modelTags);
        if (uniqueId != null) {
            modelIncome.setHasReminder(true);
            modelIncome.setUniqueId(uniqueId);
        }
        return modelIncome;
    }

}
