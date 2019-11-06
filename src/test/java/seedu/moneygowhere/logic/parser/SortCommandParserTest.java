package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.SortCommand;
import seedu.moneygowhere.logic.sorting.SortAttribute;
import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SortOrder;

//@@author Nanosync
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsOneParam_returnsFindCommand() {
        // ---------- Date ----------
        // ASC
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DATE, SortOrder.ASCENDING));
        SortCommand expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_DATE + "ASC", expectedSortCommand);

        // DESC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DATE, SortOrder.DESCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_DATE + "DESC", expectedSortCommand);

        // ---------- Cost ----------
        // ASC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.COST, SortOrder.ASCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_COST + "ASC", expectedSortCommand);

        // DESC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.COST, SortOrder.DESCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_COST + "DESC", expectedSortCommand);

        // ---------- Name ----------
        // ASC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.NAME, SortOrder.ASCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_NAME + "ASC", expectedSortCommand);

        // DESC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.NAME, SortOrder.DESCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_NAME + "DESC", expectedSortCommand);

        // ---------- Remark ----------
        // ASC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.REMARK, SortOrder.ASCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_REMARK + "ASC", expectedSortCommand);

        // DESC
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.REMARK, SortOrder.DESCENDING));
        expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_REMARK + "DESC", expectedSortCommand);
    }

    @Test
    public void parse_validArgsTwoParam_returnsFindCommand() {
        // ---------- Date ----------
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DATE, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.COST, SortOrder.DESCENDING));
        SortCommand expectedSortCommand = new SortCommand(fields);
        assertParseSuccess(parser, " " + PREFIX_DATE + "ASC" + " "
                + PREFIX_COST + "DESC", expectedSortCommand);
    }

    @Test
    public void parse_duplicateArgs_failure() {
        assertParseFailure(parser, " " + PREFIX_DATE + "DESC" + " " + PREFIX_DATE + "ASC",
                SortCommand.MESSAGE_SORT_DUPLICATE_FIELD);

        assertParseFailure(parser, " " + PREFIX_DATE + "ASC" + " " + PREFIX_DATE + "ASC",
                SortCommand.MESSAGE_SORT_DUPLICATE_FIELD);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // Unrecognised prefix
        assertParseFailure(parser, " " + "aaaa/ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // No value for prefixes
        assertParseFailure(parser, " " + PREFIX_DATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_COST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_REMARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Combined invalid args
        assertParseFailure(parser, " " + PREFIX_DATE + " " + PREFIX_COST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Two args with one arg defined before
        assertParseFailure(parser, " " + PREFIX_DATE + "ASC" + " " + PREFIX_COST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Two args with one arg defined after
        assertParseFailure(parser, " " + PREFIX_DATE + " " + PREFIX_COST + "ASC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
