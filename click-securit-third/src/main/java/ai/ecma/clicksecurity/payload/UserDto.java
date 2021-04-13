package ai.ecma.clicksecurity.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID uuid;

    private  String firstName;

    private String lastName;


    @NotBlank
    private String password;


    private String userName;


    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private Set<Integer> rolesId;


}
