package com.example.controller;

import com.example.service.QRCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

/**
 * Created by Marius on 4/11/2017.
 */
@Controller
public class MainController {

    private QRCreator qrCreator;
    private String inputText;

    public MainController(QRCreator qrCreator) {
        this.qrCreator = qrCreator;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String setHome(@RequestParam(name = "qrText", defaultValue = " ") String qrText) {

        inputText = qrText;
        return "home";
    }

    @GetMapping("/qrCode")
    @ResponseBody
    public ResponseEntity<byte[]> getQRCode() {

        ByteArrayOutputStream outputStream = qrCreator.createQR(inputText);

        return ResponseEntity.ok().body(outputStream.toByteArray());
    }
}
