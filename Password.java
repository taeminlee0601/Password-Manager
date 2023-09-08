import java.io.Serializable;

public class Password implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[][] encodingMatrix;
    private int[][] passwordMatrix;
    private int[][] usernameMatrix;

    public Password(String username, String password) {

        encodingMatrix = Encoder.generateEncodingMatrix();

        usernameMatrix = Encoder.encode(username, encodingMatrix);
        passwordMatrix = Encoder.encode(password, encodingMatrix);
    }

    public int[][] getPasswordMatrix() {
        return passwordMatrix;
    }

    public int[][] getUsernameMatrix() {
        return usernameMatrix;
    }

    public int[][] getEncodingMatrix() {
        return encodingMatrix;
    }

    public void setUsernmae(String username) {
        usernameMatrix = Encoder.encode(username, encodingMatrix);
    }

    public void setPassword(String password) {
        passwordMatrix = Encoder.encode(password, encodingMatrix);
    }
}
