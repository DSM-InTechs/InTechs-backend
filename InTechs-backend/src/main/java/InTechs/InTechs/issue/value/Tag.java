package InTechs.InTechs.issue.value;

import lombok.*;

@ToString
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag {
    private String tag;

    public static Tag valueOf(String tag) {
        return new Tag(tag);
    }
}
