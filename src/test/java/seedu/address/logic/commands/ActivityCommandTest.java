package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ActivityBook;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.stub.ModelStub;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TypicalPersons;

public class ActivityCommandTest {

    @Test
    public void constructor_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ActivityCommand(new Title("Title"), null));
        assertThrows(NullPointerException.class, () -> new ActivityCommand(null, new ArrayList<String>()));
        assertThrows(NullPointerException.class, () -> new ActivityCommand(null, null));
    }

    @Test
    public void execute_validActivityWithoutParticipants_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().build();

        CommandResult commandResult =
                new ActivityCommand(new Title(ActivityBuilder.DEFAULT_TITLE), new ArrayList<String>())
                .execute(modelStub);

        assertEquals(String.format(ActivityCommand.MESSAGE_SUCCESS, validActivity, "", ""),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validActivity), modelStub.activityList);
    }

    @Test
    public void execute_validActivityWithParticipant_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().addPerson(TypicalPersons.ALICE).build();
        ArrayList<String> searchTerms = new ArrayList<String>();
        searchTerms.add("Alice");

        CommandResult commandResult =
                new ActivityCommand(new Title(ActivityBuilder.DEFAULT_TITLE), searchTerms)
                .execute(modelStub);

        assertEquals(String.format(ActivityCommand.MESSAGE_SUCCESS, validActivity, "Alice Pauline", ""),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validActivity), modelStub.activityList);
    }

    @Test
    public void execute_validActivityWithInvalidParticipant_multipleMatches() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        modelStub.addPerson(TypicalPersons.ANDY);
        Activity emptyActivity = new ActivityBuilder().build();
        ArrayList<String> searchTerms = new ArrayList<String>();
        searchTerms.add("Pauline");

        CommandResult commandResult =
                new ActivityCommand(new Title(ActivityBuilder.DEFAULT_TITLE), searchTerms)
                .execute(modelStub);

        String warningMessage = String.format(ActivityCommand.WARNING_SEARCH_RESULTS, "Pauline", 2);

        assertEquals(String.format(ActivityCommand.MESSAGE_SUCCESS, emptyActivity, "", warningMessage),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(emptyActivity), modelStub.activityList);
    }

    @Test
    public void equals() {

        Title breakfastTitle = new Title("Breakfast");
        Title lunchTitle = new Title("LunchTitle");

        ArrayList<String> aliceBob = new ArrayList<String>(Arrays.asList("Alice", "Bob"));
        ArrayList<String> charlie = new ArrayList<String>(Arrays.asList("Charlie"));

        ActivityCommand validActivityNoParticipant = new ActivityCommand(breakfastTitle, new ArrayList<String>());
        ActivityCommand validActivityNoParticipantTwo = new ActivityCommand(lunchTitle, new ArrayList<String>());
        ActivityCommand validActivityOneParticipant = new ActivityCommand(breakfastTitle, charlie);
        ActivityCommand validActivityTwoParticipants = new ActivityCommand(breakfastTitle, aliceBob);

        // same object -> returns true
        assertTrue(validActivityNoParticipant.equals(validActivityNoParticipant));

        // same values (with participants) -> returns true
        ActivityCommand validActivityCopy = new ActivityCommand(breakfastTitle, aliceBob);
        assertTrue(validActivityTwoParticipants.equals(validActivityCopy));

        // different types -> returns false
        assertFalse(validActivityNoParticipant.equals(1));

        // null -> returns false
        assertFalse(validActivityNoParticipant.equals(null));

        // different title -> returns false
        assertFalse(validActivityNoParticipantTwo.equals(validActivityNoParticipant));

        // different participants -> returns false
        assertFalse(validActivityOneParticipant.equals(validActivityTwoParticipants));
    }

    /**
     * A Model stub that is initialized with a predefined list of Person, with functional search.
     */
    private class ModelStubAcceptingActivityAdded extends ModelStub {
        final ArrayList<Activity> activityList = new ArrayList<>();
        private final List<Person> personList = TypicalPersons.getTypicalPersons();

        @Override
        public void addPerson(Person person) {
            personList.add(person);
        }

        @Override
        public ArrayList<Person> findPerson(NameContainsKeywordsPredicate predicate) {
            requireNonNull(predicate);
            ArrayList<Person> matches = new ArrayList<Person>();
            for (Person person : personList) {
                if (predicate.test(person)) {
                    matches.add(person);
                }
            }
            return matches;
        }

        @Override
        public void addActivity(Activity activity) {
            requireNonNull(activity);
            activityList.add(activity);
        }

        @Override
        public ActivityBook getActivityBook() {
            ActivityBook activityBook = new ActivityBook();
            activityBook.setActivities(activityList);
            return activityBook;
        }
    }

}
