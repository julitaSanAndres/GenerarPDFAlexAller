package com.alexa.crearPDF;

import harmony.java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.alexa.crearPDF.R;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class GenerarPDFActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Generaremos el documento al hacer click sobre el botón.
		findViewById(R.id.btnGenerar).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// Creamos el documento.
		Document documento = new Document();

		try {

			// Creamos el fichero con el nombre que deseemos.
			File f = crearFichero("pruebaPFD.pdf");

			// Creamos el flujo de datos de salida para el fichero donde
			// guardaremos el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream(
					f.getAbsolutePath());

			// Asociamos el flujo que acabamos de crear al documento.
			PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

			// Añadimos la cabecera y el pie de pagina al PDF
			HeaderFooter cabecera = new HeaderFooter(new Phrase(
					"Cabecera de el documento"), false);
			HeaderFooter pie = new HeaderFooter(
					new Phrase("Pie del documento"), false);

			documento.setHeader(cabecera);
			documento.setFooter(pie);

			// Abrimos el documento.
			documento.open();

			// Creamos las fuentes
			Font fuente1 = FontFactory.getFont(FontFactory.COURIER, 36,
					Font.BOLD);
			Font fuente2 = FontFactory.getFont(FontFactory.HELVETICA, 16,
					Font.NORMAL);

			// Añadimos el titulo
			documento.add(new Paragraph("Titulo del PDF", fuente1));
			// Añadimos una linea de texto
			documento
					.add(new Paragraph("Esto es una linea de prueba", fuente2));

			// Añadir una imagen
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.ic_launcher);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			Image imagen = Image.getInstance(stream.toByteArray());
			documento.add(imagen);

			// Añadimos una tabla
			PdfPTable tabla = new PdfPTable(4);
			// Añadimos las celdas
			tabla.addCell("Notas");
			tabla.addCell("Matematicas");
			tabla.addCell("Lengua");
			tabla.addCell("Ingles");
			tabla.addCell("Alumno1");
			tabla.addCell("5");
			tabla.addCell("3");
			tabla.addCell("7");
			tabla.addCell("Alumno2");
			tabla.addCell("4");
			tabla.addCell("2");
			tabla.addCell("8");
			// Añadimos la tabla al documento
			documento.add(tabla);

		} catch (DocumentException e) {

		} catch (IOException e) {

		} finally {

			// Cerramos el documento.
			Toast.makeText(this, "PDF creado con exito.", Toast.LENGTH_SHORT).show();
			documento.close();
		}
	}

	// Con este metodo creamos el fichero

	public static File crearFichero(String nombreFichero) throws IOException {
		File ruta = getRuta();
		File fichero = null;
		if (ruta != null)
			fichero = new File(ruta, nombreFichero);
		return fichero;
	}

	// Con este metodo creamos la ruta del fichero

	public static File getRuta() {

		File ruta = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			ruta = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"miPDF");

			if (ruta != null) {
				if (!ruta.mkdirs()) {
					if (!ruta.exists()) {
						return null;
					}
				}
			}
		} else {
		}

		return ruta;
	}

}
