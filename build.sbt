val defaultSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings"),
  parallelExecution in Test := false,
  logBuffered in Test := false,
  testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"))

defaultSettings

val scalacheckSettings = libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"

val scalatestSettings = libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

val chromedriver = "webdriver.chrome.driver"

val chromedriverValue = file(sys.props.getOrElse(chromedriver, "chromedriver")).getAbsolutePath

val playScalatestSettings = Seq(
  javaOptions in Test += s"-D$chromedriver=$chromedriverValue",
  libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % "test")

val protobufSettings = Seq(
  PB.targets in Compile := Seq(
    PB.gens.java -> (sourceManaged in Compile).value,
    scalapb.gen(javaConversions = true, grpc = false, flatPackage = true) -> (sourceManaged in Compile).value))

val playSettings = routesImport ++= Seq("binders._", "protobuf._", "protobuf.core._", "protobuf.character._")

val protobufUtilSettings = libraryDependencies +=
  "com.google.protobuf" % "protobuf-java-util" % com.trueaccord.scalapb.compiler.Version.protobufVersion

lazy val protobuf = (project in file("protobuf")).settings(defaultSettings, scalacheckSettings, protobufSettings)

lazy val domain = (project in file("domain")).
  settings(defaultSettings, scalatestSettings).
  dependsOn(protobuf % "compile->compile;test->test")

lazy val tool = (project in file("tool")).
  enablePlugins(PlayScala).
  settings(defaultSettings, protobufUtilSettings, playSettings, playScalatestSettings).
  dependsOn(domain, protobuf % "test->test")
