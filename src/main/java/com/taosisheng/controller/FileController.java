package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.taosisheng.dtoentity.R;
import com.taosisheng.dtoentity.ResponseFile;
import com.taosisheng.dtoentity.User;
import com.taosisheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@ResponseBody
@Slf4j

/**
 * 文件处理类:
 * avtarPath,goodsPath:从配置取出本地路径
 */
public class FileController {
    //服务器本地路径
    @Value("${catcoffee.filepath.avtar}")
    private String avtarPath;
    @Value("${catcoffee.filepath.goods}")
    private String goodsPath;
    @Value("${catcoffee.filepath.user}")
    private String userPath;

    @Autowired
    private UserService userService;

    @PostMapping("/uploadGoods")
    public R<ResponseFile> uploadGoods(@RequestBody MultipartFile file) {

        File dir = new File(avtarPath);

        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));

        //临时文件

        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(goodsPath + filename));
        } catch (IOException e) {
            return R.error("上传图像失败", 100);
        }
        log.info(file.toString());
        ResponseFile responseFile = new ResponseFile();
        responseFile.setFileName(filename);
        responseFile.setFilePathName("/file/downloadGoods?t=" + filename);
        return R.success(responseFile);
    }

    @GetMapping("/downloadGoods")
    public void downloadGoods(String t, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(goodsPath + t));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/changeAvatar")
    public R<String> changeAvatar(@RequestBody MultipartFile file, String phone) {

        File dir = new File(avtarPath);

        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));

        //临时文件

        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(avtarPath + filename));
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("phone", phone).set("avatar", filename);
            userService.update(wrapper);
        } catch (IOException e) {
            return R.error("上传头像失败", 100);
        }
        log.info(file.toString());
        return R.success("/file/downloadAvtar?t=" + filename);

    }

    @GetMapping("/downloadAvatar")
    public void downloadAvatar(String t, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(avtarPath + t));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
