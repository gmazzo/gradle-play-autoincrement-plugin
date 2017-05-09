package com.github.gmazzo.gradle.plugins.api;

import com.google.api.services.androidpublisher.AndroidPublisher;

import java.io.IOException;

public abstract class APIAccessor {
    private AndroidPublisher publisher;

    protected abstract AndroidPublisher providePublisher() throws IOException;

    public AndroidPublisher getPublisher() throws IOException {
        if (publisher == null) {
            publisher = providePublisher();
        }
        return publisher;
    }

}
