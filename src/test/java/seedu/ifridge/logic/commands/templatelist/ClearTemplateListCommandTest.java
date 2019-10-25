package seedu.ifridge.logic.commands.templatelist;

import org.junit.jupiter.api.Test;

public class ClearTemplateListCommandTest {

    @Test
    public void execute_emptyTemplateList_success() {
        /**Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearTemplateItemCommand(), model, ClearTemplateItemCommand.MESSAGE_SUCCESS,
         expectedModel);**/
    }

    @Test
    public void execute_nonEmptyTemplateList_success() {

        // Unable to implement this check yet as ModelManager does not contain TemplateList methods
        /**Model model = new ModelManager(getTypicalTemplateList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTemplateList()), new UserPrefs());
        expectedModel.setTemplateList(new TemplateList());

        assertCommandSuccess(new ClearTemplateItemCommand(), model, ClearTemplateItemCommand.MESSAGE_SUCCESS,
         expectedModel);**/
    }

}
