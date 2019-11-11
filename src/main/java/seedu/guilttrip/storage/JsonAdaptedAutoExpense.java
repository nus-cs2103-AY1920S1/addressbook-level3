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
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.model.util.Frequency;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedAutoExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AutoExpense's %s field is missing!";
    private final String category;
    private final String desc;
    private final String date;
    private final String amt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String frequency;
    private final String lastTime;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedAutoExpense(@JsonProperty("category") String category, @JsonProperty("desc") String desc,
            @JsonProperty("amt") String amt, @JsonProperty("time") String time,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("frequency") String frequency,
            @JsonProperty("lastTime") String lastTime) {
        this.category = category;
        this.desc = desc;
        this.amt = amt;
        this.date = time;
        this.frequency = frequency;
        this.lastTime = lastTime;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedAutoExpense(AutoExpense source) {
        category = source.getCategory().getCategoryName();
        desc = source.getDesc().fullDesc;
        amt = source.getAmount().toString();
        date = source.getDate().toString();
        tagged.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        frequency = source.getFrequency().toString();
        lastTime = source.getLastTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted entry object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted entry.
     */
    public AutoExpense toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (desc == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }

        if (category == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }

        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category, CategoryType.EXPENSE);

        final Description modelDesc = new Description(desc);

        final Date modelTime = new Date(date);

        final Amount modelAmt = new Amount(amt);

        final Set<Tag> modelTags = new HashSet<>(entryTags);

        final Frequency modelFrequency = Frequency.parse(frequency);

        final Date modelLastTime = new Date(lastTime);

        return new AutoExpense(modelCategory, modelDesc, modelAmt, modelTags, modelFrequency, modelTime, modelLastTime);
    }

}
