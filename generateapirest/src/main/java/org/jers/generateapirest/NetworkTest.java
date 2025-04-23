package org.jers.generateapirest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.time.Instant;

public class NetworkTest {
	public static double testDownloadSpeed(String fileUrl) {
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			long startTime = System.currentTimeMillis();
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			long totalBytesRead = 0;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				totalBytesRead += bytesRead;
			}

			long endTime = System.currentTimeMillis();
			inputStream.close();

			double timeTakenSec = (endTime - startTime) / 1000.0;
			double downloadSpeedMbps = (totalBytesRead * 8) / (timeTakenSec * 1_000_000);

			return downloadSpeedMbps;
		} catch (Exception e) {
			System.err.println("Error en la prueba de descarga: " + e.getMessage());
			return 0;
		}
	}

	// Mide la velocidad de subida
	public static double testUploadSpeed(String targetUrl) {
		try {
			URL url = new URL(targetUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			byte[] data = new byte[1_000_000]; // Enviar 1 MB de datos
			long startTime = System.currentTimeMillis();
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();

			long endTime = System.currentTimeMillis();

			double timeTakenSec = (endTime - startTime) / 1000.0;
			double uploadSpeedMbps = (data.length * 8) / (timeTakenSec * 1_000_000);

			return uploadSpeedMbps;
		} catch (Exception e) {
			System.err.println("Error en la prueba de subida: " + e.getMessage());
			return 0;
		}
	}

	// Mide el ping
	public static long testPing(String host) {
		try {
			InetAddress inet = InetAddress.getByName(host);
			Instant start = Instant.now();
			boolean status = inet.isReachable(5000);
			Instant end = Instant.now();

			if (status) {
				return java.time.Duration.between(start, end).toMillis();
			} else {
				System.err.println("Host no accesible.");
				return -1;
			}
		} catch (IOException e) {
			System.err.println("Error en el ping: " + e.getMessage());
			return -1;
		}
	}

	public static void main(String[] args) {
		// URLs y host para la prueba
		String downloadUrl = "https://nbg1-speed.hetzner.com//1GB.bin"; // Archivo de prueba
		String uploadUrl = "https://httpbin.org/post"; // Servidor de prueba para subida
		String pingHost = "google.com";

		System.out.println("Prueba de velocidad de Internet");
		System.out.println("-------------------------------");

		// Prueba de descarga
		double downloadSpeed = testDownloadSpeed(downloadUrl);
		if (downloadSpeed > 0) {
			System.out.printf("Velocidad de descarga: %.2f Mbps\n", downloadSpeed);
		} else {
			System.out.println("Error en la prueba de descarga.");
		}

		// Prueba de subida
		double uploadSpeed = testUploadSpeed(uploadUrl);
		if (uploadSpeed > 0) {
			System.out.printf("Velocidad de subida: %.2f Mbps\n", uploadSpeed);
		} else {
			System.out.println("Error en la prueba de subida.");
		}

		// Prueba de ping
		long ping = testPing(pingHost);
		if (ping >= 0) {
			System.out.printf("Latencia (ping): %d ms\n", ping);
		} else {
			System.out.println("Error en la prueba de ping.");
		}
	}
}