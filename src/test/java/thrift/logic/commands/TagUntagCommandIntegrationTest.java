package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.tag.Tag;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TagSetBuilder;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.TypicalTransactions;

class TagUntagCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
    }

    @Test
    void execute_tagThenUntag_success() throws CommandException {
        Expense originalExpense = new ExpenseBuilder(model.getFilteredTransactionList().get(0))
                .build();

        Set<Tag> tagSet = new TagSetBuilder("Food", "Shopping").build();
        TagCommand tagCommand = new TagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet);
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet);


        assertEquals(originalExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //Before
        tagCommand.execute(model, null);
        untagCommand.execute(model, null);
        assertEquals(originalExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //After
    }

    @Test
    void execute_tagTwoThenUntagOne_success() throws CommandException {
        Expense originalExpense = new ExpenseBuilder(model.getFilteredTransactionList().get(0))
                .build();

        Set<Tag> tagSet = new TagSetBuilder("Food", "Shopping").build();
        TagCommand tagCommand = new TagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet);

        Set<Tag> tagSet2 = new TagSetBuilder("Shopping").build();
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet2);

        Set<Tag> expectedTag = new TagSetBuilder(originalExpense.getTags())
                .addTags("Food")
                .build();
        Expense updatedExpense = new Expense(
                originalExpense.getDescription(),
                originalExpense.getValue(),
                originalExpense.getRemark(),
                originalExpense.getDate(),
                expectedTag);


        assertEquals(originalExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //Before
        tagCommand.execute(model, null);
        untagCommand.execute(model, null);
        assertEquals(updatedExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //After
    }

    @Test
    void execute_tagOneThenUntagTwo_success() throws CommandException {
        Expense originalExpense = new ExpenseBuilder(model.getFilteredTransactionList().get(0))
                .build();

        Set<Tag> tagSet = new TagSetBuilder("Food").build();
        TagCommand tagCommand = new TagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet);

        Set<Tag> tagSet2 = new TagSetBuilder("Food", "Shopping").build();
        UntagCommand untagCommand = new UntagCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, tagSet2);


        assertEquals(originalExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //Before
        tagCommand.execute(model, null);
        untagCommand.execute(model, null);
        assertEquals(originalExpense.getTags(), model.getFilteredTransactionList().get(0).getTags()); //After
    }

}
