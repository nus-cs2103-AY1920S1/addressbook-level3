package tagline.model.tag;

import java.util.Optional;
import java.util.OptionalLong;

/**
 * A singleton to help in allocating {@link TagId} for every {@link Tag}.
 */
public class TagIdGenerator {

    public static final TagIdGenerator SHARED = new TagIdGenerator();

    private TagBook tagBook;

    private TagIdGenerator() {
        tagBook = new TagBook();
    }

    TagId getTagId(TagType tagType, String content) {
        Optional<TagId> optionalTagId = tagBook.findTagId(tagType, content);
        if (optionalTagId.isPresent()) {
            return optionalTagId.get();
        } else {
            TagId newTagId = generateUniqueTagId();
            tagBook.addTag(newTagId, tagType, content);
            return newTagId;
        }
    }

    /**
     * Generates a unique {@code TagId} based on the stored {@code TagBook}.
     *
     * @return A new unique {@code TagId}.
     */
    private TagId generateUniqueTagId() {
        OptionalLong optionalLong = tagBook.findMaxValueOfTagId();
        if (optionalLong.isPresent()) {
            return new TagId(optionalLong.getAsLong() + 1);
        } else {
            return new TagId((long) 0);
        }
    }
}
