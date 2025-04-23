package org.jers.generateapirest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateFile {
	public static void writeFile(String filePath, String content) {
		Path path = Paths.get(filePath);
		try {
			Files.createDirectories(path.getParent());
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
				writer.write(content);
				System.out.println(filePath);
			}
		} catch (IOException e) {
			System.err.println("Error al crear o escribir en el archivo: " + e.getMessage());
		}
	}

	public static boolean deleteExist(File folder) {
		boolean result = true;
		if (folder.exists()) {
			if (folder.isDirectory()) {
				File[] fileList = folder.listFiles();
				for (File file : fileList) {
					result = result && deleteExist(file);
				}
			}
			result = result && folder.delete();
		}
		return result;
	}
}
