package seedu.sugarmummy.logic.commands.aesthetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand.MESSAGE_COLOURS_TOO_CLOSE;
import static seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand.MESSAGE_CURRENT_BACKGROUND;
import static seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand.MESSAGE_NO_CHANGE;
import static seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand.MESSAGE_SUCCESS;
import static seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand.MESSAGE_USAGE;
import static seedu.sugarmummy.model.aesthetics.AestheticsModelStub.VALID_BACKGROUND;
import static seedu.sugarmummy.model.aesthetics.AestheticsModelStub.VALID_FONT_COLOUR;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.aesthetics.AestheticsModelStub.ModelStubForBackground;
import seedu.sugarmummy.model.aesthetics.AestheticsModelStub.ModelStubForFontColour;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.ui.DisplayPaneType;

class BackgroundCommandTest {

    @Test
    public void executeBackgroundCommandNoAttributes_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new BackgroundCommand()).execute(null));
    }

    @Test
    public void executeBackgroundCommandValidBackground_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new BackgroundCommand(VALID_BACKGROUND)).execute(null));
    }

    @Test
    public void executeBackgroundCommandValidFontAndBackground_nullModel_throwsNullPointerException() {
        FontColourCommand fontColourCommand = new FontColourCommand(VALID_FONT_COLOUR);
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new BackgroundCommand(VALID_BACKGROUND, fontColourCommand)).execute(null));
    }

    @Test
    public void executeBackgroundCommand_noAttributes() {
        Background previousBackground = (new ModelStubForBackground()).getBackground();
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_CURRENT_BACKGROUND
                + previousBackground.toString(), false, false);
        assertCommandSuccess(new BackgroundCommand(), new ModelStubForBackground(), expectedCommandResult,
                new ModelStubForBackground());
    }

    @Test
    public void executeBackgroundCommand_coloursTooClose_throwsCommandException() {
        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        Colour colourToSet = modelStubForFontColour.getFontColour();
        Background background = new Background(colourToSet.toString());
        background.setDominantColour();
        assertThrows(CommandException.class,
                MESSAGE_COLOURS_TOO_CLOSE, () -> new BackgroundCommand(background)
                        .execute(new ModelStubForBackground()));
    }

    @Test
    public void executeBackgroundCommand_backgroundColourWithBgSize_throwsCommandException() {
        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        Background background = modelStubForBackground.getBackground();
        background.setBgSize("auto");
        assertThrows(CommandException.class, String.format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                MESSAGE_USAGE), () -> new BackgroundCommand(background).execute(modelStubForBackground));
    }

    @Test
    public void executeBackgroundCommand_backgroundColourWithBgRepeat_throwsCommandException() {
        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        Background background = modelStubForBackground.getBackground();
        background.setBgRepeat("no-repeat");
        assertThrows(CommandException.class, String.format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                MESSAGE_USAGE), () -> new BackgroundCommand(background).execute(modelStubForBackground));
    }

    @Test
    public void executeBackgroundCommand_noChange_withoutFontColour() {
        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        Background background = modelStubForBackground.getBackground();
        assertThrows(CommandException.class, MESSAGE_NO_CHANGE, () -> new BackgroundCommand(background)
                .execute(modelStubForBackground));
    }

    @Test
    public void executeBackgroundCommand_noChange_withFontColour() {
        ModelStubForBackground modelStubForBackground = new ModelStubForBackground();
        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        Background background = modelStubForBackground.getBackground();
        FontColourCommand fontColourCommand = new FontColourCommand(modelStubForFontColour.getFontColour());
        assertThrows(CommandException.class, MESSAGE_NO_CHANGE, () -> new BackgroundCommand(background,
                fontColourCommand).execute(modelStubForBackground));
    }

    @Test
    public void executeBackgroundCommandSuccessWithoutFont() {
        ModelStubForBackground model = new ModelStubForBackground();
        ModelStubForBackground expectedModel = new ModelStubForBackground();
        Background background = new Background("darkblue");

        background.setBgRepeat("");
        background.setBgSize("");
        background.setDominantColour();

        String updateMessage = "- Background has been changed from " + model.getBackground() + " to darkblue.";

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS + "\n" + updateMessage);
        expectedModel.setBackground(background);

        assertCommandSuccess(new BackgroundCommand(background), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeBackgroundCommandSuccessWithFont() throws CommandException {
        ModelStubForBackground model = new ModelStubForBackground();
        ModelStubForBackground expectedModel = new ModelStubForBackground();
        Background background = new Background("black");

        background.setBgRepeat("");
        background.setBgSize("");
        background.setDominantColour();

        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        FontColourCommand fontColourCommand = new FontColourCommand(new Colour("white"));
        expectedModel.setBackground(background);
        expectedModel.setFontColour(new Colour("white"));

        String updateMessage = "- Background has been changed from " + model.getBackground() + " to black.\n";
        updateMessage += FontColourCommand.MESSAGE_SUCCESS + "\nColour has been changed from "
                + modelStubForFontColour.getFontColour() + " to white.";

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS + "\n" + updateMessage);

        assertCommandSuccess(new BackgroundCommand(background, fontColourCommand), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void executeBackgroundCommandSuccessWithFontUnchanged() throws CommandException {
        ModelStubForBackground model = new ModelStubForBackground();
        ModelStubForBackground expectedModel = new ModelStubForBackground();
        Background background = new Background("black");

        background.setBgRepeat("");
        background.setBgSize("");
        background.setDominantColour();

        ModelStubForFontColour modelStubForFontColour = new ModelStubForFontColour();
        FontColourCommand fontColourCommand = new FontColourCommand(modelStubForFontColour.getFontColour());

        String updateMessage = "- Background has been changed from " + model.getBackground() + " to black.";
        updateMessage += "\n" + "Font Colour: " + FontColourCommand.MESSAGE_NO_CHANGE;

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS + "\n" + updateMessage);
        expectedModel.setBackground(background);

        assertCommandSuccess(new BackgroundCommand(background, fontColourCommand), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void getBackground_noAttributes() {
        assertNull(new BackgroundCommand().getBackground());
    }

    @Test
    public void getBackground_validBackground() {
        assertEquals(VALID_BACKGROUND, new BackgroundCommand(VALID_BACKGROUND).getBackground());
    }

    @Test
    public void getBackground_validFontAndBackground() {
        FontColourCommand fontColourCommand = new FontColourCommand(VALID_FONT_COLOUR);
        assertEquals(VALID_BACKGROUND, new BackgroundCommand(VALID_BACKGROUND, fontColourCommand).getBackground());
    }

    @Test
    public void getDisplayPaneType_noAttributes() {
        assertEquals(DisplayPaneType.BACKGROUND, new BackgroundCommand().getDisplayPaneType());
    }

    @Test
    public void getDisplayPaneType_validBackground() {
        assertEquals(DisplayPaneType.BACKGROUND, new BackgroundCommand(VALID_BACKGROUND).getDisplayPaneType());
    }

    @Test
    public void getDisplayPaneType_validFontAndBackground() {
        FontColourCommand fontColourCommand = new FontColourCommand(VALID_FONT_COLOUR);
        BackgroundCommand backgroundCommand = new BackgroundCommand(VALID_BACKGROUND, fontColourCommand);
        assertEquals(DisplayPaneType.COLOUR_AND_BACKGROUND, backgroundCommand.getDisplayPaneType());
    }

}
