package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.ModelStub;
import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;

class InputHistoryHandlerTest {

    public static final String LAST_INPUT = "Last input";
    public static final String SECOND_LAST_INPUT = "Second last input";

    private InputHistoryHandler inputHistoryHandler;
    private ModelStubInputHistoryHandler model;

    @BeforeEach
    void setup() {
        model = new ModelStubInputHistoryHandler();
        inputHistoryHandler = new InputHistoryHandler(model);
    }

    @Test
    void getPastCommand_getInputHistory_commandsRetrieved() {
        model.inputHistory.add(LAST_INPUT);
        model.inputHistory.add(SECOND_LAST_INPUT);
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(SECOND_LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(SECOND_LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("down"));
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("down"));
    }

    @Test
    void getPastCommand_getInputHistory_noCommandsInHistory() {
        assertEquals(null, inputHistoryHandler.getPastInput("up"));
    }

    //CHECKSTYLE:OFF: VisibilityModifier
    private static class ModelStubInputHistoryHandler extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();
        final ArrayList<Student> students = new ArrayList<>();

        List<String> inputHistory = new ArrayList<>();
        boolean isInputChanged = true;
        PartialInput suggestedCompletions;

        private List<Command> suggestedCommands = new ArrayList<>();
        private int inputHistoryIndex = 0;

        @Override
        public void addModule(Module module) {
            modules.add(module);
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            tutorials.add(tutorial);
        }

        @Override
        public void addStudent(Student student) {
            students.add(student);
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
        public ObservableList<Student> getFilteredStudentList() {
            ObservableList<Student> list = FXCollections.observableArrayList();
            list.addAll(students);
            return list;
        }

        @Override
        public void storeSuggestedCompletions(PartialInput suggestedCompletions) {
            this.suggestedCompletions = suggestedCompletions;
        }

        @Override
        public PartialInput getSuggestedCompletions() {
            return suggestedCompletions;
        }

        @Override
        public void deleteSuggestedCompletions() {
            suggestedCompletions = null;
        }

        @Override
        public boolean hasSuggestedCompletions() {
            return suggestedCompletions == null;
        }

        @Override
        public void setInputChangedToTrue() {
            isInputChanged = true;
        }

        @Override
        public void setInputChangedToFalse() {
            isInputChanged = false;
        }

        @Override
        public boolean hasInputChanged() {
            return isInputChanged;
        }

        @Override
        public List<String> getInputHistory() {
            return inputHistory;
        }

        @Override
        public int getInputHistoryIndex() {
            return inputHistoryIndex;
        }

        @Override
        public void incrementInputHistoryIndex() {
            inputHistoryIndex += 1;
        }

        @Override
        public void decrementInputHistoryIndex() {
            inputHistoryIndex -= 1;
        }

        @Override
        public void resetInputHistoryIndex() {
            inputHistoryIndex = 0;
        }

    }
}
