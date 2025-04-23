package org.jers.generateapirest.dependency.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalVersionDetector {
	public static void main(String[] args) {
		try {
			String mavenVersion = getMavenVersion();
			if (mavenVersion != null) {
				System.out.println("Maven Version Detected:");
				System.out.println(mavenVersion);
			} else {
				System.out.println("Maven is not installed or not accessible in the PATH.");
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error detecting Maven version: " + e.getMessage());
		}
	}

	public static String getMavenVersion() throws IOException, InterruptedException {
		String command = "mvn -v";

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command.split(" "));
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		Thread.sleep(2000);

		StringBuilder output = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append(System.lineSeparator());
			}
		}

		int exitCode = process.waitFor();
		if (exitCode == 0) {
			return output.toString();
		} else {
			return null;
		}
	}
}