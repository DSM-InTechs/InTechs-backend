package InTechs.InTechs.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyProjectListResponse {

    private final int id;
    private final String name;
    private final String image;
    private final LocalDateTime createdAt;

}
