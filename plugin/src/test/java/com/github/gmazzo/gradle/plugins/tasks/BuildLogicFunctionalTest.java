package com.github.gmazzo.gradle.plugins.tasks;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BuildLogicFunctionalTest {
    private GradleRunner runner;

    @Before
    public void setup() throws IOException {
        runner = GradleRunner.create()
                .withProjectDir(new File(getClass().getResource("/project/").getFile()))
                .forwardOutput();
    }

    @Test
    public void testComputeNextVersionCode() throws IOException {
        BuildResult result = runner
                .withArguments("computeNextDebugVersionCode")
                .build();

        assertEquals(result.task(":computeNextDebugVersionCode").getOutcome(), TaskOutcome.SUCCESS);
    }

}
