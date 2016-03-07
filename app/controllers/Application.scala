package controllers

import Scraper.JsScraper
import play.api.mvc._

import scala.util.{Failure, Success}

object Application extends Controller
{
  def scrap(jsPages: String, jsScrap: String) = Action { implicit request =>
    val pages = JsScraper.jsScrap(jsPages, jsScrap) match {
      case Success(v) => v
      case Failure(_) => List()
    }
    Ok( pages mkString " " )
  }

  def index = Action { implicit request =>
    Ok(views.html.index("test"))
  }
}