package ai.ecma.clicksecurity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private Integer id;

    private String name;

    private UUID projectId;

    public StatusDto(String name, UUID projectId) {
        this.name = name;
        this.projectId = projectId;
    }
}
