package com.github.gmazzo.gradle.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.github.gmazzo.gradle.plugins.tasks.ComputeNextVersionCodeTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PlayAutoincrementPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (!project.plugins.any { p -> p instanceof AppPlugin }) {
            throw new IllegalStateException('The \'com.android.application\' plugin is required.')
        }

        def android = project.extensions.getByType(AppExtension)
        def extension = project.extensions.create('autoincrement', PlayAutoincrementPluginExtension)

        android.applicationVariants.all { ApplicationVariant variant ->
            def variantName = variant.name.capitalize()
            def taskName = "computeNext${variantName}VersionCode"

            def task = project.tasks.create(taskName, ComputeNextVersionCodeTask)
            task.extension = extension
            task.accessor = new APIAccessor(project, extension, android, variant)
            task.variant = variant
            task.dependsOn project.tasks.preBuild
            task.onlyIf { !extension.releaseOnly || !variant.buildType.debuggable }
            variant.preBuild.dependsOn task
        }
    }

}
