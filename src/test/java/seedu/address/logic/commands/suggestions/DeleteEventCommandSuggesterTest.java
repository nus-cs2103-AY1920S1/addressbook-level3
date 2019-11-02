package seedu.address.logic.commands.suggestions;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.model.person.schedule.Event;

class DeleteEventCommandSuggesterTest extends SuggesterImplTester {
    protected DeleteEventCommandSuggesterTest() throws ReflectiveOperationException {
        super(DeleteEventCommandSuggester.class);
    }

    @Test
    void getSuggestion_userEventsBlankEventName_allUserEvents() {
        final ArgumentList arguments = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_EVENTNAME, 0, "")
        );
        final List<String> expectedEventNames = model.getUser()
                .getSchedule()
                .getEvents()
                .stream()
                .map(Event::getEventName)
                .collect(Collectors.toUnmodifiableList());

        assertSuggestionsEquals(arguments, arguments.get(0), expectedEventNames);
    }
}
