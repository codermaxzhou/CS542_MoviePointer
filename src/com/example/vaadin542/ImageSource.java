package com.example.vaadin542;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource.StreamSource;

public class ImageSource implements StreamSource {
    String path = "";
    
    public ImageSource(String path) {
        this.path = path;
    }
    

    @Override
    public InputStream getStream() {
        try {
            URL url = new URL(path);
            BufferedImage img = ImageIO.read(url);
            ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", imagebuffer);
            
            return new ByteArrayInputStream(imagebuffer.toByteArray());
        } catch(Exception e) {
            return null;
        }
    }

}
