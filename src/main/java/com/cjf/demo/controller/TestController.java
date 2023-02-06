package com.cjf.demo.controller;

import com.cjf.demo.service.CreateRandImageService;
import com.cjf.demo.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private CreateRandImageService strRandomGen;

    @RequestMapping("/stringImage")
    public void stringImage(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        log.info("stringImage is start!");
        response.addHeader("Content-Type", "image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 生成4位随机数
        String verificationCode = strRandomGen.createStrRandomGen(4);
        log.info("生成随机码:{}",verificationCode);

        //存储验证码到内存
        CacheUtil.put(verificationCode,verificationCode,300);

        request.getSession().setAttribute("verificationCode", verificationCode);

        // 调用随机验证码图片生成服务
        BufferedImage img = (BufferedImage) strRandomGen.doService(verificationCode);
        if (null != img) {
            log.info("验证码图片生成OK！");
        } else {
            log.error("验证码图片生成失败！");
        }
        ImageIO.write(img, "JPEG", response.getOutputStream());

        log.info("stringImage is end!");
    }

    @GetMapping("/exportUser")
    public void exportUser(HttpServletRequest request, HttpServletResponse response){
        log.info("stringImage is start!");

        log.info("stringImage is end!");
    }

}
