package com.app.fcm.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 05/06/25.
 *
 * @author hardkgosai.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val age: Int
)
