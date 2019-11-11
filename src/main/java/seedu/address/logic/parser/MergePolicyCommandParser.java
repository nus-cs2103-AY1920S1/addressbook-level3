package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new MergePolicyCommand object
 */
public class MergePolicyCommandParser implements Parser<MergePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MergePolicyCommand
     * and returns a MergePolicyCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does
     * not conform the expected format
     */
    public MergePolicyCommand parse(String args) throws ParseException {
        String trimmedArgs = removeAddPolicyCommandWord(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_COVERAGE, PREFIX_PRICE,
                        PREFIX_START_AGE, PREFIX_END_AGE);

        assert(arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_COVERAGE, PREFIX_PRICE)
                && !arePrefixesPresent(argMultimap, PREFIX_POLICY, PREFIX_TAG)
                && argMultimap.getPreamble().isEmpty());

        PolicyName name = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Coverage coverage = ParserUtil.parseCoverage(argMultimap.getValue(PREFIX_COVERAGE).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        StartAge startAge = argMultimap.getValue(PREFIX_START_AGE).isPresent()
                ? ParserUtil.parseStartAge(argMultimap.getValue(PREFIX_START_AGE).get()) : ParserUtil.parseStartAge("");
        EndAge endAge = argMultimap.getValue(PREFIX_END_AGE).isPresent()
                ? ParserUtil.parseEndAge(argMultimap.getValue(PREFIX_END_AGE).get()) : ParserUtil.parseEndAge("");
        Set<Tag> criteriaList = new HashSet<>();
        Set<Tag> tagList = new HashSet<>();

        Policy policy = new Policy(name, description, coverage, price, startAge, endAge, criteriaList, tagList);

        return new MergePolicyCommand(policy);
    }

    private String removeAddPolicyCommandWord(String args) {
        String withoutAddPolicyCommandWord = args.replaceFirst(AddPolicyCommand.COMMAND_WORD, "");
        return withoutAddPolicyCommandWord;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
