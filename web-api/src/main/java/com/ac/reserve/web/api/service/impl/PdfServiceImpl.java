package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.PdfService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] getPdfByte(String contents, String idCare, Map<String, String> data) {
        // 二维码
        String qrCodeFileName = idCare + "_qrCodeImage.png";
        int qrCodeWidth = 400;
        int qrCodeHeight = 400;
        // 条形码
        String barCodeFileName = idCare + "_barCodeImage.png";
        int barCodeWidth = 400;
        int barCodeHeight = 80;

        // 创建临时文件夹
        File tempDirectory = null;
        try {
            tempDirectory = createTempDirectory();

            File qrCodeFile = new File(tempDirectory.getPath() + File.separator + qrCodeFileName);
            System.out.println("qrCodeFilePath : " + qrCodeFile.getPath());
            File barCodeFile = new File(tempDirectory.getPath() + File.separator + barCodeFileName);
            System.out.println("barCodeFilePath : " + barCodeFile.getPath());

            // 生成二维码
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "GBK");
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix qrCodeBitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hints);
            MatrixToImageWriter.writeToFile(qrCodeBitMatrix, "png", qrCodeFile);
            // 生成条形码
            BitMatrix barCodeBitMatrix = new Code128Writer().encode(contents, BarcodeFormat.CODE_128, barCodeWidth, barCodeHeight, hints);
            // 条形码下面加数字
            writeToStream(barCodeBitMatrix, contents, "PNG", new FileOutputStream(barCodeFile));
//            MatrixToImageWriter.writeToFile(barCodeBitMatrix, "png", barCodeFile);
            // 生成PDF
            return createPdf(data, qrCodeFile.getPath(), barCodeFile.getPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        finally {
            // 删除临时文件
            try {
                FileUtils.deleteDirectory(tempDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] createPdf(Map<String, String> data, String qrCodeFilePath, String barCodeFilePath) {
        // 模板路径
        InputStream modelPdfPath = this.getClass().getClassLoader().getResourceAsStream("pdf" + File.separator + "model.pdf");
        PdfReader reader = null;
        AcroFields s = null;
        PdfStamper ps = null;
        ByteArrayOutputStream bos = null;
        try {
            reader = new PdfReader(modelPdfPath);
            bos = new ByteArrayOutputStream();
            ps = new PdfStamper(reader, bos);
            s = ps.getAcroFields();
            /**
             * 使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
             */
//            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            String pdfOutFontPath = "C:\\Windows\\Fonts\\simhei.ttf";
            BaseFont bfChinese = BaseFont.createFont(pdfOutFontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 设置编码格式
            s.addSubstitutionFont(bfChinese);
            // 遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                s.setField(key, data.get(key));
            }
            //----------------------------
            // 添加二维码
            int pageNo = s.getFieldPositions("qr_code_image").get(0).page;
            Rectangle signRect = s.getFieldPositions("qr_code_image").get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            // 读图片
            Image image = Image.getInstance(qrCodeFilePath);
            // 获取操作的页面
            PdfContentByte under = ps.getOverContent(pageNo);
            // 图片缩放
            image.scaleToFit(100f, 100f);
            // 添加图片
            image.setAbsolutePosition(x, y);

            //==============================
            // 添加条形码
            Rectangle barCodeSignRect = s.getFieldPositions("bar_code_image").get(0).position;
            float barCodeX = barCodeSignRect.getLeft();
            float barCodeY = barCodeSignRect.getBottom();
            // 读图片
            Image barCodeImage = Image.getInstance(barCodeFilePath);
            // 获取操作的页面
            PdfContentByte barCodeUnder = ps.getOverContent(1);
            // 图片缩放
            barCodeImage.scaleToFit(250f, 50f);
            // 添加图片
            barCodeImage.setAbsolutePosition(barCodeX, barCodeY);
            //==============================

            under.addImage(image);
            under.addImage(barCodeImage);

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            ps.setFormFlattening(true);
            ps.close();
            return bos.toByteArray();
        } catch (IOException | DocumentException e) {
            System.out.println("读取文件异常");
            e.printStackTrace();
        }finally {
            try {
                bos.close();
                ps.close();
                reader.close();
            } catch (IOException | DocumentException e) {
                System.out.println("关闭流异常");
                e.printStackTrace();
            }
        }
        return null;
    }

    // 创建临时文件夹
    public static File createTempDirectory() throws IOException {
        final File temp;
        temp = File.createTempFile("tempReserve", Long.toString(System.nanoTime()));
        if(!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }
        if(!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }
        return (temp);
    }

    public static void writeToStream(BitMatrix bitMatrix, String barcode, String format, OutputStream out) throws IOException {
        BufferedImage image = toBufferedImage(bitMatrix, barcode);
        if (!ImageIO.write(image, format, out)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
    private static BufferedImage toBufferedImage(BitMatrix matrix, String barcode) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int[] rowPixels = new int[width];
        BitArray row = new BitArray(width);
        for(int y = 0; y < height; ++y) {
            row = matrix.getRow(y, row);
            for(int x = 0; x < width; ++x) {
                if (y > height - 25){   //最后25像素不填充，用来写数字
                    rowPixels[x] = -1;
                }else {
                    rowPixels[x] = row.get(x) ? -1 : -16777216;
                }
            }
            image.setRGB(0, y, width, 1, rowPixels, 0, width);
        }
        Graphics graphics = image.getGraphics();
        Font font = new Font("Helvetica", Font.PLAIN,20);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        int strWidth = graphics.getFontMetrics().stringWidth(barcode);
        graphics.drawString(barcode, width/2 - strWidth / 2, height - 3);
        return image;
    }

}
