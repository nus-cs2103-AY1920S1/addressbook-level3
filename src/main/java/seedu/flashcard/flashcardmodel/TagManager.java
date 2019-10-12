package seedu.flashcard.flashcardmodel;

import java.util.ArrayList;

import seedu.flashcard.flashcardmodel.exceptions.DuplicateTagException;
import seedu.flashcard.flashcardmodel.exceptions.TagNotFoundException;

/**
 *  A class for managing flashcardmodel tagged sets.
 */
public class TagManager {

    private ArrayList<Tag> allTags;

    public TagManager() {
        allTags = new ArrayList<>();
    }

    /**
     * Identify all the relevant tags whose names contain the keyword.
     * @param keyword the user input keyword for searching.
     * @return a list of all relevant tags.
     */
    public ArrayList<Tag> findTag(String keyword) {
        ArrayList<Tag> resultList = new ArrayList<>();
        for (Tag item : allTags) {
            if (item.contains(keyword)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    /**
     * Based on the EXACT tag name, decide if there is a tag with this name inside the tag manager
     * @param s the EXACT tag name we are looking for
     * @return true if such a tag with this name exists, false otherwise
     */
    public boolean hasTag(String s) {
        for (Tag item : allTags) {
            if (item.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Based on the EXACT tag name given, return back the corresponding tag
     * @param s the EXACT tag name we are looking for
     * @return the tag with this name
     * @throws TagNotFoundException if such a tag does not exist
     */
    public Tag getTag(String s) throws TagNotFoundException {
        for (Tag item : allTags) {
            if (item.getName().equals(s)) {
                return item;
            }
        }
        throw new TagNotFoundException();
    }

    /**
     * list out all tags
     * @return a list of all the tags.
     */
    public ArrayList<Tag> getAllTags() {
        return allTags;
    }

    /**
     * edit the name of a tag to a new name
     * @param tagName the original tag name
     * @param newName the new tag name
     * @throws TagNotFoundException if the tag with this name does not exist
     */
    public void setTagName(String tagName, String newName) throws TagNotFoundException {
        Tag editTag = getTag(tagName);
        editTag.setName(newName);
    }

    /**
     * add a new tag to the current tag list
     * @param name name of the new tag
     * @throws DuplicateTagException if this tag name already exists
     */
    public void addTag(String name) throws DuplicateTagException {
        assert (!hasTag(name)) : "Flashcard list failed to check for duplicate tags.";
        allTags.add(new Tag(name));
    }

    /**
     * Remove a specific tag from the current tag list.
     * @param name the name of tag that is to be deleted.
     */
    public void deleteTag(String name) {
        Tag targetTag = getTag(name);
        targetTag.detachFromAllCards();
        allTags.remove(targetTag);
    }

    /**
     * Check whether there exists a tag with the given name.
     * @param name the name of the tag to be searched.
     * @return a boolean variable that informs either true or false
     */
    public boolean contains(String name) {
        return allTags.contains(new Tag(name));
    }
}
