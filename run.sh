#!/usr/bin/env bash
./gradlew install && ./gradlew -p plugin/src/test/resources/project/ assembleDebug -DPLUGIN_DEPENDENCY=com.github.gmazzo.gradle.plugins:play-autoincrement-plugin:0.1 "$@"
