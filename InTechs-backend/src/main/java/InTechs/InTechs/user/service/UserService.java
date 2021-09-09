package InTechs.InTechs.user.service;

import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;

import java.util.List;

public interface UserService {

    ProfileResponse getProfile(String email);

    MyPageResponse getMyPage(String token);

    void updateUser(ProfileRequest profileRequest, String token);

    List<MyProjectListResponse> getMyProject(String token);

}
