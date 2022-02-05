package aibles.springdatajdbc.userservice.user.models;

import aibles.springdatajdbc.userservice.user.models.compositekey.UserRoleId;
import org.springframework.data.annotation.Id;

public class UserRole {

    @Id
    private UserRoleId id;

    public UserRole() {
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}
