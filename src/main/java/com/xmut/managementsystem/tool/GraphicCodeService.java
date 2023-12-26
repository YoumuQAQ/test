package com.xmut.managementsystem.tool;

import java.awt.*;
import java.io.IOException;

public interface GraphicCodeService {
    String createCaptcha() throws IOException, FontFormatException;

    Boolean checkCaptcha(String code, String redisCode);
}
