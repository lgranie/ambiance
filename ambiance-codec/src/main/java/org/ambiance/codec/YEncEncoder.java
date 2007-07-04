package org.ambiance.codec;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.yenc.YEnc;
import org.apache.commons.codec.binary.yenc.YEncHeader;
import org.apache.commons.codec.binary.yenc.YEncTrailer;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.codec.Encoder" 
 *                   role-hint="yenc"
 * 
 */
public class YEncEncoder extends AbstractLogEnabled implements Encoder,
		Initializable {

	/**
	 * @plexus.configuration default-value="/tmp"
	 */
	private String outputDirName;
	
	/**
	 * @plexus.configuration default-value=".yenc"
	 */
	private String extension;
	
	/**
	 * @plexus.configuration default-value="128"
	 */
	private int lineSize;
	
	private BinaryEncoder encoder;

	public void initialize() throws InitializationException {
		encoder = new YEnc();
		
		if(lineSize <= 0)
			throw new InitializationException("Line size must be > to zero.");
	}

	public void encode(File input) throws EncoderException {
		try {
			// Initialize
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
			
			YEncHeader header = new YEncHeader();
			header.setName(input.getName());
			header.setLine(lineSize);
			
			YEncTrailer trailer = new YEncTrailer();

			CRC32 crc32 = new CRC32();
			
			StringBuffer outputName = new StringBuffer(outputDirName);
			outputName.append(File.separator);
			outputName.append(header.getName());
			outputName.append(extension);
			
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(outputName.toString()));
			
			// Read and encode
			int c;
			long size = 0;
			while ((c = bis.read()) != -1) {
				crc32.update(c);
				if (size % header.getLine() == 0 && size != 0) {
					bos.write((int) '\r');
					bos.write((int) '\n');
				}
				size++;
			}
		} catch (FileNotFoundException e) {
			throw new EncoderException("Unable to find file to encode");
		} catch (IOException e) {
			throw new EncoderException("Unable to read file to encode");
		}
		
	}

}
