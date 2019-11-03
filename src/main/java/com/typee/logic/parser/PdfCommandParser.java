package com.typee.logic.parser;

import static com.typee.logic.parser.CliSyntax.PREFIX_FROM;
import static com.typee.logic.parser.CliSyntax.PREFIX_LIST_INDEX;
import static com.typee.logic.parser.CliSyntax.PREFIX_TO;

import java.util.stream.Stream;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.PdfCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.person.Person;

/**
 * Parses the given {@code String} of arguments in the context of the PdfCommand
 * and returns an  PdfCommand object for execution.
 */
public class PdfCommandParser implements Parser<PdfCommand> {
    public static final String INPUT_INVALID_NUMBER_FORMAT = "Input is in invalid number format. Please try again. ";
    @Override
    public PdfCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args);

        if (isInvalidMultimap(argMultimap)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    PdfCommand.MESSAGE_USAGE));
        }
        try {
            int engagementListIndex = Integer.valueOf(argMultimap.getValue(PREFIX_LIST_INDEX).get());
            Person to = parsePerson(argMultimap.getValue(PREFIX_TO).get());
            Person from = parsePerson(argMultimap.getValue(PREFIX_FROM).get());

            return new PdfCommand(engagementListIndex, to, from);
        } catch (NumberFormatException e) {
            throw new ParseException(INPUT_INVALID_NUMBER_FORMAT + e.getMessage());
        }
    }

    /**
     * Maps the user entered arguments to {@code PdfCommand} prefixes.
     * @param args user input
     * @return {@code ArgumentMultimap} containing a mapping of prefixes to actual arguments.
     */
    private ArgumentMultimap getArgumentMultimap(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_LIST_INDEX, PREFIX_TO, PREFIX_FROM);
    }

    /**
     * Returns true if the arguments don't correspond to {@code PdfCommand} parameters.
     * @param argMultimap user input arguments mapped by their prefixes.
     * @return true if the command entered is invalid.
     */
    private boolean isInvalidMultimap(ArgumentMultimap argMultimap) {
        return (!arePrefixesPresent(argMultimap, PREFIX_LIST_INDEX, PREFIX_TO, PREFIX_FROM)
                || !argMultimap.getPreamble().isEmpty());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a {@code String} representing a {@code Person}.
     * @param args string representing name of the person.
     * @return {@code Person}.
     */
    private Person parsePerson(String args) {
        return new Person(InteractiveParserUtil.parseNameDeterministic(args.trim()));
    }

}
