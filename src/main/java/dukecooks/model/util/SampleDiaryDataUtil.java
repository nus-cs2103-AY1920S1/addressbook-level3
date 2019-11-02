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
            new Diary(new DiaryName("Healthy Living"), pages),
            new Diary(new DiaryName("Meat Lovers")),
            new Diary(new DiaryName("Vegan Diet"), pages),
            new Diary(new DiaryName("One Week Slimming")),
            new Diary(new DiaryName("Core Exercises"), pages),
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
