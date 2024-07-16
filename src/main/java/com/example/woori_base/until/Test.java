package com.example.woori_base.until;

import com.example.woori_base.controller.PartnerController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {
    private static final Logger logger = LogManager.getLogger(PartnerController.class);

    public static void main(String[] args) {
        logger.info("This is an info message");
        logger.error("This is an error message");

        System.out.println("Check if logs are written to the file.");
    }
}
