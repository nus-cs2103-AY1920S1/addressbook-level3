package com.dukeacademy.model.State;

import com.dukeacademy.observable.Observable;
import com.dukeacademy.observable.StandardObservable;

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
