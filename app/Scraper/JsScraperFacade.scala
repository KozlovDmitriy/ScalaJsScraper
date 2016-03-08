package Scraper

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

// Intelij не справляется с подсветкой конструкций типа >> , не паримся
class JsScraperFacade(url: String) {
  lazy val browser = new Browser
  lazy val document = browser.get(url)

  def text(selector: String) : List[String] = {
    val extractor = texts(selector)
    val results: Seq[String] = document >> extractor
    results toList
  }

  def attr(selector: String, attribute: String) : List[String] = {
    val extractor = attrs(attribute)(selector)
    val results: Seq[String] = document >> extractor
    results toList
  }
}
