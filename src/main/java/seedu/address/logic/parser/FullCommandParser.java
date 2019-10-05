package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class FullCommandParser implements Parser<Command> {

    @Override
    public Command parse(String userInput) throws ParseException {

        State state1 = new State();
        State state2 = new State();
        State state3 = new State();
        State state4 = new State();

        state1.addPattern("\\s*", match -> state2);
        state2.addPattern("[^\\s]+", match -> {
            new CommandBlueprintParser().parse(match);
            return state3;
        });
        state3.addPattern("\\s*", match -> match.length() == 0 ? null : state4);
        state4.addPattern("\".*?\"|[^\\s]+", match -> state3);

        State state = state1;
        while (state != null) {
            boolean matches = false;
            for (Map.Entry<Pattern, Function<String, State>> entry : state.patterns.entrySet()) {
                Pattern pattern = entry.getKey();
                Function<String, State> function = entry.getValue();

                Matcher matcher = pattern.matcher(userInput);
                if (matcher.find()) {
                    String match = matcher.group();
                    System.out.format("Match: '%s'\n", match);

                    state = function.apply(match);
                    userInput = userInput.substring(match.length());
                    matches = true;
                    break;
                }
            }

            // Throw exception if no pattern matches.
            if (!matches) {
                throw new ParseException("Invalid User Input.");
            }
        }
        return null;
    }

    private class State {

        private Map<Pattern, Function<String, State>> patterns;

        private State() {
            this.patterns = new HashMap<>();
        }

        private void addPattern(String pattern, Function<String, State> function) {
            patterns.put(Pattern.compile("^(" + pattern + ")"), function);
        }
    }
}
