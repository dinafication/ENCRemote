package hr.hackweek.encchecker.lib;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class EncPageParserTest {

	@Test
	public void testParsing() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("/resault.html");

		EncPageParser epp = new EncPageParser(is);

		try {
			String ret = epp.getEncState();

			assertEquals("60,68 HRK", ret);
		} catch (AuthenticationException e) {
			fail("AuthenticationException");
		} catch (IOException e) {
			fail("IOException");
		}
	}

	@Test
	public void testException() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("/exception.html");

		EncPageParser epp = new EncPageParser(is);

		try {
			epp.getEncState();
			fail("Nije izbacio exception");
		} catch (AuthenticationException e) {
			assertEquals("Korisničko ime ili lozinka nisu prepoznati u sustavu.", e.getLocalizedMessage());
		} catch (IOException e) {
			fail("IOException");
		}

	}
	
	@Test
	public void testNoUsernameException() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("/nousername.html");

		EncPageParser epp = new EncPageParser(is);

		try {
			epp.getEncState();
			fail("Nije izbacio exception");
		} catch (AuthenticationException e) {
			assertEquals("Niste unijeli podatke za pristup privatnom dijelu portala.", e.getLocalizedMessage());
		} catch (IOException e) {
			fail("IOException");
		}

	}
}
