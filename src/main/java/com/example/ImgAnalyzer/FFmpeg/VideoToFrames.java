package com.example.ImgAnalyzer.FFmpeg;

import java.io.File;

public final class VideoToFrames extends FrameConverter{

    public File split(File[] str){
        throw new RuntimeException("You cannot convert using this context.");
    }

    public File[] join(File file){
        return new File[]{new File("kl.txt"), new File("pp.txt") };
    }
}
