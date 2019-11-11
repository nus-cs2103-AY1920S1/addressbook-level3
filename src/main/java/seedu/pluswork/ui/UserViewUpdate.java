package seedu.pluswork.ui;

import javafx.scene.layout.Pane;
import seedu.pluswork.logic.commands.calendar.FindMeetingTimeCommand;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.commands.inventory.DeleteInventoryCommand;
import seedu.pluswork.logic.commands.inventory.EditInventoryCommand;
import seedu.pluswork.logic.commands.inventory.ListInventoryCommand;
import seedu.pluswork.logic.commands.member.AddMemberCommand;
import seedu.pluswork.logic.commands.member.AssignCommand;
import seedu.pluswork.logic.commands.member.DeleteMemberCommand;
import seedu.pluswork.logic.commands.member.EditMemberCommand;
import seedu.pluswork.logic.commands.member.FindMemberCommand;
import seedu.pluswork.logic.commands.member.FireCommand;
import seedu.pluswork.logic.commands.member.ListMemberCommand;
import seedu.pluswork.logic.commands.member.SetImageCommand;
import seedu.pluswork.logic.commands.multiline.AddDCommand;
import seedu.pluswork.logic.commands.multiline.AddICommand;
import seedu.pluswork.logic.commands.multiline.NoCommand;
import seedu.pluswork.logic.commands.multiline.YesCommand;
import seedu.pluswork.logic.commands.settings.ClockCommand;
import seedu.pluswork.logic.commands.settings.SettingsCommand;
import seedu.pluswork.logic.commands.settings.ThemeCommand;
import seedu.pluswork.logic.commands.statistics.GetStatisticsCommand;
import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.commands.task.DeleteTaskCommand;
import seedu.pluswork.logic.commands.task.DoingTaskCommand;
import seedu.pluswork.logic.commands.task.DoneTaskCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.logic.commands.task.FindTaskCommand;
import seedu.pluswork.logic.commands.task.ListTaskCommand;
import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.logic.commands.universal.HelpCommand;
import seedu.pluswork.logic.commands.universal.HomeCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * Communicates with {@code UserViewManager} to update the view based on user's command.
 * Updates each time a command is executed to refresh data.
 */
public class UserViewUpdate {

    private Pane paneToRender;
    private UserViewMain userViewMain;

    public UserViewUpdate(Pane paneToRender, UserViewMain userViewMain) {
        this.paneToRender = paneToRender;
        this.userViewMain = userViewMain;
        paneToRender.getChildren().add(userViewMain.loadDashboard());
    }

    /**
     * Retrieves user command's preamble and retrieves the respective pane.
     * Clears the current view first and switches to the desired view.
     * If a command that does not require a view to be switched is entered,
     * the view persists.
     *
     * @param commandText raw command of user
     */
    public void parseUserCommand(String commandText) throws ParseException {
        String trimmedCommand = commandText.trim();
        String[] split = trimmedCommand.split(" ");

        String preamble = split[0];
        assert !(preamble.isEmpty());

        paneToRender.getChildren().clear();

        switch (preamble) {
        case AddTaskCommand
                .COMMAND_WORD:

        case ListTaskCommand
                .COMMAND_WORD:

        case DeleteTaskCommand
                .COMMAND_WORD:

        case EditTaskCommand
                .COMMAND_WORD:

        case SetDeadlineCommand
                .COMMAND_WORD:

        case DoingTaskCommand
                .COMMAND_WORD:

        case DoneTaskCommand
                .COMMAND_WORD:

        case FindTaskCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadFoundTasks());
            break;

        case AddMemberCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadSpecificMember());
            break;

        case SetImageCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadSetImage());
            break;


        case AssignCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadAssign());
            break;

        case FireCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadTasks());
            break;

        case EditMemberCommand
                .COMMAND_WORD:

        case DeleteMemberCommand
                .COMMAND_WORD:

        case FindMemberCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadFoundMembers());
            break;

        case ListMemberCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadMembers());
            break;

        case DeleteInventoryCommand
                .COMMAND_WORD:

        case EditInventoryCommand
                .COMMAND_WORD:

        case AddInventoryCommand
                .COMMAND_WORD:

        case AddICommand
                .COMMAND_WORD:

        case ListInventoryCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadInventories());
            break;

        case GetStatisticsCommand
                .COMMAND_WORD_MEMBER:
            paneToRender.getChildren().add(userViewMain.loadMemberStats());
            break;

        case GetStatisticsCommand.COMMAND_WORD_TASK:
            paneToRender.getChildren().add(userViewMain.loadTaskStats());
            break;

        case FindMeetingTimeCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadMeetingTimes());
            break;

        case SettingsCommand
                .COMMAND_WORD:

        case ThemeCommand
                .COMMAND_WORD:

        case ClockCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadSettingsView());
            break;

        case ClearCommand
                .COMMAND_WORD:

        case HomeCommand
                .COMMAND_WORD:

        case AddDCommand
                .COMMAND_WORD:

        case NoCommand
                .COMMAND_WORD:

        case YesCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadDashboard());
            break;

        case HelpCommand
                .COMMAND_WORD:
            paneToRender.getChildren().add(userViewMain.loadHelpView());
            break;

        default:
            // show nothing (only exit has no case)
        }
    }
}
