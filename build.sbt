name := "ScalaJsScraper"

version := "1.0"

lazy val `scalajsscraper` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc , cache , ws   , specs2 % Test,
  "org.webjars" % "npm" % "1.3.26",
  "net.ruippeixotog" %% "scala-scraper" % "0.1.2",
  "org.webjars" % "webjars-locator" % "0.9",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.webjars" % "highlightjs" % "8.0-3",
  "org.webjars" % "ace" % "01.08.2014"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"