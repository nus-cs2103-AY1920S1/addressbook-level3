package seedu.eatme.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.ReadOnlyEateryList;

import seedu.eatme.model.eatery.Eatery;

/**
 * An Immutable EateryList that is serializable to JSON format.
 */
@JsonRootName(value = "eaterylist")
class JsonSerializableEateryList {

    public static final String MESSAGE_DUPLICATE_EATERY = "Eateries list contains duplicate eatery(ies).";

    private final List<JsonAdaptedEatery> eateries = new ArrayList<>();
    private final List<JsonAdaptedEatery> todos = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEateryList} with the given eateries.
     */
    @JsonCreator
    public JsonSerializableEateryList(@JsonProperty("eateries") List<JsonAdaptedEatery> eateries) {
        this.eateries.addAll(eateries);
    }

    /**
     * Converts a given {@code ReadOnlyEateryList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEateryList}.
     */
    public JsonSerializableEateryList(ReadOnlyEateryList source) {
        eateries.addAll(source.getEateryList().stream().map(JsonAdaptedEatery::new).collect(Collectors.toList()));

        todos.addAll(source.getTodoList().stream().map(JsonAdaptedEatery::new).collect(Collectors.toList()));
    }

    /**
     * Converts this eatery list into the model's {@code EateryList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EateryList toModelType() throws IllegalValueException {
        EateryList eateryList = new EateryList();
        for (JsonAdaptedEatery jsonAdaptedEatery : eateries) {
            Eatery eatery = jsonAdaptedEatery.toModelType();
            if (eateryList.hasEatery(eatery)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EATERY);
            }
            eateryList.addEatery(eatery);
        }

        eateryList.toggle();
        for (JsonAdaptedEatery jsonAdaptedEatery : todos) {
            Eatery eatery = jsonAdaptedEatery.toModelType();
            if (eateryList.hasEatery(eatery)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EATERY);
            }
            eateryList.addEatery(eatery);
        }

        return eateryList;
    }

}
