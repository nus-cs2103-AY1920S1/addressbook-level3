package com.typee.logic.interactive.parser.state;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;

/**
 * Represents the final state of a finite state machine from which a {@code Command} can be built.
 */
public abstract class EndState extends State {

    protected static final Logger logger = LogsCenter.getLogger(EndState.class);

    protected static final String LOG_BUILD_FAILURE = "Failed to build %s - %s";

    protected static final String MESSAGE_END_STATE = "Cannot transition from an end state!";
    protected static final Prefix DUMMY_PREFIX = new Prefix("");

    protected EndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    /**
     * Returns a command built using the arguments accumulated from the finite state machine.
     * @return
     * @throws CommandException
     */
    public abstract Command buildCommand() throws CommandException;

}
