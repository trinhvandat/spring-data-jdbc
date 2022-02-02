package aibles.springdatajdbc.userservice.user.repositories;

import aibles.springdatajdbc.userservice.user.models.UserInfo;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserInfoRepository extends PagingAndSortingRepository<UserInfo, Integer> {

    @Query("SELECT CASE WHEN COUNT(u.id)> 0 THEN true ELSE false END FROM user_info u WHERE u.username = :username")
    boolean isExistUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u.id)> 0 THEN true ELSE false END FROM user_info u WHERE u.email = :email")
    boolean isExistEmail(@Param("email") String email);

    @Query("SELECT u.id, u.username, u.password, u.email, u.is_active FROM user_info u WHERE u.username = :username")
    Optional<UserInfo> retrieveUserByUsername(@Param("username") String username);

    @Query("SELECT u.id, u.username, u.password, u.email FROM user_info u WHERE u.email = :email")
    Optional<UserInfo> retrieveUserByEmail(@Param("email") String email);
}
