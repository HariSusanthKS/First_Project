import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.*;
import java.util.Collections;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;


public class GoogleLoginModule implements LoginModule {

  private CallbackHandler handler;
  private Subject subject;
  private UserPrincipal userPrincipal;
  private RolePrincipal rolePrincipal;
  private String login;
  private List<String> userGroups;

  public void initialize(Subject subject,
      CallbackHandler callbackHandler,
      Map<String, ?> sharedState,
      Map<String, ?> options) {

    handler = callbackHandler;
    this.subject = subject;
  }
  
  public boolean login() throws LoginException {

    Callback[] callbacks = new Callback[2];
    callbacks[0] = new NameCallback("login");
    callbacks[1] = new PasswordCallback("password", true);

    try 
	{
		handler.handle(callbacks);
		String name = ((NameCallback) callbacks[0]).getName();
		String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
			  
		System.out.println(name+"Payload googlr 1 password "+password);
		GoogleIdToken.Payload payLoad=null;
		System.out.println(payLoad+"Payload byte1 ");
		try 
		{
			payLoad = IdTokenVerifierAndParser.getPayload(password);
			System.out.println(payLoad+"Payload byte 2 ");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		if(payLoad!=null&&name.equals("harisusanthzoho@gmail.com") )
		{
			System.out.println(payLoad+"Payload byte 3 name: "+name);
			login = name;
			userGroups = new ArrayList<String>();
			userGroups.add("admin");
			return true;		  
		}
		return false;
    } catch (IOException e) {
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      throw new LoginException(e.getMessage());
    }

  }


  public boolean commit() throws LoginException 
  {
	userPrincipal = new UserPrincipal(login);
	subject.getPrincipals().add(userPrincipal);
    if (userGroups != null && userGroups.size() > 0) {
      for (String groupName : userGroups) {
        rolePrincipal = new RolePrincipal(groupName);
        subject.getPrincipals().add(rolePrincipal);
      }
    }

    return true;
  }


  public boolean abort() throws LoginException {
    return false;
  }


  public boolean logout() throws LoginException {
    subject.getPrincipals().remove(userPrincipal);
    subject.getPrincipals().remove(rolePrincipal);
    return true;
  }

}