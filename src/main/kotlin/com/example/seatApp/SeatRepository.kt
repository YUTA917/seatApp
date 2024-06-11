package com.example.seatApp


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.ResultSet


@Component
class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        return User(rs.getLong(1), rs.getString(2))
    }
}

@Component
class SeatRowMapper : RowMapper<Seat> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Seat {
        return Seat(rs.getInt(1), rs.getInt(2),rs.getBoolean(3))
    }
}

@Repository
class SeatRepository(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val userRowMapper: UserRowMapper,
    @Autowired val seatRowMapper: SeatRowMapper
) {
    fun getUsers(): List<User> {
        return jdbcTemplate.query("SELECT id, name FROM users", userRowMapper)
    }

    fun postUser(userRequest: UserRequest): Int {
        println("--run postUser--")
        println("--useRequest.name--,"+userRequest.name)
        return jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", userRequest.name)
    }

    fun deleteUser(userRequest: UserRequest): Int {
        println("--run deleteUser--")
        println("--useRequest.name--,"+userRequest.name)
        return jdbcTemplate.update("DELETE FROM users WHERE name = ?", userRequest.name)
    }

    fun getSeats(): List<Seat> {
        return jdbcTemplate.query("SELECT id, user_id, filled FROM seats ORDER BY id", seatRowMapper)
    }

    fun putSeat ( seatRequest : SeatRequest): Int {
        println("--run putSeat--")

        return jdbcTemplate.update("UPDATE seats SET user_id = ? , filled = ? WHERE id = ?", seatRequest.userId ,seatRequest.filled ,seatRequest.seatId)
    }


}

