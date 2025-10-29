import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class Steganography {
    public static String textToBits(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(String.format("%08b", (int)c));
        }
        sb.append("1111111111111110");
        return sb.toString();
    }

    public static void encodeImage(String imagePath, String outputPath, String secretText) throws Exception {
        BufferedImage img = ImageIO.read(new File(imagePath));
        int h = img.getHeight(), w = img.getWidth();
        String bits = textToBits(secretText);
        if (bits.length() > h * w * 3)
            throw new Exception("Text too long for this image.");

        int idx = 0;
        outer: for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = img.getRGB(x, y);
                int[] colors = {
                    (rgb >> 16) & 0xFF,
                    (rgb >> 8) & 0xFF,
                    rgb & 0xFF
                };
                for (int c = 0; c < 3; c++) {
                    if (idx < bits.length()) {
                        colors[c] = (colors[c] & ~1) | (bits.charAt(idx++) - '0');
                    }
                }
                int newRgb = ((colors[0] & 0xFF) << 16) | ((colors[1] & 0xFF) << 8) | (colors[2] & 0xFF);
                img.setRGB(x, y, (rgb & 0xFF000000) | newRgb);
                if (idx >= bits.length()) break outer;
            }
        }
        ImageIO.write(img, "png", new File(outputPath));
        System.out.println("✅ Secret data encoded and saved to " + outputPath);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String imagePath = "reference.jpeg";
        String outputPath = "encoded_image.png";
        System.out.print("Enter secret message to encode: ");
        String secretText = sc.nextLine();
        encodeImage(imagePath, outputPath, secretText);
    }
}
