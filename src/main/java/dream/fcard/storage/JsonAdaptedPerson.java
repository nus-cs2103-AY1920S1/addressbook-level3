package dream.fcard.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import dream.fcard.commons.exceptions.IllegalValueException;
import dream.fcard.model.cards.Person;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson() {

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     */
    public Person toModelType() {
        return new Person();
    }

}
