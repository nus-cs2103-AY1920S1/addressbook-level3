package seedu.address.logic.commands.utils;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.model.person.Person;

/**
 * A Model stub that contains a single person.
 */
public class ModelWithPersonStub extends ModelStub {
    private final Person person;

    public ModelWithPersonStub(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public boolean hasPatient(Person person) {
        requireNonNull(person);
        return this.person.isSameAs(person);
    }

    @Override
    public void setTabListing(OmniPanelTab tab) {
    }
}
