package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import mams.logic.commands.HistoryCommand;
import mams.logic.history.HistoryFilterSettings;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {

    public static final Option OPTION_HIDE_OUTPUT = new Option("o");
    public static final Option OPTION_HIDE_UNSUCCESSFUL = new Option("s");

    public static final String MESSAGE_OPTIONS_NOT_RECOGNIZED = "Invalid options: %1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns HistoryCommand object for execution.
     * @param args {@code String} to be parsed
     */
    @Override
    public HistoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        OptionsSet optionsSet = OptionsTokenizer.tokenize(args);

        verifyNoUnrecognizedOptions(optionsSet, OPTION_HIDE_OUTPUT, OPTION_HIDE_UNSUCCESSFUL);

        // determine whether user has decided to filter successful or unsuccessful command out
        HistoryFilterSettings displayOptions;
        if (optionsSet.isOptionPresent(OPTION_HIDE_UNSUCCESSFUL)) {
            displayOptions = HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL;
        } else {
            displayOptions = HistoryFilterSettings.SHOW_ALL;
        }
        return new HistoryCommand(optionsSet.isOptionPresent(OPTION_HIDE_OUTPUT), displayOptions);
    }

    /**
     * Throws a ParseException if there are any options in {@code optionsSet} not matching those in the
     * supplied {@code options} array.
     * @param optionsSet tokenized options
     * @param options recognized options to compare against
     * @throws ParseException if any options other than the recognized options are found.
     */
    private void verifyNoUnrecognizedOptions(OptionsSet optionsSet, Option... options) throws ParseException {
        List<Option> unrecognizedOptions = optionsSet.getAllOtherOptions(options);
        if (!unrecognizedOptions.isEmpty()) {
            String unrecognizedOptionsAsString = unrecognizedOptions.stream().map(Option::toString)
                    .collect(Collectors.joining(" "));
            throw new ParseException(String.format(MESSAGE_OPTIONS_NOT_RECOGNIZED, unrecognizedOptionsAsString)
                    + " \n" + HistoryCommand.MESSAGE_USAGE);
        }
    }
}
