package seedu.weme.model.util;

import static seedu.weme.commons.util.FileUtil.MESSAGE_READ_FILE_FAILURE;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.Weme;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.records.Records;
import seedu.weme.model.records.RecordsManager;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Contains utility methods for populating {@code Weme} with sample data.
 */
public class SampleDataUtil {
    public static List<Meme> getSampleMemes(ReadOnlyUserPrefs userPrefs) {
        // sample memes from resources folder
        List<MemeFieldsContainer> memeFields = List.of(
                new MemeFieldsContainer("memes/5642dc30-927c-4e02-805d-831ea16bc68e.png",
                        "", "doge", "cute"), // doge
                new MemeFieldsContainer("memes/74b9fc9f-a545-4bbc-98d5-09596a9166a9.jpg",
                        "char char?", "charmander", "cute"), // charmander
                new MemeFieldsContainer("memes/8de6b9f5-32a5-4eab-aebe-f47c2257e7d5.png",
                        "Sites in a nutshell", "joker"), // joker
                new MemeFieldsContainer("memes/ab6e1ed6-6025-4e84-b5da-8555ef1e0b05.png",
                        "Our app rn", "toy", "funny"), // toy
                new MemeFieldsContainer("memes/b3afd215-8746-4113-aa19-1747d3578f41.jpg",
                        "Pon PE sua", "test", "funny"), // test
                new MemeFieldsContainer("memes/2ce63128-e706-4892-87fd-4618cadd8d51.jpg",
                        "Not a true story", "programming", "programmerhumor"), // errors
                new MemeFieldsContainer("memes/3539d75b-d840-4ded-aed8-9d661b93d46c.jpg",
                        "Unsang heroes", "programming"), // stackoverflow
                new MemeFieldsContainer("memes/3f8b3924-4910-4171-a9f9-67ad9bfd84c8.jpg",
                        "Us rn", "hmm"), // jetbrains
                new MemeFieldsContainer("memes/b10b6ab4-52b7-44a3-a317-ca39449a0379.gif",
                        "Why hawaii happened", "reallifeconsequences"), // UI gif
                new MemeFieldsContainer("memes/7e438d2c-bab2-4ec9-bfa9-01ceea6b5b42.jpg",
                        "Field is a lie", "csgg"), // google everything!
                new MemeFieldsContainer("memes/781f6250-bf30-4d2b-87fd-01def759d342.jpg",
                        "Me dealing with javafx", "javafx"), // css
                new MemeFieldsContainer("memes/16ae22bc-97e8-48c9-b181-3f070e8c6253.jpg",
                        "tech debt at its finest"), // weird func
                new MemeFieldsContainer("memes/be28e3ba-26fc-4c13-988b-9404d21ef6b4.jpg",
                        "git commit; git push -f;"), // grind
                new MemeFieldsContainer("memes/62e5bd42-7916-4eb5-80f3-2671897b06f7.jpg",
                        "is this 2040", "personallyattacked") // compile & run
        );
        return createSampleMemes(memeFields, userPrefs);
    }

    public static List<Template> getSampleTemplates(ReadOnlyUserPrefs userPrefs) {
        // sample templates from resources folder
        List<TemplateFieldsContainer> templateFields = List.of(
                new TemplateFieldsContainer("Drake Reaction",
                        "templates/e2493713-6904-4530-98d1-eedc7fd88e5d.jpg"),
                new TemplateFieldsContainer("Is This",
                        "templates/0b4cc6ed-85b5-4ca0-a6b2-95ba5d29d06a.jpg"),
                new TemplateFieldsContainer("Quiz Kid",
                        "templates/51460170-ef3e-41ad-8243-d0890e838cff.jpg")
        );
        return createSampleTemplates(templateFields, userPrefs);
    }

    public static Records getSampleRecords() {
        Set<String> pathRecords = Stream.of(
                "/home/me/Pictures/weme.jpg",
                "/home/me/Pictures/cutie.jpg",
                "/home/me/Pictures/hey.jpg",
                "/home/me/Pictures/hey.png",
                "/home/me/Downloads/favorite_meme.jpg",
                "/home/me/CS/Y2S1/ProgrammingMemes/CS2103.jpg")
                .collect(Collectors.toSet());
        Set<String> descriptionRecords = Stream.of(
                "char char?",
                "Sites in a nutshell",
                "Our app rn",
                "Pon PE sua",
                "Not a true story",
                "Unsang heroes",
                "Us rn",
                "Why hawaii happened",
                "Field is a lie",
                "Me dealing with javafx",
                "tech debt at its finest",
                "git commit; git push -f;",
                "is this 2040",
                "OMG my favorite meme!!!",
                "T$T: head = null",
                "CS2103 is so fun!",
                "My first self-made meme",
                "My second self-made meme")
                .collect(Collectors.toSet());
        Set<String> tagRecords = Stream.of(
                "doge",
                "cute",
                "charmander",
                "joker",
                "toy",
                "funny",
                "test",
                "CS2103",
                "GoT",
                "CS",
                "CSLectures",
                "programming",
                "cuteAnimals",
                "CUTECATS",
                "best",
                "favorite",
                "weme",
                "programmerhumor",
                "personallyattacked",
                "javafx",
                "csgg",
                "reallifeconsequences",
                "hmm",
                "wow")
                .collect(Collectors.toSet());
        Set<String> nameRecords = Stream.of(
                "Drake Reaction",
                "Is This",
                "Quiz Kid",
                "disaster girl",
                "hey girl",
                "sponge bob",
                "LOLcats",
                "Double Rainbow",
                "Gangnam Style",
                "TheDress")
                .collect(Collectors.toSet());
        Set<String> textRecords = Stream.of(
                "Success starts with SU",
                "When you fix the problem in coding",
                "Would be fun they said",
                "Now...only God knows",
                "People who only know Python")
                .collect(Collectors.toSet());
        return new RecordsManager(pathRecords, descriptionRecords, tagRecords, nameRecords, textRecords);
    }

    /**
     * Copies meme images from Resource folder to the Data folder.
     *
     * @param memeFields the data for the memes in the resource folder
     * @param userPrefs  the user preferences for this instance of Weme
     * @return a List of Memes to import
     */
    public static List<Meme> createSampleMemes(List<MemeFieldsContainer> memeFields, ReadOnlyUserPrefs userPrefs) {
        ClassLoader classLoader = SampleDataUtil.class.getClassLoader();
        List<Meme> copiedMemes = new ArrayList<>();

        for (MemeFieldsContainer fields : memeFields) {
            String path = fields.getImagePath();
            Path newPath = userPrefs.getMemeImagePath().resolve(FileUtil.getFileName(path));
            try {
                FileUtil.copy(classLoader.getResourceAsStream(path), newPath);
            } catch (FileAlreadyExistsException e) {
                // let the file pass
            } catch (IOException e) {
                throw new IllegalArgumentException(MESSAGE_READ_FILE_FAILURE);
            }
            copiedMemes.add(new Meme(new ImagePath(newPath.toString()),
                    new Description(fields.getDescription()), getTagSet(fields.getTags())));
        }
        return copiedMemes;
    }

    /**
     * Copies template images from Resource folder to the Data folder.
     *
     * @param templateFields the data for the templates in the resource folder
     * @param userPrefs      the user preferences for this instance of Weme
     * @return a List of {@code Templates} to import
     */
    public static List<Template> createSampleTemplates(List<TemplateFieldsContainer> templateFields,
                                                       ReadOnlyUserPrefs userPrefs) {
        ClassLoader classLoader = SampleDataUtil.class.getClassLoader();
        List<Template> copiedTemplates = new ArrayList<>();

        for (TemplateFieldsContainer fields : templateFields) {
            String path = fields.getImagePath();
            Path newPath = userPrefs.getTemplateImagePath().resolve(FileUtil.getFileName(path));
            try {
                FileUtil.copy(classLoader.getResourceAsStream(path), newPath);
            } catch (FileAlreadyExistsException e) {
                // let the file pass
            } catch (IOException e) {
                throw new IllegalArgumentException(MESSAGE_READ_FILE_FAILURE);
            }
            copiedTemplates.add(new Template(new Name(fields.getName()), new ImagePath(newPath.toString())));
        }
        return copiedTemplates;
    }

    public static ReadOnlyWeme getSampleWeme(ReadOnlyUserPrefs userPrefs) {
        Weme sampleWeme = new Weme();
        for (Meme sampleMeme : getSampleMemes(userPrefs)) {
            sampleWeme.addMeme(sampleMeme);
        }
        for (Template sampleTemplate : getSampleTemplates(userPrefs)) {
            sampleWeme.addTemplate(sampleTemplate);
        }
        sampleWeme.setRecords(getSampleRecords());
        return sampleWeme;
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

    /**
     * Container class for sample template data
     */
    private static class TemplateFieldsContainer {
        private String name;
        private String imagePath;

        public TemplateFieldsContainer(String name, String imagePath) {
            this.name = name;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getImagePath() {
            return imagePath;
        }

    }
}
