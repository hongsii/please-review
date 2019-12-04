pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "please-review"

include("please-review-core")
include("please-review-common")
include("please-review-api")
include("please-review-github-client")

project(":please-review-github-client").projectDir = file("please-review-clients/please-review-github-client")