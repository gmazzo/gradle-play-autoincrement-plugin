package com.github.gmazzo.gradle.plugins

import com.android.build.gradle.api.ApplicationVariant

class PlayAutoincrementPluginExtension {

    /**
     * The JSON credential file downloaded from Google Console API project
     */
    File apiFile

    /**
     * A callback for computing the next versionCode. Defaults to 'current + 1'.
     */
    Closure<Integer> codeFormatter = { int code, ApplicationVariant variant -> code + 1 }

    /**
     * A callback for computing the next versionName. By default appends '.version' to the current name. Set it to 'null' to disable this feature
     */
    Closure<String> nameFormatter = { int code, ApplicationVariant variant -> "${variant.versionName}.$code" }

    void apiFile(File file) {
        apiFile = file
    }

    void apiFile(String file) {
        apiFile(new File(file))
    }

}
