package aibles.springdatajdbc.userservice.repositories;

import aibles.springdatajdbc.userservice.models.UserInfo;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IUserInfoRepository extends PagingAndSortingRepository<UserInfo, Integer> {

    @Query("SELECT CASE WHEN COUNT(u.id)> 0 THEN true ELSE false END FROM user_info u WHERE u.username = :username")
    boolean isExistUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u.id)> 0 THEN true ELSE false END FROM user_info u WHERE u.email = :email")
    boolean isExistEmail(@Param("email") String email);
}
