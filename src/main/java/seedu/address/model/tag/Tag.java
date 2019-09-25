package seedu.address.model.tag;

import java.util.ArrayList;

public class Tag {
    private ArrayList<Flashcard> cardlist;
    private String name;
    private Statistics stats;

    public Tag(String _name) {
        this.name = _name;
        stats = new Statistics();
        cardList = new ArrayList<>();
    }

    public ArrayList<Flashcard> getFlashcards() {
        ArrayList<Flashcard> resultList = new ArrayList<>();
        resultList.addAll(cardlist);
        return resultList;
    }

    public void deleteFlashcard(ID id) {
        ArrayList<Flashcard> newList = new ArrayList<>();
        newList.addAll(cardlist);
        for (Flashcard item: newList) {
            if (item.getId() == id.getId()) { // there are two different getId() and ID stored as int
                newList.remove(item);
            }
        }
        this.cardlist = newList;
    }

    public void addFlashcard(Flashcard c) {
        ArrayList<Flashcard> newList = new ArrayList<>();
        newList.addAll(cardlist);
        newList.add(c);
        this.cardlist = newList;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Statistics getStatistics() {
        return stats;
    }

    public boolean contains(Flashcard c) {
        boolean isContain = false;
        for (Flashcard item: cardlist) {
            if (item.equals(c)) {
                isContain = true;
            }
        }
        return isContain;
    }

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
