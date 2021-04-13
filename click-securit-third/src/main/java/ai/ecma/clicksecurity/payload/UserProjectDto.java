package ai.ecma.clicksecurity.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectDto {
    private UUID id;

    private UUID userId;

    private UUID projectId;

}
