package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.exceptions.DuplicateMemeException;
import seedu.weme.model.records.Records;
import seedu.weme.model.records.RecordsManager;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.StatsManager;
import seedu.weme.model.statistics.TagWithCount;
import seedu.weme.model.statistics.TagWithDislike;
import seedu.weme.model.statistics.TagWithLike;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Template;
import seedu.weme.testutil.MemeBuilder;

public class WemeTest extends ApplicationTest {

    private final Weme weme = new Weme();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), weme.getMemeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weme.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWeme_replacesData() {
        Weme newData = getTypicalWeme();
        weme.resetData(newData);
        assertEquals(newData, weme);
    }

    @Test
    public void resetData_withDuplicateMemes_throwsDuplicateMemeException() {
        // Two memes with the same identity fields
        Meme editedAlice = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER).withTags(VALID_TAG_JOKER).build();
        List<Meme> newMemes = Arrays.asList(JOKER, editedAlice);
        WemeStub newData = new WemeStub();
        newData.setMemes(newMemes);

        assertThrows(DuplicateMemeException.class, () -> weme.resetData(newData));
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weme.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInWeme_returnsFalse() {
        assertFalse(weme.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeInWeme_returnsTrue() {
        weme.addMeme(DOGE);
        assertTrue(weme.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeWithSameIdentityFieldsInWeme_returnsTrue() {
        weme.addMeme(JOKER);
        Meme editedBob = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER)
                .withTags(VALID_TAG_JOKER).build();
        assertTrue(weme.hasMeme(editedBob));
    }

    @Test
    public void getMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> weme.getMemeList().remove(0));
    }

    @Test
    public void equals() {
        assertTrue(weme.equals(weme));

        assertFalse(weme.equals("random string"));
    }

    /**
     * A stub ReadOnlyWeme whose memes list can violate interface constraints.
     */
    private static class WemeStub implements ReadOnlyWeme {
        private final ObservableList<Meme> memes = FXCollections.observableArrayList();
        private final ObservableList<Meme> stagedMemes = FXCollections.observableArrayList();
        private final ObservableList<Meme> importList = FXCollections.observableArrayList();
        private final ObservableList<Template> templates = FXCollections.observableArrayList();
        private ObservableValue<Meme> viewableMeme;
        private final Stats stats = new StatsManager();
        private final Records records = new RecordsManager();
        private final MemeCreation memeCreation = new MemeCreation();

        WemeStub() {
        }

        void setMemes(List<Meme> memes) {
            this.memes.setAll(memes);
        }

        @Override
        public Stats getStats() {
            return stats;
        }

        @Override
        public ObservableList<Meme> getMemeList() {
            return memes;
        }

        @Override
        public ObservableList<Meme> getStagedMemeList() {
            return stagedMemes;
        }

        @Override
        public ObservableList<Meme> getImportList() {
            return importList;
        }

        public ObservableList<Template> getTemplateList() {
            return templates;
        }

        @Override
        public ObservableValue<Meme> getViewableMeme() {
            return viewableMeme;
        }

        @Override
        public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
            return stats.getObservableLikeData();
        }

        @Override
        public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
            return stats.getObservableDislikeData();
        }

        @Override
        public int getCountOfTag(Tag tag) {
            return stats.getCountOfTag(getMemeList(), tag);
        }

        @Override
        public List<TagWithCount> getTagsWithCountList() {
            return stats.getTagsWithCountList(memes);
        }

        @Override
        public List<TagWithLike> getTagsWithLikeCountList() {
            return stats.getTagsWithLikeCountList(memes);
        }

        @Override
        public List<TagWithDislike> getTagsWithDislikeCountList() {
            return stats.getTagsWithDislikeCountList(memes);
        }

        @Override
        public Records getRecords() {
            return records;
        }

        @Override
        public MemeCreation getMemeCreation() {
            return memeCreation;
        }

        @Override
        public ObservableList<MemeText> getMemeTextList() {
            return memeCreation.getMemeTextList();
        }

        @Override
        public Optional<BufferedImage> getMemeCreationImage() {
            return memeCreation.getCurrentImage();
        }

    }

}
