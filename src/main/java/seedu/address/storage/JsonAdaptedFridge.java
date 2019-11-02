package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;

//@@author ambervoong
/**
 * Jackson-friendly version of {@link Fridge}.
 */
class JsonAdaptedFridge {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Fridge's %s field is missing!";
    // This field can't be null.
    private final String fridgeIdNum;
    private final String fridgeStatus;

    // Stores the ID number of the body, not the actual ID.
    private final String body;


    /**
     * Constructs a {@code JsonAdaptedFridge} with the given worker details.
     */
    @JsonCreator
    public JsonAdaptedFridge(@JsonProperty("fridgeIdNum") String fridgeIdNum,
                             @JsonProperty("fridgeStatus") String fridgeStatus,
                             @JsonProperty("body") String body) {
        this.fridgeIdNum = fridgeIdNum;
        this.fridgeStatus = fridgeStatus;
        this.body = body;
    }

    /**
     * Converts a given {@code Fridge} into this class for Jackson use.
     */
    public JsonAdaptedFridge(Fridge source) {
        fridgeIdNum = Integer.toString(source.getIdNum().getIdNum());
        fridgeStatus = source.getFridgeStatus().toString();
        Body bodySource = source.getBody().orElse(null);
        if (!(bodySource == null)) {
            body = Integer.toString(bodySource.getIdNum().getIdNum());
        } else {
            body = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted fridge object into the model's {@code Fridge} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted fridge.
     */
    public Fridge toModelType() throws IllegalValueException {
        // Convert ID number.
        if (fridgeIdNum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentificationNumber.class.getSimpleName()));
        }
        final int idNumber;
        try {
            idNumber = Integer.parseInt(fridgeIdNum);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }

        // Convert FridgeStatus
        if (fridgeStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FridgeStatus.class.getSimpleName()));
        }

        final FridgeStatus actualStatus = ParserUtil.parseFridgeStatus(fridgeStatus);


        // Convert Body
        final int actualBodyId;
        if (body == null) {
            actualBodyId = 0;
        } else {
            try {
                actualBodyId = Integer.parseInt(body);
            } catch (NumberFormatException e) {
                throw new IllegalValueException(MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
            }
        }

        Fridge fridge = Fridge.generateNewStoredFridge(idNumber);
        fridge.setFridgeStatus(actualStatus);
        fridge.setBodyId(actualBodyId);
        return fridge;
    }
}

