//package com.travelcompass.api.oauth.repository;
//
//import com.travelcompass.api.oauth.jwt.RefreshToken;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.Optional;
//
//public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
//    Optional<RefreshToken> findByRefreshToken(String refreshToken); //reissue
//    boolean existsById(String username);
//    void deleteById(String username);
//}
