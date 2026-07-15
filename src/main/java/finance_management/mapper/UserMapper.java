package finance_management.mapper;

import finance_management.dto.user.UserRequest;
import finance_management.dto.user.UserResponse;
import finance_management.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public static User toEntity(UserRequest userRequest){
        return new User(
                userRequest.getUserName(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );
    }
}
