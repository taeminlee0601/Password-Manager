public class Decoder {
    public static String decode(int[][] encodingMatrix, int[][] encodedMatrix) {
        int[][] decodedMatrix = new int[encodedMatrix.length][encodedMatrix[0].length];

        int determinant = Encoder.getDeterminant(encodingMatrix);
        int[][] decodingMatrix = {{encodingMatrix[1][1], -encodingMatrix[0][1]}, 
                                  {-encodingMatrix[1][0], encodingMatrix[0][0]}};
        
        for (int i = 0; i < decodingMatrix.length; i++) {
            for (int j = 0; j < encodedMatrix[0].length; j++) {
                int[] col = Encoder.getRow(decodingMatrix, i);
                int[] row = Encoder.getCol(encodedMatrix, j);

                decodedMatrix[i][j] = (col[0] * row[0] + col[1] * row[1]) / determinant;
            }
        }

        String decodedString = "";

        for (int i = 0; i < decodedMatrix.length; i++) {
            for (int j = 0; j < decodedMatrix[i].length; j++) {
                if (decodedMatrix[i][j] == -1) {
                    break;
                }
                
                decodedString = decodedString + (char)decodedMatrix[i][j];
            }
        }

        return decodedString;
    }
}
