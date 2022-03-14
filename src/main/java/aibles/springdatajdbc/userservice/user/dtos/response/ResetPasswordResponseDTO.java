package aibles.springdatajdbc.userservice.user.dtos.response;

public class ResetPasswordResponseDTO {

    private String accessToken;

    public ResetPasswordResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
