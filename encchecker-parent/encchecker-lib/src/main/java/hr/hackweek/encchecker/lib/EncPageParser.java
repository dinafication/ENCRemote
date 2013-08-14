package hr.hackweek.encchecker.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class EncPageParser {

	private BufferedReader reader;

	public EncPageParser(InputStream stream) throws UnsupportedEncodingException {
		reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	}
	
	public Float getEncState() throws AuthenticationException, IOException{
		String line = null;
		Float ret = null;
		
		while ((line = reader.readLine()) != null) {
			
		}
		
		return ret;
	}
}
