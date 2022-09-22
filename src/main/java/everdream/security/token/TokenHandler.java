package everdream.security.token;

import javax.servlet.http.HttpServletRequest;

public interface TokenHandler {
    String generateJwtToken ();
    String getEmailFromJwtToken (String token);
    String parseJwtToken (HttpServletRequest request);
    boolean validateJwtToken (String token);
}
