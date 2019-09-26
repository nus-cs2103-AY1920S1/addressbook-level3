package seedu.address.flashcard;

public class McqFlashcard extends Flashcard {

    public McqFlashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
        this.tags = new FlashcardTagList();
    }
}
