package controllers

import Scraper.JsScraper
import play.api.libs.json.Json
import play.api.mvc._

import scala.util.{Failure, Success}

object Application extends Controller
{
  def scrap(jsPages: String, jsScrap: String) = Action { implicit request =>
    JsScraper.jsScrap(jsPages, jsScrap) match {
      case Success(s) => Ok( Json.toJson(s) )
      case Failure(f) => BadRequest( f getMessage )
    }
  }

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }
}