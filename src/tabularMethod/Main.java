package tabularMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Scanner sc;
		while (true) {
			int x = 0;
			sc = new Scanner(System.in);
			try {
				System.out.println("Pick and option: ");
				System.out.println("1-To read from Console.");
				System.out.println("2-To read from File.");
				x = sc.nextInt();
				if (!(x == 1 || x == 2))
					throw new RuntimeException("Invalid Input");
			} catch (Exception e) {
				System.out.println("Invalid option!!");
				break;
			}
			Table firstTable = new Table();
			if (x == 1) {
				try {
					System.out.println("Enter minterms");
					sc.reset();
					sc = new Scanner(System.in);
					String minterms = sc.nextLine();
					System.out.println("Enter Don't Care Terms");
					sc.reset();
					String dontCare = sc.nextLine();
					sc.reset();
					firstTable.initialize(minterms, dontCare);
					firstTable.minimizeMyTable();
				} catch (Exception e) {
					if (e.getMessage() == "Empty Expression") {
						System.out.println("This function is always turned off!");
						break;
					} else if (e.getMessage() == "Full Expression") {
						System.out.println("This function is always turned on!");
						break;
					}
					System.out.println("Duplicates are not allowed!");
				}
			} else {
				try {
					sc.reset();
					System.out.print("Enter File Name(name.txt): ");
					sc = new Scanner(System.in);
					String fileDirectory = sc.nextLine();
					sc = new Scanner(new File(fileDirectory));
					String minterms = sc.nextLine();
					sc.reset();
					String dontCare = sc.nextLine();
					firstTable.initialize(minterms, dontCare);
					firstTable.minimizeMyTable();
				} catch (Exception e) {
					System.out.println("Duplicates are not allowed!");
					break;
				}
			}
			try {
				sc = new Scanner(System.in);
				System.out.println("Pick and option: \n1-To write to Console.\n2-To write to File.");
				x = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Null Scanner!");
				break;
			}
			if (x == 1) {
				try {
					System.out.println(firstTable.printMyTable());
				} catch (Exception e) {
					if (e.getMessage() == "Empty Expression") {
						System.out.println("This function is always turned off!");
						break;
					} else if (e.getMessage() == "Full Expression") {
						System.out.println("This function is always turned on!");
						break;
					}
					break;
				}
			} else {
				FileWriter fw;
				File file = null;

				BufferedWriter bw = null;
				String fileName = new String();
				try {
					String content = firstTable.printMyTable();
					sc = new Scanner(System.in);
					System.out.println("Enter file name(filename.txt): ");
					fileName = sc.nextLine();
					file = new File(fileName);
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					fw = new FileWriter(file.getAbsoluteFile());
					bw = new BufferedWriter(fw);
					bw.write(content);
					bw.close();
				} catch (Exception e) {
					file = new File("output.txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					fw = new FileWriter(file.getAbsoluteFile());
					bw = new BufferedWriter(fw);
					if (e.getMessage().equals("Empty Expression")) {
						bw.write("This function is always turned off!");
						break;
					} else if (e.getMessage().equals("Full Expression")) {
						String z = "This function is always turned on!";
						bw.write(z);
						break;
					}
					System.out.println("Invalid fileName");

				}
			}
			sc.close();
			break;
		}
		sc.close();
	}
}