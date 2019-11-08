package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.ui.Tab;

/**
 * Represents the accepting state of the finite state machine that parses the {@code TabCommand}.
 */
public class TabEndState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Tab successfully shifted!";
    private static final String MESSAGE_INVALID_INPUT = "No such tab exists!";
    private static final String KEYWORD_GAME_TAB = "TypingGame";
    private static final String KEYWORD_ENGAGEMENT_TAB = "Engagement";
    private static final String KEYWORD_REPORT_TAB = "Report";
    private static final String KEYWORD_CALENDAR_TAB = "Calendar";
    private static final String KEYWORD_GAME_INPUT = "game";
    private static final String KEYWORD_ENGAGEMENT_INPUT = "engagement";
    private static final String KEYWORD_REPORT_INPUT = "report";
    private static final String KEYWORD_CALENDAR_INPUT = "calendar";

    public TabEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        String tab = soFar.getValue(PREFIX_TAB).get();
        Tab tabToSwitchTo = makeTab(tab);
        return new TabCommand(tabToSwitchTo);
    }

    /**
     * Makes a {@code Tab} from the entered {@code String}.
     *
     * @param tab String that represents a tab.
     * @return {@code Tab} that corresponds to the {@code String}.
     * @throws CommandException If no such tab exists.
     */
    private Tab makeTab(String tab) throws CommandException {
        String tabLowerCase = tab.toLowerCase().trim();
        switch(tabLowerCase) {
        case KEYWORD_GAME_INPUT:
            return new Tab(KEYWORD_GAME_TAB);

        case KEYWORD_ENGAGEMENT_INPUT:
            return new Tab(KEYWORD_ENGAGEMENT_TAB);

        case KEYWORD_REPORT_INPUT:
            return new Tab(KEYWORD_REPORT_TAB);

        case KEYWORD_CALENDAR_INPUT:
            return new Tab(KEYWORD_CALENDAR_TAB);

        default:
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }
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
}
