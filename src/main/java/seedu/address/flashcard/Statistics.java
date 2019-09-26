package seedu.address.flashcard;

import java.util.ArrayList;

public class Statistics {

    public static ArrayList<String> getTagPercentage (ArrayList<Tag> tags){
        int overallTotal = 0;
        int overallTotalCorrectAns = 0;
        int tagTotal = 0;

        int tagTotalCorrect = 0;
        ArrayList<String> results = new ArrayList<>();
        for (Tag tag : tags){
                tagTotal = 0;
                tagTotalCorrect = 0;
            for(Flashcard flashcard  : tag.getFlashcards()){
                tagTotal += flashcard.getScore().getTotalAnswerNumber();
                overallTotal += tagTotal;
                tagTotalCorrect += flashcard.getScore().getCorrectAnswerNumber();
                overallTotalCorrectAns += tagTotalCorrect;
            }
            String result = "Total percentage under " + tag.getName() + ": " + ((tagTotalCorrect/tagTotal)*100) +"%";
            results.add(result);
        }
        if(tags.size() > 1){
            String totalResult = "Total percentage for these tags: " + (overallTotalCorrectAns/overallTotal) + "%";
            results.add(totalResult);
        }

        return results;
    }

    public static ArrayList<String> getFlashcardPercentage(ArrayList<Flashcard> flashcards){
        int overallTotal = 0;
        int overallTotalCorrectAns = 0;
        ArrayList<String> results = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            String result = "Percentage correct for " + flashcard.toString()
                    + ": " + flashcard.getScore().getCorrectRate()*100 + "%";
            results.add(flashcard.toString());
            overallTotal += flashcard.getScore().getTotalAnswerNumber();
            overallTotalCorrectAns += flashcard.getScore().getCorrectAnswerNumber();
        }
        if(flashcards.size() > 1){
            String totalResult = "Total percentage for these tags: " + (overallTotalCorrectAns/overallTotal) + "%";
            results.add(totalResult);
        }
        return results;
    }

    public static ArrayList<String> getTagAnswerCount(ArrayList<Tag> tags){
        int overallTotal = 0;
        int tagTotal = 0;
        ArrayList<String> results = new ArrayList<>();
        for (Tag tag : tags){
            tagTotal = 0;
            for(Flashcard flashcard  : tag.getFlashcards()){
                tagTotal += flashcard.getScore().getTotalAnswerNumber();
                overallTotal += tagTotal;
            }
            String result = "Total attempts under " + tag.getName() + ": " + overallTotal;
            results.add(result);
        }
        if(tags.size() > 1){
            String totalResult = "Total attempts for these tags: " + overallTotal;
            results.add(totalResult);
        }

        return results;
    }

    public static ArrayList<String> getFlashcardAnswerCount(ArrayList<Flashcard> flashcards){
        int overallTotal = 0;
        ArrayList<String> results = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            String result = "Total attempts for " + flashcard.toString()
                    + ": " + flashcard.getScore().getTotalAnswerNumber();
            overallTotal += flashcard.getScore().getTotalAnswerNumber();
        }
        if(flashcards.size() > 1){
            String totalResult = "Total attempts: " + overallTotal;
            results.add(totalResult);
        }
        return results;
    }

    public static ArrayList<String> getCardsRead(Tag tag){
        return getCardsRead(tag.getFlashcards());
    }

    public static ArrayList<String> getCardsRead(ArrayList<Flashcard> flashcards){
        ArrayList<String> cardsRead = new ArrayList<>();
        for (Flashcard flashcard : flashcards){
            if(flashcard.getScore().getTotalAnswerNumber()>0){
                cardsRead.add(flashcard.toString());
            }
        }
        cardsRead.add("total unread cards:" + (flashcards.size()-cardsRead.size()));
        return cardsRead;
    }
}
