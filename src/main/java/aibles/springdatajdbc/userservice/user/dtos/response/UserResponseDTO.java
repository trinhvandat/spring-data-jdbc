package aibles.springdatajdbc.userservice.user.dtos.response;

public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
    private String password;

    public UserResponseDTO(UserResponseDTOBuilder userResponseDTOBuilder) {
        this.id = userResponseDTOBuilder.id;
        this.username = userResponseDTOBuilder.username;
        this.email = userResponseDTOBuilder.email;
        this.password = userResponseDTOBuilder.password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static class UserResponseDTOBuilder{
        private int id;
        private String username;
        private String email;
        private String password;

        public UserResponseDTOBuilder id(int id){
            this.id = id;
            return this;
        }

        public UserResponseDTOBuilder username(String username){
            this.username = username;
            return this;
        }

        public UserResponseDTOBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserResponseDTOBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserResponseDTO build(){
            UserResponseDTO userResponseDTO = new UserResponseDTO(this);
            return userResponseDTO;
        }
    }
}
