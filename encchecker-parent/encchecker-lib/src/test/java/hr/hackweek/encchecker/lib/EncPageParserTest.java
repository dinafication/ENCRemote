package hr.hackweek.encchecker.lib;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class EncPageParserTest {

	@Test
	public void testParsing() {
		fail("Not yet implemented");
	}

	@Test
	public void testException() throws FileNotFoundException, UnsupportedEncodingException {
		InputStream is = this.getClass().getResourceAsStream("/exception.html");
		
		EncPageParser epp = new EncPageParser(is);
		
		try {
			Float ret = epp.getEncState();
			fail("Nije izbacio exception");
		} catch (AuthenticationException e) {
		} catch (IOException e) {
			fail("IOException");
		}
		
		
	}
}
