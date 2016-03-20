package com.ultracash.upi.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.security.cert.X509Certificate;

import com.sun.jersey.core.util.Base64;

public class Trail {
	public static void main(String[] args) {
		String encryptedData = "A+O6YpJOBKfhDwoItr137DcHek7tthEaGYSFATDLsSKvROHxRzs4Q2hsGTtGDn0Vap06aKeSgKj/QPayBIZGZ6tMQOuBsn0aekuaWMOG6/x3zPFblbS2x+4N18cUbSAHNm17Zm9fNcAwjUEAL0pjIED7eMP57FyCeCYcedkW9VBnM/20C4rsAm9RhxsZAqZ/k5QgwqVHbnmjUZLiFAY/KPEE1gBxfJOILJm3z9WU8XDxrH2LD8wvjUGB7Lt2mfzm/kg1UCCXetnbZYHCQcG4CAl44QwI39qp4ELG/6gWwmYRwXR6MAv+ex5eDB+N7qa/jWkR/S8+Urt5FB+IbDCZfw==";
		System.out.println(Trail.decrypt(encryptedData));
		// System.out.println(Trail.decrypt(Trail.encrypt(Trail.decrypt(encryptedData))));
		System.out.println(Trail.encrypt("11"));
		// System.out.println(encryptedData);
	}

	public static String decrypt(String encryptedData) {
		try {
			PrivateKey privateKey = null;
			PublicKey publicKey = null;
			String password = "Ultracash";
			String alias = "ultracash";
			KeyStore keystore = null;
			Cipher encryptionCipher;
			Cipher decryptionCipher;
			File signerFile = new File(
					"E:\\GIT Ultracash\\UPIServer\\src\\main\\resources\\config\\certs\\ultracash.p12");
			FileInputStream is = new FileInputStream(signerFile);
			keystore = KeyStore.getInstance("PKCS12");
			keystore.load(((InputStream) is), password.toCharArray());

			PrivateKey key = (PrivateKey) keystore.getKey(alias,
					password.toCharArray());
			privateKey = key;
			Certificate cert = keystore.getCertificate(alias);
			publicKey = cert.getPublicKey();
			encryptionCipher = Cipher.getInstance("RSA");
			decryptionCipher = Cipher.getInstance("RSA");
			encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
			String decryptData = null;
			byte[] dectyptedText = null;

			dectyptedText = decryptionCipher.doFinal(Base64
					.decode(encryptedData));
			decryptData = new String(dectyptedText);
			return decryptData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static PublicKey getPublicKeyFromEncodedCertData(
			String encodedCertData) throws CertificateException,
			javax.security.cert.CertificateException {
		if (encodedCertData == null || encodedCertData.equals(""))
			return null;

		X509Certificate cert = getCertificateFromBase64String(encodedCertData);

		if (cert == null)
			return null;

		return cert.getPublicKey();
	}

	public static X509Certificate getCertificateFromBase64String(String string)
			throws CertificateException,
			javax.security.cert.CertificateException {
		if (string.equals("") || string == null) {
			return null;
		}

		byte[] certBytes = Base64.decode(string.getBytes());

		javax.security.cert.X509Certificate cert = javax.security.cert.X509Certificate
				.getInstance(certBytes);

		return cert;
	}

	public static String encrypt(String rawData) {
		try {
			Cipher encryptionCipher;
			String key = "MIIFZDCCBEygAwIBAgIKUxoVFlQDvKiqtzANBgkqhkiG9w0BAQsFADCB0TELMAkGA1UEBhMCSU4xDjAMBgNVBAoTBUlEUkJUMR0wGwYDVQQLExRDZXJ0aWZ5aW5nIEF1dGhvcml0eTEPMA0GA1UEERMGNTAwMDU3MRIwEAYDVQQIEwlURUxBTkdBTkExEjAQBgNVBAkTCVJvYWQgTm8uMTEVMBMGA1UEMxMMQ2FzdGxlIEhpbGxzMSAwHgYDVQQDExdJRFJCVCBDQSBURVNUIDIwMTUtVEVTVDEhMB8GCSqGSIb3DQEJARYSY2FoZWxwQGlkcmJ0LmFjLmluMB4XDTE1MTAxNDA4MDUwM1oXDTE2MTAxNDA4MDUwM1owVzELMAkGA1UEBhMCSU4xDTALBgNVBAoTBE5QQ0kxDDAKBgNVBAsTA1VQSTESMBAGA1UECBMJVEFNSUxOQURVMRcwFQYDVQQDEw4xOTIuMTY4LjIxNS4yODCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANNQ6swbCU8jB09kvTCuQCMhVzVOuOpMIjit7splyMvRFtJ0SjSkZHiEU5RsF+MBJ8phrRoF+QfyI9XpZCzeFSfRqZQ1fsH9C6dotquBOrkYliM7KquNjAkIL/BavyD6o6j23j/cof/GD5gYjHoQZoUHd6s5e+wtN5OndMgSDuav8153kyvbhQTRC+cjabpKyxgXH92/VR7I16RGkK8RkjBwoDVqUIF6RHAa+TidP18Vm4DAmTJZGHtXeoLMI7gkWf8cScBH20juQLJz7cqsUiI8oYBIvURLWJ82lsDgTeCQqrdKTC9I2tt5VMFSbjcJMDRigV75BqF1JZ/En+zyL+8CAwEAAaOCAbUwggGxMA4GA1UdDwEB/wQEAwIFoDAdBgNVHQ4EFgQU3cVKT+hYRCS3i9cupLg+1L67OjwwHwYDVR0jBBgwFoAUiUtyc0+ypZ7lakt16fgaVUuK2FkwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMIHCBgNVHSAEgbowgbcwgbQGBmCCZGQCAzCBqTCBpgYIKwYBBQUHAgIwgZkagZZOb3RpY2UgVGV4dD1DbGFzcyAzIGxldmVsIGlzIHJlbGV2YW50IHRvIGVudmlyb25tZW50cyB3aGVyZSB0aHJlYXRzIHRvIGRhdGEgYXJlIGhpZ2ggb3IgdGhlIGNvbnNlcXVlbmNlcyBvZiB0aGUgZmFpbHVyZSBvZiBzZWN1cml0eSBzZXJ2aWNlcyBhcmUgaGlnaCAwJAYDVR0RBB0wG4cEwKjXHIITVVBJVEVTVC5ucGNpLm9yZy5pbjBVBgNVHR8ETjBMMCSgIqAghh5odHRwOi8vMTAuMC42NS42NS9jcmxfMjczQi5jcmwwJKAioCCGHmh0dHA6Ly8xNzIuMTYuNi45L2NybF8yNzNCLmNybDANBgkqhkiG9w0BAQsFAAOCAQEAJU4LZwAyF9RE6LDEgJoKyQeX8aX8XaNRsbL/0xMPyjawC18/TpMEC/ZiQeWWdAtklxt0odBK7ErKV+ygdAKSSJiLs9dtMnMPg98hIYPr200hiAT2TDxGFzhCyjoWbkWSyF9CO125QZY6AvWs1zy7FYEaRcLPzp7evIKPNTttTeKR2h6tLeTF9laVh0qWVtV0pWSV3xAt2g1yjtsiKoyjbIgm9Fb85uSEdrU17iZvVEsOSlz6kQuK1rfL935RX4YdovrsmyxFFBeWjL+RAmijY1rXYXrchaRPMnGKV6txNnOhA9ljFtIZJeRDvQ4Twj01U6iGNyqwleubeFl5oSNXKA==";
			PublicKey publicKey = getPublicKeyFromEncodedCertData(key);
			encryptionCipher = Cipher.getInstance("RSA");
			encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			String encryptData = null;
			byte[] dectyptedText = null;
			try {
				dectyptedText = encryptionCipher.doFinal(rawData.getBytes());
			} catch (IllegalBlockSizeException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			} catch (BadPaddingException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			encryptData = new String(Base64.encode(dectyptedText));
			return encryptData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
