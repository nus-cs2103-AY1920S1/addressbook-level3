package seedu.address.logic.parser;

import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;
import seedu.address.model.project.Title;
import seedu.address.model.timetable.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddProjectCommandParser implements Parser<AddProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        List<Task> tasks = new ArrayList<>();
        List<String> members = new ArrayList<>();
        Finance finance = new Finance();
        Timetable timetable = new Timetable();

        Project project = new Project(title, description, members, tasks, finance, timetable);

        return new AddProjectCommand(project);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
