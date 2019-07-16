package connectors

import com.google.inject.Inject
import javax.inject.Singleton
import models.Person
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class BackEnd @Inject()(ws: WSClient) {
  def checklogin(name: String): Future[Person] = {
    val request = ws.url("http://localhost:9001/check-login")
    request.post(Json.obj("name" -> name)).map {
      response =>
        response.status match {
          case 200 => Json.parse(response.body).as[Person]
          case _ => throw new Exception("BAH!")
        }
    }
  }
}
