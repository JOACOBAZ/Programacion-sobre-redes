package tp6_cliente;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CifradoUtil {
 
    private static final String CLAVE_SECRETA = "ClaveSegura2026!";
    private static final String ALGORITMO = "AES";

    public static String encriptar(String textoPlano) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8), ALGORITMO);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytesEncriptados = cipher.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytesEncriptados);
    }

    public static String desencriptar(String textoEncriptado) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8), ALGORITMO);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytesDecodificados = Base64.getDecoder().decode(textoEncriptado);
        byte[] bytesDesencriptados = cipher.doFinal(bytesDecodificados);
        return new String(bytesDesencriptados, StandardCharsets.UTF_8);
    }
}
