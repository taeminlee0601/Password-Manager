import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
    private static PasswordManager manager = new PasswordManager();
    private static Scanner input = new Scanner(System.in);
    private static File openedFile;
    private static boolean hasOpenedFile = false;
    
    public static void main(String[]args) {
        int choice = 0;

        System.out.println();
        System.out.println("Welcome to Password Manager Terminal!");
        System.out.println();

        while(true) {
            System.out.println("1. Print Passwords");
            System.out.println("2. Add Password");
            System.out.println("3. Edit Exisiting Password");
            System.out.println("4. Delete a Password");
            System.out.println("5. Open a File");
            System.out.println("6. Save to a File");
            System.out.println("7. Exit the Program");
            System.out.print("Choose an action (1-7): ");

            try {
                choice = input.nextInt();

                System.out.println();

                switch (choice) {
                    case 1:
                        manager.printInfo();
                        break;
                    case 2:
                        addPassword();
                        break;
                    case 3:
                        if (manager.getSize() == 0) {
                            System.out.println("You have not added any info!");
                            System.out.println("Please enter info before editing!");
                            System.out.println();
                            continue;
                        }

                        editInfo();
                        break;
                    case 4:
                        if (manager.getSize() == 0) {
                            System.out.println("You have not added any info!");
                            System.out.println("Please enter info before editing!");
                            System.out.println();
                            continue;
                        }

                        deleteRow();
                        break;
                    case 5:
                        openFile();
                        break;
                    case 6:
                        if (hasOpenedFile) {
                            save(openedFile);
                            break;
                        }

                        saveTo();
                        break;
                    case 7:
                        System.out.println("Thank you for using Password Manager!");
                        System.exit(0);
                    default:
                        System.out.println("Please choose a valid option!");
                        break;
                }

                System.out.println();

            } catch (Exception e) {
                input.nextLine();
                System.out.println();
                System.out.println("Please choose a valid option!");
                System.out.println();
                continue;
            }
        }
    }

    public static void addPassword() {
        String siteName = "";
        String username = "";
        String password = "";

        input.nextLine();

        System.out.print("Enter the site name: ");
        siteName = input.nextLine();

        System.out.print("Enter the username: ");
        username = input.nextLine();

        System.out.print("Enter the password: ");
        password = input.nextLine();

        manager.add(siteName, username, password);
    }

    public static void editInfo() {
        int row = 0;
        int info = 0;
        String change = "";

        input.nextLine();

        while (true) {
            System.out.print("Enter the row number to change: ");

            try {
                row = input.nextInt();

                if (row <= 0 || row > manager.getSize()) {
                    System.out.println();
                    System.out.println("Please enter a valid input!");
                    System.out.println();
                    continue;
                }

                break;
            } catch (Exception e) {
                System.out.println();
                System.out.println("Please enter a valid input!");
                System.out.println();
            }
        }

        System.out.println();

        while (true) {
            System.out.println("1. Change site name");
            System.out.println("2. Change username");
            System.out.println("3. Change password");
            System.out.print("Enter a choice (1-3): ");
            try {
                info = input.nextInt();
                break;
            } catch (Exception e) {
                input.nextLine();
                System.out.println();
                System.out.println("Please enter a valid input!");
                System.out.println();
            }
        }

        input.nextLine();

        System.out.print("Enter the change: ");
        change = input.nextLine();

        manager.editInfo(row, info, change);
    }

    public static void deleteRow() {
        int row = 0;

        while (true) {
            System.out.print("Enter a row to delete: ");

            try {
                row = input.nextInt();

                if (row <= 0 || row > manager.getSize()) {
                    System.out.println();
                    System.out.println("Please enter a valid input!");
                    System.out.println();
                    continue;
                }

                break;
            } catch (Exception e) {
                System.out.println();
                System.out.println("Please enter a valid input!");
                System.out.println();
            }
        }

        manager.deleteRow(row);
    }

    public static void openFile() {
        String filename = "";
        String password = "";
        PasswordManager tempManager = new PasswordManager();

        input.nextLine();

        System.out.print("Enter the file name (NOTE DO NOT ADD FILE EXTENSION): ");
        filename = input.nextLine();

        filename = filename + ".passMngr";

        File file = new File(filename);

        if (!file.exists()) {
            System.out.println();
            System.out.println("The file does not exist!");
            return;
        }

        try {
            FileInputStream input = new FileInputStream(file);
            ObjectInputStream inStream = new ObjectInputStream(input);
            tempManager = (PasswordManager)inStream.readObject();
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("File is not valid!");
            return;
        }

        System.out.print("Enter the password: ");
        password = input.nextLine();

        if (tempManager.checkAccessPassword(password)) {
            manager = tempManager;
            openedFile = file;
            hasOpenedFile = true;
        } else {
            System.out.println();
            System.out.println("Incorrect Password! Access Denied!");
        }
    }

    public static void saveTo() {
        String filename = "";
        String password = "";

        input.nextLine();

        System.out.print("Enter the file name (NOTE DO NOT ADD EXTENSION): ");
        filename = input.nextLine();

        filename = filename + ".passMngr";

        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println();
                System.out.println("Please enter a valid name!");
                return;
            }
        }

        System.out.print("Enter a password for the file: ");
        password = input.nextLine();

        manager.setAccessPassword(password);

        try {
            FileOutputStream output = new FileOutputStream(filename);
            ObjectOutputStream outStream = new ObjectOutputStream(output);
            outStream.writeObject(manager);
            outStream.close();

            System.out.println();
            System.out.println("File Saved Successfully!");

        } catch (Exception e) {
            System.out.println();
            System.out.println("Please enter a valid name!");
            return;
        }
    }

    public static void save(File file) {
        try {
            FileOutputStream output = new FileOutputStream(file);
            ObjectOutputStream outStream = new ObjectOutputStream(output);
            outStream.writeObject(manager);
            outStream.close();

            System.out.println("File Saved Successfully!");

        } catch (Exception e) {
            System.out.println();
            System.out.println("Please enter a valid name!");
            return;
        }
    }
}