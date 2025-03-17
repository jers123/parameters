package org.jers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Prueba {
    public static void main(String[] args) {

    }

    public static void maina() {
        List<String> names = new ArrayList<>();
        names.add("civilStatusName");
        names.add("==================================================");
        names.add("documentTypeName");
        names.add("documentTypeAcronym");
        names.add("==================================================");
        names.add("genderName");
        names.add("==================================================");
        names.add("sectorName");
        names.add("==================================================");
        names.add("telephoneTypeName");
        names.add("==================================================");
        names.add("stateTypeName");
        names.add("==================================================");
        names.add("cityTypeName");
        names.add("==================================================");
        names.add("countryName");
        names.add("phoneIdentifier");
        names.add("==================================================");
        names.add("stateName");
        names.add("idCountry");
        names.add("idStateType");
        names.add("landlinePhoneIdentifier");
        names.add("==================================================");
        names.add("cityName");
        names.add("stateTypeName");
        names.add("idState");
        names.add("idCityType");
        names.add("landlinePhoneIdentifier");
        names.add("area");
        names.add("minimumTemperature");
        names.add("maximumTemperature");
        names.add("latitude");
        names.add("longitude");
        names.add("heightAboveSeaLevel");
        names.add("==================================================");
        names.add("idOriginCity");
        names.add("idDestinationCity");
        names.add("distance");
        names.add("routeNumbers");

        for(String name: names) {
            System.out.println(name.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase());
        }
    }

    public static void prueba() {
        int n = 1531, r= 0, d=n;
        String bin = "";
        do {
            r = d % 2;
            d = d / 2;
            bin = r + bin;
            System.out.println("d = " + d + " r = " + r + " B " + bin);
        } while (d != 1);
        bin = d + bin;
        System.out.println(n + " = " + bin);
    }

    public static void mainasd() throws Exception {
        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        int t = Integer.parseInt(name);
        int n, s = 0;
        while((name = br.readLine()) != null && t > 0) {
            n = Integer.parseInt(name);
            for(int i = 1; i < n; i++) {
                if(n % i == 0) {
                    s = s + i;
                }
            }
            if(s == n) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
            s = 0;
            t--;
        }
    }
}