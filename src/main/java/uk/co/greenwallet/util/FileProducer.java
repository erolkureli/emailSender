package uk.co.greenwallet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileProducer {

	public static void printContent(File file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));

		while ((br.readLine()) != null) {

		}

		br.close();
	}
}
