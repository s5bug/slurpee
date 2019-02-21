import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val cross212 = crossScalaVersions := Seq("2.12.8")

publish      := {}
publishLocal := {}

val publishSettings = Seq(
  organization         := "tf.bug",
  organizationName     := "bugtf",
  organizationHomepage := Some(url("https://bug.tf/")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/sorenbug/slurpee"),
      "scm:git@github.com:sorenbug/slurpee.git"
    )
  ),
  developers := List(
    Developer(
      id = "srnb",
      name = "Anthony Cerruti",
      email = "me@s5.pm",
      url = url("https://s5.pm")
    )
  ),
  description := "An implementation of JSON-RPC for Scala",
  licenses    := List("GPLv3" -> new URL("https://www.gnu.org/licenses/gpl-3.0.txt")),
  homepage    := Some(url("https://github.com/sorenbug/slurpee")),
  // Remove all additional repository other than Maven Central from POM
  //  pomIncludeRepository := { _ =>
  //    false
  //  },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle    := true,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
)

val sharedSettings = Seq(
  organization := "tf.bug",
  name         := "slurpee",
  version      := "1.1.0",
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
) ++ publishSettings

lazy val slurpee =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .settings(sharedSettings)
    .jsSettings(cross212)
    .jvmSettings(cross212)
    .nativeSettings( /* ... */ )

lazy val slurpeeJS = slurpee.js
lazy val slurpeeJVM = slurpee.jvm
lazy val slurpeeNative = slurpee.native
