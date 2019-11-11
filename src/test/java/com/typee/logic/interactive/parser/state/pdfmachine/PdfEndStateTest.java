package com.typee.logic.interactive.parser.state.pdfmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.AddCommand;
import com.typee.logic.commands.PdfCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;
import com.typee.testutil.PersonBuilder;

class PdfEndStateTest {
    private PdfEndState typicalPdfEndState;

    @BeforeEach
    public void setUp() {
        State pdfEndState = new PdfEndState(new ArgumentMultimap());
        typicalPdfEndState = (PdfEndState) pdfEndState;
    }

    @Test
    public void buildCommand() {
        try {
            EngagementBuilder engagementBuilder = new EngagementBuilder();
            Engagement engagement = engagementBuilder.buildAsMeeting();
            new AddCommand(engagement);

            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
            argumentMultimap.put(CliSyntax.PREFIX_FROM, "Ko Gi Hun");
            argumentMultimap.put(CliSyntax.PREFIX_TO, "Jenny Ko");
            typicalPdfEndState.transition(argumentMultimap);

            assertEquals(typicalPdfEndState.buildCommand(), new PdfCommand(1,
                    new PersonBuilder().withName("Ko Gi Hun").build(),
                    new PersonBuilder().withName("Ko Na Yeon").build()));
        } catch (CommandException | StateTransitionException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalPdfEndState.getStateConstraints(), "Generating PDF.");
    }

    @Test
    public void isEndState() {
        assertTrue(typicalPdfEndState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalPdfEndState.getPrefix(), new Prefix(""));
    }
}
