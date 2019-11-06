package seedu.sugarmummy.logic.commands.aesthetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand.MESSAGE_CURRENT_FONT_COLOUR;
import static seedu.sugarmummy.model.aesthetics.AestheticsModelStub.VALID_BACKGROUND;
import static seedu.sugarmummy.model.aesthetics.AestheticsModelStub.VALID_FONT_COLOUR;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.aesthetics.AestheticsModelStub;
import seedu.sugarmummy.model.aesthetics.AestheticsModelStub.ModelStubForBackground;
import seedu.sugarmummy.model.aesthetics.AestheticsModelStub.ModelStubForFontColour;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.ui.DisplayPaneType;

class FontColourCommandTest {

    @Test
    public void executeFontColourCommandNoAttributes_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new FontColourCommand()).execute(null));
    }

    @Test
    public void executeFontColourCommandValidFontColour_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new FontColourCommand(VALID_FONT_COLOUR)).execute(null));
    }

    @Test
    public void executeFontColourCommandValidFontAndFontColour_nullModel_throwsNullPointerException() {
        BackgroundCommand backgroundCommand = new BackgroundCommand(VALID_BACKGROUND);
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new FontColourCommand(VALID_FONT_COLOUR, backgroundCommand)).execute(null));
    }

    @Test
    public void executeFontColourCommand_noAttributes() {
        Colour previousFontColour = (new AestheticsModelStub.ModelStubForFontColour()).getFontColour();
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_CURRENT_FONT_COLOUR
                + previousFontColour.toString(), false, false);
        assertCommandSuccess(new FontColourCommand(), new ModelStubForFontColour(), expectedCommandResult,
                new ModelStubForFontColour());
    }

    @Test
    public void executeFontColourCommand_coloursTooClose_throwsCommandException() {
        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        Colour colourToSet = new Colour("black");
        assertThrows(CommandException.class,
                FontColourCommand.MESSAGE_COLOURS_TOO_CLOSE, () -> new FontColourCommand(colourToSet)
                        .execute(new ModelStubForFontColour()));
    }

    @Test
    public void executeFontColourCommand_noChange_withoutBackground() {
        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        Colour fontColour = modelStubForFontColour.getFontColour();
        assertThrows(CommandException.class, FontColourCommand
                .MESSAGE_NO_CHANGE, () -> new FontColourCommand(fontColour)
                .execute(modelStubForFontColour));
    }

    @Test
    public void executeFontColourCommand_noChange_withBackground() {
        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        Colour fontColour = modelStubForFontColour.getFontColour();
        BackgroundCommand backgroundCommand = new BackgroundCommand(modelStubForBackground.getBackground());
        assertThrows(CommandException.class, FontColourCommand
                .MESSAGE_NO_CHANGE, () -> new FontColourCommand(fontColour,
                backgroundCommand).execute(modelStubForFontColour));
    }

    @Test
    public void executeFontColourCommandSuccessWithoutBackground() {
        ModelStubForFontColour model = new ModelStubForFontColour();
        ModelStubForFontColour expectedModel = new ModelStubForFontColour();
        Colour fontColour = new Colour("yellow");
        String updateMessage = "Colour has been changed from " + model.getFontColour() + " to yellow.";

        CommandResult expectedCommandResult = new CommandResult(FontColourCommand.MESSAGE_SUCCESS
                + "\n" + updateMessage);
        expectedModel.setFontColour(fontColour);

        assertCommandSuccess(new FontColourCommand(fontColour), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeFontColourCommandSuccessWithBackground() throws CommandException {
        ModelStubForFontColour model = new ModelStubForFontColour();
        ModelStubForFontColour expectedModel = new ModelStubForFontColour();
        Colour fontColour = new Colour("black");

        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();

        Background background = new Background("white");
        background.setBgRepeat("");
        background.setBgSize("");
        background.setDominantColour();

        BackgroundCommand backgroundCommand = new BackgroundCommand(background);
        expectedModel.setBackground(background);
        expectedModel.setFontColour(fontColour);

        String updateMessage = "Colour has been changed from " + model.getFontColour() + " to black.\n";
        updateMessage += BackgroundCommand.MESSAGE_SUCCESS + "\n- Background has been changed from "
                + modelStubForBackground.getBackground() + " to white.";

        CommandResult expectedCommandResult =
                new CommandResult(FontColourCommand.MESSAGE_SUCCESS + "\n" + updateMessage);

        assertCommandSuccess(new FontColourCommand(fontColour, backgroundCommand), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void executeFontColourCommandSuccessWithBackgroundUnchanged() throws CommandException {
        ModelStubForFontColour model = new ModelStubForFontColour();
        ModelStubForFontColour expectedModel = new ModelStubForFontColour();
        Colour fontColour = new Colour("yellow");

        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        BackgroundCommand backgroundCommand = new BackgroundCommand(modelStubForBackground.getBackground());

        String updateMessage = "Colour has been changed from " + model.getFontColour() + " to yellow.";
        updateMessage += "\n" + "Background: " + BackgroundCommand.MESSAGE_NO_CHANGE;

        CommandResult expectedCommandResult = new CommandResult(FontColourCommand.MESSAGE_SUCCESS
                + "\n" + updateMessage);
        expectedModel.setFontColour(fontColour);

        assertCommandSuccess(new FontColourCommand(fontColour, backgroundCommand), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void getFontColour_noAttributes() {
        assertNull(new FontColourCommand().getFontColour());
    }

    @Test
    public void getFontColour_validFontColour() {
        assertEquals(VALID_FONT_COLOUR, new FontColourCommand(VALID_FONT_COLOUR).getFontColour());
    }

    @Test
    public void getFontColour_validBackgroundAndFontColour() {
        BackgroundCommand backgroundCommand = new BackgroundCommand(VALID_BACKGROUND);
        assertEquals(VALID_FONT_COLOUR, new FontColourCommand(VALID_FONT_COLOUR, backgroundCommand).getFontColour());
    }

    @Test
    public void getDisplayPaneType_noAttributes() {
        assertEquals(DisplayPaneType.COLOUR, new FontColourCommand().getDisplayPaneType());
    }

    @Test
    public void getDisplayPaneType_validFontColour() {
        assertEquals(DisplayPaneType.COLOUR, new FontColourCommand(VALID_FONT_COLOUR).getDisplayPaneType());
    }

    @Test
    public void getDisplayPaneType_validFontAndFontColour() {
        BackgroundCommand backgroundCommand = new BackgroundCommand(VALID_BACKGROUND);
        FontColourCommand fontColourCommand = new FontColourCommand(VALID_FONT_COLOUR, backgroundCommand);
        assertEquals(DisplayPaneType.COLOUR_AND_BACKGROUND, fontColourCommand.getDisplayPaneType());
    }

}
