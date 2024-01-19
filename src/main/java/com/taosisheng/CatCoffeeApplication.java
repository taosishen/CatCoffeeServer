package com.taosisheng;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.taosisheng"})
@ServletComponentScan
@EnableTransactionManagement
public class CatCoffeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatCoffeeApplication.class,args);
         System.out.println("                       /$$                          /$$$$$$   /$$$$$$                   \n" +
                            "                      | $$                         /$$__  $$ /$$__  $$                  \n" +
                            "  /$$$$$$$  /$$$$$$  /$$$$$$    /$$$$$$$  /$$$$$$ | $$  \\__/| $$  \\__//$$$$$$   /$$$$$$ \n" +
                            " /$$_____/ |____  $$|_  $$_/   /$$_____/ /$$__  $$| $$$$    | $$$$   /$$__  $$ /$$__  $$\n" +
                            "| $$        /$$$$$$$  | $$    | $$      | $$  \\ $$| $$_/    | $$_/  | $$$$$$$$| $$$$$$$$\n" +
                            "| $$       /$$__  $$  | $$ /$$| $$      | $$  | $$| $$      | $$    | $$_____/| $a$_____/\n" +
                            "|  $$$$$$$|  $$$$$$$  |  $$$$/|  $$$$$$$|  $$$$$$/| $$      | $$    |  $$$$$$$|  $$$$$$$\n" +
                            " \\_______/ \\_______/   \\___/   \\_______/ \\______/ |__/      |__/     \\_______/ \\_______/\n" +
                            "                                                                                        \n" +
                            "                                                                                        \n" +
                            "                                                                                        ");
        log.info("catcoffee系统启动完毕...");

    }
}
