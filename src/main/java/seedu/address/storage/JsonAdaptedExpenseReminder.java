package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.person.ExpenseContainsTagPredicate;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.ExpenseTracker;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedExpenseReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    private String message;
    private long quota;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedExpenseReminder(@JsonProperty("desc") String desc,
                                      @JsonProperty("quota") long quota,
                                      @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.message = desc;
        this.quota = quota;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedExpenseReminder(ExpenseReminder source) {
        message = source.getMessage();
        quota = source.getQuota();
        tagged.addAll(source.getTracker().getPredicate().getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public ExpenseReminder toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }
        if (message == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final String modelMessage = message;
        final long modelQuota = quota;
        final Set<Tag> modelTags = new HashSet<>(entryTags);
        final ExpenseContainsTagPredicate predicate = new ExpenseContainsTagPredicate(modelTags);
        final ExpenseTracker tracker = new ExpenseTracker(predicate);
        return new ExpenseReminder(modelMessage, modelQuota, tracker);
    }

}
