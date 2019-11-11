package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.MemeUtil.isSameMemeImage;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Template;
import seedu.weme.model.template.exceptions.MemeCreationException;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.TestUtil;
import seedu.weme.testutil.UserPrefsBuilder;

public class MemeAddCommandTest extends ApplicationTest {

    @BeforeEach
    public void setUp() {
        TestUtil.clearSandBoxFolder();
    }

    @Test
    public void constructor_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MemeAddCommand(null));
    }

    @Test
    public void execute_memeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemeAdded modelStub = new ModelStubAcceptingMemeAdded();
        Meme validMeme = new MemeBuilder().build();

        CommandResult commandResult = new MemeAddCommand(validMeme).execute(modelStub);

        assertEquals(String.format(MemeAddCommand.MESSAGE_SUCCESS, validMeme), commandResult.getFeedbackToUser());
        assertTrue(isSameMemeImage(validMeme, modelStub.memesAdded.get(modelStub.memesAdded.size() - 1)));
    }

    @Test
    public void equals() {
        Meme meme1 = new MemeBuilder().withFilePath("src/test/data/memes/charmander_meme.jpg").build();
        Meme meme2 = new MemeBuilder().withFilePath("src/test/data/memes/joker_meme.jpg").build();
        MemeAddCommand addMeme1Command = new MemeAddCommand(meme1);
        MemeAddCommand addMeme2Command = new MemeAddCommand(meme2);

        // same object -> returns true
        assertTrue(addMeme1Command.equals(addMeme1Command));

        // same values -> returns true
        MemeAddCommand addAliceCommandCopy = new MemeAddCommand(meme1);
        assertTrue(addMeme1Command.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addMeme1Command.equals(1));

        // null -> returns false
        assertFalse(addMeme1Command.equals(null));

        // different meme -> returns false
        assertFalse(addMeme1Command.equals(addMeme2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void importMemes() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public List<Path> getExportPathList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Path getDefaultExportPath() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public SimpleObjectProperty<Meme> getViewableMeme() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setViewableMeme(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isMemeStaged(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isStagingAreaEmpty() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearExportList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearImportList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setImportedMeme(Meme target, Meme editedMeme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteImportedMeme(Meme target) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableMap<String, String> getObservableUserPreferences() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void loadMemes(List<Path> pathList) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDataFolderPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDataFolderPath(Path dataFolderPath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDataFilePath() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Path getMemeImagePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTemplateImagePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWeme getWeme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWeme(ReadOnlyWeme newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearMemes() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeme(Meme target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stageMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unstageMeme(Meme meme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeme(Meme target, Meme editedMeme) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTemplate(Template template) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTemplate(Template template) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearTemplates() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTemplate(Template template) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTemplate(Template target, Template editedTemplate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meme> getFilteredMemeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meme> getStagedMemeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meme> getImportList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMemeList(Predicate<Meme> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Template> getFilteredTemplateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTemplateList(Predicate<Template> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableValue<ModelContext> getContext() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContext(ModelContext context) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void startMemeCreation(Template template) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void abortMemeCreation() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MemeText> getMemeTextList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMemeText(MemeText text) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMemeText(MemeText toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMemeText(MemeText toReplace, MemeText replacement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<BufferedImage> getMemeCreationImage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createMeme(Path path) throws MemeCreationException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoWeme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoWeme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String undoWeme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String redoWeme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitWeme(String feedback) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cleanMemeStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cleanTemplateStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void incrementMemeLikeCount(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void decrementMemeLikeCount(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void incrementMemeDislikeCount(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void decrementMemeDislikeCount(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public int getCountOfTag(Tag tag) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public List<TagWithCount> getTagsWithCountList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearMemeStats(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Set<String> getPathRecords() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Set<String> getDescriptionRecords() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Set<String> getTagRecords() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Set<String> getNameRecords() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Set<String> getTextRecords() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addMemeToRecords(Meme meme) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addTemplateToRecords(Template template) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addMemeTextToRecords(MemeText memeText) {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single meme.
     */
    private class ModelStubWithMeme extends ModelStub {
        private final Meme meme;

        ModelStubWithMeme(Meme meme) {
            requireNonNull(meme);
            this.meme = meme;
        }

        @Override
        public boolean hasMeme(Meme meme) {
            requireNonNull(meme);
            return this.meme.isSameMeme(meme);
        }

        @Override
        public Path getMemeImagePath() {
            return UserPrefsBuilder.DEFAULT_DATA_FOLDER_PATH.resolve("memes");
        }

    }

    /**
     * A Model stub that always accept the meme being added.
     */
    private class ModelStubAcceptingMemeAdded extends ModelStub {
        final ArrayList<Meme> memesAdded = new ArrayList<>();

        @Override
        public boolean hasMeme(Meme meme) {
            requireNonNull(meme);
            return memesAdded.stream().anyMatch(meme::isSameMeme);
        }

        @Override
        public void addMeme(Meme meme) {
            requireNonNull(meme);
            memesAdded.add(meme);
        }

        @Override
        public void commitWeme(String feedback) {
            // called by {@code MemeAddCommand#execute()}
        }

        public Path getMemeImagePath() {
            return UserPrefsBuilder.DEFAULT_DATA_FOLDER_PATH.resolve("memes");
        }

        @Override
        public ReadOnlyWeme getWeme() {
            return new Weme();
        }

        @Override
        public void addMemeToRecords(Meme meme) {
        }

        @Override
        public void addTemplateToRecords(Template template) {
        }

        @Override
        public void addMemeTextToRecords(MemeText memeText) {
        }

    }

}
