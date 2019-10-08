package seedu.address.inventory.commands;

import seedu.address.person.logic.commands.AddCommand;
import seedu.address.transaction.model.Model;

public class PersonCommand extends Command {
    public static final String COMMAND_WORD = "person";
    private AddCommand personAddCommand;

    public PersonCommand(AddCommand personAddCommand) {
        this.personAddCommand = personAddCommand;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        seedu.address.person.logic.commands.CommandResult personCommandResult = personAddCommand.execute(personModel);
        return new CommandResult(personCommandResult.getFeedbackToUser());
    }
}
