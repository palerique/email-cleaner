package br.com.sitedoph.app.tools;

import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class CriadorDeCSVs {

	private static CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] {
				new UniqueHashCode()
		};

		return processors;
	}

	public void criarCSV(String nome, Set<String> valores) throws IOException {
		ICsvBeanWriter beanWriter = null;
		try {

			final URL resource = this.getClass().getClassLoader().getResource("");

			final String fileName = resource.getPath() + "/planilhas-geradas/" + nome + ".csv";
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			beanWriter = new CsvBeanWriter(new FileWriter(file), CsvPreference.STANDARD_PREFERENCE);

			final CellProcessor[] processors = getProcessors();
			final String[] header = new String[] { "" };
			beanWriter.writeHeader(header);

			for (final String customer : valores) {
				beanWriter.write(customer, header, processors);
			}

		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}

	}

}
