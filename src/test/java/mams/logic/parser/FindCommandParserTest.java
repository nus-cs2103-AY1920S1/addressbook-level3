package mams.logic.parser;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import mams.logic.commands.FindCommand;

import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.StudentContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noRelevantPrefixesPresent_throwParseException() {
        assertParseFailure(parser,
                " t/1 y/1 8/ -s -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_someIrrelevantPrefixesPresent_returnsFindCommand() {
        List<Predicate> studentNames = new ArrayList<>();
        studentNames.add(new StudentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        FindCommand expectedFindCommand =
                new FindCommand(studentNames);

        // irrelevant prefix with keywords
        assertParseSuccess(parser, " s/Alice Bob t/cs1231", expectedFindCommand);

        // irrelevant prefix without keywords
        assertParseSuccess(parser, " s/Alice Bob t/ y/", expectedFindCommand);
    }

    @Test
    public void parse_allEmptyKeywords_throwParseException() {
        // single prefix
        assertParseFailure(parser,
                " a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // multiple prefixes
        assertParseFailure(parser,
                " s/ m/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_someEmptyKeywords_returnsFindCommand() {
        List<Predicate> studentNames = new ArrayList<>();
        studentNames.add(new StudentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        FindCommand expectedFindCommand =
                new FindCommand(studentNames);
        assertParseSuccess(parser, " s/Alice Bob m/ a/", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate> studentNames = new ArrayList<>();
        studentNames.add(new StudentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        FindCommand expectedFindCommand =
                new FindCommand(studentNames);
        assertParseSuccess(parser, " s/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " s/ \n Alice \n  Bob ", expectedFindCommand);

        // multiple prefixes
        List<Predicate> multiplePreds = new ArrayList<>();
        multiplePreds.add(new StudentContainsKeywordsPredicate(Arrays.asList("alice", "bob")));
        multiplePreds.add(new ModuleContainsKeywordsPredicate(Collections.singletonList("cs1231")));
        multiplePreds.add(new AppealContainsKeywordsPredicate(Collections.singletonList("unresolved")));

        expectedFindCommand = new FindCommand(multiplePreds);
        assertParseSuccess(parser, " s/alice bob m/cs1231 a/unresolved", expectedFindCommand);

        // different ordering but same object
        assertParseSuccess(parser, " a/unresolved s/alice bob m/cs1231", expectedFindCommand);
    }
}
