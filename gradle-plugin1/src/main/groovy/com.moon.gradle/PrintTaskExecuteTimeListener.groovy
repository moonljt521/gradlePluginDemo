package com.moon.gradle

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

class PrintTaskExecuteTimeListener implements TaskExecutionListener, BuildListener {

    Clock clock;

    private times = []

    long startTime;

    @Override
    void beforeExecute(Task task) {
        clock = new Clock(new Date().getTime())
//        clock = new Clock()
        startTime = clock.getStartTime()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = System.currentTimeMillis() - startTime
        times.add([ms, task.path])
        task.project.logger.warn "${task.path} spend ${ms}ms"
    }

    @Override
    void buildFinished(BuildResult result) {
        println "Task spend time:"
        for (time in times) {
            if (time[0] >= 50) {
                printf "%7sms  %s\n", time
            }
        }
    }

    @Override
    void buildStarted(Gradle gradle) {}

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}
}