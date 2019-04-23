name := "DistributedSystemsBradfield"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-language:higherKinds")

libraryDependencies += "com.twitter" %% "finagle-mysql" % "19.3.0"
libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.12.0",
  "com.twitter" %% "scrooge-core" % "19.3.0" exclude("com.twitter", "libthrift"),
  "com.twitter" %% "finagle-thrift" % "19.3.0" exclude("com.twitter", "libthrift")
)
