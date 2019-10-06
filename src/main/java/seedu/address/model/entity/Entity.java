package seedu.address.model.entity;

import seedu.address.model.person.Name;

/**
 * Represents the different types of entities in Mortago.
 */
public interface Entity {

    Name getName();
    boolean isSameEntity(Object o);
}
