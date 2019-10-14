package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.algobase.logic.commands.AddPlanCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.task.Task;


/**
 * Parses input arguments and creates a new AddPlanCommand object
 */
public class AddPlanCommandParser implements Parser<AddPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPlanCommand
     * and returns an AddPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPlanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION,
                        PREFIX_START_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPlanCommand.MESSAGE_USAGE));
        }
        PlanName name = ParserUtil.parsePlanName(argMultimap.getValue(PREFIX_NAME).get());

        PlanDescription description;
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            description = ParserUtil.parsePlanDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = PlanDescription.DEFAULT_PLAN_DESCRIPTION;
        }

        LocalDateTime startDate;
        if (arePrefixesPresent(argMultimap, PREFIX_START_DATE)) {
            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        } else {
            startDate = LocalDateTime.now();
        }

        LocalDateTime endDate;
        if (arePrefixesPresent(argMultimap, PREFIX_END_DATE)) {
            endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());
        } else {
            endDate = LocalDateTime.now();
        }

        Set<Task> tasks = new HashSet<>();
        // TODO: implementation

        Plan plan = new Plan(name, description, startDate, endDate, tasks);

        return new AddPlanCommand(plan);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
