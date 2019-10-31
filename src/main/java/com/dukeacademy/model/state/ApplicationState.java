package com.dukeacademy.model.state;

import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

/**
 * Represents the current state of the application. The current implementation contains information about the user's
 * current activity.
 */
public class ApplicationState {
    private final StandardObservable<Activity> currentActivity;

    public ApplicationState() {
        this.currentActivity = new StandardObservable<>(Activity.HOME);
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity.setValue(activity);
    }

    public Observable<Activity> getCurrentActivityObservable() {
        return this.currentActivity;
    }

    public Activity getCurrentActivity() {
        return this.currentActivity.getValue().orElse(null);
    }
}
