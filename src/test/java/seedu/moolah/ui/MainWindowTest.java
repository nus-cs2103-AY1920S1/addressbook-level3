package seedu.moolah.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;
import java.nio.file.Path;
import java.util.List;
import java.util.Timer;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.logic.Logic;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.Timekeeper;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.ui.panel.PanelName;

// work in progress test
class MainWindowTest extends ApplicationTest {

    @Test
    void show() {
        Platform.runLater(() -> {
            Logic logic = new LogicStub();
            Timekeeper timekeeper = new Timekeeper(logic);
            Timer timer = new Timer();
            Stage stage = new Stage();
            MainWindow mainWindow = new MainWindow(stage, logic, timekeeper, timer);
            mainWindow.fillInnerParts();
            mainWindow.show();
            assertTrue(stage.isShowing());
        });
    }

    @Test
    void getPrimaryStage() {
        Platform.runLater(() -> {
            Logic logic = new LogicStub();
            Timekeeper timekeeper = new Timekeeper(logic);
            Timer timer = new Timer();
            Stage stage = new Stage();
            MainWindow mainWindow = new MainWindow(stage, logic, timekeeper, timer);
            assertEquals(stage, mainWindow.getPrimaryStage());
        });
    }

    @Test
    void fillInnerParts() {
        Platform.runLater(() -> {
            try {
                Logic logic = new LogicStub();
                Timekeeper timekeeper = new Timekeeper(logic);
                Timer timer = new Timer();
                Stage stage = new Stage();
                MainWindow mainWindow = new MainWindow(stage, logic, timekeeper, timer);
                mainWindow.fillInnerParts();
            } catch (Exception e) {
                fail();
            }
        });
    }
    static class LogicStub implements Logic {

        @Override
        public CommandResult execute(String commandText, String commandGroup) throws CommandException, ParseException {
            return new CommandResult("", false, false, new PanelName("ignored"));
        }

        @Override
        public boolean hasBudgetWithName(Description targetDescription) {
            return false;
        }

        @Override
        public Statistics getStatistics() {
            return null;
        }

        @Override
        public ReadOnlyMooLah getMooLah() {
            return new MooLah() {
                @Override
                public ObservableList<Budget> getBudgetList() {
                    return FXCollections.observableArrayList();
                }
            };
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Budget getPrimaryBudget() {
            return Budget.DEFAULT_BUDGET;
        }

        @Override
        public Path getMooLahFilePath() {
            return Path.of("a");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings() {
                @Override
                public double getWindowHeight() {
                    return 10;
                }

                @Override
                public double getWindowWidth() {
                    return 10;
                }

                @Override
                public Point getWindowCoordinates() {
                    return new Point(0, 0);
                }
            };
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

        }

        @Override
        public AliasMappings getAliasMappings() {
            return new AliasMappings();
        }

        @Override
        public boolean deleteAliasWithName(String aliasName) {
            return false;
        }

        @Override
        public void deleteTranspiredEvents(List<Event> eventsToBeRemoved) {

        }

        @Override
        public CommandResult addExpenseFromEvent(Event currentEvent) throws CommandException, ParseException {
            return null;
        }

        @Override
        public boolean[] recordInitialPrimaryBudgetStatus() {
            return new boolean[0];
        }

        @Override
        public boolean[] recordFinalPrimaryBudgetStatus() {
            return new boolean[0];
        }
    }

}
