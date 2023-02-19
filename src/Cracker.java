import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.HashSet;

public class Cracker {

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // read common passwords file into a set
        File commonPasswordsFile = new File("common-passwords.txt");
        Scanner scanner = new Scanner(commonPasswordsFile);
        HashSet<String> commonPasswords = new HashSet<>();
        while (scanner.hasNextLine()) {
            commonPasswords.add(scanner.nextLine());
        }
        scanner.close();

        // read shadow file and try to crack passwords
        File shadowFile = new File("shadow");
        scanner = new Scanner(shadowFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            String username = parts[0];
            String[] hashParts = parts[1].split("\\$");
            String salt = hashParts[2];
            String expectedHash = hashParts[3];

            for (String password : commonPasswords) {
                String actualHash = MD5Shadow.crypt(password,salt);
                if (actualHash.equals(expectedHash)) {
                    System.out.println(username + ":" + password);
                    break;
                }
            }
        }
        scanner.close();
    }
}
