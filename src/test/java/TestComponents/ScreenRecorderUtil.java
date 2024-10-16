package TestComponents;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorderUtil extends ScreenRecorder {
    public static ScreenRecorder screenRecorder;
    public String name;
    private File currentMovieFile;  // To store the current recording file

    public ScreenRecorderUtil(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
                              Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        currentMovieFile = new File(movieFolder,
                name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
        return currentMovieFile;
    }

    public static void startRecord(String methodName) throws Exception {
        File file = new File("/Users/abhayverma/IdeaProjects/CICD/reports");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        // Change to MP4 format
        screenRecorder = new ScreenRecorderUtil(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),  // Change to QuickTime (.mp4)
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG,  // Set to .mp4 compatible encoding
                        CompressorNameKey, ENCODING_QUICKTIME_JPEG, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);
        screenRecorder.start();
    }

    public static void stopRecord() throws Exception {
        screenRecorder.stop();
    }

    // Convert the recorded file to Base64
    public String convertMovieToBase64() throws IOException {
        if (currentMovieFile != null && currentMovieFile.exists()) {
            FileInputStream fileInputStream = new FileInputStream(currentMovieFile);
            byte[] fileBytes = new byte[(int) currentMovieFile.length()];
            fileInputStream.read(fileBytes);
            fileInputStream.close();
            return Base64.getEncoder().encodeToString(fileBytes);
        } else {
            throw new IOException("Movie file does not exist.");
        }
    }
}
