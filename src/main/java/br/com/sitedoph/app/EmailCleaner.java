package br.com.sitedoph.app;

import br.com.sitedoph.app.tools.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class EmailCleaner {

	public static final String HOST = "pop3.email.email";

	public static final String USER = "email@email.email";

	public static final String PASSWORD = "password";

	public static void main(String[] args) throws MessagingException, InterruptedException, IOException, ClassNotFoundException {
		//obter set de e-mails inválidos
		CapturadorDeEmailsInvalidos capturadorDeEmailsInvalidos = new CapturadorDeEmailsInvalidos(HOST, USER, PASSWORD);
		Collection<String> emailsInvalidos = capturadorDeEmailsInvalidos.obterEmailsInvalidos();

		//obter set de e-mails cadastrados
		LeitorDeCSV leitorDeCSV = new LeitorDeCSV("export-locaweb27-06-13.csv");
		final Set<String> todosOsEmails = leitorDeCSV.ler();

		//gerar set de e-mails válidos
		SanitizadorDeListaDeEmails sanitizadorDeListaDeEmails = new SanitizadorDeListaDeEmails(todosOsEmails, emailsInvalidos);
		Set<String> emailsValidos = sanitizadorDeListaDeEmails.sanitiza();

		try {
			//salvar planilha e csv com todos os tipos de e-mail
			CriadorDePlanilhasDoExcel criadorDePlanilhasDoExcel = new CriadorDePlanilhasDoExcel();
			criadorDePlanilhasDoExcel.criarPlanilha("emails-validos", emailsValidos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			CriadorDeCSVs criadorDeCSVs = new CriadorDeCSVs();
			criadorDeCSVs.criarCSV("emails-validos", emailsValidos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			SerializadorDeObjetos serializadorDeObjetos = new SerializadorDeObjetos();
			serializadorDeObjetos.serializar("emails-invalidos", emailsInvalidos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
