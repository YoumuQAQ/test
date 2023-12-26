package com.xmut.managementsystem.tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.IOException;

@SpringBootTest
class GraphicCodeTest {

    @Autowired
    private GraphicCodeService graphicCodeService;
    @Test
    void createCaptcha() throws IOException, FontFormatException {
        System.out.println(graphicCodeService.createCaptcha());
    }
}