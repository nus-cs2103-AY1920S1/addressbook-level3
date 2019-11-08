package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
    @Order(1)
    public void execute_valid() {
        assertCommandSuccess(new PdfCommand(1,
                new PersonBuilder().withName("Jason").build(),
                new PersonBuilder().withName("Gihun").build(),
                tempDir.toPath()), model, PdfCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
