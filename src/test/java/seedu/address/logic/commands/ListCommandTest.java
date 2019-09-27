package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.ModelManager;
import seedu.address.testutil.modelutil.TypicalModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void execute(){
        assertNotNull(new ListCommand().execute(model));
    }

}
