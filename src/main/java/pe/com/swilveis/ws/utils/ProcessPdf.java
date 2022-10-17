package pe.com.swilveis.ws.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.swilveis.ws.entity.general.Confirmacion;
import pe.com.swilveis.ws.enums.PdfAnexo;

@Component
public class ProcessPdf {

	private final String UBICACION_HTML = "template/";
	private final String UBICACION_PDF = "/tmp/";
	
	@Autowired
	private Comandos comandos;

	public Confirmacion process(PdfAnexo pdfAnexo, Map<String, String> parameters) {
		Confirmacion responseBuildPDF = this.buildPdf(pdfAnexo, parameters);
		if (responseBuildPDF.getIdResult() == 1) {
			return this.transformPDFToBase64(responseBuildPDF.getMensaje());
		} else {
			return responseBuildPDF;
		}
	}

	private Confirmacion buildPdf(PdfAnexo pdfAnexo, Map<String, String> parameters) {
		String plantillaHTML = null;
		String aleatorio = UUID.randomUUID().toString();
		String archivoHTML = UBICACION_PDF + aleatorio + ".html";
		String archivoPDF = UBICACION_PDF + aleatorio + ".pdf";
		
		if(pdfAnexo == PdfAnexo.ANEXO_1) {
			plantillaHTML = "anexo1.html";
		} else {
			plantillaHTML = null;
		}
		
		if(plantillaHTML == null) {
			return Confirmacion.errorNegocio("No se encuentra el formato requerido.");
		}
		
		Confirmacion responseCopiarArchivo = comandos.copiarArchivo(UBICACION_HTML + plantillaHTML, archivoHTML);
		
		if(responseCopiarArchivo.getIdResult() == 1) {
			
			Confirmacion responseReplaceText = comandos.replaceText(parameters, archivoHTML);
			
			if(responseReplaceText.getIdResult() == 1) {
				Confirmacion responseGenerarPDF = comandos.generarPDF(archivoHTML, archivoPDF);
				
				if(responseGenerarPDF.getIdResult() == 1) {
					comandos.eliminarHTML(archivoHTML);
					return Confirmacion.ok(archivoPDF);
				} else {
					comandos.eliminarHTML(archivoHTML);
					return responseReplaceText;
				}
			} else {
				comandos.eliminarHTML(archivoHTML);
				return responseReplaceText;
			}
		} else {
			comandos.eliminarHTML(archivoHTML);
			return responseCopiarArchivo;
		}
	}

	private Confirmacion transformPDFToBase64(String ruta) {
		try {
			File file = new File(ruta);
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);
			fis.close();
			file.delete();
			return Confirmacion.ok(new String(Base64.encodeBase64(bytes)));
		} catch (IOException e) {
			return Confirmacion.errorAplicacion("ERROR al transformPDFToBase64:\n" + e.getMessage());
		}
	}

}
