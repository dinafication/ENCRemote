package hr.hackweek.encchecker.lib;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class EncPageParser {
	private TagNode rootNode;

	public EncPageParser(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

		HtmlCleaner cleaner = new HtmlCleaner();
		rootNode = cleaner.clean(reader);
	}

	public String getEncState() throws AuthenticationException, IOException {
		String ret = null;
		boolean isRecursive = true;
		boolean isCaseSensitive = true;

		TagNode errorDiv = rootNode.findElementByAttValue("id", "error_box", isRecursive, isCaseSensitive);
		if (errorDiv != null) {
			TagNode errorMessageNode = errorDiv.findElementByAttValue("class", "errorMessage", isRecursive, isCaseSensitive);
			CharSequence errorMessage = errorMessageNode.findElementByName("span", isRecursive).getText();

			throw new AuthenticationException(errorMessage.toString());
		} else {
			// Dohvatimo tablicu
			TagNode table = rootNode.findElementByName("table", isRecursive);

			TagNode[] rows = table.getElementsByName("tr", isRecursive);

			// Podatak o stanju se nalazi na drugom retku
			TagNode dataRow = rows[1];

			TagNode[] td = dataRow.getElementsByName("td", isRecursive);

			// Podatak o stanju se nalazi u 4 koloni
			ret = td[3].getText().toString();
			ret = ret.trim();
		}

		return ret;
	}
}
