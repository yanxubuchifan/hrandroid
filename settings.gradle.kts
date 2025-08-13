pluginManagement {
    repositories {
//        maven { url 'https://maven.aliyun.com/repository/public' } // 如果在中国使用
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven{
//            uri("https://maven.aliyun.com/repository/public")
//            uri("https://maven.aliyun.com/repository/google")
//            uri("https://jitpack.io")
//            uri("https://repo1.maven.org/maven2/")
//            uri("https://plugins.gradle.org/m2/")
        }
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "My Application"
include(":app")
 