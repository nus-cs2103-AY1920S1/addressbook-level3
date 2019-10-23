package seedu.address.logic.commands.ui;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.panel.PanelName;

/**
 * Changes the currently viewed panel in the MainWindow's PanelView.
 */
public class ViewPanelCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Navigates to the specified GUI panel. \n"
            + "PARAMETERS: <Panel Name> \n"
            + "EXAMPLE: view Budget List \n";
    public static final String MESSAGE_SUCCESS = "Viewing: %s";
    public static final String SHOW_AVAILABLE_PANELS = "Here are the panels that you may view:\n%s";

    private PanelName panelName;


    public ViewPanelCommand(PanelName panelName) {
        this.panelName = panelName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewPanelCommand)) {
            return false;
        }
        ViewPanelCommand otherViewPanelCommand = (ViewPanelCommand) obj;
        return otherViewPanelCommand.panelName.equals(panelName);
    }

    @Override
    protected void validate(Model model) throws CommandException {
        // No need to validate as model is not affected.
    }

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Now Showing: " + panelName.toString(), false, false, false, true, panelName);
    }

}
