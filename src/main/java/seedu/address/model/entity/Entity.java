package seedu.address.model.entity;

/**
 * Represents the different types of entities in Mortago.
 */
public interface Entity {

    String OPTIONAL_FIELD_EMPTY = "No input given.";

    boolean isSameEntity(Object o);

    boolean equals(Object o);
}
