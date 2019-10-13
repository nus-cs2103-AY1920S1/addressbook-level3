package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SelectSuggestionCommand}.
 */
public class SelectSuggestionCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1061";
    public static final String VALID_TUT_NAME = "T02";

    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_validIndexInput_success() throws CommandException {
        SelectSuggestionCommandTest.ModelStubTutorialCommand modelStub = new SelectSuggestionCommandTest
                .ModelStubTutorialCommand();

        Module module = new ModuleBuilder().withModCode(SIMILAR_MOD_CODE).build();
        modelStub.modules.add(module);

        Tutorial similarTutorial = new TutorialBuilder().withModCode(SIMILAR_MOD_CODE).build();

        List<Command> suggestedCommands = new ArrayList<>();
        suggestedCommands.add(new AddTutorialCommand(similarTutorial));
        modelStub.storeSuggestedCommands(suggestedCommands, "");

        CommandResult commandResult = new SelectSuggestionCommand(Index.fromOneBased(1)).execute(modelStub);
        String expectedMessage = String.format(AddTutorialCommand.MESSAGE_SUCCESS, similarTutorial,
                similarTutorial.getTimeTable().getDay(), similarTutorial.getTimeTable().getWeeks(),
                similarTutorial.getTimeTable().getStartTime(),
                similarTutorial.getTimeTable().getDuration().toMinutes());
        List<Tutorial> expectedTutorialList = new ArrayList<>();
        expectedTutorialList.add(similarTutorial);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedTutorialList, modelStub.tutorials);
    }

    private class ModelStubTutorialCommand extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();
        private List<Command> suggestedCommands = new ArrayList<>();

        @Override
        public void addTutorial(Tutorial tutorial) {
            tutorials.add(tutorial);
        }

        @Override
        public boolean hasModuleOfCode(ModCode modCode) {
            for (Module module : modules) {
                if (module.getModCode().equals(modCode)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            ObservableList<Module> list = FXCollections.observableArrayList();
            list.addAll(modules);
            return list;
        }

        @Override
        public void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {
            this.suggestedCommands = suggestedCommands;
        }
        @Override
        public List<Command> getSuggestedCommands() {
            return suggestedCommands;
        }
    }

}
