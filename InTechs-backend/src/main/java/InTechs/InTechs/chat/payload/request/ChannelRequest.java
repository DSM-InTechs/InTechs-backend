package InTechs.InTechs.chat.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChannelRequest {

    private final String newName;

}
