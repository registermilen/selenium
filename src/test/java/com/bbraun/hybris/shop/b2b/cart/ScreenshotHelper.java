package com.bbraun.hybris.shop.b2b.cart;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * Browser Screenshot utility class
 *
 */
public class ScreenshotHelper {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotHelper.class);

    /**
     * Captures a screenshot of the entire page for the Allure framework
     * @param driver
     */
    public static void capturePageScreenshotForReport(WebDriver driver) {
        try {
            Screenshot screenshotOfPage = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            BufferedImage screenshotImage = screenshotOfPage.getImage();
            getImageBytes(screenshotImage);
        } catch (Exception e) {
            logger.info("Error capturing screenshot. Proceed. " + e.getMessage());
        }
    }

    /**
     * Used to save screenshot for Allure reporting framework
     * @param screenshotImage
     * @return
     */
    @Attachment(value = "Screenshot", type="image/png")
    public static byte[] getImageBytes(BufferedImage screenshotImage) {
        byte[] imageInByte;

        // convert BufferedImage to byte array
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshotImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            logger.error("Error getting Screenshot for report: " + e.getMessage());
        }
        return null;
    }


}
