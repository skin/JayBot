package it.nands.jaybot.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/***
 * Classe di utilita' per i files
 * @author a.franzi
 *
 */
public class FileUtils {


	/**
	 * Converte i LN con CRLF
	 * 
	 * @param aFilenameInput		: file name in input
	 * @param aFilenameOutput		: file name in output
	 * @throws IOException			: eccezione di I/O
	 */
	public static void convertLFtoCRLF(String aFilenameInput,String aFilenameOutput) throws IOException{
		FileInputStream inputFile = new FileInputStream (new File(aFilenameInput));
		FileOutputStream outputFile = new FileOutputStream(new File(aFilenameOutput));
		int character = inputFile.read();
		char carriageReturn = (char)(13);
		char lineFeed = (char)(10);
		while (character != -1){ 
			character = inputFile.read();
			if (character==lineFeed){
				outputFile.write(carriageReturn);
				outputFile.write(lineFeed);
			} 
			else {
				outputFile.write(character); 
			}
		}
		inputFile.close();
		outputFile.close();
	}
	
	/**
	 * Metodo per rimuovere un file
	 * @param aFilename	: filename
	 */
	public static void removeFile(String aFilename){
		new File(aFilename).delete();
	}
	
	/***
	 * Metodo per trasformare un inputStream in un array di Byte
	 * @param inputStream		: input stream
	 * @return					: array di byte
	 * @throws IOException		: eccezione di I/O
	 */
	 public static byte[] getBytesFromInputStreams(InputStream inputStream) throws IOException {
		//stream su cui memorizzare i dati letti
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         //lettura dallo stream
         int r;
         while ((r = inputStream.read()) != -1)
         {
             bos.write(r);
         }
         //conversione in array di byte
         byte[] data = bos.toByteArray(); 
         return data;
	  }


}
