package com.example.ImgAnalyzer.Base64.Controller.ConversionUtils;

import java.io.File;


public sealed abstract class Base64Converter permits Base64Encoder, Base64Decoder {

//    public abstract  T convert(U u);

    public abstract void encode(String ...str) throws Exception;

    public abstract String decode(File ...file) throws Exception;
}
