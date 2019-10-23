package seedu.travezy.achievements.logic;

import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;
import seedu.travezy.logic.parser.exceptions.ParseException;

public interface AchievementsLogic {

    CommandResult execute(String commandText) throws CommandException, ParseException;

    int getTotalPersons();
}
