package com.example.ImgAnalyzer.FFmpeg;

import java.io.File;

public sealed abstract class FrameConverter permits FramesToVideo, VideoToFrames {

    public abstract File split(File[] str);

    public abstract File[] join(File file);
}
