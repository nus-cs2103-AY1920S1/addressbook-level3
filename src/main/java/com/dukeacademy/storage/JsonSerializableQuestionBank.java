package com.dukeacademy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.question.Question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * An Immutable QuestionBank that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableQuestionBank {

    public static final String MESSAGE_DUPLICATE_QUESTION = "Questions list "
        + "contains duplicate question(s).";

    private final List<JsonAdaptedQuestion> questions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuestionBank} with the given questions.
     */
    @JsonCreator
    public JsonSerializableQuestionBank(@JsonProperty("questions") List<JsonAdaptedQuestion> questions) {
        this.questions.addAll(questions);
    }

    /**
     * Converts a given {@code ReadOnlyQuestionBank} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableQuestionBank}.
     */
    public JsonSerializableQuestionBank(ReadOnlyQuestionBank source) {
        questions.addAll(source.getQuestionList().stream().map(JsonAdaptedQuestion::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code QuestionBank} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public QuestionBank toModelType() throws IllegalValueException {
        QuestionBank addressBook = new QuestionBank();
        for (JsonAdaptedQuestion jsonAdaptedQuestion : questions) {
            Question question = jsonAdaptedQuestion.toModelType();
            if (addressBook.hasQuestion(question)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_QUESTION);
            }
            addressBook.addQuestion(question);
        }
        return addressBook;
    }

}
