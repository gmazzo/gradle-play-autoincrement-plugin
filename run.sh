#!/usr/bin/env bash
./gradlew install && ./gradlew -p sample/project assembleRelease "$@"
