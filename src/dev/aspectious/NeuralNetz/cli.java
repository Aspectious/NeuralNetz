package dev.aspectious.NeuralNetz;

import java.io.*;
import java.util.Scanner;

public class cli {
	private static Scanner stdin;
	private static PrintStream stdout = System.out;
	public static void attachScannerToSTIN() {
		stdin = new Scanner(System.in);
		System.out.println("[INFO] Attached CLI to Input stream.");
		
		stdout.print("> ");
		stdin.nextLine();
	}
}
