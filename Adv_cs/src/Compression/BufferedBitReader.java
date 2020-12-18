package Compression;
import java.io.*;

// BufferedBitReader by Mr. David

// If you are curious, check this out. Does some cool stuff with bits/bytes.
// If it's gibberish and you don't want to deal with this, don't worry about it.

// Reads bits from a file, one at a time.  
// Assumes that the BufferedBitWriter was closed correctly, so the
// last byte of the file contains the number of
// valid bits in the previous byte.
public class BufferedBitReader {
	
	// Note that we need to look ahead 3 bytes, because when the
	// third byte is -1 (the EOF indicator that is automatically written 
	// when a filewriter closes) then the second byte is a count
	// of the number of bits in the first byte.
	private int current;    // Current byte being returned, bit by bit
	private int next;       // Next byte to be returned 
	private int afterNext;  // Byte two after the current byte
	private int bitDigit;    // keeps track of which digit in the byte we are at

	private BufferedInputStream input;	// our input stream

	// initializes our variables
	public BufferedBitReader(String pathName) throws IOException {
		input = new BufferedInputStream(new FileInputStream(pathName));

		// immediately reads the first three bytes. If there is less than 2 bytes, the file 
		// we are trying to read is not valid for the BufferedBitReader algorithm
		current = input.read();
		if(current == -1)
			throw new EOFException("File did not have two bytes - cannot be read by a BufferedBitReader");
		next = input.read();
		if(next == -1) 
			throw new EOFException("File did not have two bytes - cannot be read by a BufferedBitReader");	
		afterNext = input.read();
		
		bitDigit = 128;   // a 1 in leftmost bit position
	}

	// Test to decide whether or not there is a valid next bit to read.
	// The only time that there will not be a valid next bit is when there is no
	// third byte (meaning the next byte indicates the number of bits in our current byte)
	// and the number of bits left to read in our current byte is 0.
	// Code to read: while (reader.hasNext()) { boolean bit = reader.readBit(); }
	public boolean hasNext() {
		return afterNext != -1 || next != 0;
	}

	 // Reads a bit and returns it as a false (0) or a true (1).
	 // Throws an exception if there isn't one, so use hasNext() to check first,
	 // or else catch the EOFException
	public boolean readBit() throws IOException {
		boolean returnBit;   // the bit to return

		// check if we are emptying the last byte
		// if we are, next is the count of bits remaining.
		if(afterNext == -1)  
			
			// check if there are no more bits in the final byte
			if(next == 0)   
				throw new EOFException("No more bits");
			else {
				// this gives us the bit at our current digit
				if((bitDigit & current) == 0)
					returnBit = false;
				else 
					returnBit = true;

				next--;          			// One fewer bit to return
				bitDigit = bitDigit >> 1;    // Shift to next digit in the byte
				return returnBit;
			}
		
		// if we are not emptying the last byte
		else {
			if((bitDigit & current) == 0)
				returnBit = false;
			else 
				returnBit = true;

			bitDigit = bitDigit >> 1;    // Shift to mask next bit

			// check if we are finished going through the current byte
			if(bitDigit == 0)  {        
				bitDigit = 128;           // reset to the leftmost digit
				
				// shift our current and next bytes 
				current = next;
				next = afterNext;
				afterNext = input.read();
			}
			return returnBit;
		}
	}

	 // Close this bitReader.
	public void close() throws IOException {
		input.close();
	}
}
