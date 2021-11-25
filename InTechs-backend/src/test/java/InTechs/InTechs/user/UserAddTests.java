package InTechs.InTechs.user;

import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAddTests {
    @Autowired
    UserRepository userRepository;
    @Test
    public void userAdd(){
        User user = User.builder()
                .email("t02@com")
                .fileName("dd.jpg")
                .name("이름")
                .password("dkssuddkssud")
                .isActive(true)
                .build();
        userRepository.save(user);
    }
}
