package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CliSyntax.PREFIX_MASS_RESOLVE;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mams.commons.core.index.Index;
import mams.commons.exceptions.IllegalValueException;

import mams.logic.commands.Approve;
import mams.logic.commands.ApproveCommand;
import mams.logic.commands.MassApprove;
import mams.logic.parser.exceptions.ParseException;

import mams.model.appeal.Appeal;

/**
 * Parses input arguments and creates a new {@code ApproveCommand} object
 */
public class ApproveCommandParser implements Parser<Approve> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ApproveCommand}
     * and returns a {@code ApproveCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Approve parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REASON, PREFIX_MASS_RESOLVE);

        Index index;
        if (argMultimap.getValue(PREFIX_REASON).isPresent()) {
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ApproveCommand.MESSAGE_USAGE_APPROVE), ive);
            }
            String remark = argMultimap.getValue(PREFIX_REASON).orElse("");
            return new ApproveCommand(index, remark);
        } else if (argMultimap.getValue(PREFIX_MASS_RESOLVE).isPresent()) {
            Optional<String> appealLine = argMultimap.getValue(PREFIX_MASS_RESOLVE);
            String[] appeals = appealLine.get().split(" ");
            List<String> validIds = new ArrayList<>();
            List<String> invalidIds = new ArrayList<>();
            for (String appeal : appeals) {
                if (Appeal.isValidAppealId(appeal)) {
                    validIds.add(appeal);
                } else {
                    invalidIds.add(appeal);
                }
            }
            return new MassApprove(validIds, invalidIds);
        } else {
            throw new ParseException(Approve.MESSAGE_USAGE_APPROVE);
        }


    }
}
