package com.github.gmazzo.gradle.plugins.api

import com.google.api.services.androidpublisher.AndroidPublisher

public abstract class APIAccessor {
    private AndroidPublisher publisher;

    protected abstract AndroidPublisher providePublisher()

    public AndroidPublisher getPublisher() {
        if (publisher == null) {
            publisher = providePublisher()
        }
        return publisher
    }

}
