package finance_management.dto.user;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class UserResponse {

    private String userName;

    private String email;

    private LocalDateTime createdAt;

    public UserResponse() {
    }

    public UserResponse(String userName, String email, LocalDateTime createdAt) {
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
