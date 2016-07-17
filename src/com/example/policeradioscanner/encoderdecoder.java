package com.example.policeradioscanner;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.akero.fuzzradiopro.R;

public class encoderdecoder extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.encoder_decoder_layout);
	String s = "This is my data I want to keep safe.";
	String encryptedText = encrypt(s);
	String clearText = decrypt(encryptedText);
	((TextView)findViewById(R.id.encrypted_text)).setText(encryptedText);
	((TextView)findViewById(R.id.clear_text)).setText(clearText);
	}
	private byte[] getKey() {
		byte[] seed = "here_is_your_aes_key".getBytes();
		KeyGenerator kg;
		try {
		kg = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
		return null;
		}
		SecureRandom sr;
		try {
		sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
		return null;
		}
		sr.setSeed(seed);
		kg.init(128, sr);
		SecretKey sk = kg.generateKey();
		byte[] key = sk.getEncoded();
		return key;
		}
	private String encrypt(String clearText) {
		byte[] encryptedText = null;
		try {
		SecretKeySpec ks = new SecretKeySpec(getKey(), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, ks);
		encryptedText = c.doFinal(clearText.getBytes("UTF-8"));
		return Base64.encodeToString(encryptedText, Base64.DEFAULT);
		} catch (Exception e) {
		return null;
		}

		}private String decrypt (String encryptedText) {

		byte[] clearText = null;
		try {
		SecretKeySpec ks = new SecretKeySpec(getKey(), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, ks);
		clearText = c.doFinal(Base64.decode(encryptedText, Base64.DEFAULT));
		return new String(clearText, "UTF-8");
		} catch (Exception e) {Log.d("error in decrypt"+e.toString(),"tagggggg");
		return null;
		}
		}
}
