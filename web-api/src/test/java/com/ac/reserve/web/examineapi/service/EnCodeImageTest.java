package com.ac.reserve.web.examineapi.service;

import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.web.api.WebApiApplication;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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

@SpringBootTest(classes = WebApiApplication.class)
@RunWith(SpringRunner.class)
public class EnCodeImageTest {

    /**
     * 生成二维码
     * @throws IOException
     */
    @Test
    public void fun_01() throws IOException {
        String contents = "contents";
        String fileName = "aaa.png";
        int width = 400;
        int height = 400;
        String property = System.getProperty("user.dir") + File.separator + "image";

        System.out.println(property);
        File propertyFile = new File(property);
        if (!propertyFile.exists()){
            FileUtils.cleanDirectory(propertyFile);
        }
        String imgPath = property + File.separator + fileName;

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成条形码
     */
    @Test
    public void fun_02() {
        String contents = "xxxxx";
        int width = 400;
        int height = 80;
        String imgPath = "F:\\note\\code\\reserve\\image\\bb.png";

        BitMatrix bitMatrix;
        try {
            bitMatrix = new Code128Writer().encode("xxxxx", BarcodeFormat.CODE_128, 400, 80, null);
//            bitMatrix = new CodaBarWriter().encode("xxxxx", BarcodeFormat.EAN_13, 400, 80, null);
//            writeToStream(bitMatrix, "12345678", "PNG", new FileOutputStream(imgPath));
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File(imgPath)));
            System.out.println("Code128 Barcode Generated.");
        } catch (Exception e) {
            System.out.println("Exception Found." + e.getMessage());
        }

    }


    /**
     * 生成PDF，
     * @throws Exception
     */
    @Test
    public void fun_03() throws Exception{
//        // 输出新路径
//        String newPdfPath = "F:\\test\\P1234000000.pdf";
//        // 图片路径
//        String imgpath="F:\\note\\code\\reserve\\image\\aa.png";
//        // 模板路径
//        String modelPdfPath = "F:\\test\\P1234.pdf";
//        Map<String, String> data = new HashMap<>();
//        data.put("possessor_name", "1111");
//        data.put("possessor_phone", "2222");
//        data.put("type", "3333");
//        data.put("round", "4444");
//
//        PdfReader reader = null;
//        AcroFields s = null;
//        PdfStamper ps = null;
//        ByteArrayOutputStream bos = null;
//        try {
//            reader = new PdfReader(modelPdfPath);
//            bos = new ByteArrayOutputStream();
//            ps = new PdfStamper(reader, bos);
//            s = ps.getAcroFields();
//
//            /**
//             * 使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
//             */
////            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//            String pdfOutFontPath = "C:\\Windows\\Fonts\\simhei.ttf";
//            BaseFont bfChinese = BaseFont.createFont(pdfOutFontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            /**
//             * 设置编码格式
//             */
//            s.addSubstitutionFont(bfChinese);
//
//            // 遍历data 给pdf表单表格赋值
//            for (String key : data.keySet()) {
//                s.setField(key,data.get(key).toString());
//            }
//
////            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
////            ps.setFormFlattening(true);
//            /**
//             * 添加图片
//             */
//
//            int pageNo = s.getFieldPositions("qr_code_image").get(0).page;
//            Rectangle signRect = s.getFieldPositions("qr_code_image").get(0).position;
//            float x = signRect.getLeft();
//            float y = signRect.getBottom();
//            System.out.println("x : " + x + ", y : " + y);
//            // 读图片
//            Image image = Image.getInstance(imgpath);
//            // 获取操作的页面
//            PdfContentByte under = ps.getOverContent(pageNo);
//            // 根据域的大小缩放图片
////            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
//            image.scaleToFit(100f, 100f);
//            // 添加图片
//            image.setAbsolutePosition(x, y);
//            under.addImage(image);
//            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            ps.setFormFlattening(true);
//            @SuppressWarnings("resource")
//            FileOutputStream fos = new FileOutputStream(newPdfPath);
//            ps.close();
//            fos.write(bos.toByteArray());
//        } catch (IOException | DocumentException e) {
//            System.out.println("读取文件异常");
//            e.printStackTrace();
//        }finally {
//            try {
//                bos.close();
//                ps.close();
//                reader.close();
//
//            } catch (IOException | DocumentException e) {
//                System.out.println("关闭流异常");
//                e.printStackTrace();
//            }
//        }
    }

    @Autowired
    UserService userService;

    @Test
    public void fun_04() {
        String openid = "";
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        wrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        User user = userService.getOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void fun_05() {

        Map data = new HashMap();
        data.put("possessor_name", "11三生三世11");
        data.put("possessor_phone", "2222");
        data.put("type", "3333");
        data.put("round", "4444");


        String contents = "contents";
        String qrCodeFileName = "aaa.png";
        int qrCodeWidth = 400;
        int qrCodeHeight = 400;
        String barCodeFileName = "bbb.png";
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
            MatrixToImageWriter.writeToFile(barCodeBitMatrix, "png", barCodeFile);


            // 生成PDF
            createPdf(data, "C:\\Users\\Demoo\\AppData\\Local\\Temp\\tempReserve4621509403359920106476196093918800\\aaa.png",
                    "C:\\Users\\Demoo\\AppData\\Local\\Temp\\tempReserve4621509403359920106476196093918800\\bbb.png");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        finally {
//            // 删除临时文件
//            try {
//                FileUtils.deleteDirectory(tempDirectory);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        // 新PDF输出路径：
//        File pdfFile = new File(tempDirectory.getPath() + File.separator + pdfName);
    }

    public void createPdf(Map<String, String> data, String qrCodeFilePath, String barCodeFilePath) {
        // 输出新路径
        String newPdfPath = "F:\\test\\P1234000000.pdf";
        // 模板路径
        InputStream modelPdfPath = this.getClass().getClassLoader().getResourceAsStream("pdf"+File.separator+"model.pdf");

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
                s.setField(key,data.get(key));
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
            @SuppressWarnings("resource")
            FileOutputStream fos = new FileOutputStream(newPdfPath);
            ps.close();
            fos.write(bos.toByteArray());
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
    }



    // 创建临时文件夹
    public static File createTempDirectory() throws IOException {
        final File temp;
        temp = File.createTempFile("tempReserve", Long.toString(System.nanoTime()));
        if(!(temp.delete()))
        {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }
        if(!(temp.mkdir()))
        {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }
        return (temp);
    }



}
