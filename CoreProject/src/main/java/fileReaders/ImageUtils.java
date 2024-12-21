package fileReaders;


import logging.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import seleniumCore.CoreConstants;
import seleniumCore.CoreVariables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;


public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static boolean isImagesIdentical(String baseFileName, String changeFileName, String newFileName) {
        CoreVariables.setImageTest(true);
        String baseFilePath = CoreConstants.pathScreenshots + baseFileName;
        String changeFilePath = CoreConstants.pathScreenshots + changeFileName;
        Log.link("Expected Image:", baseFileName);
        Log.link("Actual Image", changeFileName);
        return compareWithBaseImage(new File(baseFilePath), new File(changeFilePath), newFileName);
    }

    private static void createPngImage(BufferedImage image, String fileName) throws IOException {
        String newFilePath = CoreConstants.pathScreenshots + fileName;
        ImageIO.write(image, "png", new File(newFilePath));
    }

    private static void createJpgImage(BufferedImage image, String fileName) throws IOException {
        String newFilePath = CoreConstants.pathScreenshots + fileName;
        ImageIO.write(image, "jpg", new File(newFilePath));
    }

    private static boolean compareWithBaseImage(File baseImage, File compareImage, String resultOfComparison) {
        boolean isImagesIdentical = true;
        try {
            BufferedImage bImage = ImageIO.read(baseImage);
            BufferedImage cImage = ImageIO.read(compareImage);
            int height = bImage.getHeight();
            int width = bImage.getWidth();
            BufferedImage rImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    try {
                        int pixelC = cImage.getRGB(x, y);
                        int pixelB = bImage.getRGB(x, y);
                        if (pixelB == pixelC) {
                            rImage.setRGB(x, y, bImage.getRGB(x, y));
                        } else {
                            isImagesIdentical = false;
                            int a = 0xff | bImage.getRGB(x, y) >> 24,
                                    r = 0xff & bImage.getRGB(x, y) >> 16,
                                    g = 0x00 & bImage.getRGB(x, y) >> 8,
                                    b = 0x00 & bImage.getRGB(x, y);

                            int modifiedRGB = a << 24 | r << 16 | g << 8 | b;
                            rImage.setRGB(x, y, modifiedRGB);
                        }
                    } catch (Exception e) {
                        // handled hieght or width mismatch
                        rImage.setRGB(x, y, 0x80ff0000);
                    }
                }
            }
            String filePath = baseImage.toPath().toString();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.'), filePath.length());
            if (!isImagesIdentical) {
                logger.info("Images are not identical");
                if (fileExtension.toUpperCase().contains("PNG")) {
                    createPngImage(rImage, resultOfComparison + fileExtension);
                } else {
                    createJpgImage(rImage, resultOfComparison + fileExtension);
                }
                CoreVariables.setImagePath(resultOfComparison + fileExtension);
                Assert.fail("The comparison failed");
            } else
                logger.info("Images are identical");
        } catch (IOException io) {

        }
        return isImagesIdentical;
    }


    static boolean compareAndHighlight(final BufferedImage img1, final BufferedImage img2, String fileName, boolean highlight, int colorCode) throws IOException {
        CoreVariables.setImageTest(true);
        final int w = img1.getWidth();
        final int h = img1.getHeight();
        final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);

        if (!(java.util.Arrays.equals(p1, p2))) {

            logger.warn("Image compared - does not match");
            if (highlight) {
                for (int i = 0; i < p1.length; i++) {
                    if (p1[i] != p2[i]) {
                        p1[i] = colorCode;
                    }
                }
                final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                out.setRGB(0, 0, w, h, p1, 0, w);
                saveImage(out, fileName);
            }
            return false;
        }
        return true;
    }

    static void saveImage(BufferedImage image, String file) {
        try {
            File outputfile = new File(file);
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String takeScreenShot(WebDriver driver, String methodName) {

        String fileLocation = CoreConstants.pathScreenshots + methodName
                + ".png";
        try {
            File scrFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile,
                    new File(fileLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return methodName
                + ".png";
    }

    public static String takeFullScreenShot(WebDriver driver, String methodName) {

        String fileLocation = CoreConstants.pathScreenshots + methodName
                + ".png";
        try {
            Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ImageIO.write(s.getImage(), "PNG", new File(fileLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return methodName
                + ".png";
    }

    public static float compareImage(String baseFile, String changeFile) {

        float percentage = 0;
        try {
            File fileA= new File(CoreConstants.pathScreenshots+baseFile);
            File fileB= new File(CoreConstants.pathScreenshots+changeFile);
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            int count = 0;
            // compare data-buffer objects //
            if (sizeA == sizeB) {

                for (int i = 0; i < sizeA; i++) {

                    if (dbA.getElem(i) == dbB.getElem(i)) {
                        count = count + 1;
                    }

                }
                percentage = (count * 100) / sizeA;
            } else {
                System.out.println("Both the images are not of same size");
            }

        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
        }
        return percentage;
    }

}
