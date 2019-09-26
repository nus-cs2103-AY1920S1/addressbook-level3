package seedu.address.flashcard;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *  A class for managing flashcard tagged sets.
 */
public class TagManager {

    private HashSet<Tag> allTags;

    /**
     * Constructor for TagManager.
     *
     * @param allTags a list of all the tagged sets.
     */
    public TagManager(HashSet<Tag> allTags) {
        this.allTags = allTags;
    }

    /**
     * Identify all the relevant tags whose names contain the keyword.
     *
     * @param keyword the user input keyword for searching.
     * @return a list of all relevant tags.
     */
    public HashSet<Tag> findTag(String keyword) {
        HashSet<Tag> resultList = new HashSet<>();
        for (Tag item : allTags) {
            if (item.getName().contains(keyword)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    /**
     * Give all the tags currently available.
     *
     * @return a list of all the tags.
     */
    public HashSet<Tag> getTags() {
        return allTags;
    }

    /**
     * Edit the name of a tag to a new name.
     *
     * @param tagName the original tag name.
     * @param newName the new tag name.
     */
    public void setTagName(String tagName, String newName) {


        Tag cur = null;
        for (Tag item : allTags) {
            if (item.getName().equals(tagName)) {
                cur = item;
            }
        }
        cur.setName(newName);
    }

    /**
     * Add a new tag to the current tag list.
     *
     * @param name name of the new tag.
     */
    public void addTag(String name) {
        allTags.add(new Tag(name));
    }

    /**
     * Remove a specific tag from the current tag list.
     *
     * @param name the name of tag that is to be deleted.
     */
    public void deleteTag(String name) {
        allTags.remove(new Tag(name));
    }

    /**
     * Check whether there exists a tag with the given name.
     *
     * @param name the name of the tag to be searched.
     * @return a boolean variable that informs either true or false
     */
    public boolean contains(String name) {
        return allTags.contains(new Tag(name));
    }
}
