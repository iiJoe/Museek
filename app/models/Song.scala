package models

case class Song(
  circle: String,
  title: String,
  original: List[String],
  file: String,
  sources: List[String],
  id: Int
)
