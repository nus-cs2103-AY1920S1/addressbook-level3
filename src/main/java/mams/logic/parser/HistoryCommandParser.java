package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import mams.logic.commands.HistoryCommand;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {

    public static final Prefix PREFIX_HIDE_OUTPUT = new Prefix("-h");

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns HistoryCommand object for execution.
     * @param args {@code String} to be parsed
     */
    @Override
    public HistoryCommand parse(String args) {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HIDE_OUTPUT);

        return new HistoryCommand(argMultimap.getValue(PREFIX_HIDE_OUTPUT).isPresent());
    }
}
