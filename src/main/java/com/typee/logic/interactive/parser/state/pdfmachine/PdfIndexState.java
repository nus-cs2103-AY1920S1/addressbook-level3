package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LIST_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.commons.core.index.Index;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Represents the initial state of the state machine that builds the {@code PdfCommand}.
 */
public class PdfIndexState extends State {
    private static final String MESSAGE_CONSTRAINTS = "PDF command initiated. Please enter the index of the"
            + " engagement you would like to generate a report for. The index should be prefixed by \"i/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter an index prefixed by \"i/\".";

    public PdfIndexState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> index = newArgs.getValue(PREFIX_LIST_INDEX);
        performGuardChecks(newArgs, index);
        collateArguments(this, newArgs, PREFIX_LIST_INDEX);

        return new FromState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> index)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(index, MESSAGE_MISSING_KEYWORD);
        enforceValidity(index);
    }

    /**
     * Ensures that the entered index is valid.
     *
     * @param index Index.
     * @throws StateTransitionException If the index is invalid.
     */
    private void enforceValidity(Optional<String> index) throws StateTransitionException {
        try {
            Index listIndex = InteractiveParserUtil.parseIndex(index.get());
        } catch (ParseException e) {
            throw new StateTransitionException(e.getMessage());
        }
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return false;
    }

    @Override
    public Prefix getPrefix() {
        return PREFIX_LIST_INDEX;
    }
}
