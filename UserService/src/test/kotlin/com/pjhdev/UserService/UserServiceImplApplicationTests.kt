package com.pjhdev.UserService

import com.pjhdev.UserService.util.logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@SpringBootTest
class UserServiceImplApplicationTests {
	val logger = logger()

	@Test
	fun `generate key and valid key`() {
		val now = Instant.now()
		val accessExpiration = now.plus(1, ChronoUnit.HOURS)

		// key generate
		val key = Jwts.SIG.HS512.key().build();
		val keyString = Encoders.BASE64.encode(key.encoded);

		val accessToken = Jwts.builder()
			.subject("parkjonghyun")
			.claim("role", "ADMIN")
			.expiration(Date.from(accessExpiration))
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("qzXxcu0eMmiTljmLeiikLY3L2ULGDwbLbNMsvT/oEJ0lr3GGEtx4zVygOwZB94yM+HxmsTYyt09xe8WsxSUf1A==")))
			.compact()

		val claim = Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("qzXxcu0eMmiTljmLeiikLY3L2ULGDwbLbNMsvT/oEJ0lr3GGEtx4zVygOwZB94yM+HxmsTYyt09xe8WsxSUf1A==")))
			.build()
			.parseSignedClaims(accessToken)

		logger.info(claim.toString())
	}

	@Test
	fun `generate key and valid key throw SignatureException`() {
		val now = Instant.now()
		val accessExpiration = now.plus(1, ChronoUnit.HOURS)

		val accessToken = Jwts.builder()
			.subject("parkjonghyun")
			.expiration(Date.from(accessExpiration))
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("qzXxcu0eMmiTljmLeiikLY3L2ULGDwbLbNMsvT/oEJ0lr3GGEtx4zVygOwZB94yM+HxmsTYyt09xe8WsxSUf1A==")))
			.compact()

		val claim = Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("ckoQEuWYg3fjSDsb30HPDK9xcTFogHpHkpgj7EBofs+/Ig5Y3CZ6I9gEKK1wcacDolQtukXFcAFJulWvRvCLdA==")))
			.build()
			.parseSignedClaims(accessToken)

		logger.info(claim.toString())
	}

	@Test
	fun `generate key and valid key throw ExpiredJwtException`() {
		val now = Instant.now()
		val accessExpiration = now.minus(1, ChronoUnit.HOURS)

		val accessToken = Jwts.builder()
			.subject("parkjonghyun")
			.expiration(Date.from(accessExpiration))
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("qzXxcu0eMmiTljmLeiikLY3L2ULGDwbLbNMsvT/oEJ0lr3GGEtx4zVygOwZB94yM+HxmsTYyt09xe8WsxSUf1A==")))
			.compact()

		val claim = Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("qzXxcu0eMmiTljmLeiikLY3L2ULGDwbLbNMsvT/oEJ0lr3GGEtx4zVygOwZB94yM+HxmsTYyt09xe8WsxSUf1A==")))
			.build()
			.parseSignedClaims(accessToken)

		logger.info(claim.toString())
	}

}
