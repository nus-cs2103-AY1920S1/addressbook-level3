package seedu.address.model.entity;

import seedu.address.model.person.Name;

/**
 * Represents the different types of entities in Mortago.
 */
public interface Entity {

    Name getName();
    void setName(Name name);
    boolean isSameEntity(Object o);
}
