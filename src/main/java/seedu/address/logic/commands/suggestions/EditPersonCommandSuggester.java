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
import seedu.address.model.tag.Tag;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.EditPersonCommand}.
 */
public class EditPersonCommandSuggester extends Suggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_EDIT,
            CliSyntax.PREFIX_NAME,
            CliSyntax.PREFIX_PHONE,
            CliSyntax.PREFIX_EMAIL,
            CliSyntax.PREFIX_ADDRESS,
            CliSyntax.PREFIX_REMARK,
            CliSyntax.PREFIX_TAG
    );

    protected static Optional<Person> getSelectedPerson(final Model model, final ArgumentList arguments) {
        final Optional<String> personNameInput = arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_EDIT);
        if (personNameInput.isEmpty()) {
            return Optional.empty();
        }

        final Name personName = new Name(personNameInput.get());
        final Person person = model.findPerson(personName);

        return Optional.ofNullable(person);
    }

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();
        final String value = commandArgument.getValue();

        if (prefix.equals(CliSyntax.PREFIX_EDIT)) {
            return model.personSuggester(value);
        } else if (SUPPORTED_PREFIXES.contains(prefix)) {
            final Optional<Person> optionalSelectedPerson = getSelectedPerson(model, arguments);
            if (optionalSelectedPerson.isEmpty()) {
                return null;
            }

            final Person selectedPerson = optionalSelectedPerson.get();
            if (prefix.equals(CliSyntax.PREFIX_NAME)) {
                return List.of(selectedPerson.getName().toString());
            } else if (prefix.equals(CliSyntax.PREFIX_PHONE)) {
                return List.of(selectedPerson.getPhone().toString());
            } else if (prefix.equals(CliSyntax.PREFIX_EMAIL)) {
                return List.of(selectedPerson.getEmail().toString());
            } else if (prefix.equals(CliSyntax.PREFIX_ADDRESS)) {
                return List.of(selectedPerson.getAddress().toString());
            } else if (prefix.equals(CliSyntax.PREFIX_REMARK)) {
                return List.of(selectedPerson.getRemark().toString());
            } else if (prefix.equals(CliSyntax.PREFIX_TAG)) {
                return selectedPerson
                        .getTags()
                        .stream()
                        .map(Tag::toString)
                        .collect(Collectors.toUnmodifiableList());
            }
        }

        return null;
    }
}
