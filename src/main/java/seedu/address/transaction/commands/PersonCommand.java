/*, seedu.address.transaction.logic.Logic transactionLogicpackage seedu.address.transaction.commands;

import seedu.address.person.logic.commands.AddCommand;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.Model;

public class PersonCommand extends Command {
    public static final String COMMAND_WORD = "person";
    private AddCommand personAddCommand;

    public PersonCommand(AddCommand personAddCommand) {
        this.personAddCommand = personAddCommand;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel, Logic transactionLogic) throws Exception {
        seedu.address.person.logic.commands.CommandResult personCommandResult = personAddCommand.execute(personModel, transactionLogic);
        return new seedu.address.transaction.commands.CommandResult(personCommandResult.getFeedbackToUser());
    }
}*/
