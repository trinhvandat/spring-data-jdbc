package aibles.springdatajdbc.userservice.user.repositories;

import aibles.springdatajdbc.userservice.user.models.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IRoleRepository extends PagingAndSortingRepository<Role, Integer> {
}
