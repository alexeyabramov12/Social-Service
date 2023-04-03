package ru.skillbox.diplom.group33.social.service.service.captcha;

import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaBuilder {

    public BufferedImage image;
    public Graphics2D graphics;
    private String captchaString;


    public CaptchaBuilder createImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        graphics.setColor(new Color(33, 164, 93));
        graphics.fillRect(0, 0, width, height);
        return this;
    }

    public CaptchaBuilder setText(int beginX, int beginY, int fontSize, int lengthText) {
        captchaString = createCaptchaString(lengthText);
        graphics.setColor(new Color(255, 255, 255));
        graphics.setFont(new Font("Osaka", Font.ITALIC, fontSize));
        graphics.drawString(captchaString, beginX, beginY);
        return this;
    }


    public Captcha build() {
        Captcha captcha = new Captcha();
        captcha.setCode(captchaString);
        captcha.setImage(image);
        return captcha;
    }


    public CaptchaBuilder setLines(int lineCount) {
        graphics.setColor(new Color(12, 234, 113));
        for (int i = 0; i < lineCount; i++) {
            int[] rL = randomLine();
            graphics.drawLine(rL[0], rL[1], rL[2], rL[3]);
        }
        return this;
    }


    public String createCaptchaString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            builder.append(randomNumber);
        }
        return builder.toString();
    }

    public int[] randomLine() {
        Random random = new Random();
        int[] randomLine = new int[4];
        int test = random.nextInt(image.getWidth());
        randomLine[0] = random.nextInt(image.getWidth());
        randomLine[1] = random.nextInt(image.getHeight());
        randomLine[2] = random.nextInt(image.getWidth());
        randomLine[3] = random.nextInt(image.getHeight());
        return randomLine;
    }


}
