package br.com.sitedoph.app.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paulohl on 12/11/2015.
 */
public class SanitizadorDeListaDeEmails {
	private final Collection<String> todosOsEmails;

	private final Collection<String> emailsInvalidos;

	public SanitizadorDeListaDeEmails(Collection<String> todosOsEmails, Collection<String> emailsInvalidos) {
		this.todosOsEmails = todosOsEmails;
		this.emailsInvalidos = emailsInvalidos;
	}

	public Set<String> sanitiza() {

		Set<String> emailsValidos = new HashSet<>();

		for (String emailEmVerificacao : todosOsEmails) {
			if (!emailsInvalidos.contains(emailEmVerificacao)) {
				emailsValidos.add(emailEmVerificacao);
			}
		}

		return emailsValidos;
	}
}
