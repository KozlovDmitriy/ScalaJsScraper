package controllers

import Scraper.JsScraper
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.json.Json._

object Application extends Controller with JsScraper
{
  def scrap(jsPages: String, jsScrap: String) = Action { implicit request =>
    Ok("test")
  }

  def index = Action { implicit request =>
    Ok(views.html.index("test"))
  }
}