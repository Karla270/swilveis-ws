package pe.com.swilveis.ws.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import pe.com.swilveis.ws.entity.general.Confirmacion;
import pe.com.swilveis.ws.entity.general.StreamGobbler;

@Component
public class Comandos {
	
	private final String UBICACION = "/tmp/";
	
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}
	
	private Confirmacion ejecutar(ProcessBuilder builder, String nombreProceso) {
		if(isWindows()) {
			return Confirmacion.errorNegocio("Ejecución de comandos solo disponible en Servidor Linux.");
		} else {
			try {
				Process process;
				process = builder.start();
				
				StreamGobbler streamGobbler = new StreamGobbler(process.getErrorStream(), System.out::println);
				Executors.newSingleThreadExecutor().submit(streamGobbler);
				
				int result = process.waitFor();
				
				if(result == 0) {
					return Confirmacion.ok("Se ejecutó el comando correctamente para el proceso: " + nombreProceso + ".");
				} else {
					return Confirmacion.errorAplicacion("Ocurrió un error en la ejecución de comandos en " + nombreProceso + ".");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return Confirmacion.errorAplicacion("ERROR en " + nombreProceso + " IOException: \r\n" + e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
				return Confirmacion.errorAplicacion("ERROR en " + nombreProceso + " InterruptedException: \r\n" + e.getMessage());
			}
		}
	}
	
	private String ubicacionNuevoSH(Map<String, String> parameters, String dirFile) {
		String output = UBICACION + UUID.randomUUID() + ".sh";
		try {
			File file = new File(output);
			// Si el archivo no existe es creado
	        if (!file.exists()) {
				file.createNewFile();
	        }
	        FileWriter fw = new FileWriter(file);
	        fw.write("#!/bin/bash\n");
	        
	        for (Map.Entry<String, String> entry : parameters.entrySet()) {
	        	fw.write("sed -i 's/${" + entry.getKey() + "}/" + entry.getValue() + "/g' " + dirFile + "\n");
			}
	        fw.close();
	        return output;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Confirmacion copiarArchivo(String fileName, String fileNameCopy) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cp", fileName, fileNameCopy);
		builder.directory(new File(System.getProperty("user.home")));
		return ejecutar(builder, "copiando archivo");
	}
	
	public Confirmacion replaceText(Map<String, String> parameters, String dirFile) {
		String ubicacionSH = ubicacionNuevoSH(parameters, dirFile);
		if(ubicacionSH == null) {
			return Confirmacion.errorAplicacion("No se pudo crear el BASH");
		}
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", ubicacionSH);
		builder.directory(new File(System.getProperty("user.home")));
		
		Confirmacion confirmacion = ejecutar(builder, "Reemplazando texto");
		File file = new File(ubicacionSH);
		file.delete();
		return confirmacion;
	}
	
	public Confirmacion generarPDF(String dirHTML, String outputPDF) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bin/wkhtmltopdf", dirHTML, outputPDF);
		builder.directory(new File(System.getProperty("user.home")));
		return ejecutar(builder, "Generando PDF");
	}
	
	public void eliminarHTML(String dirHTML) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("rm", dirHTML);
		builder.directory(new File(System.getProperty("user.home")));
		ejecutar(builder, "Eliminando HTML");
	}
	
	public void eliminarPDF(String dirPDF) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("rm", dirPDF);
		builder.directory(new File(System.getProperty("user.home")));
		ejecutar(builder, "Eliminando PDF");
	}

}
