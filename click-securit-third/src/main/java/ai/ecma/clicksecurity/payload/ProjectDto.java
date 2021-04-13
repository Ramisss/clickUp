package ai.ecma.clicksecurity.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private UUID userId;

    private UUID projectId;

    private String name;

    public ProjectDto(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public ProjectDto(String name) {
        this.name = name;
    }
}
