package com.github.gmazzo.gradle.plugins.tasks

import com.android.build.gradle.api.ApplicationVariant
import com.github.gmazzo.gradle.plugins.APIAccessor
import com.github.gmazzo.gradle.plugins.PlayAutoincrementPluginExtension
import com.google.api.services.androidpublisher.model.ApksListResponse
import com.google.api.services.androidpublisher.model.AppEdit
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class ComputeNextVersionCodeTask extends DefaultTask {

    PlayAutoincrementPluginExtension extension

    APIAccessor accessor

    ApplicationVariant variant

    @TaskAction
    public void increaseVersionCode() {
        def publisher = accessor.publisher
        def packageName = variant.applicationId

        AppEdit edit = publisher.edits().insert(packageName, null).execute()
        ApksListResponse list = publisher.edits().apks().list(packageName, edit.getId()).execute()
        publisher.edits().delete(packageName, edit.getId())

        def code = list.getApks().collect({ apk -> apk.getVersionCode() }).inject(0, { a, b -> Math.max(a, b) })
        def flavor = variant.mergedFlavor
        if (extension.codeFormatter) {
            flavor.versionCode = code = extension.codeFormatter.call(code, variant)
        }
        if (extension.nameFormatter) {
            flavor.versionName = extension.nameFormatter.call(code, variant)
        }
    }

}
