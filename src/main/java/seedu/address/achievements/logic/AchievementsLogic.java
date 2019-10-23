package seedu.address.achievements.logic;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public interface AchievementsLogic {

    CommandResult execute(String commandText) throws CommandException, ParseException;

    int getTotalPersons();
}
