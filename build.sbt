import com.typesafe.sbt.SbtStartScript
import spray.revolver.RevolverPlugin.Revolver

organization  := "example"

name          := "application_name"

version       := "0.1"

scalaVersion  := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers     ++= Seq(
  "spray" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.3.2"
  val sprayV = "1.3.1"
  Seq(
    "io.spray"                  %   "spray-can"                 % sprayV,
    "io.spray"                  %   "spray-routing"             % sprayV,
    "io.spray"                  %   "spray-client"              % sprayV,
    "io.spray"                  %   "spray-caching"             % sprayV,
    "io.spray"                  %%  "spray-json"                % "1.2.6",
    "io.spray"                  %   "spray-testkit"             % sprayV  % "test",
    "com.typesafe.akka"         %%  "akka-actor"                % akkaV,
    "com.typesafe.akka"         %%  "akka-testkit"              % akkaV   % "test",
    "org.specs2"                %%  "specs2"                    % "2.2.3" % "test"
  )
}

SbtStartScript.startScriptForClassesSettings

Revolver.settings

Revolver.reColors := Seq("magenta")

ideaExcludeFolders ++= Seq(".idea", ".idea_modules", "target")
