package org.mcrest.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
 
/**
 * 	登录Token的生成和解析
 * 
 */
public class Token {
 
	/** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfjeffGKL */
	public static final String SECRET = "JKKLJOoasdlfjeffGKL";
	/** token 过期时间: 1天 */
	public static final int calendarField = Calendar.DATE;
	public static final int calendarInterval = 1;
 
	/**
	 * JWT生成Token.<br/>
	 * 
	 * JWT构成: header, payload, signature
	 * 
	 * @param user_id
	 *            登录成功后用户user_id, 参数user_id不可传空
	 */
	public static String createToken(UUID user_id) {
		Date iatDate = new Date();
		// expire time
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(calendarField, calendarInterval);
		Date expiresDate = nowTime.getTime();
 
		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
 
		// build token
		// param backups {iss:Service, aud:APP}
		String token = JWT.create().withHeader(map) // header
				.withClaim("iss", "Service") // payload
				.withClaim("aud", "Client")
				.withClaim("user_id", null == user_id ? null : user_id.toString())
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(SECRET)); // signature
 
		return token;
	}
 
	/**
	 * 解密Token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			jwt = verifier.verify(token);
		} catch (Exception e) {
			// e.printStackTrace();
			// token 校验失败, 抛出Token验证非法异常
		}
		return jwt.getClaims();
	}
 
	/**
	 * 根据Token获取user_id
	 * 
	 * @param token
	 * @return user_id
	 */
	public static String getUser_Id(String token) {
		Map<String, Claim> claims = verifyToken(token);
		Claim user_id_claim = claims.get("user_id");
		if (null == user_id_claim || (user_id_claim.asString()).isEmpty()) {
			// token 校验失败, 抛出Token验证非法异常
		}
		return String.valueOf(user_id_claim.asString());
	}
}
