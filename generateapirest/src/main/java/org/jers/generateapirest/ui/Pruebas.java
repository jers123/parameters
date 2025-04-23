package org.jers.generateapirest.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jers.generateapirest.structure.ClassRuntime;
import org.jers.generateapirest.structure.FieldRuntime;
import org.jers.generateapirest.structure.MethodRuntime;
import org.jers.generateapirest.structure.ParameterRuntime;
import org.jers.generateapirest.structure.enums.Access;
import org.jers.generateapirest.structure.enums.TypeClass;

import lombok.Getter;
import lombok.Setter;

public class Pruebas {

	public static void main(String[] args) {
		/*
		 * ClassRuntime clase0 = new ClassRuntime("void"); ClassRuntime clase1 = new
		 * ClassRuntime(String.class, 0); ClassRuntime clase2 = new
		 * ClassRuntime(Integer.class, 0); ClassRuntime clase3 = new
		 * ClassRuntime(List.class, 1); clase3.setTypeClass(TypeClass.INTERFACE);
		 * clase3.setParameterClass(clase1); ClassRuntime clase4 = new
		 * ClassRuntime("Map"); clase4.setParameterClass(clase3);
		 * clase4.setParameterClass(clase2); clase4.setField(new FieldRuntime(clase1,
		 * "type")); clase4.setField(new FieldRuntime(clase2, "value")); MethodRuntime
		 * method = new MethodRuntime(clase0, "setter"); method.setStatic(true);
		 * method.setParameters(new ParameterRuntime(clase3, "list"));
		 * method.setParameters(new ParameterRuntime(clase1, "text"));
		 * clase4.setMethod(method); method = new MethodRuntime(clase2, "getter");
		 * method.setAccess(Access.PROTECTED); method.setAbstract(true);
		 * method.setArray(true); clase4.setMethod(method);
		 * 
		 * 
		 * System.out.println(clase4.generatePlantUML() + "\n" + clase4.generate());
		 */

		/*List<String> versions = new ArrayList<>();
		versions.add("42.0.0");
		versions.add("42.0.0.jre6");
		versions.add("42.0.0.jre7");
		versions.add("42.1.0");
		versions.add("42.1.0.jre7");
		versions.add("42.1.1");
		versions.add("42.1.1.jre6");
		versions.add("42.1.1.jre7");
		versions.add("42.1.2");
		versions.add("42.1.2.jre6");
		versions.add("42.1.2.jre7");
		versions.add("42.1.3");
		versions.add("42.1.3.jre6");
		versions.add("42.1.3.jre7");
		versions.add("42.1.4");
		versions.add("42.1.4.jre6");
		versions.add("42.1.4.jre7");
		versions.add("42.2.0");
		versions.add("42.2.0.jre6");
		versions.add("42.2.0.jre7");
		versions.add("42.2.1");
		versions.add("42.2.1.jre6");
		versions.add("42.2.1.jre7");
		versions.add("42.2.10");
		versions.add("42.2.10.jre6");
		versions.add("42.2.10.jre7");
		versions.add("42.2.11");
		versions.add("42.2.11.jre6");
		versions.add("42.2.11.jre7");
		versions.add("42.2.12");
		versions.add("42.2.12.jre6");
		versions.add("42.2.12.jre7");
		versions.add("42.2.13");
		versions.add("42.2.13.jre6");
		versions.add("42.2.13.jre7");
		versions.add("42.2.14");
		versions.add("42.2.14.jre6");
		versions.add("42.2.14.jre7");
		versions.add("42.2.15");
		versions.add("42.2.15.jre6");
		versions.add("42.2.15.jre7");
		versions.add("42.2.16");
		versions.add("42.2.16.jre6");
		versions.add("42.2.16.jre7");
		versions.add("42.2.17");
		versions.add("42.2.17.jre6");
		versions.add("42.2.17.jre7");
		versions.add("42.2.18");
		versions.add("42.2.18.jre6");
		versions.add("42.2.18.jre7");
		versions.add("42.2.19");
		versions.add("42.2.19.jre6");
		versions.add("42.2.19.jre7");
		versions.add("42.2.2");
		versions.add("42.2.2.jre6");
		versions.add("42.2.2.jre7");
		versions.add("42.2.20");
		versions.add("42.2.20.jre6");
		versions.add("42.2.20.jre7");
		versions.add("42.2.21");
		versions.add("42.2.21.jre6");
		versions.add("42.2.21.jre7");
		versions.add("42.2.22");
		versions.add("42.2.22.jre6");
		versions.add("42.2.22.jre7");
		versions.add("42.2.23");
		versions.add("42.2.23.jre6");
		versions.add("42.2.23.jre7");
		versions.add("42.2.24");
		versions.add("42.2.24.jre6");
		versions.add("42.2.24.jre7");
		versions.add("42.2.25");
		versions.add("42.2.25.jre6");
		versions.add("42.2.25.jre7");
		versions.add("42.2.26");
		versions.add("42.2.26.jre6");
		versions.add("42.2.26.jre7");
		versions.add("42.2.27");
		versions.add("42.2.27.jre6");
		versions.add("42.2.27.jre7");
		versions.add("42.2.28");
		versions.add("42.2.28.jre7");
		versions.add("42.2.29");
		versions.add("42.2.3");
		versions.add("42.2.3.jre6");
		versions.add("42.2.3.jre7");
		versions.add("42.2.4");
		versions.add("42.2.4.jre6");
		versions.add("42.2.4.jre7");
		versions.add("42.2.5");
		versions.add("42.2.5.jre6");
		versions.add("42.2.5.jre7");
		versions.add("42.2.6");
		versions.add("42.2.6.jre6");
		versions.add("42.2.6.jre7");
		versions.add("42.2.7");
		versions.add("42.2.7.jre6");
		versions.add("42.2.7.jre7");
		versions.add("42.2.8");
		versions.add("42.2.8.jre6");
		versions.add("42.2.8.jre7");
		versions.add("42.2.9");
		versions.add("42.2.9.jre6");
		versions.add("42.2.9.jre7");
		versions.add("42.3.0");
		versions.add("42.3.1");
		versions.add("42.3.10");
		versions.add("42.3.2");
		versions.add("42.3.3");
		versions.add("42.3.4");
		versions.add("42.3.5");
		versions.add("42.3.6");
		versions.add("42.3.7");
		versions.add("42.3.8");
		versions.add("42.3.9");
		versions.add("42.4.0");
		versions.add("42.4.1");
		versions.add("42.4.2");
		versions.add("42.4.3");
		versions.add("42.4.4");
		versions.add("42.4.5");
		versions.add("42.5.0");
		versions.add("42.5.1");
		versions.add("42.5.2");
		versions.add("42.5.3");
		versions.add("42.5.4");
		versions.add("42.5.5");
		versions.add("42.5.6");
		versions.add("42.6.0");
		versions.add("42.6.1");
		versions.add("42.6.2");
		versions.add("42.7.0");
		versions.add("42.7.1");
		versions.add("42.7.2");
		versions.add("42.7.3");
		versions.add("42.7.4");
		versions.add("42.7.5");
		versions.add("9.2-1002-jdbc4");
		versions.add("9.2-1003-jdbc3");
		versions.add("9.2-1003-jdbc4");
		versions.add("9.2-1004-jdbc3");
		versions.add("9.2-1004-jdbc4");
		versions.add("9.2-1004-jdbc41");
		versions.add("9.3-1100-jdbc3");
		versions.add("9.3-1100-jdbc4");
		versions.add("9.3-1100-jdbc41");
		versions.add("9.3-1101-jdbc3");
		versions.add("9.3-1101-jdbc4");
		versions.add("9.3-1101-jdbc41");
		versions.add("9.3-1102-jdbc3");
		versions.add("9.3-1102-jdbc4");
		versions.add("9.3-1102-jdbc41");
		versions.add("9.3-1103-jdbc3");
		versions.add("9.3-1103-jdbc4");
		versions.add("9.3-1103-jdbc41");
		versions.add("9.3-1104-jdbc4");
		versions.add("9.3-1104-jdbc41");
		versions.add("9.4-1200-jdbc4");
		versions.add("9.4-1200-jdbc41");
		versions.add("9.4-1201-jdbc4");
		versions.add("9.4-1201-jdbc41");
		versions.add("9.4-1202-jdbc4");
		versions.add("9.4-1202-jdbc41");
		versions.add("9.4-1202-jdbc42");
		versions.add("9.4-1203-jdbc4");
		versions.add("9.4-1203-jdbc41");
		versions.add("9.4-1203-jdbc42");
		versions.add("9.4-1204-jdbc4");
		versions.add("9.4-1204-jdbc41");
		versions.add("9.4-1204-jdbc42");
		versions.add("9.4-1205-jdbc4");
		versions.add("9.4-1205-jdbc41");
		versions.add("9.4-1205-jdbc42");
		versions.add("9.4-1206-jdbc4");
		versions.add("9.4-1206-jdbc41");
		versions.add("9.4-1206-jdbc42");
		versions.add("9.4.1207");
		versions.add("9.4.1207.jre6");
		versions.add("9.4.1207.jre7");
		versions.add("9.4.1208");
		versions.add("9.4.1208.jre6");
		versions.add("9.4.1208.jre7");
		versions.add("9.4.1209");
		versions.add("9.4.1209.jre6");
		versions.add("9.4.1209.jre7");
		versions.add("9.4.1210");
		versions.add("9.4.1210.jre6");
		versions.add("9.4.1210.jre7");
		versions.add("9.4.1211");
		versions.add("9.4.1211.jre6");
		versions.add("9.4.1211.jre7");
		versions.add("9.4.1212");
		versions.add("9.4.1212.jre6");
		versions.add("9.4.1212.jre7");
		*/
		
		readNewContacts();
	}
	
	private static void readContacts() {
		String folderPath = "C:\\Users\\JERS2\\OneDrive\\Documents\\otros\\mami\\contactos";
		File folder = new File(folderPath);
		File[] vcfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".vcf"));
		List<Telefono> telefonos = new ArrayList<Telefono>();
		if (vcfFiles != null && vcfFiles.length > 0) {
			for (File vcfFile : vcfFiles) {
				try (BufferedReader reader = new BufferedReader(new FileReader(vcfFile))) {
					String line = "";
					String cont;
					String contact = "";
					int i = 0;
					Telefono telefono = null;
					boolean b = false;
					while ((cont = reader.readLine()) != null) {
						contact = contact + cont + "\n";
						i++;
						if (i == 4) {
							line = cont;
							cont = reader.readLine();
							contact = contact + cont + "\n";
							telefono = new Telefono(line, cont.split(":")[1]);
						}
					}
					telefono.setContact(contact);
					for(int j = 0; j < telefonos.size(); j++) {
						if (telefonos.get(j).getTelefono().equals(telefono.getTelefono())) {
							telefonos.get(j).addTelefono(telefono);
							b = true;
							break;
						}
					}
					if(!b) {
						telefonos.add(telefono);
					}
					/*if(line.toLowerCase().contains("nels".toLowerCase()) || line.toLowerCase().contains("lara".toLowerCase())) {
						System.out.println(line);
					}*/
				} catch (IOException e) {
					System.err.println("Error al leer el archivo " + vcfFile.getName() + ": " + e.getMessage());
				}
			}
        } else {
            System.out.println("No se encontraron archivos .vcf en la carpeta especificada.");
        }
		System.out.println("original: " + vcfFiles.length + " - final: " + telefonos.size() + " - repetidos:" + (vcfFiles.length - telefonos.size()));
		for (Telefono telefono : telefonos) {
			System.out.println(telefono.print());
		}
		
		System.out.println("===================================================================");
		for (Telefono telefono : telefonos) {
			if(telefono.getTelefonos().size() > 0) {
				System.out.println(telefono.print());
			}
		}
	}
	
	private static void readNewContacts() {
		String folderPath = "C:\\Users\\JERS2\\OneDrive\\Documents\\otros\\mami\\Contact.txt";
		File file = new File(folderPath);
		List<Telefono> telefonos = new ArrayList<Telefono>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = "";
			String[] cont;
			boolean b = false;
			while ((line = reader.readLine()) != null) {
				cont = line.split("-");
				telefonos.add(new Telefono(cont[0].trim(), cont[1].trim()));
			}
			Collections.sort(telefonos, Comparator.comparing(Telefono::getNombre));
			for(int j = 0; j < telefonos.size(); j++) {
				//System.out.println(telefonos.get(j).getNombre().length() + "==========================");
				System.out.println(telefonos.get(j).print2());
			}
			
		} catch (IOException e) {
			System.err.println("Error al leer el archivo " + file.getName() + ": " + e.getMessage());
		}
	}
	
	@Getter
	public static class Telefono {
		private final String nombre;
		private final String telefono;
		private List<Telefono> telefonos;
		@Setter
		private String contact;
		
		public Telefono(String nombre, String telefono) {
			this.nombre = nombre.replace("=20", " ").replace(";", "").trim();
			if(telefono.length() == 7) {
				this.telefono = "60X" + telefono; 
			} else if(telefono.startsWith("03")) {
				this.telefono = "60" + telefono.substring(2);
			}
			else {
				this.telefono = telefono;
			}
			this.telefonos = new ArrayList<Telefono>();
		}
		
		public void addTelefono(Telefono telefono) {
			telefonos.add(telefono);
		}
		
		public String print() {
			String s = nombre + " - " + telefono + " {\n";
			for (Telefono telefono : telefonos) {
				s = s+ "	" + telefono.getNombre() + " - " + telefono.getTelefono() + "\n";
			}
			s = s + "}\n";
			return s;
		}
		
		public String print2() {
			String s = "BEGIN:VCARD\r\n"
					+ "VERSION:2.1\r\n"
					+ "N;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:;"+ nombre + ";;;\n"
					+ "FN;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:" + nombre + "\n"
					+ "TEL;CELL:" + ((!telefono.startsWith("60")) ? telefono : "") + "\n"
					+ "TEL;HOME:" + ((telefono.startsWith("60")) ? telefono : "") + "\n"
					+ "END:VCARD";
			return s;
		}
	}
}