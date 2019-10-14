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
    private ArrayList<Integer> participantIds;
    private ArrayList<Expense> expenses;

    public ActivityBuilder() {
        title = new Title(DEFAULT_TITLE);
        participantIds = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Activity activityToCopy) {
        title = activityToCopy.getTitle();
        participantIds = new ArrayList<>(activityToCopy.getParticipantIds());
        expenses = new ArrayList<>(activityToCopy.getExpenses());
    }

    /**
     * Sets the {@code Title} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Adds a {@code Person} into the {@code Activity} that we are building.
     */
    public ActivityBuilder addPerson(Person person) {
        participantIds.add(person.getPrimaryKey());
        return this;
    }

    // TODO: Add functionality to add expenses once it is working

    public Activity build() {
        return new Activity(title, participantIds.toArray(new Integer[participantIds.size()]));
    }

}
