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
        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noRelevantPrefixesPresent_throwsParseException() {
        assertParseFailure(parser,
                " t/1 y/1 8/ -s -all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_someIrrelevantPrefixesPresent_returnsFindCommand() {
        List<Predicate> studentNames = new ArrayList<>();
        studentNames.add(new StudentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "t/cs1231", "y/")));

        FindCommand expectedFindCommand =
                new FindCommand(studentNames);

        // irrelevant prefix will be taken as part of keywords
        assertParseSuccess(parser, " s/Alice Bob t/cs1231 y/", expectedFindCommand);
    }

    @Test
    public void parse_nonEmptyPreAmple_throwsParseException() {
        assertParseFailure(parser,
                " p/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                " alice a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allEmptyKeywords_throwsParseException() {
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

        // single prefix: s/
        List<Predicate> studentPreds = new ArrayList<>();
        studentPreds.add(new StudentContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        FindCommand expectedFindCommand = new FindCommand(studentPreds);
        assertParseSuccess(parser, " s/Alice Bob", expectedFindCommand);

        // single prefix: m/
        List<Predicate> modulePreds = new ArrayList<>();
        modulePreds.add(new ModuleContainsKeywordsPredicate(Collections.singletonList("cs1231")));

        expectedFindCommand = new FindCommand(modulePreds);
        assertParseSuccess(parser, " m/cs1231", expectedFindCommand);

        // single prefix: a/
        List<Predicate> appealPreds = new ArrayList<>();
        appealPreds.add(new AppealContainsKeywordsPredicate(Collections.singletonList("unresolved")));

        expectedFindCommand = new FindCommand(appealPreds);
        assertParseSuccess(parser, " a/unresolved", expectedFindCommand);

        // multiple prefixes
        List<Predicate> multiplePrefixes = new ArrayList<>();
        multiplePrefixes.add(new StudentContainsKeywordsPredicate(Arrays.asList("alice", "bob")));
        multiplePrefixes.add(new ModuleContainsKeywordsPredicate(Collections.singletonList("cs1231")));
        multiplePrefixes.add(new AppealContainsKeywordsPredicate(Collections.singletonList("unresolved")));

        expectedFindCommand = new FindCommand(multiplePrefixes);
        assertParseSuccess(parser, " s/alice bob m/cs1231 a/unresolved", expectedFindCommand);

        // different ordering but same object
        assertParseSuccess(parser, " a/unresolved s/alice bob m/cs1231", expectedFindCommand);
    }
}
