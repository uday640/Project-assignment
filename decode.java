import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class StegDecode {
    public static String bitsToText(String bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i + 7 < bits.length(); i += 8) {
            String byteStr = bits.substring(i, i + 8);
            sb.append((char) Integer.parseInt(byteStr, 2));
        }
        return sb.toString();
    }

    public static String decodeImage(String imagePath) throws Exception {
        BufferedImage img = ImageIO.read(new File(imagePath));
        int h = img.getHeight();
        int w = img.getWidth();
        StringBuilder bits = new StringBuilder();
        boolean ended = false;

        for (int y = 0; y < h && !ended; y++) {
            for (int x = 0; x < w && !ended; x++) {
                int rgb = img.getRGB(x, y);
                bits.append(((rgb >> 16) & 1));
                bits.append(((rgb >> 8) & 1));
                bits.append((rgb & 1));
                if (bits.length() >= 16 && bits.substring(bits.length()-16).equals("1111111111111110")) {
                    ended = true;
                }
            }
        }
        if (bits.length() >= 16)
            bits.setLength(bits.length() - 16);

        return bitsToText(bits.toString());
    }

    public static void main(String[] args) throws Exception {
        String encodedPath = "encoded_image.png";
        String secret = decodeImage(encodedPath);
        System.out.println("🔍 Recovered secret message: " + secret);
    }
}

