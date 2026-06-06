package com.example.ImgAnalyzer.FFmpeg;

import java.io.File;

public final class FramesToVideo extends FrameConverter {

    public File split(File[] str){
      return new File("vid");
    }

    public File[] join(File file){
        throw new RuntimeException("You cannot convert using this context.");
    }
}
