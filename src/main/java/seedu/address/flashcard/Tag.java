package seedu.address.flashcard;

import java.util.ArrayList;

/**
 * A class that represents each individual tagged flashcard set
 */
public class Tag {
    private ArrayList<Flashcard> cardList;
    private String name;

    /**
     * Constructor for a tag object.
     *
     * @param _name the name of the tag to be initialized.
     */
    public Tag(String _name) {
        this.name = _name;
        cardList = new ArrayList<>();
    }

    /**
     * Give a list of all the flashcards under this tag.
     *
     * @return a list of all flashcards.
     */
    public ArrayList<Flashcard> getFlashcards() {
        ArrayList<Flashcard> resultList = new ArrayList<>();
        resultList.addAll(cardList);
        return resultList;
    }

    /**
     * Remove a particular flashcard from the tag based on the flashcard ID.
     *
     * @param id the ID of the flashcard to be removed.
     */
    public void deleteFlashcard(ID id) {
        ArrayList<Flashcard> newList = new ArrayList<>();
        newList.addAll(cardList);
        for (Flashcard item: newList) {
            if (item.getId() == id.getId()) { // there are two different getId() and ID stored as int
                newList.remove(item);
            }
        }
        this.cardList = newList;
    }

    /**
     * Add a new flashcard to the tag.
     *
     * @param c the flashcard to be added.
     */
    public void addFlashcard(Flashcard c) {
        ArrayList<Flashcard> newList = new ArrayList<>();
        newList.addAll(cardList);
        newList.add(c);
        this.cardList = newList;
    }

    /**
     * Inform the name of the tag.
     *
     * @return name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Edit the name of the tag to a new one.
     *
     * @param newName the new name of the tag.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Check whether the tag contains a particular flashcard.
     *
     * @param c the flashcard to be checked.
     * @return whether the flashcard exists.
     */
    public boolean contains(Flashcard c) {
        boolean isContain = false;
        for (Flashcard item: cardList) {
            if (item.equals(c)) {
                isContain = true;
            }
        }
        return isContain;
    }

    /**
     * Check if two tags are equivalent to each other.
     *
     * @param other the other tag to be tested.
     * @return a boolean variable that informs whether the two tags are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return otherTag.getName().equals(getName())
                && otherTag.getFlashCards().equals(getFlashcards());
    }

}
