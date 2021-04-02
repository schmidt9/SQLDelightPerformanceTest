package comexampledb

import kotlin.Long
import kotlin.String

data class Project(
  val _id: Long,
  val name: String?,
  val created: Long?,
  val update_time: Long?,
  val is_active: Long?
) {
  override fun toString(): String = """
  |Project [
  |  _id: $_id
  |  name: $name
  |  created: $created
  |  update_time: $update_time
  |  is_active: $is_active
  |]
  """.trimMargin()
}
