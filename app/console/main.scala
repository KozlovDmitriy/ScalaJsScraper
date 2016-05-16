package console

import java.io.File
import java.net.URL

import Scraper.JsScraper

import scala.io.Source
import scala.sys.process._
import scala.util.{Failure, Success, Try}

/**
  * Точка входа консольного приложения
  */
object MainConsole {

  def downloadFile(url: String, dir: String) = Try {
    val fileUrl = new URL(url)
    val fileName = url.split("/").last
    val file: File = new File(dir, fileName)
    fileUrl #> file !!
  }

  def scrap(dir: String, jsPages: String, jsScrap: String) = JsScraper.jsScrap(jsPages, jsScrap) match {
    case Success(s) => {
        s.foreach( url => downloadFile(url, dir) match {
          case Failure(f) => println( url + " failed" )
          case Success(_) => println( url + " correctly" )
        })
    }
    case Failure(f) => println( f getMessage )
  }

  def main(args: Array[String]) {
    if (args.length > 1) {
      val dir = args(0)
      if (new File(dir).mkdir()) {
        val sources = args.drop(1).map( path => {
          val source = Source.fromFile( path )
          val content = try source.mkString catch {
            case e: Exception => {
              println( e.getMessage() )
              ""
            }
          } finally source.close()
          content
        } ).reduce(_+_)
        scrap(dir, sources, sources)
      } else {
        println( "Warning: cant create downloaded dir" )
      }
    }
  }
}