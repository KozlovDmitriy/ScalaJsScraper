package console

import java.io.File

import Scraper.JsScraper
import play.api.libs.json.Json

import scala.io.Source
import scala.util.{Failure, Success}

case class Config( pages: File = new File("."), scrap: File = new File(".") )

/**
  * Точка входа консольного приложения
  */
object MainConsole {

  /*val parser = new scopt.OptionParser[Config]("scopt") {
    head("scopt", "3.x")
    opt[File]('p', "pages") required() valueName ("<file>") action { (x, c) =>
      c.copy(pages = x)
    } text ("getPages script file")
    opt[File]('s', "scrap") required() valueName ("<file>") action { (x, c) =>
      c.copy(scrap = x)
    } text ("scrap script file")
  }*/

  def scrap(jsPages: String, jsScrap: String) = JsScraper.jsScrap(jsPages, jsScrap) match {
    case Success(s) => println( Json.toJson(s) )
    case Failure(f) => println( f getMessage )
  }

  def main(args: Array[String]) {
    /*parser.parse(args, Config()) match {
      case Some(config) =>
        println( config )
      // do stuff

      case None =>
      // arguments are bad, error message will have been displayed
    }*/
    val sources = args.map( path => {
      val source = Source.fromFile( path )
      val content = try source.mkString catch {
        case e => {
          println(e.getMessage())
          ""
        }
      } finally source.close()
      content
    } ).reduce(_+_)
    scrap(sources, sources)
  }
}
