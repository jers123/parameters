package org.jers.generateapirest.dependency.maven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.dependency.DependencyData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OnlineVersionDetector {

	private static final String MAVEN_REPO_URL = "https://repo.maven.apache.org/maven2/";

	public static void main(String[] args) {
		List<String> versions = fetchMavenVersions(getUrl(Constants.H2));
		for (String version : versions) {
			System.out.println(version);
		}
		Constants.H2.setVersion(versions.getFirst());
		System.out.println("spring " + Constants.H2.getVersion());
	}

	private static String getUrl(DependencyData data) {
		return MAVEN_REPO_URL + data.getRepositoryUrl();
	}

	public static String getVersion(DependencyData data) {
		try {
			List<String> versions = fetchMavenVersions(getUrl(data));
			return versions.getLast();
		} catch (Exception e) {
			return Config.VERSION_DEFAULT;
		}
	}

	public static List<String> fetchMavenVersions(String url) {
		System.out.println(url);
		List<String> versions = new ArrayList<>();
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a");

			for (int i = 1; i < links.size(); i++) {
				String version = links.get(i).text().replace("/", "");
				//String date = links.get(i).parent().ownText().trim().substring(0, 16);
				if (!contains(version, Constants.getProperties(Constants.MAVEN_VERSION_EXCEPTIONS, ","))) {
					versions.add(version);
				}
			}
			Collections.sort(versions, new VersionComparator());
		} catch (IOException e) {
			versions.add(Config.VERSION_DEFAULT);
		}
		return versions;
	}

	private static boolean contains(String text, String[] exceptions) {
		boolean result = false;
		for (int i = 1; i < exceptions.length; i++) {
			if (text.toLowerCase().contains(exceptions[i].toLowerCase())) {
				result = true;
				break;
			}
		}
		return result;
	}

	private static class VersionComparator implements Comparator<String> {
		@Override
		public int compare(String v1, String v2) {
			String[] parts1 = v1.split("\\.");
			String[] parts2 = v2.split("\\.");

			int length = Math.max(parts1.length, parts2.length);
			for (int i = 0; i < length; i++) {
				String part1 = i < parts1.length ? parts1[i] : "";
				String part2 = i < parts2.length ? parts2[i] : "";

				int result = comparePart(part1, part2);
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}

		private int comparePart(String part1, String part2) {
			String[] subParts1 = part1.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
			String[] subParts2 = part2.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");

			for (int i = 0; i < Math.max(subParts1.length, subParts2.length); i++) {
				String subPart1 = i < subParts1.length ? subParts1[i] : "";
				String subPart2 = i < subParts2.length ? subParts2[i] : "";

				if (subPart1.matches("\\d+") && subPart2.matches("\\d+")) {
					int num1 = Integer.parseInt(subPart1);
					int num2 = Integer.parseInt(subPart2);
					if (num1 != num2) {
						return num1 - num2;
					}
				} else {
					int result = subPart1.compareTo(subPart2);
					if (result != 0) {
						return result;
					}
				}
			}
			return 0;
		}
	}
}