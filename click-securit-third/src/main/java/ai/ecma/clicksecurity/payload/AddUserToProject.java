package ai.ecma.clicksecurity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserToProject {

    private UUID projectId;

    private UUID userId;

    private List<String> listPermission;

}
