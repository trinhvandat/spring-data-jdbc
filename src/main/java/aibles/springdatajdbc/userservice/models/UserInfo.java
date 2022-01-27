package aibles.springdatajdbc.userservice.models;

import org.springframework.data.annotation.Id;

public class UserInfo {

    @Id
    private Integer id;
    private String username;
    private String email;
    private String password;

    public UserInfo() {
    }

    public UserInfo(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.username = userBuilder.username;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder {
        private Integer id;
        private String username;
        private String email;
        private String password;

        public UserBuilder id(int id){
            this.id = id;
            return this;
        }

        public UserBuilder username(String username){
            this.username = username;
            return this;
        }

        public UserBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserInfo build() {
            UserInfo userInfo = new UserInfo(this);
            return userInfo;
        }
    }
}