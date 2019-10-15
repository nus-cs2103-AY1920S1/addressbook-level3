package seedu.address.storage;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.Title;

/**
 * Jackson-friendly version of {@link Activity}.
 */
public class JsonAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String title;
    private final ArrayList<Integer> participants = new ArrayList<Integer>();
    private final ArrayList<JsonAdaptedExpense> expenses = new ArrayList<JsonAdaptedExpense>();

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("title") String title,
                               @JsonProperty("participants") ArrayList<Integer> participants) {
        this.title = title;
        if (participants != null) {
            this.participants.addAll(participants);
        }
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        title = source.getTitle().title;
        participants.addAll(source.getParticipantIds());
        expenses.addAll(source.getExpenses().stream()
                .map(JsonAdaptedExpense::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity.
     */
    public Activity toModelType() throws IllegalValueException {

        final ArrayList<Expense> activityExpenses = new ArrayList<>();
        for (JsonAdaptedExpense exp : expenses) {
            activityExpenses.add(exp.toModelType());
        }
        // converting arraylist to array for vararg
        Expense[] expenditures = activityExpenses.toArray(new Expense[0]);

        // converting arraylist to array for vararg
        Integer[] participantIds = participants.toArray(new Integer[0]);

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title activityTitle = new Title(title);
        Activity activity = new Activity(activityTitle, participantIds);
        activity.addExpense(expenditures);

        return activity;
    }
}
