package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.FindCommand;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.CostInRangePredicate;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.DateInRangePredicate;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.RemarkContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.model.tag.TagPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Spending>> predicates = new ArrayList<>();
        predicates.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice \n \t Bob  \t", expectedFindCommand);

        // two predicates to avoid '==' bug
        predicates = new ArrayList<>();
        predicates.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        predicates.add(new DateInRangePredicate(new Date("01/01/2019"), new Date("02/01/2019")));
        expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob " + PREFIX_DATE
                + "d/01/01/2019 - 02/01/2019", expectedFindCommand);

        // Cost
        predicates = new ArrayList<>();
        predicates.add(new CostInRangePredicate(new Cost(VALID_COST_BOB), new Cost(VALID_COST_AMY)));
        expectedFindCommand = new FindCommand(predicates);

        // c/COST1 c/COST2
        assertParseSuccess(parser, " " + PREFIX_COST + VALID_COST_BOB + " "
                + PREFIX_COST + VALID_COST_AMY, expectedFindCommand);

        // c/COST1-COST2
        assertParseSuccess(parser, " " + PREFIX_COST + VALID_COST_BOB + "-"
                + VALID_COST_AMY, expectedFindCommand);

        // Remark
        predicates = new ArrayList<>();
        predicates.add(new RemarkContainsKeywordsPredicate(Arrays.asList(VALID_REMARK_AMY.split("\\s+"))));
        expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " " + PREFIX_REMARK + VALID_REMARK_AMY, expectedFindCommand);

        // Tag
        predicates = new ArrayList<>();
        predicates.add(new TagPredicate(Set.of(VALID_TAG_FRIEND)));
        expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " " + PREFIX_TAG + VALID_TAG_FRIEND, expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // empty date
        assertParseFailure(parser, INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // empty name
        assertParseFailure(parser, " " + PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);

        // empty remark
        assertParseFailure(parser, " " + PREFIX_REMARK, Remark.MESSAGE_CONSTRAINTS);

        // empty tag
        assertParseFailure(parser, " " + PREFIX_TAG, Tag.MESSAGE_CONSTRAINTS);

        // one Date only
        assertParseFailure(parser, " " + PREFIX_DATE + "1/1/2019", FindCommandParser.DATE_RANGE_MESSAGE_CONSTRAINTS);

        // one Cost only
        assertParseFailure(parser, " " + PREFIX_COST + "1.00", FindCommandParser.COST_RANGE_MESSAGE_CONSTRAINTS);

        // Cost max > min
        assertParseFailure(parser, " " + PREFIX_COST + "2.00-1.00", FindCommandParser.COST_RANGE_MESSAGE_CONSTRAINTS);
    }
}
