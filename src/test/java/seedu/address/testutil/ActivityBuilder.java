package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_TITLE = "Resort World Sentosa";

    private Title title;
    private ArrayList<Person> participants;
    private ArrayList<Expense> expenses;

    public ActivityBuilder() {
        title = new Title(DEFAULT_TITLE);
        participants = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Activity activityToCopy) {
        title = activityToCopy.getTitle();
        participants = new ArrayList<>(activityToCopy.getParticipants());
        expenses = new ArrayList<>(activityToCopy.getExpenses());
    }

    /**
     * Sets the {@code Title} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    public Activity build() {
        return new Activity(title);
    }

}
