package mx.com.ia.cinemorelia.datasource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MoviesEntity(
    @PrimaryKey
    val id: Long,
    val poster: String,
    val name: String,
    val genre: String,
    val synopsis: String,
    val length: String,
    val rating: String,
    var posterUrl: String
)
