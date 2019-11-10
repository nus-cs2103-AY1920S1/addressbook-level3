package com.dukeacademy.model.state;

import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

/**
 * Represents the current state of the application. The current implementation contains information about the user's
 * current activity.
 */
public class ApplicationState {
    private final StandardObservable<Activity> currentActivity;
    private final StandardObservable<Boolean> isEvaluating;

    public ApplicationState() {
        this.currentActivity = new StandardObservable<>(Activity.HOME);
        this.isEvaluating = new StandardObservable<>(false);
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity.setValue(activity);
    }

    public Observable<Activity> getCurrentActivityObservable() {
        return this.currentActivity;
    }

    public void setIsEvaluating(boolean isEvaluating) {
        this.isEvaluating.setValue(isEvaluating);
    }

    public Observable<Boolean> getIsEvaluating() {
        return this.isEvaluating;
    }

    public Activity getCurrentActivity() {
        return this.currentActivity.getValue().orElse(null);
    }
}
