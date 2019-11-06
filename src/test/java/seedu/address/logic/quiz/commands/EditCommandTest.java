package seedu.address.logic.quiz.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.quiz.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_QUESTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_QUESTION;
import static seedu.address.testutil.TypicalQuestion.getTypicalAddressQuizBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.EditCommand.EditQuestionDescriptor;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.model.quiz.UserPrefs;
import seedu.address.model.quiz.person.Question;
import seedu.address.testutil.EditQuestionDescriptorBuilder;
import seedu.address.testutil.QuestionBuilder;



/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelQuizManager(new AddressQuizBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Question editedPerson = new QuestionBuilder().build();
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelQuizManager(new AddressQuizBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredQuestionList().size());
        Question lastPerson = model.getFilteredQuestionList().get(indexLastPerson.getZeroBased());

        QuestionBuilder personInList = new QuestionBuilder(lastPerson);
        Question editedPerson = personInList.withName(VALID_NAME_BOB).withAnswer(VALID_ANSWER_BOB)
                .withTags(VALID_TAG_LECTURE).build();

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAnswer(VALID_ANSWER_BOB).withTags(VALID_TAG_LECTURE).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelQuizManager(new AddressQuizBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION, new EditQuestionDescriptor());
        Question editedPerson = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelQuizManager(new AddressQuizBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        Question personInFilteredList = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        Question editedPerson = new QuestionBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedPerson);

        Model expectedModel = new ModelQuizManager(new AddressQuizBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Question firstPerson = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_QUESTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        // edit person in filtered list into a duplicate in address book
        Question personInList = model.getAddressBook().getQuestionList().get(INDEX_SECOND_QUESTION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);
        Index outOfBoundIndex = INDEX_SECOND_QUESTION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getQuestionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }


}
