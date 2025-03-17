package org.jers.generateapirest.dependency.maven;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.dependency.DependencyData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OnlineVersionDetector {
    
    private static final String MAVEN_REPO_URL = "https://repo.maven.apache.org/maven2/";

    public static void main(String[] args) throws IOException {
        List<String> versions = fetchMavenVersions(getUrl(Constants.SPRINGDOC_OPENAPI_STARTER_WEBMVC_UI));
        for (String version : versions) {
            System.out.println(version);
        }
        Constants.SPRING_BOOT_STARTER.setVersion(versions.getFirst());
        System.out.println("spring " + Constants.SPRING_BOOT_STARTER.getVersion());
    }
    
    private static String getUrl(DependencyData data) {
        return MAVEN_REPO_URL + data.getRepositoryUrl();
    }
    
    public static String getVersion(DependencyData data) {
        try {
            List<String> versions = fetchMavenVersions(getUrl(data));
            data.setVersion(versions.getFirst());
            return data.getVersion();
        } catch (IOException e) {
            return Config.MAVEN_VERSION_DEFAULT;
        }
    }

    private static void sortVersions(List<String> versions) {
        Collections.sort(versions, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] parts1 = s1.split("\\.");
                String[] parts2 = s2.split("\\.");
                int i = 0;
                while (i < parts1.length || i < parts2.length) {
                    try {
                        int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
                        int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
                        int comparison = Integer.compare(num1, num2);
                        if (comparison != 0) {
                            return comparison;
                        }
                    } catch (NumberFormatException e) {
                        String str1 = i < parts1.length ? parts1[i] : "";
                        String str2 = i < parts2.length ? parts2[i] : "";
                        int comparison = str1.compareTo(str2);
                        if (comparison != 0) {
                            return comparison;
                        }
                    }
                    i++;
                }
                return 0;
            }
        });
    }
    
    public static List<String> fetchMavenVersions(String url) throws IOException {
        System.out.println(url);
        List<String> versions = new ArrayList<>();

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch Maven versions. HTTP error code: " + connection.getResponseCode());
        }

        Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
        Elements links = doc.select("a[href]"); // Seleccionar todos los enlaces

        for (Element link : links) {
            String href = link.attr("href");
            if (href.matches("\\d+\\.\\d+(\\.\\d+)?/")) {
                versions.add(href.replace("/", ""));
            }
        }
        sortVersions(versions);
        Collections.reverse(versions);
        return versions;
    }

    public static List<String> filterStableVersions(List<String> versions) {
        List<String> stableVersions = new ArrayList<>();
        for (String version : versions) {
            if (!version.toLowerCase().contains("beta") && !version.toLowerCase().contains("alpha")) {
                stableVersions.add(version);
            }
        }
        return stableVersions;
    }
}