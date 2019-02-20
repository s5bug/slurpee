import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val sharedSettings = Seq(
  organization := "tf.bug",
  name         := "slurpee",
  version      := "0.1.0",
  scalaVersion := "2.11.12",
  resolvers ++= Seq(
    "mmreleases" at "https://artifactory.mediamath.com/artifactory/libs-release-global",
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("alexknvl", "maven"),
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "com.mediamath" %%% "scala-json" % "1.0",
  ),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:higherKinds"),
)

lazy val slurpee =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .settings(sharedSettings)
    .jsSettings( /* ... */ )
    .jvmSettings( /* ... */ )
    .nativeSettings( /* ... */ )

lazy val slurpeeJS = slurpee.js
lazy val slurpeeJVM = slurpee.jvm
lazy val slurpeeNative = slurpee.native
