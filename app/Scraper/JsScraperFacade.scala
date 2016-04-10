package Scraper

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

// Intelij не справляется с подсветкой конструкций типа >> , не паримся
class JsScraperFacade(url: String) {
  lazy val browser = new Browser
  lazy val document = browser.get(url)

  def absolute( relativeUrl: String, baseUrl: String = url ) : String = {
    if ( relativeUrl.startsWith( baseUrl ) || relativeUrl.indexOf("//") > 0 )
      return relativeUrl

    val baseUrlParts : Seq[String] = baseUrl.split( '/' )
    val count = baseUrlParts.length
    val cleanUrl = relativeUrl.dropWhile( c => c == '/' )
    val index = baseUrlParts.zipWithIndex.find{
      case (_, i) => {
        val suburl = baseUrlParts.slice(i, count).reduce(_ + '/' + _)
        cleanUrl.startsWith(suburl)
      }
    } match {
      case Some(r) => r._2
      case _ => count
    }
    val parts = baseUrlParts.slice(0, index)
    parts.reduce(_ + '/' + _) + '/' + cleanUrl
  }

  def text(selector: String) : Array[String] = {
    val extractor = texts(selector)
    val results: Seq[String] = document >> extractor
    results toArray
  }

  def attr(selector: String, attribute: String) : Array[String] = {
    val extractor = attrs(attribute)(selector)
    val results: Seq[String] = document >> extractor
    results toArray
  }
}
