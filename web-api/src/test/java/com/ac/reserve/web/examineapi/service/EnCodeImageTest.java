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
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
            bitMatrix = new Code128Writer().encode("xxxxx", BarcodeFormat.EAN_13, 400, 80, null);
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
        // 输出新路径
        String newPdfPath = "F:\\test\\P1234000000.pdf";
        // 图片路径
        String imgpath="F:\\note\\code\\reserve\\image\\aa.png";
        // 模板路径
        String modelPdfPath = "F:\\test\\P1234.pdf";
        Map<String, String> data = new HashMap<>();
        data.put("possessor_name", "1111");
        data.put("possessor_phone", "2222");
        data.put("type", "3333");
        data.put("round", "4444");

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
            /**
             * 设置编码格式
             */
            s.addSubstitutionFont(bfChinese);

            // 遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                s.setField(key,data.get(key).toString());
            }

//            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            ps.setFormFlattening(true);
            /**
             * 添加图片
             */

            int pageNo = s.getFieldPositions("qr_code_image").get(0).page;
            Rectangle signRect = s.getFieldPositions("qr_code_image").get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            System.out.println("x : " + x + ", y : " + y);
            // 读图片
            Image image = Image.getInstance(imgpath);
            // 获取操作的页面
            PdfContentByte under = ps.getOverContent(pageNo);
            // 根据域的大小缩放图片
//            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            image.scaleToFit(100f, 100f);
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);
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

    @Autowired
    UserService userService;

    @Test
    public void fun_04() {
        String openid = "";
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        wrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        User user = userService.getOne(wrapper);
    }


}
