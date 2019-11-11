package seedu.address.diaryfeature.logic.predicates;

import java.util.function.Predicate;

import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

/**
 * Find entries which (in the specified prefix) contain the user specified search query
 */

public class FindSpecificPredicate implements Predicate<DiaryEntry> {
    private final DiaryParametersEnum category;
    private final String userIsLookingFor;


    public FindSpecificPredicate(String[] input) {
        this.category = DiaryParametersEnum.of(input[0]);
        this.userIsLookingFor = input[1];
    }

    private boolean checkStrings(String diary, String userInput) {
        return diary.toLowerCase().contains(userInput.toLowerCase());
    }

    @Override
    public boolean test(DiaryEntry diaryEntry) {
        boolean answer = false;
        switch (this.category) {
            case TITLE:
                answer = checkStrings(diaryEntry.getTitle().toString(), userIsLookingFor);
                break;
            case DATE:
                answer = checkStrings(diaryEntry.getDateAsStringtoStore(), userIsLookingFor);
                break;
            case PLACE:
                answer = checkStrings(diaryEntry.getPlace().toString(), userIsLookingFor);
                break;
            case MEMORY:
                answer = checkStrings(diaryEntry.getMemory().toString(), userIsLookingFor);
                break;
            default:
                assert this.category != null;
                System.out.println("Parser Exception");
        }
        return answer;
    }
}



