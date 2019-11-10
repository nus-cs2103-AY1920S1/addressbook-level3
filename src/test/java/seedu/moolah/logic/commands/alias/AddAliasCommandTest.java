package seedu.moolah.logic.commands.alias;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_A_TO_B;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_B_TO_C;
import static seedu.moolah.testutil.AliasTestUtil.ALIAS_C_TO_A;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.ui.alias.AliasListPanel;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class AddAliasCommandTest {

    private Model model;

    @BeforeEach
    public void freshModel() {
        model = new ModelSupportingAliasStub();
    }

    @Test
    public void constructor_nullAlias_throwsException() {
        assertThrows(NullPointerException.class, () -> new AddAliasCommand(null));
        assertDoesNotThrow((() -> new AddAliasCommand(ALIAS_A_TO_B)));
    }

    @Test
    public void validate_nullModel_throwsException() {
        assertDoesNotThrow(() -> new AddAliasCommand(ALIAS_A_TO_B).validate(model));
        assertThrows(NullPointerException.class, () -> new AddAliasCommand(ALIAS_C_TO_A).validate(null));
    }

    @Test
    public void execute_nullModel_throwsException() {
        assertDoesNotThrow(() -> new AddAliasCommand(ALIAS_A_TO_B).execute(model));
        assertThrows(NullPointerException.class, () -> new AddAliasCommand(ALIAS_C_TO_A).execute(null));
    }

    @Test
    public void equals() {

        AddAliasCommand validAliasCommandOne = new AddAliasCommand(ALIAS_A_TO_B);
        AddAliasCommand validAddAliasCommandTwo = new AddAliasCommand(ALIAS_B_TO_C);

        // same object -> returns true
        assertTrue(validAliasCommandOne.equals(validAliasCommandOne));

        // same alias -> returns true
        AddAliasCommand validAliasCommandOneCopy = new AddAliasCommand(ALIAS_A_TO_B);
        assertTrue(validAliasCommandOne.equals(validAliasCommandOneCopy));

        // different types -> returns false
        assertFalse(validAliasCommandOne.equals(1));

        // null -> returns false
        assertFalse(validAliasCommandOne.equals(null));

        // different alias -> returns false
        assertFalse(validAliasCommandOne.equals(validAddAliasCommandTwo));
    }

    @Test
    public void execute_addAlias_modelAliasWithNameExistsBeforeFalseAfterTrue() {
        Alias toAdd = ALIAS_A_TO_B;
        assertFalse(model.aliasWithNameExists(toAdd.getAliasName()));
        AddAliasCommand command = new AddAliasCommand(toAdd);
        command.execute(model);
        assertTrue(model.aliasWithNameExists(toAdd.getAliasName()));
    }

    @Test
    void getDescription_aliasNameMatchesAliasToAdd_descriptionMatches() {
        Alias toAdd0 = ALIAS_A_TO_B;
        String expected0 = String.format(AddAliasCommand.COMMAND_DESCRIPTION, toAdd0.getAliasName());
        assertEquals(new AddAliasCommand(toAdd0).getDescription(), expected0);

        Alias toAdd1 = ALIAS_B_TO_C;
        String expected1 = String.format(AddAliasCommand.COMMAND_DESCRIPTION, toAdd1.getAliasName());
        assertEquals(new AddAliasCommand(toAdd1).getDescription(), expected1);
    }

    @Test
    void getDescription_aliasNameDoesNotMatchesAliasToAdd_descriptionDoesNotMatch() {
        Alias toAdd0 = ALIAS_A_TO_B;
        String expected0 = String.format(AddAliasCommand.COMMAND_DESCRIPTION, toAdd0.getAliasName());
        Alias toAdd1 = ALIAS_B_TO_C;
        String expected1 = String.format(AddAliasCommand.COMMAND_DESCRIPTION, toAdd1.getAliasName());

        assertNotEquals(new AddAliasCommand(toAdd0).getDescription(), expected1);
        assertNotEquals(new AddAliasCommand(toAdd1).getDescription(), expected0);
    }

    @Test
    void execute_executed_commandResultPanelIsAliasPanel() {
        assertEquals(
                new AddAliasCommand(ALIAS_A_TO_B).execute(new ModelSupportingAliasStub()).viewRequest(),
                AliasListPanel.PANEL_NAME);
    }
}
