package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.DeleteEventCommand}.
 */
public class DeleteEventCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_EVENTNAME
    );

    protected static Optional<Person> getSelectedPerson(final Model model, final ArgumentList arguments) {
        final Optional<String> personNameInput = arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_NAME);
        if (personNameInput.isEmpty()) {
            return Optional.empty();
        }

        final Name personName = new Name(personNameInput.get());
        Person person = null;
        try {
            person = model.findPerson(personName);
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(person);
    }

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();
        final String value = commandArgument.getValue();

        if (prefix.equals(CliSyntax.PREFIX_NAME)) {
            return model.personSuggester(value);
        } else if (prefix.equals(CliSyntax.PREFIX_EVENTNAME)) {
            final Optional<Person> optionalSelectedPerson = getSelectedPerson(model, arguments);
            if (optionalSelectedPerson.isEmpty()) {
                return null;
            }

            final Person person = optionalSelectedPerson.get();

            return person
                    .getSchedule()
                    .getEvents()
                    .stream()
                    .map(Event::getEventName)
                    .collect(Collectors.toUnmodifiableList());
        }

        return null;
    }
}
