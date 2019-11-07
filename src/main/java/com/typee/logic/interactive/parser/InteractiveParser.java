package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that interacts with the user to sequentially build a {@code Command}.
 *
 * The interactive parser is an implementation of an abstract machine, namely the finite state automaton. Each
 * {@code Command}'s parser is a finite state machine (FSM). The accepting state of the FSM is the final stage
 * from which a {@code Command} can be built.
 */
public interface InteractiveParser {
    /**
     * Parses the input entered by the user and updates the parser's state.
     *
     * @param commandText Input entered by the user.
     * @throws ParseException If the input is invalid.
     */
    void parseInput(String commandText) throws ParseException;

    /**
     * Fetches the result of executing the user input. Returns the message in
     * a {@code CommandResult} object.
     *
     * @return result of execution.
     */
    CommandResult fetchResult();

    /**
     * Returns true if the parser has parsed all the necessary arguments of the {@code Command} being built.
     *
     * @return true if the command can be built.
     */
    boolean hasParsedCommand();

    /**
     * Builds and returns the {@code Command} that is available after parsing.
     *
     * @return {@code Command} entered by the user.
     * @throws ParseException If the {@code Command} has invalid arguments.
     */
    Command makeCommand() throws ParseException;

}
