package LibraryProyect.services;

import LibraryProyect.dtos.UserRequest;
import LibraryProyect.dtos.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse create(UserRequest request);
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);

}
