package com.dukeacademy.model;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.ALICE;
import static com.dukeacademy.testutil.TypicalQuestions.getTypicalQuestionBank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.exceptions.DuplicateQuestionException;
import com.dukeacademy.testutil.QuestionBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StandardQuestionBankTest {

    private final StandardQuestionBank standardQuestionBank = new StandardQuestionBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), standardQuestionBank.getReadOnlyQuestionListObservable());
        
    }


    @Test
    void getReadOnlyQuestionListObservable() {
    }

    @Test
    void addQuestion() {
    }

    @Test
    void setQuestions() {
    }

    @Test
    void replaceQuestion() {
    }

    @Test
    void removeQuestion() {
    }

    @Test
    void resetQuestions() {
    }

    private boolean matchListData(ObservableList<Question> observableList, List<Question> questionList) {
        if (observableList.size() != questionList.size()) {
            return false;
        }

        return IntStream.range(0, observableList.size())
                .mapToObj(i -> observableList.get(i).equals(questionList.get(i)))
                .reduce((x, y) -> x && y).orElseThrow();
    }
}
