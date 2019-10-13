package com.dukeacademy.logic.parser;

import static com.dukeacademy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_STATUS;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TAG;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TITLE;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.Set;
import java.util.stream.Stream;

import com.dukeacademy.logic.commands.AddCommand;
import com.dukeacademy.logic.parser.exceptions.ParseException;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TOPIC,
                    PREFIX_STATUS, PREFIX_DIFFICULTY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DIFFICULTY, PREFIX_TOPIC, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseName(argMultimap.getValue(PREFIX_TITLE).get());
        Topic topic = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_TOPIC).get());
        Status
            status = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_STATUS).get());
        Difficulty difficulty = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Question question = new Question(title, topic, status, difficulty, tagList);

        return new AddCommand(question);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
