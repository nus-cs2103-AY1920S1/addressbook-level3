package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATIONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * This is the class for the command to find the closest location
 */
public class ClosestLocationCommand extends Command {

    public static final String COMMAND_WORD = "closestlocation";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_LOCATIONS + " LIST OF LOCATIONS ";
    public static final String MESSAGE_SUCCESS = "Closest location found: ";
    public static final String MESSAGE_FAILURE = "Cannot find closest location.";

    private ArrayList<String> locationNameList;

    public ClosestLocationCommand(ArrayList<String> locationNameList) {
        this.locationNameList = locationNameList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String userInput = "";
        for (int i = 0; i < locationNameList.size(); i++) {
            userInput = userInput + locationNameList.get(i) + " ";
        }
        try {
            return new CommandResultBuilder(MESSAGE_SUCCESS
                    + model.getClosestLocationDataString(locationNameList)
                    + " location you entered: " + userInput).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_FAILURE
                + "\nlocation you entered: " + userInput);
    }

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof ClosestLocationCommand); // instanceof handles nulls
    }
}
