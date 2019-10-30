package seedu.address.diaryfeature.logic.predicates;

import java.util.function.Predicate;

import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

public class FindSpecificPredicate implements Predicate<DiaryEntry> {
    private final DiaryParametersEnum category;
    private final String userIsLookingFor;

    public FindSpecificPredicate(String[] input) {

        DiaryParametersEnum type = DiaryParametersEnum.of(input[0]);
        this.category = type;
        this.userIsLookingFor = input[1];
    }


    @Override
    public boolean test(DiaryEntry diaryEntry) {
        boolean answer = false;

        switch (this.category) {
            case TITLE:
                answer =  diaryEntry.getTitle().toString().contains(userIsLookingFor);
                 break;
            case DATE:
                answer =  diaryEntry.getDateAsStringtoStore().contains(userIsLookingFor);
                break;
            case PLACE:
                answer =  diaryEntry.getPlace().toString().contains(userIsLookingFor);
                break;
            case MEMORY:
                answer =  diaryEntry.getMemory().toString().contains(userIsLookingFor);
                break;
        }
            return answer;
    }
}



