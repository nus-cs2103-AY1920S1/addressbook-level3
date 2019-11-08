package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.testutil.PersonBuilder;

class PdfCommandTest {
    @TempDir
    public static File tempDir;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
        expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
    }

    @Test
    public void execute_valid() {
        assertCommandSuccess(new PdfCommand(1,
                new PersonBuilder().withName("Harry").build(),
                new PersonBuilder().withName("Jenny").build(),
                tempDir.toPath()), model, PdfCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalid_index() {
        assertCommandFailure(new PdfCommand(0,
                        new PersonBuilder().withName("Harry").build(),
                        new PersonBuilder().withName("Jenny").build(),
                        tempDir.toPath()),
                model,
                PdfCommand.MESSAGE_INDEX_INVALID);
    }
}
