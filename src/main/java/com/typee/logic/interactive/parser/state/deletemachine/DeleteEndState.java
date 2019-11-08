package com.typee.logic.interactive.parser.state.deletemachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LIST_INDEX;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.typee.commons.core.index.Index;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.DeleteCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Represents the end state of the state machine that builds the {@code DeleteCommand}.
 */
public class DeleteEndState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Delete command end state";

    public DeleteEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        throw new StateTransitionException(MESSAGE_END_STATE);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return true;
    }

    @Override
    public Prefix getPrefix() {
        return DUMMY_PREFIX;
    }

    @Override
    public Command buildCommand() throws CommandException {
        String oneBasedIndex = fetchIndex(soFar.getValue(PREFIX_LIST_INDEX));
        try {
            Index index = InteractiveParserUtil.parseIndex(oneBasedIndex);
            return new DeleteCommand(index);
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Returns the index from the {@code String} representation.
     *
     * @param value {@code String} representation of the index.
     * @return index.
     */
    private String fetchIndex(Optional<String> value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value.get());
        matcher.find();
        return matcher.group();
    }
}
