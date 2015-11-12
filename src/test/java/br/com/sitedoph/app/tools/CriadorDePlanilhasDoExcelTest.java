package br.com.sitedoph.app.tools;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class CriadorDePlanilhasDoExcelTest {

	@Test
	public void testCriarPlanilha() throws Exception {
		Set<String> array = new HashSet<>();
		array.add("xpto");
		new CriadorDePlanilhasDoExcel().criarPlanilha("nome", array);
	}
}