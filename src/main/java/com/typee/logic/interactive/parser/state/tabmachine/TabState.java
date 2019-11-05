package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class TabState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Tab command initiated. Please enter the tab you would like"
            + " to shift to, prefixed by \"b/\"."
            + " The available tabs are \"game\", \"calendar\", \"engagement\" and \"report\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter a valid tab name after \"b/\".";
    private static final String MESSAGE_INVALID = "Invalid input! The tab name should be one of"
            + " game, calendar, engagement or report";

    public TabState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> tab = newArgs.getValue(PREFIX_TAB);
        performGuardChecks(newArgs, tab);
        collateArguments(this, newArgs, PREFIX_TAB);

        return new TabEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> tab)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(tab, MESSAGE_MISSING_KEYWORD);
        enforceValidity(tab);
    }

    private void enforceValidity(Optional<String> tab) throws StateTransitionException {
        String tabString = tab.get();
        if (!isValid(tabString)) {
            throw new StateTransitionException(MESSAGE_INVALID);
        }
    }

    private boolean isValid(String tabString) {
        return tabString.equalsIgnoreCase("game")
                || tabString.equalsIgnoreCase("engagement")
                || tabString.equalsIgnoreCase("calendar")
                || tabString.equalsIgnoreCase("report");
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
