package ai.ecma.clicksecurity.payload;

import lombok.Data;

@Data
public class ReqSignIn {
    private String email;
    private String password;
}
