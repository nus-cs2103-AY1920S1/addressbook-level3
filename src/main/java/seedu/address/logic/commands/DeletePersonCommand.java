package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class DeletePersonCommand extends Command {
    public static final String COMMAND_WORD = "deleteperson";
    public static final String MESSAGE_SUCCESS = "Delete person success";
    public static final String MESSAGE_FAILURE = "Unable to delete person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public final Name name;

    public DeletePersonCommand(Name name){
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person toDelete = model.findPerson(name);
        if(toDelete != null){
            if(model.deletePerson(toDelete.getPersonID())){
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}
