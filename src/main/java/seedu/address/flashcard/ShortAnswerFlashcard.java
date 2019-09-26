package seedu.address.flashcard;

public class ShortAnswerFlashcard extends Flashcard {

    public ShortAnswerFlashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
        this.tags = new FlashcardTagList();
    }
}
