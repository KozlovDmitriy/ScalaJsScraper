package controllers

import Scraper.JsScraper
import play.api.mvc._

import scala.util.{Failure, Success}

object Application extends Controller
{
  def scrap(jsPages: String, jsScrap: String) = Action { implicit request =>
    JsScraper.jsScrap(jsPages, jsScrap) match {
      case Success(s) => Ok( s mkString " " )
      case Failure(f) => Ok( f getMessage )
    }
  }

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }
}