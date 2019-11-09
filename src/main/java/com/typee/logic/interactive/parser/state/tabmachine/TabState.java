package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the initial and the penultimate state of the state machine that builds the {@code TabCommand}.
 */
public class TabState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "Tab command initiated. Please enter the tab you would like"
            + " to shift to, prefixed by \"b/\"."
            + " The available tabs are \"game\", \"calendar\", \"engagement\" and \"report\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid tab name after \"b/\".";
    private static final String MESSAGE_INVALID = "Invalid input! The tab name should be one of"
            + " game, calendar, engagement or report";
    private static final String KEYWORD_GAME_TAB = "game";
    private static final String KEYWORD_ENGAGEMENT_TAB = "engagement";
    private static final String KEYWORD_CALENDAR_TAB = "calendar";
    private static final String KEYWORD_REPORT_TAB = "report";

    public TabState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> tab = newArgs.getValue(PREFIX_TAB);
        performGuardChecks(newArgs, tab);
        collateArguments(this, newArgs, PREFIX_TAB);

        enforceNoExcessiveArguments(newArgs);

        return new TabEndState(soFar);
    }

    /**
     * Checks that the input doesn't have duplicate prefixes, contains the required keyword and is valid.
     *
     * @param newArgs Unprocessed arguments.
     * @param tab User entered {@code String}.
     * @throws StateTransitionException If there are duplicate prefixes, the required keyword is missing, or
     * the input is invalid.
     */
    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> tab)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(tab, MESSAGE_MISSING_KEYWORD);
        enforceValidity(tab);
    }

    /**
     * Ensures that the input {@code String} is a valid tab.
     *
     * @param tab Tab to switch to.
     * @throws StateTransitionException If the tab is invalid.
     */
    private void enforceValidity(Optional<String> tab) throws StateTransitionException {
        String tabString = tab.get();
        if (!isValid(tabString)) {
            throw new StateTransitionException(MESSAGE_INVALID);
        }
    }

    /**
     * Returns true if the {@code String} input is not a valid tab.
     *
     * @param tabString {@code String} representing a tab.
     * @return true if the {@code String} is a valid tab.
     */
    private boolean isValid(String tabString) {
        return tabString.equalsIgnoreCase(KEYWORD_GAME_TAB)
                || tabString.equalsIgnoreCase(KEYWORD_ENGAGEMENT_TAB)
                || tabString.equalsIgnoreCase(KEYWORD_CALENDAR_TAB)
                || tabString.equalsIgnoreCase(KEYWORD_REPORT_TAB);
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
        return PREFIX_TAB;
    }
}
