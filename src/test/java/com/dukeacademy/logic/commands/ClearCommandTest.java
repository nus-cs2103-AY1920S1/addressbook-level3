package com.dukeacademy.logic.commands;

import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import com.dukeacademy.model.Model;
import com.dukeacademy.model.ModelManager;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.UserPrefs;
import com.dukeacademy.testutil.TypicalQuestions;

public class ClearCommandTest {

    @Test
    public void execute_emptyQuestionBank_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyQuestionBank_success() {
        Model model = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());
        expectedModel.setQuestionBank(new QuestionBank());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
