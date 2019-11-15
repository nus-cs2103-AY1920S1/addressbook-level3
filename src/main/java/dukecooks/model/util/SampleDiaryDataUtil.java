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

        ArrayList<Page> foodPageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getFoodSamplePages()));
        ObservableList<Page> foodPages = FXCollections.observableArrayList(foodPageList);

        ArrayList<Page> exercisePageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getExerciseSamplePages()));
        ObservableList<Page> exercisePages = FXCollections.observableArrayList(exercisePageList);

        ArrayList<Page> meatPageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getMeatSamplePages()));
        ObservableList<Page> meatPages = FXCollections.observableArrayList(meatPageList);

        ArrayList<Page> singaporeList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getSingaporeSamplePages()));
        ObservableList<Page> singaporePages = FXCollections.observableArrayList(singaporeList);

        ArrayList<Page> slimmingList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getSlimmingSamplePages()));
        ObservableList<Page> slimmingPages = FXCollections.observableArrayList(slimmingList);

        ArrayList<Page> mixedPageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getMixedSamplePages()));
        ObservableList<Page> mixedPages = FXCollections.observableArrayList(mixedPageList);

        ArrayList<Page> studentPageList = new ArrayList<>(Arrays.asList(SamplePagesDataUtil.getStudentSamplePages()));
        ObservableList<Page> studentPages = FXCollections.observableArrayList(studentPageList);

        return new Diary[] {
            new Diary(new DiaryName("Asian Food"), foodPages),
            new Diary(new DiaryName("Healthy Living"), mixedPages),
            new Diary(new DiaryName("Meat Lovers"), meatPages),
            new Diary(new DiaryName("Singapore Favourites"), singaporePages),
            new Diary(new DiaryName("One Week Slimming"), slimmingPages),
            new Diary(new DiaryName("Core Exercises"), exercisePages),
            new Diary(new DiaryName("Student Essentials"), studentPages),
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
