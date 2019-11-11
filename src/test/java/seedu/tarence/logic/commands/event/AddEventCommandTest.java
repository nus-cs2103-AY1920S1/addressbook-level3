package seedu.tarence.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.logic.commands.event.AddEventCommand.MESSAGE_ADD_EVENT_SUCCESS;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.ModelStub;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class AddEventCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1602";
    public static final String OTHER_MOD_CODE = "GEQ1917";
    public static final String VALID_TUT_NAME = "T02";
    public static final String VALID_START_DATE = "01-01-2020 0000";
    public static final String VALID_END_DATE = "31-12-2020 2359";
    public static final String VALID_EVENT_NAME = "Consultation";

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Tutorial.DATE_FORMAT);

    @Test
    void execute_eventAcceptedByTutorial_addSuccessful() throws ParseException, CommandException {
        ModelStubEventCommand model = new ModelStubEventCommand();

        Tutorial validTutorial = new TutorialBuilder().build();
        model.addTutorial(validTutorial);

        Date startDate = dateFormatter.parse(VALID_START_DATE);
        Date endDate = dateFormatter.parse(VALID_END_DATE);

        AddEventCommand addEventCommand = new AddEventCommand(validTutorial.getModCode(), validTutorial.getTutName(),
                null, VALID_EVENT_NAME, startDate, endDate);

        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_ADD_EVENT_SUCCESS, VALID_EVENT_NAME));

        assertEquals(expectedCommandResult, addEventCommand.execute(model));
    }

    @Test
    void execute_invalidTutorial_throwsCommandException() throws ParseException {
        ModelStubEventCommand model = new ModelStubEventCommand();

        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE).build();
        model.addTutorial(validTutorial);

        Date startDate = dateFormatter.parse(VALID_START_DATE);
        Date endDate = dateFormatter.parse(VALID_END_DATE);

        AddEventCommand addEventCommand = new AddEventCommand(new ModCode(OTHER_MOD_CODE), validTutorial.getTutName(),
                null, VALID_EVENT_NAME, startDate, endDate);

        assertThrows(CommandException.class,
                MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> addEventCommand.execute(model));
    }

    @Test
    void execute_similarTutorial_suggestSimilarCommands() throws ParseException, CommandException {
        ModelStubEventCommand model = new ModelStubEventCommand();

        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE).build();
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addTutorial(validTutorial);
        model.addModule(validModule);
        validModule.addTutorial(validTutorial);

        Date startDate = dateFormatter.parse(VALID_START_DATE);
        Date endDate = dateFormatter.parse(VALID_END_DATE);

        AddEventCommand addEventCommand = new AddEventCommand(new ModCode(SIMILAR_MOD_CODE), validTutorial.getTutName(),
                null, VALID_EVENT_NAME, startDate, endDate);

        AddEventCommand suggestedCommand = new AddEventCommand(new ModCode(VALID_MOD_CODE), validTutorial.getTutName(),
                null, VALID_EVENT_NAME, startDate, endDate);
        String expectedMessage = String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                SIMILAR_MOD_CODE + " " + validTutorial.getTutName())
                + "1. " + VALID_MOD_CODE + ", " + validTutorial.getTutName() + "\n";

        assertEquals(new CommandResult(expectedMessage), addEventCommand.execute(model));
        assertEquals(List.of(suggestedCommand), model.getSuggestedCommands());
    }

    private static class ModelStubEventCommand extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();
        private List<Command> suggestedCommands = new ArrayList<>();

        @Override
        public boolean hasTutorial(Tutorial tutorial) {
            return false;
        }

        @Override
        public void addTutorialToModule(Tutorial tutorial) {}

        @Override
        public void storePendingCommand(Command command) {}

        @Override
        public void addModule(Module module) {
            modules.add(module);
        }

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
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            requireAllNonNull(modCode, tutName);
            Optional<Module> module = Optional.empty();
            for (Module currMod : modules) {
                if (currMod.getModCode().equals(modCode)) {
                    module = Optional.of(currMod);
                    break;
                }
            }
            if (module.isEmpty()) {
                return false;
            }
            boolean hasTut = false;
            for (Tutorial tutorial : module.get().getTutorials()) {
                if (tutorial.getTutName().equals(tutName)) {
                    hasTut = true;
                    break;
                }
            }
            return hasTut;
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            ObservableList<Module> list = FXCollections.observableArrayList();
            list.addAll(modules);
            return list;
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            ObservableList<Tutorial> list = FXCollections.observableArrayList();
            list.addAll(tutorials);
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
