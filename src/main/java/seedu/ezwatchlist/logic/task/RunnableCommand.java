package seedu.ezwatchlist.logic.task;

import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;

public class RunnableCommand implements Runnable {

    private SearchCommand command;
    private Model model;
    private CommandResult commandResult = null;
    private CommandException commandException = null;

    public RunnableCommand(SearchCommand command, Model model) {
        this.command = command;
        this.model = model;
    }

    public CommandException getCommandException() {
        return this.commandException;
    }

    public CommandResult getCommandResult() {
        return this.commandResult;
    }

    public SearchCommand getCommand() {
        return command;
    }

    public void setCommand(SearchCommand command) {
        this.command = command;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void run() {
        try {
            commandResult = command.execute(model);
        } catch (CommandException e) {
            this.commandException = e;
        }

    }
}
