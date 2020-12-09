package Compression;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// BufferedBitWriter by Mr. David

// If you are curious, check this out. Does some cool stuff with bits/bytes.
// If it's gibberish and you don't want to deal with this, don't worry about it.

// Writes bits to a file.  Accumulates bits until gets a byte, 
// then writes it.  On closing writes an additional byte holding
// the number of valid bits in the final byte written.
 
public class BufferedBitWriter {
	private byte currentByte;     			// The byte that is being filled
	private int numBitsWritten;  			// Number of bits written to the current byte
	public static int maxBytes = 1000000000; // So we can stop if file gets crazy big
	private int totalBytes;					
	private BufferedOutputStream output; 	// The output byte stream

	// initializes our variables and opens the output stream. 
	// shouldn't have to worry about a FileNotFoundException because 
	// we are creating a new txt file.
	public BufferedBitWriter(String pathName) throws FileNotFoundException {
		currentByte = 0;
		numBitsWritten = 0;
		totalBytes = 0;
		output = new BufferedOutputStream(new FileOutputStream(pathName));
	}

	// "writes" a bit to the file (in reality, it only adds the bit to our 
	// current byte, then later writes this byte to the file.
	public void writeBit(boolean bit) throws IOException {
		numBitsWritten++;
		
		// add the bit to our current byte at the appropriate digit
		currentByte |= (bit?1:0) << (8 - numBitsWritten);
		
		// if we have filled our byte, writes the byte to the file
		// and starts a new byte.
		if(numBitsWritten == 8) { 
			output.write(currentByte);
			numBitsWritten = 0;
			currentByte = 0;
			totalBytes++;
			
			// quit if we have written a crazy number of bytes
			if (totalBytes >= maxBytes) 
				throw new IOException("file overflow - do you have an infinite loop??");
		}
	}

	 // Closes this stream.  Writes any partial byte (unfinished), followed by 
	 // the number of valid bits in the final byte.
	 // The file will always have at least 2 bytes (the partially completed byte 
	 // and the number of bits in this "final" byte).
	 // A file representing no bits will have two zero bytes.
	 // These last two steps are essential, so make sure close() is always called!!
	 // Otherwise the BufferedBitReader will not work.
	public void close() throws IOException {
		output.write(currentByte);
		output.write(numBitsWritten);
		output.close();
	}
}
