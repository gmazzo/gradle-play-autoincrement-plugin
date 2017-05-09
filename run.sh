#!/usr/bin/env bash
./gradlew install && ./gradlew -p plugin/src/test/resources/project/ assembleRelease -DPLUGIN_DEPENDENCY=gradle.plugin.gmazzo:play-autoincrement-plugin:+ "$@"
