package seedu.address.diaryfeature.logic.predicates;

import java.util.function.Predicate;

import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

/**
 * Find entries which  contain the user specified search query
 */

public class FindPredicate implements Predicate<DiaryEntry> {
    private final String userIsLookingFor;

    public FindPredicate(String input) {
        this.userIsLookingFor = input;
    }

    private boolean checkStrings(String diary, String userInput) {
        return diary.toLowerCase().contains(userInput.toLowerCase());
    }


    @Override
    public boolean test(DiaryEntry diaryEntry) {
        String title = diaryEntry.getTitle().toString();
        String date = diaryEntry.getDateAsStringtoStore();
        String place = diaryEntry.getPlace().toString();
        String memory = diaryEntry.getMemory().toString();
        String[] myHolder = new String[]{title, date, place, memory};
        for (String curr : myHolder) {
            if (checkStrings(curr, userIsLookingFor)) {
                return true;
            }
        }
        return false;
    }

}





