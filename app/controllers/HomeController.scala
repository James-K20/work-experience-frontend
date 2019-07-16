package controllers

import connectors.BackEnd
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(backend: BackEnd, cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def loggedIn(name: String) = Action.async { implicit request =>
    backend.checklogin(name).map {
      person =>
        Ok(views.html.loggedin(person))
    }.recover {
      case _ =>
        Redirect(routes.HomeController.index())
    }
  }

  def postLogin = Action.async { implicit request =>
    val name: String = request.body.asFormUrlEncoded.get("name").head
    backend.checklogin(name).map {
      person =>
        Redirect(routes.HomeController.loggedIn(person.name))
    }.recover {
      case _ =>
        Redirect(routes.HomeController.index())
    }
  }

}