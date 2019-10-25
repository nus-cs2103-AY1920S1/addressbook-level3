package seedu.ifridge.logic.commands.templatelist;

import seedu.ifridge.model.Model;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListTemplateListCommandTest {

    private Model model;
    private Model expectedModel;

    /**@BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTemplateList(), new UserPrefs());
        expectedModel = new ModelManager(model.getTemplate(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTemplateItemCommand(), model,
            ListTemplateItemCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTemplateListAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListTemplateItemCommand(), model,
            ListTemplateItemCommand.MESSAGE_SUCCESS, expectedModel);
    }*/
}
