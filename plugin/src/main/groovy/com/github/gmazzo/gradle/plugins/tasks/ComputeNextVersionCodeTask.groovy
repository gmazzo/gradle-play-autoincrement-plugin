package com.github.gmazzo.gradle.plugins.tasks

import com.android.build.gradle.api.ApplicationVariant
import com.github.gmazzo.gradle.plugins.APIAccessor
import com.google.api.services.androidpublisher.model.ApksListResponse
import com.google.api.services.androidpublisher.model.AppEdit
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class ComputeNextVersionCodeTask extends DefaultTask {

    APIAccessor apiAccessor

    ApplicationVariant variant

    @TaskAction
    public void increaseVersionCode() {
        def publisher = apiAccessor.publisher
        def packageName = variant.applicationId

        AppEdit edit = publisher.edits().insert(packageName, null).execute()
        ApksListResponse list = publisher.edits().apks().list(packageName, edit).execute()
    }

}
