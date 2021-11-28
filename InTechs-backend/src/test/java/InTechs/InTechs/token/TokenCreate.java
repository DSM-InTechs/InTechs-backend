package InTechs.InTechs.token;

import InTechs.InTechs.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenCreate {
    @Autowired
    JwtTokenProvider provider;
    @Test
    public void createToken(){
        String email = "ddd@hs.kr";
        provider.generateAccessToken(email);
    }
}
