package seedu.address.diaryfeature.logic.predicates;

import java.util.function.Predicate;

import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;

public class FindSpecificPredicate implements Predicate<DiaryEntry> {
    private final DiaryParametersEnum category;
    private final String userIsLookingFor;

    public FindSpecificPredicate(String[] input) {
        System.out.println("String is missing?" + input[0]);
        DiaryParametersEnum type = DiaryParametersEnum.of(input[0]);
        this.category = type;
        this.userIsLookingFor = input[1];
    }


    @Override
    public boolean test(DiaryEntry diaryEntry) {
        boolean answer = false;

        switch (this.category) {
            case TITLE:
                answer =  diaryEntry.getTitle().toString().toLowerCase().contains(userIsLookingFor.toLowerCase());
                 break;
            case DATE:
                answer =  diaryEntry.getDateAsStringtoStore().toLowerCase().contains(userIsLookingFor.toLowerCase());
                break;
            case PLACE:
                answer =  diaryEntry.getPlace().toString().toLowerCase().contains(userIsLookingFor.toLowerCase());
                break;
            case MEMORY:
                answer =  diaryEntry.getMemory().toString().toLowerCase().contains(userIsLookingFor.toLowerCase());
                break;
        }
            return answer;
    }
}



