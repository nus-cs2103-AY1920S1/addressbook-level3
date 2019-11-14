package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.ParserUtil.manageIndexParseException;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.UndoCommand;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        if (args.isBlank()) {
            return new UndoCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new UndoCommand(index);
        } catch (ParseException pe) {
            //This will always throw a ParseException
            manageIndexParseException(pe, UndoCommand.MESSAGE_USAGE);
            //This is included to ensure compiler complies
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UndoCommand.MESSAGE_USAGE), pe);
        }
    }
}
