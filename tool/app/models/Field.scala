package models

trait Field[A] { self =>
  val label: String

  val value: A => Long

  def id: String = label.toLowerCase

  def getter: String = s"${id}Value"

  def fix(a: A): FixedField = {
    new FixedField {
      override val value: Long = self.value(a)
      override val label: String = self.label
    }
  }
}
