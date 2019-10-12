package com.dukeacademy.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dukeacademy.model.Model;
import com.dukeacademy.model.ModelManager;
import com.dukeacademy.model.UserPrefs;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.testutil.QuestionBuilder;
import com.dukeacademy.testutil.TypicalQuestions;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());
    }

    @Test
    public void execute_newQuestion_success() {
        Question validQuestion = new QuestionBuilder().build();

        Model expectedModel = new ModelManager(model.getQuestionBank(), new UserPrefs());
        expectedModel.addQuestion(validQuestion);

        CommandTestUtil.assertCommandSuccess(new AddCommand(validQuestion), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validQuestion), expectedModel);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question questionInList = model.getQuestionBank().getQuestionList().get(0);
        CommandTestUtil.assertCommandFailure(new AddCommand(questionInList), model,
            AddCommand.MESSAGE_DUPLICATE_QUESTION);
    }

}
