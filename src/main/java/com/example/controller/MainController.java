package com.example.controller;

import com.example.service.QRCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;

/**
 * Created by Marius on 4/11/2017.
 */
@Controller
public class MainController {

    private HttpSession httpSession;
    private QRCreator qrCreator;

    public MainController(QRCreator qrCreator, HttpSession httpSession) {
        this.qrCreator = qrCreator;
        this.httpSession = httpSession;

    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String setHome(@RequestParam(name = "qrText", defaultValue = " ") String qrText) {

        httpSession.setAttribute("input", qrText);

        return "home";
    }

    @GetMapping("/qrCode")
    @ResponseBody
    public ResponseEntity<byte[]> getQRCode() {

        String inputText = (String) httpSession.getAttribute("input");

        ByteArrayOutputStream outputStream = qrCreator.createQR(inputText);

        return ResponseEntity.ok().body(outputStream.toByteArray());
    }
}
