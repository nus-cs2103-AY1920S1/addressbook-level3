package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.logic.commands.exceptions.CommandException;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.ReadOnlyArchiveWrapper;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.ReadOnlyUserPrefs;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.model.tag.TagCountManager;
import seedu.billboard.model.tag.UniqueTagList;
import seedu.billboard.testutil.ExpenseBuilder;

import javafx.collections.transformation.FilteredList;

public class AddCommandTest {

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, null, null,
                null, null));
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Expense validExpense = new ExpenseBuilder().build();

        CommandResult commandResult = new AddCommand(validExpense.getName(), validExpense.getDescription(),
                validExpense.getAmount(), validExpense.getCreated(), getTagNames(validExpense)).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validExpense), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validExpense), modelStub.expensesAdded);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense validExpense = new ExpenseBuilder().build();
        AddCommand addCommand = new AddCommand(validExpense.getName(), validExpense.getDescription(),
                validExpense.getAmount(), validExpense.getCreated(), getTagNames(validExpense));
        ModelStub modelStub = new ModelStubWithExpense(validExpense);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_EXPENSE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Expense alice = new ExpenseBuilder().withName("Alice").build();
        Expense bob = new ExpenseBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice.getName(), alice.getDescription(),
                alice.getAmount(), alice.getCreated(), getTagNames(alice));
        AddCommand addBobCommand = new AddCommand(bob.getName(), bob.getDescription(),
                bob.getAmount(), bob.getCreated(), getTagNames(bob));

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice.getName(), alice.getDescription(),
                alice.getAmount(), alice.getCreated(), getTagNames(alice));
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotEquals(null, addAliceCommand);

        // different expense -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    private List<String> getTagNames(Expense expense) {
        return expense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBillboardFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBillboardFilePath(Path billboardFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBillboard(ReadOnlyBillboard newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBillboard getBillboard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Tag> retrieveTags(List<String> tagNames) {
            throw new AssertionError("This method should not be called. ");
        }

        @Override
        public void incrementCount(Set<Tag> toIncrement) {
            throw new AssertionError("This method should not be called. ");
        }

        @Override
        public void decreaseCount(Set<Tag> toDecrease) {
            throw new AssertionError("This method should not be called. ");
        }

        @Override
        public List<String> getTagNames() {
            throw new AssertionError("This method should not be called. ");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenses() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenses(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableData<StatisticsFormat> getStatisticsFormat() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticsFormat(StatisticsFormat type) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableData<StatisticsFormatOptions> getStatisticsFormatOptions() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticsFormatOptions(StatisticsFormatOptions options) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Billboard getCombinedBillboard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<String> getArchiveNames() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setArchives(ReadOnlyArchiveWrapper archives) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyArchiveWrapper getArchives() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasArchiveExpense(String archiveName, Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasArchive(String archive) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteArchive(String archiveName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteArchiveExpense(String archiveName, Expense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addArchiveExpense(String archiveName, Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addArchive(Archive archive) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredArchiveExpenses(String archiveName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredArchiveExpenses(String archiveName, Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Model getClone() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModel(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HashMap<String, FilteredList<Expense>> getFilteredArchives() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single expense.
     */
    private class ModelStubWithExpense extends ModelStub {
        private final Expense expense;
        private final UniqueTagList tags = new UniqueTagList();
        private final TagCountManager count = new TagCountManager();

        ModelStubWithExpense(Expense expense) {
            requireNonNull(expense);
            this.expense = expense;
            List<String> tagNames = expense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());
            tags.addNewTags(tagNames);
            count.incrementAllCount(expense.getTags());
        }

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return this.expense.equals(expense);
        }

        @Override
        public Set<Tag> retrieveTags(List<String> tagNames) {
            return tags.retrieveTags(tagNames);
        }

        @Override
        public void incrementCount(Set<Tag> toIncrement) {
            count.incrementAllCount(toIncrement);
        }
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> expensesAdded = new ArrayList<>();
        private final TagCountManager count = new TagCountManager();

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return expensesAdded.stream().anyMatch(expense::equals);
        }

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            expensesAdded.add(expense);
        }

        @Override
        public Set<Tag> retrieveTags(List<String> tagNames) {
            return tagNames.stream().map(Tag::new).collect(Collectors.toSet());
        }

        @Override
        public void incrementCount(Set<Tag> toIncrement) {
            count.incrementAllCount(toIncrement);
        }

        @Override
        public ReadOnlyBillboard getBillboard() {
            return new Billboard();
        }
    }

}
