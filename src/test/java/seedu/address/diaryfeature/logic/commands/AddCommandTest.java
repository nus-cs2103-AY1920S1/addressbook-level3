package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DateParseException;
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddCommandTest {
    private final String TITLE_SAMPLE = "Title";
    private final String DATE_SAMPLE_AS_STRING = "12/12/1212 2000";
    private final String PLACE_SAMPLE = "Place";
    private final String MEMORY_SAMPLE = "Memory";


    private DiaryEntry getSampleDiaryEntry() {
        try {
            return new DiaryEntry(new Title(TITLE_SAMPLE), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING), new Place(PLACE_SAMPLE), new Memory(MEMORY_SAMPLE));
        } catch (DateParseException error) {
            assert DATE_SAMPLE_AS_STRING != null;
            System.out.println("Error with sample data");
            return null;
        }
    }


    @Test
    public void constructor_nullDiaryEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEntriesAdded modelStub = new ModelStubAcceptingEntriesAdded();
        DiaryEntry valid = getSampleDiaryEntry();

        CommandResult commandResult = new AddCommand(valid).execute(modelStub);

        assertEquals(AddCommand.MESSAGE_SUCCESS + "\n" + valid, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(valid), modelStub.entriesAdded);
    }


    @Test
    public void execute_duplicateEntry_throwsCommandException()  {
        DiaryEntry valid = getSampleDiaryEntry();
        AddCommand addCommand = new AddCommand(valid);
        ModelStub modelStub = new ModelStubWithDiaryEntry(valid);

        assertThrows(CommandException.class, () -> addCommand.execute(modelStub));
    }



    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub extends DiaryModel {
        @Override
        public DiaryEntry deleteDiaryEntry(DiaryEntry input) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDiaryEntryPrivate(DiaryEntry input) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDiaryEntryUnPrivate(DiaryEntry input) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDetails(Details attempt) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * @param diaryEntry
         * @return
         */
        @Override
        public DiaryEntry addDiaryEntry(DiaryEntry diaryEntry) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean contains(DiaryEntry otherEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Details> getDetails() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkDetails(Details input) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDetails() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
         * {@code versionedAddressBook}
         */
        @Override
        public ObservableList<DiaryEntry> getFilteredDiaryEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDiaryList(Predicate<DiaryEntry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public void setinnerDetails(Optional<Details> input) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DiaryBook getDiaryBook() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Statistics =================================================================================
        @Override
        public int getTotalDiaryEntries() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean equals(Object obj) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithDiaryEntry extends ModelStub {
        private final DiaryEntry entry;

        ModelStubWithDiaryEntry(DiaryEntry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean contains(DiaryEntry person) {
            requireNonNull(person);
            return this.entry.equals(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingEntriesAdded extends ModelStub {
        final ArrayList<DiaryEntry> entriesAdded = new ArrayList<>();

        @Override
        public boolean contains(DiaryEntry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::equals);
        }

        @Override
        public DiaryEntry addDiaryEntry(DiaryEntry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
            return entry;

        }

    }

}
