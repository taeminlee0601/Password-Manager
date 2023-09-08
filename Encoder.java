public class Encoder {
    public static int[][] encode(String str, int[][] encodingMatrix) {
        int[][] encodedMatrix;

        if (str.length() % 2 == 1) {
            encodedMatrix = new int[2][str.length()/2 + 1];
        } else {
            encodedMatrix = new int[2][str.length()/2];
        }

        int count = 0;

        for (int i = 0; i < encodedMatrix.length; i++) {
            for (int j = 0; j < encodedMatrix[i].length; j++) {
                if (count < str.length()) {
                    char c = str.charAt(count);
                    encodedMatrix[i][j] = (int)c;
                    count++;
                } else {
                    encodedMatrix[i][j] = -1;
                }
            }
        }

        return multiplyMatrices(encodingMatrix, encodedMatrix);
    }

    public static int[][] generateEncodingMatrix() {
        int[][] encodingMatrix = new int[2][2];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                encodingMatrix[i][j] = (int)(Math.random() * 99) + 1;
            }
        }

        if (!checkEncodingMatrix(encodingMatrix)) {
            encodingMatrix = generateEncodingMatrix();
        }

        return encodingMatrix;
    }

    public static int getDeterminant(int[][] encodingMatrix) {
        return encodingMatrix[0][0] * encodingMatrix[1][1] - encodingMatrix[0][1] * encodingMatrix[1][0];
    }

    public static boolean checkEncodingMatrix(int[][] encodingMatrix) {
        int determinant = getDeterminant(encodingMatrix);

        if (determinant != 0) {
            return true;
        }

        return false;
    }

    public static int[][] multiplyMatrices(int[][] encodingMatrix, int[][] asciiMatrix) {
        int[][] encodedMatrix = new int[2][asciiMatrix[0].length];

        for (int i = 0; i < encodingMatrix.length; i++) {
            for (int j = 0; j < asciiMatrix[i].length; j++) {
                int[] row = getRow(encodingMatrix, i);
                int[] col = getCol(asciiMatrix, j);

                encodedMatrix[i][j] = row[0] * col[0] + row[1] * col[1];
            }
        }

        return encodedMatrix;
    }

    public static int[] getRow(int[][] matrix, int index) {
        int[] row = new int[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            row[i] = matrix[index][i];
        }

        return row;
    }

    public static int[] getCol(int[][] matrix, int index) {
        int[] col = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            col[i] = matrix[i][index];
        }

        return col;
    }
}
