package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.ArrayList;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class FindPersonCommand extends Command{
    public static final String COMMAND_WORD = "findperson";
    public static final String MESSAGE_SUCCESS = "Found person: ";
    public static final String MESSAGE_FAILURE = "Unable to find person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public final Name name;

    public FindPersonCommand(Name name){
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.findPerson(name);
        if(person != null){
            ArrayList<GroupID> groups = model.findGroupsOfPerson(person.getPersonID());
            String s = "Groups: \n";
            int i;
            for(i = 0; i < groups.size(); i++){
                s += model.findGroup(groups.get(i)).toString();
            }
            return new CommandResult(MESSAGE_SUCCESS + person.toString() + s);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
