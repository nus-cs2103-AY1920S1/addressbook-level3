package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DAY;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_DURATION_IN_MINUTES;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_START_TIME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.tarence.logic.commands.AddTutorialCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Parses input arguments and creates a new AddMTutorialCommand object
 */
public class AddTutorialCommandParser implements Parser<AddTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTutorialCommand
     * and returns an AddTutorialCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for all the required arguments.
     */
    public AddTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_DAY, PREFIX_MODULE,
                PREFIX_TUTORIAL_DURATION_IN_MINUTES, PREFIX_TUTORIAL_NAME,
                PREFIX_TUTORIAL_START_TIME, PREFIX_TUTORIAL_WEEKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_DAY, PREFIX_MODULE,
                PREFIX_TUTORIAL_DURATION_IN_MINUTES, PREFIX_TUTORIAL_NAME,
                PREFIX_TUTORIAL_START_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE));
        }

        String tutorialName = argMultimap.getValue(PREFIX_TUTORIAL_NAME).get();
        TutName tutName = ParserUtil.parseTutorialName(tutorialName);
        ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());

        // Attributes for TimeTable class.
        String tutorialDay = argMultimap.getValue(PREFIX_TUTORIAL_DAY).get();
        String tutorialDuration = argMultimap.getValue(PREFIX_TUTORIAL_DURATION_IN_MINUTES).get();
        String tutorialStartTime = argMultimap.getValue(PREFIX_TUTORIAL_START_TIME).get();
        String tutorialWeeks;
        if (arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_WEEKS)) {
            tutorialWeeks = argMultimap.getValue(PREFIX_TUTORIAL_WEEKS).get();
        } else {
            tutorialWeeks = TimeTable.DEFAULT_WEEKS;
        }

        DayOfWeek day = ParserUtil.parseDayOfWeek(tutorialDay);
        Duration duration = ParserUtil.parseDuration(tutorialDuration);
        Set<Week> weeks = ParserUtil.parseWeeks(tutorialWeeks);
        LocalTime startTime = ParserUtil.parseLocalTime(tutorialStartTime);

        // Empty list of Students is created for a new Tutorial
        ArrayList<Student> emptyListOfStudents = new ArrayList<Student>();

        // Creates a new Tutorial object with the user String.
        Tutorial newTutorial = new Tutorial(tutName, day, startTime, weeks, duration, emptyListOfStudents, modCode);

        return new AddTutorialCommand(newTutorial);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
