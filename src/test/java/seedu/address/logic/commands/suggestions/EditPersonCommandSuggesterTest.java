package seedu.address.logic.commands.suggestions;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

class EditPersonCommandSuggesterTest extends EditCommandSuggesterTest {
    static final PersonDescriptor KNOWN_PERSON = TypicalPersonDescriptor.ALICE;

    protected EditPersonCommandSuggesterTest() throws ReflectiveOperationException {
        super(EditPersonCommandSuggester.class, PrefixEditType.PERSON);
    }

    /**
     * Provides the {@link Arguments} that represent the currently known values of the {@code KNOWN_PERSON}'s details
     * (e.g. their name, phone number etc)
     *
     * @return A stream with each value containing the {@link Prefix} and the corresponding expected value.
     */
    static Stream<Arguments> knownPersonPrefixesAndValueGenerator() {
        final Collection<String> tags = KNOWN_PERSON.getTags()
                .stream()
                .map(tag -> {
                    return tag.tagName;
                })
                .collect(Collectors.toUnmodifiableList());

        return Stream.of(
                arguments(CliSyntax.PREFIX_NAME, List.of(KNOWN_PERSON.getName().toString())),
                arguments(CliSyntax.PREFIX_PHONE, List.of(KNOWN_PERSON.getPhone().toString())),
                arguments(CliSyntax.PREFIX_EMAIL, List.of(KNOWN_PERSON.getEmail().toString())),
                arguments(CliSyntax.PREFIX_ADDRESS, List.of(KNOWN_PERSON.getAddress().toString())),
                arguments(CliSyntax.PREFIX_REMARK, List.of(KNOWN_PERSON.getRemark().toString())),
                arguments(CliSyntax.PREFIX_TAG, tags)
        );
    }

    @ParameterizedTest
    @MethodSource("knownPersonPrefixesAndValueGenerator")
    void getSuggestions_exactPersonChangingPrefixBlankValue_currentPersonPrefixValue(
            final Prefix prefix, final List<String> expectedSuggestions) {
        final ArgumentList argumentList = argumentListOf(
                new CommandArgument(CliSyntax.PREFIX_EDIT, 0, KNOWN_PERSON.getName().toString()),
                new CommandArgument(prefix, 1, EMPTY_STRING)
        );

        assertSuggestionsEquals(argumentList, argumentList.get(1), expectedSuggestions);
    }
}
