import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.HashSet;

public class SimpleCracker {

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // read common passwords file into a set
        File commonPasswordsFile = new File("common-passwords");
        Scanner scanner = new Scanner(commonPasswordsFile);
        HashSet<String> commonPasswords = new HashSet<>();
        while (scanner.hasNextLine()) {
            commonPasswords.add(scanner.nextLine());
        }
        scanner.close();

        // read shadow file and try to crack passwords
        File shadowFile = new File("shadow-simple");
        scanner = new Scanner(shadowFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            String username = parts[0];
            String salt = parts[1];
            String expectedHash = parts[2];

            for (String password : commonPasswords) {
                String candidate = salt + password;
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] candidateBytes = candidate.getBytes();
                byte[] hashBytes = md.digest(candidateBytes);
                String actualHash = toHex(hashBytes);
                if (actualHash.equals(expectedHash)) {
                    System.out.println(username + ":" + password);
                    break;
                }
            }
        }
        scanner.close();
    }
}
