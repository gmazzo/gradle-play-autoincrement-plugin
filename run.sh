#!/usr/bin/env bash
./gradlew install && ./gradlew -p plugin/src/test/resources/project/ assembleRelease "$@"
