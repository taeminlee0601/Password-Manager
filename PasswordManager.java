import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;

public class PasswordManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private TreeMap<String, ArrayList<Password>> manager = new TreeMap<String, ArrayList<Password>>();
    private int[][] accessPassword;
    private int[][] encodingMatrix;

    public void add(String siteName, String username, String password) {
        if (manager.get(siteName) == null) {
            manager.put(siteName, new ArrayList<Password>());
        }

        manager.get(siteName).add(new Password(username, password));
    }

    public void add(String siteName, Password p) {
        if (manager.get(siteName) == null) {
            manager.put(siteName, new ArrayList<Password>());
        }

        manager.get(siteName).add(p);
    }

    public String[] getInfo(Password password) {
        String[] info = new String[2];

        info[0] = Decoder.decode(password.getEncodingMatrix(), password.getUsernameMatrix());
        info[1] = Decoder.decode(password.getEncodingMatrix(), password.getPasswordMatrix());

        return info;
    }

    public void editInfo(int row, int type, String change) {
        Password p = new Password("", "");
        String siteName = "";
        Set<String> key = manager.keySet();
        boolean hasFound = false;

        int count = 1;

        for (String i: key) {
            ArrayList<Password> list = manager.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (count == row) {
                    siteName = i;
                    p = list.remove(j);
                    hasFound = true;
                }

                count++;
            }

            if (hasFound) {
                break;
            }
        }

        switch (type) {
            case 1:
                add(change, p);
                break;
            case 2:
                p.setUsernmae(change);
                add(siteName, p);
                break;
            case 3:
                p.setPassword(change);
                add(siteName, p);
                break;
        }

        if (manager.get(siteName).size() == 0) {
            manager.remove(siteName);
        }
    }

    public void deleteRow(int row) {
        String siteName = "";
        Set<String> key = manager.keySet();
        boolean hasFound = false;

        int count = 1;

        for (String i: key) {
            ArrayList<Password> list = manager.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (count == row) {
                    siteName = i;
                    list.remove(j);
                    hasFound = true;
                }

                count++;
            }

            if (hasFound) {
                break;
            }
        }

        if (manager.get(siteName).size() == 0) {
            manager.remove(siteName);
        }
    }

    public int getSize() {
        return manager.size();
    }

    public void printInfo() {
        Set<String> key = manager.keySet();

        int count = 1;

        System.out.printf("%26s%35s%35s\n", "Site Name", "Username", "Password");

        for (String i: key) {
            ArrayList<Password> list = manager.get(i);

            for (int j = 0; j < list.size(); j++) {
                String[] info = getInfo(list.get(j));
                System.out.printf("%5d:%20s%35s%35s\n", count, i, info[0], info[1]);
                count++;
            }   
        }
    }

    public void setAccessPassword(String password) {
        encodingMatrix = Encoder.generateEncodingMatrix();
        accessPassword = Encoder.encode(password, encodingMatrix);
    }

    public boolean checkAccessPassword(String password) {
        String accessPassword = Decoder.decode(encodingMatrix, this.accessPassword);

        if (accessPassword.equals(password)) {
            return true;
        }

        return false;
    }
}