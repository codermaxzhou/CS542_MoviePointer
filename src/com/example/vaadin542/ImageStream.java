package com.example.vaadin542;

import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

public class ImageStream implements StreamSource {
    InputStream data = null;
    
    public ImageStream(InputStream data) {
        this.data = data;
    }
    

    @Override
    public InputStream getStream() {
        return data;
    }

}
