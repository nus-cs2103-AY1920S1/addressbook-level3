package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.schedule.Event;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.DeleteEventCommand}.
 */
public class DeleteEventCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_EVENTNAME
    );

    static Optional<Person> getSelectedPerson(final Model model, final ArgumentList arguments) {
        final Optional<CommandArgument> commandArgument = arguments.getFirstOfPrefix(CliSyntax.PREFIX_NAME);
        if (commandArgument.isEmpty()) {
            // user did not type a "n/" prefix, so it's implied the current User object is intended
            return Optional.of(model.getUser());
        }

        return commandArgument.flatMap(arg -> {
            return Suggester.getSelectedPerson(model, arg);
        });
    }

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();
        final String value = commandArgument.getValue();

        if (prefix.equals(CliSyntax.PREFIX_NAME)) {
            return model.personSuggester(value);
        } else if (prefix.equals(CliSyntax.PREFIX_EVENTNAME)) {
            final Optional<Person> person = getSelectedPerson(model, arguments);
            if (person.isEmpty()) {
                return null;
            }

            final String searchTerm = commandArgument.getValue();

            return person.get()
                    .getSchedule()
                    .getEvents()
                    .stream()
                    .map(Event::getEventName)
                    .filter(eventName -> {
                        return eventName.startsWith(searchTerm);
                    })
                    .collect(Collectors.toUnmodifiableList());
        }

        return null;
    }
}
