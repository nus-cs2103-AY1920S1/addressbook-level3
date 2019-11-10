package seedu.planner.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.NewCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.commands.deletecommand.DeleteDayCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand;
import seedu.planner.logic.commands.editcommand.EditActivityCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.listcommand.ListCommand;
import seedu.planner.logic.commands.listcommand.ListDayCommand;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.commands.viewcommand.ViewAccommodationCommand;
import seedu.planner.logic.commands.viewcommand.ViewActivityCommand;
import seedu.planner.logic.commands.viewcommand.ViewCommand;
import seedu.planner.logic.commands.viewcommand.ViewContactCommand;
import seedu.planner.ui.UiPart;
import seedu.planner.ui.cards.HelpCard;
import seedu.planner.ui.cards.UserGuideHelpCard;

/**
 * Panel containing all the help for commands.
 */
public class HelpListPanel extends UiPart<Region> {
    private static final String FXML = "HelpListPanel.fxml";

    @FXML
    private ListView<Node> helpListView;

    public HelpListPanel() {
        super(FXML);
    }

    /**
     * Generates a complete summary of all commands available in plan2travel.
     */
    public void generateCommandHelpSummary() {
        if (helpListView.getItems().size() == 0) {
            helpListView.getItems().addAll(
                    new UserGuideHelpCard().getRoot(),
                    new HelpCard(AddAccommodationCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(AddActivityCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(AddContactCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(AddDayCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(DeleteAccommodationCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(DeleteActivityCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(DeleteContactCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(DeleteDayCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(EditAccommodationCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(EditActivityCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(EditContactCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ViewAccommodationCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ViewActivityCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ViewContactCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ViewCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ListCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ListDayCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ScheduleCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(AutoScheduleCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(OptimiseCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(UnscheduleCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(NewCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(UndoCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(RedoCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ClearCommand.MESSAGE_USAGE).getRoot(),
                    new HelpCard(ExitCommand.MESSAGE_USAGE).getRoot()
            );
        }
    }
}
