package seedu.weme.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;

import org.junit.jupiter.api.Test;

import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.WemeBuilder;

class TagManagerTest {
    private TagManager tagManager = new TagManager();

    @Test
    public void testCountOfTag() {
        Tag tag = new Tag(VALID_TAG_JOKER);
        Meme memeWithOneTag = new MemeBuilder()
                .withFilePath(VALID_FILEPATH_JOKER)
                .withDescription(VALID_DESCRIPTION_JOKER)
                .withTags(VALID_TAG_JOKER).build();
        Weme weme = new WemeBuilder()
                .withMeme(memeWithOneTag)
                .build();

        assertEquals(tagManager.getCountOfTag(weme.getMemeList(), tag), 1);
    }

    @Test
    public void testGetTagsWithLike() {

    }
}
