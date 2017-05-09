package com.github.gmazzo.gradle.plugins.api;

import com.github.gmazzo.gradle.plugins.PlayAutoincrementPluginExtension;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;

import java.io.IOException;
import java.util.Collections;

public class ExtensionAPIAccessor extends APIAccessor {
    private final PlayAutoincrementPluginExtension extension;

    public ExtensionAPIAccessor(PlayAutoincrementPluginExtension extension) {
        this.extension = extension;
    }

    @Override
    protected AndroidPublisher providePublisher() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(extension.getJsonStream().call());
        Credential scoped = credential.createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));

        return new AndroidPublisher.Builder(scoped.getTransport(), scoped.getJsonFactory(), scoped)
                .setApplicationName("play-autoincrement-plugin")
                .build();
    }

}
