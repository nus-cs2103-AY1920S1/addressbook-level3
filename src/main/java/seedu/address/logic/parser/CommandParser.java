package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.builders.CommandBuilder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that can parse user input into a command.
 */
public class CommandParser implements Parser<Command> {

    private Command command;
    private CommandBuilder commandBuilder;

    @Override
    public Command parse(String userInput) throws ParseException {

        State state1 = new State();
        State state2 = new State();
        State state3 = new State();
        State state4 = new State();

        state1.addPattern("\\s*", matches -> state2);
        state2.addPattern("[^\\s]+", matches -> {
            this.commandBuilder = new CommandKeywordParser().parse(matches.get(0));
            return state3;
        });
        state3.addPattern("\\s*", matches -> {
            if (matches.get(0).length() == 0) {
                this.command = this.commandBuilder.build();
                return null;
            } else {
                return state4;
            }
        });
        state4.addPattern("\"(.*?)\"", matches -> {
            this.commandBuilder.acceptSentence(matches.get(1));
            return state3;
        });
        state4.addPattern("[^\\s]+", matches -> {
            this.commandBuilder.acceptSentence(matches.get(0));
            return state3;
        });

        try {
            State state = state1;
            while (state != null) {
                StateResult result = state.apply(userInput);

                // Throw exception if no pattern matches.
                if (result == null) {
                    throw new Exception(MESSAGE_INVALID_COMMAND_FORMAT);
                }

                state = result.next;
                userInput = userInput.substring(result.matchLength);
            }
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }

        return this.command;
    }

    /**
     * Represents a function that State uses.
     * Returns the next state to transition to.
     */
    private interface StateFunction {
        State apply(List<String> matches) throws Exception;
    }

    /**
     * Represents one state of a finite state machine for this parser.
     */
    private class State {
        private Map<Pattern, StateFunction> patterns;

        private State() {
            this.patterns = new LinkedHashMap<>();
        }

        private void addPattern(String pattern, StateFunction function) {
            patterns.put(Pattern.compile("^(" + pattern + ")"), function);
        }

        /**
         * Gives input to this state and get the next state to transition to.
         * @param userInput the user input
         * @return a StateResult
         * @throws Exception if an exception is thrown in StateFunction for this pattern
         */
        private StateResult apply(String userInput) throws Exception {
            for (Map.Entry<Pattern, StateFunction> entry : this.patterns.entrySet()) {
                Pattern pattern = entry.getKey();
                StateFunction function = entry.getValue();

                Matcher matcher = pattern.matcher(userInput);
                if (matcher.find()) {
                    List<String> matches = new ArrayList<>();

                    // Add group matches
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        matches.add(matcher.group(i));
                    }

                    State next = function.apply(matches);
                    int matchLength = matches.get(0).length();
                    return new StateResult(next, matchLength);
                }
            }
            return null;
        }
    }

    /**
     * Represents the result of State.apply().
     */
    private class StateResult {
        private State next;
        private int matchLength;

        private StateResult(State next, int matchLength) {
            this.next = next;
            this.matchLength = matchLength;
        }
    }
}
