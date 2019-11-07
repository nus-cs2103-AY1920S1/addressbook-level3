package io.xpire.storage;

import io.xpire.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of object.
 */
public interface JsonAdapted {

    /**
     * Converts this Jackson-friendly adapted object into the xpireModel's object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted object.
     */
    Object toModelType() throws IllegalValueException;
}
