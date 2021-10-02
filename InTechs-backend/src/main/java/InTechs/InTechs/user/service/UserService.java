package InTechs.InTechs.user.service;

import InTechs.InTechs.user.payload.request.IsActiveRequest;
import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.IsActiveResponse;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;

import java.util.List;

public interface UserService {

    ProfileResponse getProfile(String email);

    MyPageResponse getMyPage();

    void updateUser(ProfileRequest profileRequest);

    List<MyProjectListResponse> getMyProject();

    void updateActive(IsActiveRequest isActiveRequest);

}
