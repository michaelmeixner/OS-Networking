 

/*
* A basic class for reading primitive and String data from the Keyboard
*/

import java.io.*;

public class KeyboardReader {
	private static BufferedReader reader;
	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/*
	* Prints the prompt argument and then returns a String as typed by the user.
	*/
	
	public static String readLine(String prompt) {
		try {
			System.out.println(prompt);
			if(prompt == null) {
				System.out.println("Provide input.");
			} return reader.readLine();
		} catch(IOException ioe) {
			System.err.println("Error reading from keyboard.");
		} return "";
	}
	
	/*
	* Prints the prompt argument and then returns a double as typed by the user.
	*/
	
	public static double readDouble(String prompt) {
		while(true) {
			try {
				return Double.parseDouble(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter a double value.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns a byte as typed by the user.
	*/
	
	public static byte readByte(String prompt) {
		while(true) {
			try {
				return Byte.parseByte(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter a byte value.");
			}
		}
	}
	
	/*
	*Prints the prompt argument and then returns true or false as typed by the user.
	*I left this mostly as you wrote it --Lucas
	*/
	
	public static boolean readBoolean(String prompt) {
		while(true) {
			String input = readLine(prompt);
			if(input.equalsIgnoreCase("true")) {
				return true;
			} else if(input.equalsIgnoreCase("false")) {
				return false;
			} else {
				System.out.println("Please enter true or false.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns a single byte as typed by the user.
	*/
	
	public static char readChar(String prompt) {
		while(true) {
			String input = readLine(prompt);
			int character = input.length();
			if(character == 1) {
				char c = input.charAt(0);
				return c;
			} else {
				System.out.println("please only enter one character.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns a float as typed by the user.
	*/
	
	public static final float readFloat(String prompt) {
		while(true) {
			try {
				return Float.parseFloat(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter a proper value.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns an integer as typed by the user.
	*/
	
	public static final int readInt(String prompt) {
		while(true) {
			try {
				return Integer.parseInt(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter an integer.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns a long as typed by the user.
	*/
	
	public static final long readLong(String prompt) {
		while(true) {
			try {
				return Long.parseLong(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter a proper value.");
			}
		}
	}
	
	/*
	* Prints the prompt argument and then returns a short as typed by the user.
	*/
	
	public static final short readShort(String prompt) {
		while(true) {
			try {
				return Short.parseShort(readLine(prompt));
			} catch(NumberFormatException nfe) {
				System.out.println();
				System.out.println("Please enter a proper value.");
			}
		}
	}
	
	public static void main(String[] args) {
		
		double rad = KeyboardReader.readDouble("Enter a double:");
		boolean bool = KeyboardReader.readBoolean("Enter true or false.");
		byte bite = KeyboardReader.readByte("Enter a byte value.");
		char car = KeyboardReader.readChar("Enter a single character.");
		float flo = KeyboardReader.readFloat("Enter a float.");
		int integer = KeyboardReader.readInt("Enter an integer.");
		long lon = KeyboardReader.readLong("Enter a long value.");
		short shorty = KeyboardReader.readShort("Enter a short value.");
		
		System.out.println("Your double value is: " + rad);
		System.out.println("Your boolean value is: " + bool);
		System.out.println("Your byte value is: " + bite);
		System.out.println("Your char value is: " + car);
		System.out.println("Your float value is: " + flo);
		System.out.println("Your integer value is: " + integer);
		System.out.println("Your long value is: " + lon);
		System.out.println("Your short value is: " + shorty);
	}
}