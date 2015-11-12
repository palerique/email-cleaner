package br.com.sitedoph.app.tools;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by paulohl on 12/11/2015.
 */
public class SerializadorDeObjetos {
	public void serializar(String nome, Collection<String> valores) throws IOException {
		final URL resource = this.getClass().getClassLoader().getResource("");

		FileOutputStream os;
		File fileDir = new File(resource.getPath() + "/planilhas/");
		fileDir.mkdirs();
		File file = new File(fileDir, nome + ".jo");
		os = new FileOutputStream(file);

		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(valores);
		out.close();
	}

	public Collection<String> deserializar(String nome) throws IOException, ClassNotFoundException {

		final URL resource = this.getClass().getClassLoader().getResource("");

		FileInputStream inputStream;
		File fileDir = new File(resource.getPath() + "/planilhas/");
		fileDir.mkdirs();
		File file = new File(fileDir, nome + ".jo");

		Collection<String> set;

		try {
			inputStream = new FileInputStream(file);

			ObjectInputStream in = new ObjectInputStream(inputStream);
			set = (Collection<String>) in.readObject();
		} catch (FileNotFoundException e) {
			return new HashSet<>();
		}

		return set;
	}
}
