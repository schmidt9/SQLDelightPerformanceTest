package comexampledb

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String

interface DataQueries : Transacter {
  fun <T : Any> fetchProjects(mapper: (
    _id: Long,
    name: String?,
    created: Long?,
    update_time: Long?,
    is_active: Long?
  ) -> T): Query<T>

  fun fetchProjects(): Query<Project>

  fun createProject(projectName: String?)
}
