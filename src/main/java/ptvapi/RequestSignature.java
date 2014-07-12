package ptvapi;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jrigby on 12/07/2014.
 *
 * Creates a valid request signature
 */
public class RequestSignature {

    private int developerId;
    private String apiKey;

    public RequestSignature(int developerId, String apiKey) {
        this.developerId = developerId;
        this.apiKey = apiKey.toLowerCase();
    }

    private String generateSignatureForRequest(String uri) {
        String encoding = "UTF-8";
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String signature;

        StringBuffer uriWithDeveloperID = new StringBuffer();
        uriWithDeveloperID.append(uri).append(uri.contains("?") ? "&" : "?").append("devid=" + developerId);

        try {
            byte[] keyBytes = apiKey.getBytes(encoding);
            byte[] uriBytes = uriWithDeveloperID.toString().getBytes(encoding);
            Key signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] signatureBytes = mac.doFinal(uriBytes);
            StringBuffer buf = new StringBuffer(signatureBytes.length * 2);
            for (byte signatureByte : signatureBytes) {
                int intVal = signatureByte & 0xff;
                if (intVal < 0x10) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(intVal));
            }
            signature = buf.toString().toUpperCase();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return signature;
    }


    private String generateRequestURL(String uri) {
        String baseURL="http://timetableapi.ptv.vic.gov.au";
        StringBuffer url = new StringBuffer(baseURL).append(uri).append(uri.contains("?") ? "&" : "?").append("devid="+developerId).append("&signature="+generateSignatureForRequest(uri));
        return url.toString();
    }

}
