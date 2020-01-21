package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.algobase.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.commands.problem.FindCommand;
import seedu.algobase.logic.parser.problem.FindCommandParser;
import seedu.algobase.model.searchrule.problemsearchrule.FindProblemDescriptor;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;

class FindCommandParserTest {

    private static final String EMPTY_INPUT = "";
    private static final String COMMAND_WITH_PREAMBLE = "preamble n/hello";
    private static final String NAME_KEYWORD = "Sequences";
    private static final String COMMAND_WITH_DUPLICATE_PARAM = " n/random stuff n/" + NAME_KEYWORD;

    private FindCommandParser findCommandParser = new FindCommandParser();

    @Test
    void parse_emptyInput_promptsMessageNoConstraints() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_CONSTRAINTS);
        assertParseFailure(findCommandParser, EMPTY_INPUT, expectedMessage);
    }

    @Test
    void parse_hasPreamble_promptsInvalidCommandFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertParseFailure(findCommandParser, COMMAND_WITH_PREAMBLE, expectedMessage);
    }

    @Test
    void parse_hasDuplicateParam_acceptsTheLastOccurrence() {
        FindProblemDescriptor expectedDescriptor = new FindProblemDescriptor();
        expectedDescriptor.setNamePredicate(
            new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword(NAME_KEYWORD))));
        FindCommand expectedCommand = new FindCommand(expectedDescriptor);
        assertParseSuccess(findCommandParser, COMMAND_WITH_DUPLICATE_PARAM, expectedCommand);
    }

}
