package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DateTime;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;

/**
 * Represents Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private String recordType;
    private String dateTime;
    private String concentration;
    private String height;
    private String weight;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given food details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("recordType") String recordType,
                             @JsonProperty("dateTime") String dateTime,
                             @JsonProperty("concentration") String concentration,
                             @JsonProperty("height") String height, @JsonProperty("weight") String weight) {
        this.recordType = recordType;
        this.dateTime = dateTime;
        this.concentration = concentration;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Converts a given {@code Food} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        if (source.getClass() == BloodSugar.class) {
            recordType = "BloodSugar";
            dateTime = source.getDateTime().toString();
            concentration = ((BloodSugar) source).getConcentration().toString();
        } else if (source.getClass() == Bmi.class) {
            recordType = "Bmi";
            dateTime = source.getDateTime().toString();
            height = ((Bmi) source).getHeight().toString();
            weight = ((Bmi) source).getWeight().toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted food object into the sugarmummy.recmfood.model's {@code food} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food.
     */
    public Record toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (recordType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                RecordType.class.getSimpleName()));
        }

        if (recordType.equals("BloodSugar")) {

            if (concentration == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Concentration.class.getSimpleName()));
            }
            if (!Concentration.isValidConcentration(concentration)) {
                throw new IllegalValueException(Concentration.MESSAGE_CONSTRAINTS);
            }
            final Concentration modelConcentration = new Concentration(concentration);

            return new BloodSugar(modelConcentration, modelDateTime);

        } else if (recordType.equals("Bmi")) {
            if (height == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Height.class.getSimpleName()));
            }
            if (!Height.isValidHeight(height)) {
                throw new IllegalValueException(Height.MESSAGE_CONSTRAINTS);
            }
            final Height modelHeight = new Height(height);

            if (weight == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Weight.class.getSimpleName()));
            }
            if (!Weight.isValidWeight(weight)) {
                throw new IllegalValueException(Weight.MESSAGE_CONSTRAINTS);
            }
            final Weight modelWeight = new Weight(weight);

            return new Bmi(modelHeight, modelWeight, modelDateTime);
        } else {
            throw new IllegalValueException(RecordType.MESSAGE_CONSTRAINTS);
        }
    }

}
