package br.com.sitedoph.app.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by paulohl on 12/11/2015.
 */
public class CapturadorDeEmailsInvalidos {

	public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";

	public static final String IMAPS = "imaps";

	public static final int N_THREADS = 5;

	private static final Logger LOGGER = LoggerFactory.getLogger(CapturadorDeEmailsInvalidos.class);

	private final String user;

	private final String password;

	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);

	private String host;

	private Collection<String> emailsInvalidos;

	public CapturadorDeEmailsInvalidos(String host, String user, String password) {

		this.host = host;
		this.user = user;
		this.password = password;
	}

	public Collection<String> obterEmailsInvalidos() throws MessagingException, InterruptedException, IOException, ClassNotFoundException {

		SerializadorDeObjetos serializadorDeObjetos = new SerializadorDeObjetos();
		emailsInvalidos = Collections.synchronizedCollection(serializadorDeObjetos.deserializar("emails-invalidos"));

		Properties props = System.getProperties();
		props.setProperty(MAIL_STORE_PROTOCOL, IMAPS);

		Session session = Session.getDefaultInstance(props, null);
		javax.mail.Store store = session.getStore(IMAPS);
		store.connect(host, user, password);

		Folder[] folders = store.getDefaultFolder().list("*");

		for (Folder folder : folders) {
			Thread thread = new Thread(new VerificadorDeEmailsRunnable(folder, this));
			executor.execute(thread);
		}

		executor.shutdown();

		while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
			LOGGER.debug("Aguardando a Finalização das Threads!");
		}

		return emailsInvalidos;
	}

	public Collection<String> getEmailsInvalidos() {
		return emailsInvalidos;
	}
}
