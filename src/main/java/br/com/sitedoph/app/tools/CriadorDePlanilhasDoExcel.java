package br.com.sitedoph.app.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class CriadorDePlanilhasDoExcel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CriadorDePlanilhasDoExcel.class);

	public void criarPlanilha(String nome, Set<String> valores) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();

		int i = 0;
		for (String invalido : valores) {
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(1);
			cell.setCellValue(invalido);
			i++;
		}

		final URL resource = this.getClass().getClassLoader().getResource("");

		FileOutputStream os;
		File fileDir = new File(resource.getPath() + "/planilhas/");
		fileDir.mkdirs();
		File file = new File(fileDir, nome + ".xls");

		try {
			os = new FileOutputStream(file);
			workbook.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.debug(String.format("Path do arquivo gerado: %-400s", file.getPath()));

	}
}
