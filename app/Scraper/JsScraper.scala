package Scraper

import javax.script.ScriptEngineManager

import jdk.nashorn.api.scripting.ScriptObjectMirror

import scala.util.{Failure, Success, Try}

object JsScraper {
  lazy val manager: ScriptEngineManager = new ScriptEngineManager(null)
  lazy val engine = manager.getEngineByName("nashorn")
  lazy val invocable = engine.asInstanceOf[javax.script.Invocable]

  def getPages(js: String) = Try[List[String]] {
    engine.eval(js)
    val result = invocable.invokeFunction("getPages")
    val mirror = result match {
      case mirror: ScriptObjectMirror => mirror
    }
    List()
  }

  def jsScrap(jsPages: String, jsScrap: String) = Try[List[String]] {
    getPages( jsPages ) match {
      case Success(s) => s
      case Failure(_) => List()
    }
  }
}
