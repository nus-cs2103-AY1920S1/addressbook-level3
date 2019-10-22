package seedu.address.storage;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.binitem.Binnable;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Jackson-friendly version of {@link seedu.address.model.binitem.BinItem}.
 */
class JsonAdaptedBinItem {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "BinItem's %s field is missing!";

    private final JsonAdaptedPerson personItem;
    private final JsonAdaptedPolicy policyItem;
    private final Calendar dateDeleted;
    private final Calendar expiryDate;

    /**
     * Constructs a {@code JsonAdaptedBinItem} with the given BinItem details. This is for Person.
     */
    @JsonCreator
    public JsonAdaptedBinItem(@JsonProperty("dateDeleted") Calendar dateDeleted,
                              @JsonProperty("expiryDate") Calendar expiryDate,
                              @JsonProperty("personItem") JsonAdaptedPerson personItem,
                              @JsonProperty("policyItem") JsonAdaptedPolicy policyItem) {
        this.personItem = personItem;
        this.policyItem = policyItem;
        this.dateDeleted = dateDeleted;
        this.expiryDate = expiryDate;
    }

    /**
     * Converts a given {@code BinItem} into this class for Jackson use.
     */
    public JsonAdaptedBinItem(BinItem source) {
        if (source.getItem() instanceof Person) {
            Person p = (Person) source.getItem();
            this.personItem = new JsonAdaptedPerson(p);
            this.policyItem = null;
        } else {
            Policy p = (Policy) source.getItem();
            this.policyItem = new JsonAdaptedPolicy(p);
            this.personItem = null;
        }
        this.dateDeleted = source.getDateDeleted();
        this.expiryDate = source.getExpiryDate();
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws seedu.address.commons.exceptions.IllegalValueException if there were
     * any data constraints violated in the adapted policy.
     */
    BinItem toModelType() throws IllegalValueException {
        Binnable modelItem;
        if (personItem != null) {
            modelItem = personItem.toModelType();
        } else if (policyItem != null) {
            modelItem = policyItem.toModelType();
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }

        if (dateDeleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        Calendar modelDateDeleted = Calendar.getInstance();
        modelDateDeleted.setTime(dateDeleted.getTime());

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        Calendar modelExpiryDate = Calendar.getInstance();
        modelExpiryDate.setTime(expiryDate.getTime());

        return new BinItem(modelItem, modelDateDeleted, modelExpiryDate);
    }

}
