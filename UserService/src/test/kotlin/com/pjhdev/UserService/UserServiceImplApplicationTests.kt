package com.pjhdev.UserService

import com.pjhdev.UserService.util.logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Encoders
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.crypto.SecretKey

@SpringBootTest
class UserServiceImplApplicationTests {
	val logger = logger()

	@Test
	fun contextLoads() {
		val key1 = Jwts.SIG.HS512.key().build();
		val key2 = Jwts.SIG.HS512.key().build();

		logger.info(Encoders.BASE64.encode(key1.encoded))
		logger.info(Encoders.BASE64.encode(key2.encoded))
	}

}
