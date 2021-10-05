package InTechs.InTechs.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash(value = "redis_hash")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String email;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long refreshTokenTime;

    public RefreshToken update(String refreshToken, Long refreshTokenTime) {
        this.refreshToken = refreshToken;
        this.refreshTokenTime = refreshTokenTime;

        return this;
    }
}
