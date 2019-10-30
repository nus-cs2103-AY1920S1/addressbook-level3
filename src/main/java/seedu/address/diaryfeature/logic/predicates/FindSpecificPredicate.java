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

        switch (this.category) {
            case TITLE:
                return  ;

            case DATE:
                return;

            case PLACE:
                return;

            case MEMORY:
                return;


        for(String curr:myHolder) {
            if (curr.contains(userIsLookingFor)) {
                return true;
            }
        }

    }



