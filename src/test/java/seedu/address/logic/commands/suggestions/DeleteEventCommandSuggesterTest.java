package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

class DeleteEventCommandSuggesterTest extends SuggesterImplTester {
    protected DeleteEventCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeleteEventCommandSuggester.class);
    }

    Stream<String> getPersonEventNames(final Person person) {
        return person.getSchedule().getEvents().stream().map(Event::getEventName);
    }

    @Test
    void getSuggestion_userEventsBlankEventName_allUserEvents() {
        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_EVENTNAME, 0, EMPTY_STRING)
        );
        final List<String> expectedEventNames = getPersonEventNames(model.getUser())
                .collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(arguments, arguments.get(0), expectedEventNames);
    }

    @Test
    void getSuggestion_exactPersonNameBlankEventName_allPersonEvents() {
        final Name knownPersonName = TypicalPersonDescriptor.BENSON.getName();
        final Person knownPerson;
        try {
            knownPerson = model.findPerson(knownPersonName);
        } catch (PersonNotFoundException e) {
            Assertions.fail("Sample person not found in model", e);
            return;
        }

        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, knownPersonName.toString()),
                new CommandArgument(CliSyntax.PREFIX_EVENTNAME, 1, EMPTY_STRING)
        );
        final List<String> expectedEventNames = getPersonEventNames(knownPerson)
                .collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(arguments, arguments.get(1), expectedEventNames);
    }

    @Test
    void getSuggestion_unknownPersonNameBlankEventName_noSuggestions() {
        final Name unknownPerson = TypicalPersonDescriptor.ZACK.getName();

        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_NAME, 0, unknownPerson.toString()),
                new CommandArgument(CliSyntax.PREFIX_EVENTNAME, 1, EMPTY_STRING)
        );

        assertNoSuggestions(arguments, arguments.get(1));
    }
}
