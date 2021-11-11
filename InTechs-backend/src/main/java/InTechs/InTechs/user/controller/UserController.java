package InTechs.InTechs.user.controller;

import InTechs.InTechs.user.payload.request.IsActiveRequest;
import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userEmail}")
    public ProfileResponse getProfile(@PathVariable String userEmail) throws IOException {
        return userService.getProfile(userEmail);
    }

    @GetMapping("/user")
    public MyPageResponse getMyPage() throws IOException {
        return userService.getMyPage();
    }

    @PatchMapping("/user")
    public void updateUser(@ModelAttribute @Valid ProfileRequest profileRequest) {
        userService.updateUser(profileRequest);
    }

    @GetMapping("/user/project")
    public List<MyProjectListResponse> getMyProject() {
        return userService.getMyProject();
    }

    @PatchMapping("/user/active")
    public void updateActive(@RequestBody @Valid IsActiveRequest isActiveRequest) {
        System.out.println(isActiveRequest);
        userService.updateActive(isActiveRequest);
    }

    @DeleteMapping("/user")
    public void deleteUser(){
        userService.deleteUser();
    }

}
