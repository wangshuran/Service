package com.selfsell.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具
 * 
 * @author breeze
 *
 */
public class QRCodeUtil {
	/**
	 * 创建二维码图片
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage creatQrImage(String content, int width, int height) throws Exception {
		// 二维码的参数
		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 字符集
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static String creatQrImageBase64(String content, int width, int height) throws Exception {
		BufferedImage qrImage = QRCodeUtil.creatQrImage(content, 200, 200);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(qrImage, "png", Base64.getEncoder().wrap(os));
		return os.toString(StandardCharsets.UTF_8.name());
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(creatQrImageBase64("selfsell二维码",200,200));
	}

}
