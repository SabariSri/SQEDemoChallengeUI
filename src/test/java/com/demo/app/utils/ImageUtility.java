package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.demo.app.enums.ConfigKeywords;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageUtility {
    private static final String SCREENSHOTS_DIRECTORY = System.getProperty("user.dir") + TestBase
            .getConfigProperty(ConfigKeywords.SCREENSHOT_OUTPUT.toString())
            .replaceAll("//", File.separator);
    private static final String APP_EXTENSION = "ApplicationExtensions";
    static boolean captureAllSteps = Boolean.parseBoolean(TestBase.getConfigProperty(ConfigKeywords.SCREENSHOT_CAPTURE_ALL_STEPS.toString()));
    static boolean createGif = Boolean.parseBoolean(TestBase.getConfigProperty(ConfigKeywords.GIF_RECAP_CREATE.toString()));
    static boolean deleteSnapsPostGif = Boolean.parseBoolean(TestBase.getConfigProperty(ConfigKeywords.SCREENSHOT_DEL_POST_GIF.toString()));

    private ImageUtility() {
        throw new IllegalStateException("Image utility class");
    }

    /**
     * Takes screenshot specifically only when these flags createGif, captureAllSteps from config are true
     *
     * @param testcaseName to append it in the screenshot name
     * @param isStepFail   takes screenshot if the test step is fail
     * @return screenshot path
     */
    public static String takeScreenshotDynamically(String testcaseName, String stepStatus, boolean isStepFail) {
        if (createGif) {
            return getScreenshot(testcaseName, stepStatus);
        } else if (captureAllSteps) {
            return getScreenshot(testcaseName, stepStatus);
        } else if (isStepFail) {
            return getScreenshot(testcaseName, stepStatus);
        }
        return null;
    }

    /**
     * Takes screenshot in the default screenshot directory
     *
     * @param testcaseName to append it in the screenshot name
     * @param stepStatus   to append pass, fail, warning in screenshot name
     * @return screenshot absolute path
     */
    public static String getScreenshot(String testcaseName, String stepStatus) {
        String extension = TestBase.getConfigProperty(ConfigKeywords.SCREENSHOT_EXTENSION.toString());
        String screenshotPath = SCREENSHOTS_DIRECTORY + testcaseName + "_" + stepStatus + "-"
                + CommonUtility.getDateTime() + extension;
        try {
            TakesScreenshot screenshot = ((TakesScreenshot) TestBase.getDriver());
            File file = screenshot.getScreenshotAs(OutputType.FILE);
            CommonUtility.waitFor(file, Integer.parseInt(TestBase
                    .getConfigProperty(ConfigKeywords.SCREENSHOT_GAP_MS.toString())));
            FileUtils.copyFile(file, new File(screenshotPath));
        } catch (Exception e) {
            TestBase.getLogger().error("Unable to take screenshot", e);
        }
        TestBase.getScreenshotsSet().add(screenshotPath);
        return screenshotPath;
    }

    /**
     * Deletes the screenshots after creating the gif based on below flags from config
     */
    public static void deletePassedScreenshotsPostGif() throws IOException {
        if (deleteSnapsPostGif && !captureAllSteps) {
            File folder = new File(SCREENSHOTS_DIRECTORY);
            File[] allFiles = folder.listFiles();
            for (int i = 0; i < (allFiles != null ? allFiles.length : 0); i++) {
                String screenshotExt = TestBase.getConfigProperty(ConfigKeywords.SCREENSHOT_EXTENSION.toString());
                String eachFileName = allFiles[i].getName();
                if (eachFileName.endsWith(screenshotExt) && !allFiles[i].getName().contains("Fail")) {
                    Files.delete(allFiles[i].toPath());
                }
            }
            TestBase.getLogger().info("Deleted the passed screenshots as the Gif is created");
        } else {
            TestBase.getLogger().info("Did not delete the passed screenshot as per the configuration flags");
        }
    }

    public static void createAnimatedGif(String testcaseName, int delayInMilliseconds, Integer repeatCount) throws IOException {
        if (createGif) {
            String gifPath = SCREENSHOTS_DIRECTORY + testcaseName + "_" + CommonUtility.getDateTime() + ".gif";
            new File(gifPath);
            OutputStream stream = Files.newOutputStream(Paths.get(gifPath));
            List<BufferedImage> frames = new ArrayList<>();
            if (!TestBase.getScreenshotsSet().isEmpty()) {
                TestBase.getLogger().info("Gif creation is in progress...");
                TestBase.getScreenshotsSet().forEach(eachScreenshot -> {
                    try {
                        frames.add(ImageIO.read(new File(eachScreenshot)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                try (ImageOutputStream iioStream = ImageIO.createImageOutputStream(stream)) {
                    ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
                    writer.setOutput(iioStream);
                    writer.prepareWriteSequence(null);
                    for (BufferedImage frame : frames) {
                        writeFrame(frame, delayInMilliseconds, writer, repeatCount);
                        repeatCount = null;
                    }
                    writer.endWriteSequence();
                    writer.dispose();
                }
                TestBase.getReport().stepRecap("Converted the available screenshots into gif", gifPath);
                deletePassedScreenshotsPostGif();
            } else {
                TestBase.getLogger().warn("There are no screenshots available to create a gif");
            }
        }
    }

    private static void writeFrame(BufferedImage image, int delayInSeconds, ImageWriter writer, Integer repeatCount) {
        try {
            ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(image);
            IIOMetadata metadata = writer.getDefaultImageMetadata(type, null);
            String format = metadata.getNativeMetadataFormatName();
            Node tree = metadata.getAsTree(format);
            if (repeatCount != null) {
                setRepeatCount(repeatCount, tree);
            }
            setDelayTime(delayInSeconds, tree);
            metadata.setFromTree(format, tree);
            writer.writeToSequence(new IIOImage(image, null, metadata), null);
        } catch (Exception ignored) {
        }
    }

    private static void setRepeatCount(Number repeatCount, Node imageMetadata) {
        Element root = (Element) imageMetadata;
        ByteBuffer buffer = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) 1);
        byte[] appExtBytes = buffer.putShort(repeatCount.shortValue()).array();
        Element appExtContainer;
        NodeList nodes = root.getElementsByTagName(APP_EXTENSION);
        if (nodes.getLength() > 0) {
            appExtContainer = (Element) nodes.item(0);
        } else {
            appExtContainer = new IIOMetadataNode(APP_EXTENSION);
            Node reference = null;
            nodes = root.getElementsByTagName("CommentExtensions");
            if (nodes.getLength() > 0) {
                reference = nodes.item(0);
            }
            root.insertBefore(appExtContainer, reference);
        }
        IIOMetadataNode appExt = new IIOMetadataNode(APP_EXTENSION);
        appExt.setAttribute("applicationID", "NETSCAPE");
        appExt.setAttribute("authenticationCode", "2.0");
        appExt.setUserObject(appExtBytes);
        appExtContainer.appendChild(appExt);
    }

    private static void setDelayTime(int delayInSeconds, Node imageMetadata) {
        Element root = (Element) imageMetadata;
        Element element;
        NodeList nodes = root.getElementsByTagName("GraphicControlExtension");
        if (nodes.getLength() > 0) {
            element = (Element) nodes.item(0);
        } else {
            element = new IIOMetadataNode("GraphicControlExtension");
            Node reference = null;
            nodes = root.getElementsByTagName("PlainTextExtension");
            if (nodes.getLength() > 0) {
                reference = nodes.item(0);
            }
            if (reference == null) {
                nodes = root.getElementsByTagName(APP_EXTENSION);
                if (nodes.getLength() > 0) {
                    reference = nodes.item(0);
                }
            }
            if (reference == null) {
                nodes = root.getElementsByTagName("CommentExtensions");
                if (nodes.getLength() > 0) {
                    reference = nodes.item(0);
                }
            }
            root.insertBefore(element, reference);
        }
        element.setAttribute("delayTime", String.valueOf(delayInSeconds * 100));
    }
}
