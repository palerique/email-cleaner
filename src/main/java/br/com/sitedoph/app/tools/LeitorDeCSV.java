package br.com.sitedoph.app.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class LeitorDeCSV {

	private Set<String> emails = new HashSet<>();

	private String csvFile;

	public LeitorDeCSV(String csvFile) {

		this.csvFile = csvFile;
	}

	public static void main(String[] args) {
		final Set<String> emails = new LeitorDeCSV("export-locaweb27-06-13.csv").ler();

		System.out.println(emails.size());

	}

	public Set<String> ler() {

		BufferedReader br = null;
		String line;
		String cvsSplitBy = ",";

		try {
			final URL resource = this.getClass().getClassLoader().getResource(csvFile);
			br = new BufferedReader(new FileReader(resource.getPath()));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] linha = line.split(cvsSplitBy);

				for (String s : linha) {
					final String email = s.replace("\"", "").trim();
					emails.add(email);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return emails;
	}

}
