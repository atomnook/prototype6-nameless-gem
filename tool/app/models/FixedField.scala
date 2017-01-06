package models

trait FixedField {
  val label: String

  val value: Long

  def id: String = label.toLowerCase

  def getter: String = s"${id}Value"
}
