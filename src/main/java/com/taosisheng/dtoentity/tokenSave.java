package com.taosisheng.dtoentity;

import lombok.Data;

import java.util.Date;
@Data
public class tokenSave {
    private static String id;
    private static String token;
    private static String username;
    private static Date creatTime;
    private static Date timeout;
}
