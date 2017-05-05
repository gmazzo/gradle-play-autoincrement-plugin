package com.github.gmazzo.gradle.plugins

class PlayAutoincrementPluginExtension {

    File apiFile

    void apiFile(File file) {
        apiFile = file
    }

    void apiFile(String file) {
        apiFile(new File(file))
    }

}
