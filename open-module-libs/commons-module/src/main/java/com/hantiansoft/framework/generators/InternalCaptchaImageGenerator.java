package com.hantiansoft.framework.generators;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/* Creates on 2022/8/8. */

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * 验证码生成
 *
 * @author Vincent Luo
 */
class InternalCaptchaImageGenerator {
    private final int width;
    private final int height;
    private final Random random = new Random();
    private final Color bgColor = new Color(255, 255, 255);
    private final String[] fontsName = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};
    private String code;

    public InternalCaptchaImageGenerator(String code) {
        this.code = code;
        this.width = 50 * code.length();
        this.height = 80;
    }

    @SneakyThrows
    public String generateImageBase64Code() {
        BufferedImage __IMG = addCharAndLine(this.code);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(__IMG, "png", output);
        return Algorithms.BASE64(output.toByteArray());
    }

    private String getFont() {
        int index = random.nextInt(fontsName.length);
        return fontsName[index];
    }

    private Color getColor() {
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, green, blue);
    }

    private BufferedImage getBufferedImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D pen = (Graphics2D) bi.getGraphics();
        pen.setColor(this.bgColor);
        pen.fillRect(0, 0, width, height);
        return bi;
    }

    private BufferedImage addCharAndLine(String code) {
        BufferedImage bi = getBufferedImage();
        Graphics2D pen = (Graphics2D) bi.getGraphics();

        char[] codeCharArray = code.toCharArray();

        for (int i = 0, len = codeCharArray.length; i < len; i++) {
            String font = getFont();
            // 生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
            int style = random.nextInt(4);
            pen.setColor(getColor());
            int fontSize = 70;
            pen.setFont(new Font(font, style, fontSize));
            pen.drawString(String.valueOf(codeCharArray[i]), 10 + i * 45, 60);
        }

        // 画5条干扰线
        int lineNumber = 5;
        pen.setColor(Color.BLUE);
        pen.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        for (int i = 0; i < lineNumber; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            pen.drawLine(x1, y1, x2, y2);
        }

        return bi;
    }
}
