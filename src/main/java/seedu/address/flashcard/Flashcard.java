package seedu.address.flashcard;

import java.util.ArrayList;

public abstract class Flashcard {

    Question question;
    Answer answer;
    Score score;
    Id id;
    FlashcardTagList tags;

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Score getScore() {
        return score;
    }

    public Id getId() {
        return id;
    }

    public FlashcardTagList getTags() {
        return tags;
    }

    public String addTag(Tag t) {
        tags.addTag(t);
        return "You've successfully added a tag.";
    }

    public void setId(int flashcardId){
        Id = new Id(flashcardId); // im assuming this will be possible
    }

    public void setflashcardTagList (ArrayList<Tag> tags){
        this.tags = new FlashcarTagList(tags);
    }

    public String deleteTag(Tag t) {
        tags.deleteTag(t);
        return "You've deleted this tag.";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Flashcard)) {
            return false;
        }

        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getId() == this.getId();
    }

    @Override
    public String toString() {
        return String.format("Question: %s, id: %d", this.getQuestion(), this.Id.getId());

    }

}
