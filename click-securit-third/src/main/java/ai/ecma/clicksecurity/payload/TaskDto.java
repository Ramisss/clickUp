package ai.ecma.clicksecurity.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private UUID id;

    @NotBlank
    private String name;

    private UUID projectId;

    private UUID statusId;



    public TaskDto(@NotBlank String name, UUID projectId,UUID statusId) {
        this.name = name;
        this.projectId = projectId;
        this.statusId = statusId;
    }

    public TaskDto(UUID projectId) {
        this.projectId = projectId;
    }
}
