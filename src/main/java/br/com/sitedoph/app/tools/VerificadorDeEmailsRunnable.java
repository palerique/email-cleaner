package br.com.sitedoph.app.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

/**
 * Created by paulohl on 12/11/2015.
 */
public class VerificadorDeEmailsRunnable implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerificadorDeEmailsRunnable.class);

	private Folder folder;

	private CapturadorDeEmailsInvalidos capturadorDeEmailsInvalidos;

	public VerificadorDeEmailsRunnable(Folder folder, CapturadorDeEmailsInvalidos capturadorDeEmailsInvalidos) {
		this.folder = folder;
		this.capturadorDeEmailsInvalidos = capturadorDeEmailsInvalidos;
	}

	public void run() {

		try {
			if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
				final String fullName = folder.getFullName();
				final int messageCount = folder.getMessageCount();

				LOGGER.debug("Verificando pasta " + fullName + " - quantidade de mensagens: " + messageCount);

				if (messageCount > 0) {
					folder.open(Folder.READ_ONLY);
					Message[] messages = folder.getMessages();
					for (int i = 0; i < messages.length; i++) {
						final Message mensagemDaIteracao = messages[i];
						final String assunto = mensagemDaIteracao.getSubject();
						final Address enderecoDe = mensagemDaIteracao.getFrom()[0];
						final Date recebidaEm = mensagemDaIteracao.getSentDate();

						LOGGER.debug(String.format("Pasta: %-40s Verificando a mensagem: %-160s de: %-40s recebida em: %-40s", fullName, assunto, enderecoDe, recebidaEm));

						BufferedReader bufReader = new BufferedReader(new StringReader(mensagemDaIteracao.getContent().toString()));
						String line;
						while ((line = bufReader.readLine()) != null) {
							if (line.contains("SMTP error from remote mail server after RCPT TO:<")) {
								final String[] split = line.split("<");
								final String[] split1 = split[1].split(">");
								capturadorDeEmailsInvalidos.getEmailsInvalidos().add(split1[0]);
								LOGGER.debug(">>> Quantidade de e-mails inválidos encontrados: " + capturadorDeEmailsInvalidos.getEmailsInvalidos().size());
								LOGGER.debug(String.format("Email inválido encontrado: %-40s assunto do e-mail inválido: %-160s", split1[0], assunto));
							}
						}

						LOGGER.debug(String.format(fullName + " Mensagens processadas: %s de: %s", (i + 1), messages.length));
					}
				}

			}
		} catch (MessagingException | IOException e) {
			LOGGER.error("Erro acessando e-mail!!!", e);
		}
	}
}
