package com.github.gmazzo.gradle.plugins

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes

public class APIAccessor {
    private final File apiFile
    private AndroidPublisher publisher

    public APIAccessor(File apiFile) {
        this.apiFile = apiFile
    }

    public AndroidPublisher getPublisher() throws IOException {
        if (publisher == null) {
            if (apiFile == null) {
                throw new IllegalArgumentException("Cannot connect with PlayStore API: no 'apiFile' was provided")

            } else if (!apiFile.exists()) {
                throw new IllegalArgumentException("Cannot connect with PlayStore API: '${apiFile.absolutePath}' does not exists")
            }

            GoogleCredential credential = GoogleCredential.fromStream(apiFile.newInputStream())
            Credential scoped = credential.createScoped([AndroidPublisherScopes.ANDROIDPUBLISHER])

            publisher = new AndroidPublisher.Builder(scoped.transport, scoped.jsonFactory, scoped).build()
        }
        return publisher;
    }

}
