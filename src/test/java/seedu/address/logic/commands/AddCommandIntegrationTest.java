package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bio.UserList;
import seedu.address.model.record.UniqueRecordList;
import seedu.sgm.model.food.UniqueFoodList;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserList(), new UniqueFoodList(),
            new UniqueRecordList());
    }

    //    @Test
    //    public void execute_newPerson_success() {
    //        Person validPerson = new PersonBuilder().build();
    //
    //        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new FoodMap(),
    //        new RecordBook());
    //        expectedModel.addPerson(validPerson);
    //
    //        assertCommandSuccess(new AddCommand(validPerson), model,
    //                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    //    }

    //    @Test
    //    public void execute_duplicateRecord_throwsCommandException() {
    //        Person personInList = model.getAddressBook().getPersonList().get(0);
    //        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    //    }

}
