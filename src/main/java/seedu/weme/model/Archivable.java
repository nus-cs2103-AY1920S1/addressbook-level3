package seedu.weme.model;

/**
 * Makes a resource archivable, requires an instance field {@code isArchived} as well.
 */
public interface Archivable {

    /**
     * Returns whether the implementing resource is archived.
     */
    boolean isArchived();

}
