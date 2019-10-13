package seedu.address.storage.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ReadOnlyAddressBook;
import seedu.address.model.quiz.person.Question;



/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonQuizSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Questions list contains duplicate question(s).";

    private final List<JsonQuizAdaptedQuestion> questions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given questions.
     */
    @JsonCreator
    public JsonQuizSerializableAddressBook(@JsonProperty("questions") List<JsonQuizAdaptedQuestion> questions) {
        this.questions.addAll(questions);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonQuizSerializableAddressBook(ReadOnlyAddressBook source) {
        questions.addAll(source.getQuestionList()
                .stream()
                .map(JsonQuizAdaptedQuestion::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressQuizBook toModelType() throws IllegalValueException {
        AddressQuizBook addressBook = new AddressQuizBook();
        for (JsonQuizAdaptedQuestion jsonAdaptedQuestion : questions) {
            Question question = jsonAdaptedQuestion.toModelType();
            if (addressBook.hasQuestion(question)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addQuestion(question);
        }
        return addressBook;
    }

}
