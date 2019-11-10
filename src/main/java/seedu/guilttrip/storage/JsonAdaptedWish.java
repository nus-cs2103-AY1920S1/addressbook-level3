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
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Wish}.
 */
class JsonAdaptedWish {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wish's %s field is missing!";

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
    public JsonAdaptedWish(@JsonProperty("uniqueId") String uniqueId, @JsonProperty("category") String category,
                           @JsonProperty("desc") String desc,
                           @JsonProperty("amt") String amt, @JsonProperty("date") String date,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.uniqueId = uniqueId;
        this.category = category;
        this.desc = desc;
        this.amt = amt;
        this.date = date;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedWish(Wish source) {
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
    public Wish toModelType() throws IllegalValueException {
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
        final Description modelDesc = new Description(desc);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        /*if (!Date.isValidDate(date)) {
            throw new IllegalValueException((Date.MESSAGE_CONSTRAINTS));
        }*/
        final Date modelDate = new Date(date);

        final Category modelCategory = new Category(category, "Expense");
        final Amount modelAmt = new Amount(amt);
        final Set<Tag> modelTags = new HashSet<>(entryTags);
        Wish modelWish = new Wish(modelCategory, modelDesc, modelDate, modelAmt, modelTags);
        if (uniqueId != null) {
            modelWish.setUniqueId(uniqueId);
            modelWish.setHasReminder(true);
        }
        return modelWish;
    }
}
