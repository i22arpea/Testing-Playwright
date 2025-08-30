package matricula.e2e.test.utils.accesibilidad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author aaperez
 */
public class HighlightAccessibilityIssues {
    public static void highlightViolations(String imagePath, String jsonPath, String outputImagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(jsonPath));

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        int viewportWidth = imageWidth;
        int viewportHeight = imageHeight;

        if (rootNode.has("testEnvironment")) {
            JsonNode testEnv = rootNode.get("testEnvironment");
            if (testEnv.has("windowWidth") && testEnv.has("windowHeight")) {
                viewportWidth = testEnv.get("windowWidth").asInt();
                viewportHeight = testEnv.get("windowHeight").asInt();
            }
        }

        double scaleX = (double) imageWidth / viewportWidth;
        double scaleY = (double) imageHeight / viewportHeight;

        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(3));
        g.setFont(new Font("Arial", Font.BOLD, 24));

        JsonNode violations = rootNode.get("violations");
        int violationCount = 1;

        if (violations != null && violations.isArray()) {
            for (JsonNode violation : violations) {
                for (JsonNode node : violation.get("nodes")) {
                    JsonNode target = node.get("target");
                    JsonNode boundingBox = node.get("boundingBox");

                    if (boundingBox != null &&
                            boundingBox.has("x") &&
                            boundingBox.has("y") &&
                            boundingBox.has("width") &&
                            boundingBox.has("height")) {

                        int x = (int) (boundingBox.get("x").asDouble() * scaleX);
                        int y = (int) (boundingBox.get("y").asDouble() * scaleY);
                        int width = (int) (boundingBox.get("width").asDouble() * scaleX);
                        int height = (int) (boundingBox.get("height").asDouble() * scaleY);

                        g.setColor(Color.RED);
                        g.drawRect(x, y, width, height);

                        String numberText = String.valueOf(violationCount);
                        FontMetrics fm = g.getFontMetrics();
                        int stringWidth = fm.stringWidth(numberText);
                        int stringHeight = fm.getAscent();

                        g.setColor(Color.WHITE);
                        g.fillRect(x + width + 5, y + height / 2 - stringHeight / 2, stringWidth + 5, stringHeight + 5);

                        g.setColor(Color.BLACK);
                        g.drawString(numberText, x + width + 10, y + height / 2 + fm.getAscent() / 2);
                    }
                }
                violationCount++;
            }
        }

        g.dispose();
        ImageIO.write(image, "png", new File(outputImagePath));
    }
}

