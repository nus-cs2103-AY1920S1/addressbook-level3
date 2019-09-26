package seedu.address.flashcard;

import java.util.ArrayList;

public class FlashcardList {
    private ArrayList<Flashcard> flashcards;
    private TagManager tagManager;
    private static Integer ID = 0;

    public FlashcardList(){
        flashcards = new ArrayList<Flashcard>();
        tagManager = new TagManager(new ArrayList<Tag>());
    }

    public Flashcard getFlashcard(int flashccardId){
        for (Flashcard flashcard : flashcards){
            if (flashcard.getId().getId() == flashccardId) {
                return flashcard;
            }
        }
        return null;
    }

    public void setFlashcard(int flashcardId, String answer,
                             String question, ArrayList<String> options){
        Flashcard editFlashcard = getFlashcard(flashcardId);
        Flashcard edittedFlashcard;
        if(!options.isEmpty()) {
           edittedFlashcard = new McqFlashcard(new McqQuestion(question, options), new Answer(answer));
        } else {
            edittedFlashcard = new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer));
        }

        edittedFlashcard.setId(flashcardId);
        edittedFlashcard.setflashcardTagList(editFlashcard.getTags().getTags());
        flashcards.remove(editFlashcard);
        flashcards.add(edittedFlashcard);

    }


    public ArrayList<Flashcard> findFlashcard(String search){
        ArrayList<Flashcard> matchingFlashcards = new ArrayList<Flashcard>();
        for(Flashcard flashcard : flashcards){
            if(flashcard.equals(search)){
                matchingFlashcards.add(flashcard);
            }
        }
        return matchingFlashcards;
    }

    public Tag getTag(String tagName) {
        for(Tag tag : tagManager.getTags()){
            if (tag.getName() == tagName){
                return tag;
            }
        }
    }

    public void deleteFlashcard (int flashcardId){
        Flashcard flashcardDelete = getFlashcard(flashcardId);
        for (Tag tag : flashcardDelete.getTags().getTags()){
            tag.deleteFlashcard(flashcardId); //idk what to use here new id object or int id
        }
        flashcards.remove(flashcardDelete);
    }

    public void addFlashcard (String question, ArrayList<String> options, String answer){
        if (options.size()>1){
            flashcards.add(new McqFlashcard(new McqQuestion(question, options), new Answer(answer));
        }else {
            flashcards.add(new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer)));
        }
    }




}
