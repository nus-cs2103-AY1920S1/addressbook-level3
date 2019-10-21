package seedu.weme.model.util;

import static seedu.weme.commons.util.FileUtil.MESSAGE_READ_FILE_FAILURE;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Contains utility methods for populating {@code MemeBook} with sample data.
 */
public class SampleDataUtil {
    public static Meme[] getSampleMemes(ReadOnlyUserPrefs userPrefs) {
        // array of sample memes from resources folder
        MemeFieldsContainer[] memeFields = new MemeFieldsContainer[]{
            new MemeFieldsContainer("memes/5642dc30-927c-4e02-805d-831ea16bc68e.png",
                    "A meme about doge.", "doge"), // doge
            new MemeFieldsContainer("memes/74b9fc9f-a545-4bbc-98d5-09596a9166a9.jpg",
                    "A meme about Char and charmander.", "charmander"), // charmander
            new MemeFieldsContainer("memes/8de6b9f5-32a5-4eab-aebe-f47c2257e7d5.png",
                    "A meme about joker.", "joker"), // joker
            new MemeFieldsContainer("memes/ab6e1ed6-6025-4e84-b5da-8555ef1e0b05.png",
                    "A meme about toy.", "toy", "jokes"), // toy
            new MemeFieldsContainer("memes/b3afd215-8746-4113-aa19-1747d3578f41.jpg",
                    "A meme about a test.", "test") // test
        };
        return createSampleMemes(memeFields, userPrefs);
    }

    /**
     * Copies meme images from Resource folder to the Data folder.
     * @param memeFields the data for the memes in the resource folder
     * @param userPrefs the user preferences for this instance of weme
     * @return an array of Memes to import
     */
    public static Meme[] createSampleMemes(MemeFieldsContainer[] memeFields, ReadOnlyUserPrefs userPrefs) {
        ClassLoader classLoader = SampleDataUtil.class.getClassLoader();
        Meme[] copiedMemes = new Meme[memeFields.length];

        for (int i = 0; i < copiedMemes.length; i++) {
            String path = memeFields[i].getImagePath();
            Path newPath = userPrefs.getMemeImagePath().resolve(FileUtil.getFileName(path));
            try {
                FileUtil.copy(classLoader.getResourceAsStream(path), newPath);
            } catch (FileAlreadyExistsException e) {
                // let the file pass
            } catch (IOException e) {
                throw new IllegalArgumentException(MESSAGE_READ_FILE_FAILURE);
            }
            copiedMemes[i] = new Meme(new ImagePath(newPath.toString()),
                    new Description(memeFields[i].getDescription()), getTagSet(memeFields[i].getTags()));
        }
        return copiedMemes;
    }


    public static ReadOnlyMemeBook getSampleMemeBook(ReadOnlyUserPrefs userPrefs) {
        MemeBook sampleMb = new MemeBook();
        for (Meme sampleMeme : getSampleMemes(userPrefs)) {
            sampleMb.addMeme(sampleMeme);
        }
        return sampleMb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Container class for sample meme data
     */
    private static class MemeFieldsContainer {
        private String imagePath;
        private String description;
        private String[] tags;

        public MemeFieldsContainer(String imagePath, String description, String... tags) {
            this.imagePath = imagePath;
            this.description = description;
            this.tags = tags;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getDescription() {
            return description;
        }

        public String[] getTags() {
            return tags;
        }
    }
}
