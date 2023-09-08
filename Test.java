import java.util.Arrays;

public class Test {
    public static void main(String[]args) {
        String username = "Taemin Lee";
        String password = "HelloWorld123!";
        PasswordManager manager = new PasswordManager();

        manager.add("Github", username, password);
        manager.add("Google", "taeminlee0601@gmail.com", "HelloWtodlfahsd");
        manager.add("Google", "taemonster0601@gmail.com", "HEa;dwjhd@djhalw");
        manager.add("Hotmail", "taemin@hotmail.com", "HOWDA:DSdlajwdasdW");

        manager.printInfo();
        manager.editInfo(2, 2, "Taemin Lee");
        manager.printInfo();
    }
}
