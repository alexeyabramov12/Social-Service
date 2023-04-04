package ru.skillbox.diplom.group33.social.service.service.captcha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class CaptchaBuilderTest {

    @Mock
    BufferedImage image;

    @Mock
    Graphics2D graphics;
    @InjectMocks
    public CaptchaBuilder captchaBuilder;
    @Mock
    public Graphics2D mockGraphics;
    @Mock
    public BufferedImage mockImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        captchaBuilder = new CaptchaBuilder();
        captchaBuilder.image = mockImage;
        captchaBuilder.graphics = mockGraphics;
    }

    @Test
    public void testCreateImage() {
        int width = 100;
        int height = 50;
        CaptchaBuilder captchaBuilder = new CaptchaBuilder();

        CaptchaBuilder test = captchaBuilder.createImage(width, height);

        assertEquals(width, test.image.getWidth());
        assertEquals(height, test.image.getHeight());
    }

    @Test
    public void testSetText() {
        int beginX = 20;
        int beginY = 30;
        int fontSize = 15;
        int lengthText = 5;
        String captchaString = "12345";
        CaptchaBuilder captchaBuilder = new CaptchaBuilder();

        captchaBuilder.createImage(100, 50);

        captchaBuilder.setText(beginX, beginY, fontSize, lengthText);
        assertEquals(captchaString.length(), captchaBuilder.createCaptchaString(5).length());
    }


    @Test
    public void testBuild() {
        String captchaString = "12345";
        CaptchaBuilder captchaBuilder = new CaptchaBuilder();

        captchaBuilder.createImage(100, 50);

        captchaBuilder.setText(20, 30, 15, 5);

        Captcha captcha = captchaBuilder.build();

        assertNotEquals(captchaString, captcha.getCode());
    }

    @Test
    public void testRandomLine() {
        CaptchaBuilder captchaBuilder = new CaptchaBuilder();
        captchaBuilder.createImage(100, 50);
        int[] randomLine = captchaBuilder.randomLine();

        assertEquals(4, randomLine.length);
    }
}

