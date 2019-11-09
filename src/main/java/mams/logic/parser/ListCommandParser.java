package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.OPTION_APPEAL;
import static mams.logic.parser.CliSyntax.OPTION_MODULE;
import static mams.logic.parser.CliSyntax.OPTION_STUDENT;

import java.util.List;
import java.util.stream.Collectors;

import mams.logic.commands.ListCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    public static final String MESSAGE_OPTIONS_NOT_RECOGNIZED = "Invalid parameters: %1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);

        boolean showStudents = false;
        boolean showModules = false;
        boolean showAppeals = false;
        boolean showAll;

        verifyNoUnrecognizedArguments(args, OPTION_APPEAL, OPTION_MODULE, OPTION_STUDENT);
        OptionsSet optionsSet = OptionsTokenizer.tokenize(args);

        // if no prefixes were specified, default to list all items.
        showAll = optionsSet.areAllTheseOptionAbsent(OPTION_APPEAL, OPTION_MODULE, OPTION_STUDENT);

        if (optionsSet.isOptionPresent(OPTION_APPEAL) || showAll) {
            showAppeals = true;
        }
        if (optionsSet.isOptionPresent(OPTION_MODULE) || showAll) {
            showModules = true;
        }
        if (optionsSet.isOptionPresent(OPTION_STUDENT) || showAll) {
            showStudents = true;
        }
        return new ListCommand(showAppeals, showModules, showStudents);
    }

    /**
     * Throws a ParseException if there are any options in {@code optionsSet} not matching those in the
     * supplied {@code options} array.
     * @param optionsSet tokenized options
     * @param options recognized options to compare againse
     * @throws ParseException if any options other than the recognized options are found.
     */
    private void verifyNoUnrecognizedOptions(OptionsSet optionsSet, Option... options) throws ParseException {
        List<Option> unrecognizedOptions = optionsSet.getAllOtherOptions(options);
        if (!unrecognizedOptions.isEmpty()) {
            String unrecognizedOptionsAsString = unrecognizedOptions.stream().map(Option::toString)
                    .collect(Collectors.joining(" "));
            throw new ParseException(String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, unrecognizedOptionsAsString)
                    + " \n" + ListCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Throws a ParseException if there are any arguments in {@code args} that do not match the format of
     * any {@code Option} in the supplied {@code options} array. This is a convenient wrapper around the
     * {@link OptionsTokenizer#getUnrecognizedArguments(String, String...)} method to throw error messages specific
     * to ListCommand.
     * @param args String to be parsed
     * @param recognized Array of {@code Option} that are deemed acceptable
     * @throws ParseException if args contain any arguments other than those in {@code recognized}
     */
    private void verifyNoUnrecognizedArguments(String args, Option... recognized) throws ParseException {
        List<String> unrecognized = OptionsTokenizer.getUnrecognizedArguments(args, recognized);
        if (!unrecognized.isEmpty()) {
            String unrecognizedParamsAsSingleString = String.join(" ", unrecognized).trim();
            throw new ParseException(String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED,
                    unrecognizedParamsAsSingleString)
                    + "\n" + ListCommand.MESSAGE_USAGE);
        }
    }
}
