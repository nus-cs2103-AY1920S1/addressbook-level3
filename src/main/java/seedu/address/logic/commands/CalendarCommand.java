package seedu.address.logic.commands;

import java.time.YearMonth;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.model.Model;
import seedu.address.ui.calendar.FullCalendarView;

//@@author dalsontws
/**
 * Format full help instructions for every command for display.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows full monthly schedule.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened calendar window.";

    //TODO: Replace with completed version of Calendar GUI
    @Override
    public CommandResult execute(Model model) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Calendar View");
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setScene(new Scene(new FullCalendarView(YearMonth.now(), model).getView()));
        primaryStage.show();
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false, false);
    }
}
