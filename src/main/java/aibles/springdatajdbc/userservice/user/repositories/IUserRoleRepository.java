package aibles.springdatajdbc.userservice.user.repositories;

import aibles.springdatajdbc.userservice.user.models.UserRole;
import aibles.springdatajdbc.userservice.user.models.compositekey.UserRoleId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserRoleRepository extends PagingAndSortingRepository<UserRole, UserRoleId> {
}
