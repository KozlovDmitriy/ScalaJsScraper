package Scraper

import javax.script.ScriptEngineManager

import jdk.nashorn.api.scripting.ScriptObjectMirror

import scala.collection.JavaConversions._
import scala.util.{Success, Try}

object JsScraper {
  lazy val manager: ScriptEngineManager = new ScriptEngineManager(null)

  def matchJsInvokeResult(obj: Object) = Try[List[String]] {
    obj match {
      case array: Array[String] => array toList
      case string: String => List(string)
      case list: List[String] => list
      case mirror: ScriptObjectMirror => mirror.values().toList.asInstanceOf[List[String]]
    }
  }

  def getPages(js: String) = Try[List[String]] {
    val engine = manager.getEngineByName("nashorn")
    val invocable = engine.asInstanceOf[javax.script.Invocable]
    engine.eval( js )
    val result = invocable.invokeFunction("getPages")
    matchJsInvokeResult(result) match {
      case Success(v) => v
    }
  }

  def scrap(js: String, url: String) = Try[List[String]] {
    val facade = new JsScraperFacade(url)
    val engine = manager.getEngineByName("nashorn")
    val invocable = engine.asInstanceOf[javax.script.Invocable]
    engine.eval( js )
    val result = invocable.invokeFunction("scrap", url, facade)
    matchJsInvokeResult(result) match {
      case Success(v) => v
    }
  }

  def jsScrap(jsPages: String, jsScrap: String) = Try[List[String]] {
    getPages( jsPages ) match {
      case Success(urls) => {
        for (url <- urls) yield scrap(jsScrap, url) match {
          case Success(s) => s
        }
      } flatten
    }
  }
}
