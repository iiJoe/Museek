package controllers

import play.api.http._
import play.api.mvc._
import scalatags.Text.all._

trait TagController {
  implicit def contentTypeOfTag(implicit codec: Codec): ContentTypeOf[Tag] = {
   ContentTypeOf[Tag](Some(ContentTypes.HTML))
 }

  implicit def writeableOfTag(implicit codec: Codec): Writeable[Tag] = {
    Writeable(tag => codec.encode("<!DOCTYPE html>\n" + tag.render))
  }
}
