package models

import play.api.libs.json.{Json, OFormat}

case class Person(name:String, age:Int)
object Person {
  implicit val format: OFormat[Person] = Json.format[Person]
}

