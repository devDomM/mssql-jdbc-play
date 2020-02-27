name := "mssql-jdbc-play"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.12"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += cache
libraryDependencies += javaCore
libraryDependencies += javaJdbc
libraryDependencies += javaWs
libraryDependencies += evolutions
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "7.4.1.jre8"
//libraryDependencies += "net.sourceforge.jtds" % "jtds" % "1.3.1"
