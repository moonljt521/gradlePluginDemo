# 一些加速gradle构建的小方案

## 1 提高虚拟机内存

在项目根目录的 gradle.properties 文件添加配置

org.gradle.daemon=true 

org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 

org.gradle.parallel=true 

org.gradle.configureondemand=true 


## 2 构建是通过gradle来进行的，可以打印gradle内部的task来查看哪些task比较耗时，此功能写在了demo内，可自行查阅

## 3 常规来说比较耗时的任务有lint 和 aapt 检查，为了加速构建，可以在debu时屏蔽这些检查

>
在app 的build.gradle 文件上添加配置
注： 在android { } 内
 gradle.startParameter.getTaskNames().each { task ->
        //library里 BuildConfig.DEBUG默认一直是flase;所以需要自定义
        if(task.equals("assembleDebug")){
            println ">>> "+task
            aaptOptions.cruncherEnabled = false
            project.gradle.startParameter.excludedTaskNames.add('lint')

        }else if(task.equals("assembleRelease")){
            println "<<<<<<<<"+task
            aaptOptions.cruncherEnabled = true
        }
    }

## 4另外一些测试性质的task可以直接忽略掉，在根目录的build.gradle 内配置

gradle.taskGraph.whenReady {
    tasks.each { task ->
        if (task.name.contains("Test")) {
            task.enabled = false
        }
    }
}

以上配置后可以在debug的时候至少减少一半时间


## 5 此外，项目较大，module 较多时候，debug 剔除掉部分module ，只保留当前需要调试的module，可以极大提高编译速度
