package ai.ecma.clicksecurity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJwt {
    private String tokenType="Bearer ";
    private String accessToken;

    public ResponseJwt(String accessToken) {
        this.accessToken = accessToken;
    }
}
