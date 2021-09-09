package InTechs.InTechs.user.controller;

import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userEmail}")
    public ProfileResponse getProfile(@PathVariable String userEmail) {
        return userService.getProfile(userEmail);
    }

    @GetMapping("/user")
    public MyPageResponse getMyPage(@RequestHeader("Authorization") String token) {
        return userService.getMyPage(token);
    }

    @PatchMapping("/user")
    public void updateUser(@RequestHeader("Authorization") String token) {
        return userService.updateUser(token);
    }

}
