
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


public class IdTokenVerifierAndParser {
    private static final String GOOGLE_CLIENT_ID = "850398037024-up0jr97g6s6fon0bmlt78vi3qr0b60n3.apps.googleusercontent.com";
    public static GoogleIdToken.Payload getPayload (String tokenString) throws Exception {
        JacksonFactory jacksonFactory = new JacksonFactory();		
        GoogleIdTokenVerifier googleIdTokenVerifier =new GoogleIdTokenVerifier(new NetHttpTransport(), jacksonFactory);
		System.out.println(" jefef    "+tokenString+"       "+googleIdTokenVerifier);
		GoogleIdToken token=GoogleIdToken.parse(jacksonFactory, tokenString);		
		System.out.println("  jefef  "+tokenString);		
        if(googleIdTokenVerifier.verify(token)) 
		{			
			GoogleIdToken.Payload payload = token.getPayload();
            if(!GOOGLE_CLIENT_ID.equals(payload.getAudience())) 
			{
                //throw new IllegalArgumentException("Audience mismatch");
				return null;
            } 
			else if(!GOOGLE_CLIENT_ID.equals(payload.getAuthorizedParty())) 
			{
                //throw new IllegalArgumentException("Client ID mismatch");
				return null;
            }
            return payload;
        } 
		else
		{
			return null;
            //throw new IllegalArgumentException("id token cannot be verified");
        }
    }
}


