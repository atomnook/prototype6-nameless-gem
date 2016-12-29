val defaultSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings"))

defaultSettings

val scalacheckSettings = libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"

val protobufSettings = defaultSettings ++ Seq(
  PB.targets in Compile := Seq(
    PB.gens.java -> (sourceManaged in Compile).value,
    scalapb.gen(javaConversions=true, grpc = false, flatPackage = true) -> (sourceManaged in Compile).value))

lazy val protobuf = (project in file("protobuf")).settings(defaultSettings, scalacheckSettings, protobufSettings)
