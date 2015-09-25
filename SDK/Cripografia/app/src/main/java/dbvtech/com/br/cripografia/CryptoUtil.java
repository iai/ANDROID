package dbvtech.com.br.cripografia;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Paulo on 20/07/2015.
 */
public class CryptoUtil {
    private static String vetorInicializacao = "1234567812345678";
    private static String salt = "1234567812345678";
    private static int quantidadeRotacoes = 2;
    private static int tamanhoChave = 256;

    public static String proteger(String textoClaro, String senha) throws Exception{
        byte[] saltBytes = salt.getBytes("UTF-8");
        byte[] ivBytes = vetorInicializacao.getBytes("UTF-8");

        SecretKeyFactory fabricaChave = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec especificacaoChave = new PBEKeySpec(senha.toCharArray(), saltBytes, quantidadeRotacoes, tamanhoChave);
        SecretKey chave = fabricaChave.generateSecret(especificacaoChave);

        SecretKeySpec especificacaoFechadura = new SecretKeySpec(chave.getEncoded(), "AES");
        Cipher fechadura = Cipher.getInstance("AES/CBC/PKCS5Padding");
        fechadura.init(Cipher.ENCRYPT_MODE, especificacaoFechadura, new IvParameterSpec(ivBytes));

        byte[] dadosProtegidos = fechadura.doFinal(textoClaro.getBytes("UTF-8"));
        return Base64.encodeToString(dadosProtegidos, 0);
    }

    public static String desproteger(String textoProtegido, String senha) throws Exception{
        byte[] saltBytes = salt.getBytes("UTF-8");
        byte[] ivBytes = vetorInicializacao.getBytes("UTF-8");
        byte[] dadosProtegidos = Base64.decode(textoProtegido.getBytes("UTF-8"), 0);

        SecretKeyFactory fabricaChave = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec especificacaoChave = new PBEKeySpec(senha.toCharArray(), saltBytes, quantidadeRotacoes, tamanhoChave);
        SecretKey chave = fabricaChave.generateSecret(especificacaoChave);

        SecretKeySpec especificacaoFechadura = new SecretKeySpec(chave.getEncoded(), "AES");
        Cipher fechadura = Cipher.getInstance("AES/CBC/PKCS5Padding");
        fechadura.init(Cipher.DECRYPT_MODE, especificacaoFechadura, new IvParameterSpec(ivBytes));

        byte[] dadosDesprotegidos = fechadura.doFinal(dadosProtegidos);
        return new String(dadosDesprotegidos);
    }
}
