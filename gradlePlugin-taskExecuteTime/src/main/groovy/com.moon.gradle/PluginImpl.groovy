package com.moon.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginParam {

    int id = 0

    String state = "IDLE"

}

public class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
//        project.task('testTask') << {
//            println "Hello gradle plugin"
//        }
        println "开始执行插件。。。。"


        project.extensions.create('PluginParam', PluginParam);

        println "state = " + project['PluginParam'].state

        project.gradle.addListener(new PrintTaskExecuteTimeListener())
    }
}