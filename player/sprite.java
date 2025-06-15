package player;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class sprite {
    public sprite(){

    }

    public static BufferedImage getImages(String direction, int size) {
        try{
            BufferedImage original = ImageIO.read(new File(direction + 0 + ".png"));
            BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaleOp.filter(original, scaled);
            return scaled;
        }catch (IOException ex) {
        }
        return null;
    }

    public static BufferedImage getImages(String direction, int sizeX, int sizeY) {
        try{
            BufferedImage original = ImageIO.read(new File(direction + 0 + ".png"));
            BufferedImage scaled = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = AffineTransform.getScaleInstance(sizeX / original.getWidth(), sizeY / original.getHeight());
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            scaleOp.filter(original, scaled);
            return scaled;
        }catch (IOException ex) {
        }
        return null;
    }

    public static void getImages(String direction, BufferedImage[] image,int size, int count) {
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
        }
    }

    public static void getImages(String direction, BufferedImage[] image,int sizeX, int sizeY, int count) {
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(sizeX / original.getWidth(), sizeY / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
        }
    }

    public static BufferedImage[] getImages(int size, int count, String direction){
        BufferedImage[] image = new BufferedImage[count];
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
        }
        return image;
    }
}
