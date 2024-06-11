package com.example.seatApp

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.jdbc.Sql


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/insert_test_user_data.sql","/insert_test_seats_data.sql")
class SeatAppApplicationTests(@Autowired val restTemplate: TestRestTemplate,
							  @LocalServerPort val port: Int) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `最初のテスト`() {
		assertThat(1+2, equalTo(3))
	}

	@Test
	fun `GETリクエストはOKステータスを返す`() {
		// localhost/users に GETリクエストを発行する。
		val response = restTemplate.getForEntity("http://localhost:$port/users", String::class.java)
		// レスポンスのステータスコードは OK である。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはuserオブジェクトのリストを返す`() {
		// localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		// レスポンスの Content-Type は application/json であること。
		assertThat(response.headers.contentType, equalTo(MediaType.APPLICATION_JSON))
		// 配列は2つの要素をもつこと。
		val users = response.body!!
		assertThat(users.size, equalTo(3))
		// 最初の要素は id=1 であり、text が "foo" であること。
		assertThat(users[0].id, equalTo(1))
		assertThat(users[0].name, equalTo("たなか"))
		// 次の要素は id=2 であり、text が "bar" であること。
		assertThat(users[1].id, equalTo(2))
		assertThat(users[1].name, equalTo("さとう"))
	}

	@Test
	fun `GETリクエスト(seat)はOKステータスを返す`() {
		// localhost/users に GETリクエストを発行する。
		val response = restTemplate.getForEntity("http://localhost:$port/seats", String::class.java)
		// レスポンスのステータスコードは OK である。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはseatオブジェクトのリストを返す`() {
		// localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val response = restTemplate.getForEntity("http://localhost:$port/seats", Array<Seat>::class.java)
		// レスポンスの Content-Type は application/json であること。
		assertThat(response.headers.contentType, equalTo(MediaType.APPLICATION_JSON))
		// 配列は2つの要素をもつこと。
		val seats = response.body!!
		assertThat(seats.size, equalTo(3))
		// 最初の要素は id=1 であり、text が "foo" であること。
		assertThat(seats[0].id, equalTo(1))
		assertThat(seats[0].userId, equalTo(1))
		assertThat(seats[0].filled, equalTo(true))
		// 次の要素は id=2 であり、text が "bar" であること。
		assertThat(seats[1].id, equalTo(2))
		assertThat(seats[1].userId, equalTo(2))
		assertThat(seats[1].filled, equalTo(true))
	}

	@Test
	fun `POSTリクエストはOKステータスを返す`() {
		// localhost/users に POSTリクエストを送る。このときのボディは {"name": "なかの"}
		val request = UserRequest("なかの")
		val response = restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)
		// レスポンスのステータスコードは OK であること。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `POSTリクエストはUserオブジェクトを格納する`() {
		// localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		var response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		// このときのレスポンスを users1 として記憶。
		val users1 = response.body!!

		// localhost/users に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request = UserRequest("なかの")
		val postResponse = restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)
		println("postResponse")
		println(postResponse.body)

		// ふたたび localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		// このときのレスポンスを users2 として記憶。
		val users2 = response.body!!

		// 配列 users2 は、配列 users1 よりも 1 要素だけ多い。
		assertThat(users2.size, CoreMatchers.equalTo(users1.size + 1))
		// 配列 users2 には "hello" をもつTodoオブジェクトが含まれている。
		assertThat(users2.map { user: User -> user.name }, hasItem("なかの"))
	}

	@Test
	fun `DELETEリクエストはOKステータスを返す`() {
		// localhost/users に POSTリクエストを送る。このときのボディは {"name": "なかの"}
		val request = UserRequest("なかの")
		restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)

		val requestEntity: RequestEntity<Void> = RequestEntity.delete("http://localhost:$port/users/{name}",request.name).build()
		val responseEntity = restTemplate.exchange(requestEntity, Void::class.java)
		val statusCode = responseEntity.statusCode

		assertThat(statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `DELETEリクエストはUserオブジェクトを削除する`(){
		// localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val request = UserRequest("なかの")
		restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)
		var response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		// このときのレスポンスを users1 として記憶。
		val users1 = response.body!!
		// localhost/users に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val requestEntity: RequestEntity<Void> = RequestEntity.delete("http://localhost:$port/users/{name}",request.name).build()
		val responseEntity = restTemplate.exchange(requestEntity, Void::class.java)
		response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)

		val users2 = response.body!!
		println(users1);
		println(users2);
		assertThat(users1.map { user: User -> user.name }, hasItem("なかの"))
		assertThat(users2.size, CoreMatchers.equalTo(users1.size - 1))
		assertThat(users2.map { user: User -> user.name }, hasItem(not("なかの")))
	}

	@Test
	fun `PUTリクエストはOKステータスを返す`() {
		// localhost/users に POSTリクエストを送る。このときのボディは {"name": "なかの"}
		val request = SeatRequest(1,2,true)

		val requestEntity: RequestEntity<SeatRequest> = RequestEntity.put("http://localhost:$port/seats").contentType(MediaType.APPLICATION_JSON).body(request)
		val responseEntity = restTemplate.exchange(requestEntity, Void::class.java)
		val statusCode = responseEntity.statusCode

		assertThat(statusCode, equalTo(HttpStatus.OK))
	}


	@Test
	fun `PUTリクエストはUserオブジェクトを更新する`() {
		// localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		var response = restTemplate.getForEntity("http://localhost:$port/seats", Array<Seat>::class.java)
		// このときのレスポンスを users1 として記憶。
		val seats1 = response.body!!

		// localhost/users に PUTリクエストを送る。このときのボディは {"seatId":1,"userId":2,"filled":true}
		val request = SeatRequest(1,4,true)
		val requestEntity: RequestEntity<SeatRequest> = RequestEntity.put("http://localhost:$port/seats").contentType(MediaType.APPLICATION_JSON).body(request)
		val responseEntity = restTemplate.exchange(requestEntity, Void::class.java)

		// ふたたび localhost/users に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		response = restTemplate.getForEntity("http://localhost:$port/seats", Array<Seat>::class.java)
		// このときのレスポンスを users2 として記憶。
		val seats2 = response.body!!

		assertThat(seats1[0].id, equalTo(1))
		assertThat(seats1[0].userId, equalTo(1))
		assertThat(seats1[0].filled, equalTo(true))
		assertThat(seats2[0].id, equalTo(1))
		assertThat(seats2[0].userId, equalTo(4))
		assertThat(seats2[0].filled, equalTo(true))
	}

}
