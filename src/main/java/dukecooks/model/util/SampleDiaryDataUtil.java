package dukecooks.model.util;

import java.util.ArrayList;
import java.util.Arrays;

import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains utility methods for populating {@code DiaryRecords} with sample data.
 */
public class SampleDiaryDataUtil {
    public static Diary[] getSampleDiaries() {

        ArrayList<Page> pageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getSamplePages()));
        ObservableList<Page> pages = FXCollections.observableArrayList(pageList);

        return new Diary[] {
            new Diary(new DiaryName("Asian Cuisines"), pages),
            new Diary(new DiaryName("Healthy Living")),
            new Diary(new DiaryName("Meat Lovers"), pages),
            new Diary(new DiaryName("Vegan Diet")),
            new Diary(new DiaryName("One Week Slimming"), pages),
            new Diary(new DiaryName("Core Exercises")),
        };
    }

    public static ReadOnlyDiary getSampleDiaryRecords() {
        DiaryRecords sampleDc = new DiaryRecords();
        for (Diary sampleDiary : getSampleDiaries()) {
            sampleDc.addDiary(sampleDiary);
        }
        return sampleDc;
    }
}
