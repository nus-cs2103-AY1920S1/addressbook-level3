package com.dukeacademy.storage.question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * An Immutable QuestionBank that is serializable to JSON format.
 */
@JsonRootName(value = "questionBank")
public class JsonSerializableStandardQuestionBank {

    private final List<JsonAdaptedQuestion> questions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuestionBank} with the given questions.
     *
     * @param questions the questions
     */
    @JsonCreator
    public JsonSerializableStandardQuestionBank(@JsonProperty("questions") List<JsonAdaptedQuestion> questions) {
        this.questions.addAll(questions);
    }

    /**
     * Creates a JsonSerializableQuestionBank from the given question bank as source.
     *
     * @param source the question bank to be used as the source.
     */
    public JsonSerializableStandardQuestionBank(QuestionBank source) {
        questions.addAll(source.getReadOnlyQuestionListObservable().stream()
                .map(JsonAdaptedQuestion::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this question bank into the model's {@code QuestionBank} object.
     *
     * @return the standard question bank
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StandardQuestionBank toModelType() throws IllegalValueException {
        StandardQuestionBank standardQuestionBank = new StandardQuestionBank();
        for (JsonAdaptedQuestion jsonAdaptedQuestion : questions) {
            Question question = jsonAdaptedQuestion.toModel();
            standardQuestionBank.addQuestion(question);
        }
        return standardQuestionBank;
    }
}
