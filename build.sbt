val dottyVersion = "0.18.1-RC1"

addCommandAlias("c", "~compile")
addCommandAlias("r", "run")
addCommandAlias("b", "show graalvm-native-image:packageBin")

lazy val root = project
  .in(file("."))
  .settings(
    name := "noric-bot",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
    )
  )

// requires "native-image" to be in PATH. On my macbook I have an alias "graalvm-enable"
enablePlugins(GraalVMNativeImagePlugin)
