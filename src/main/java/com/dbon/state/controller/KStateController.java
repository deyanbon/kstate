package com.dbon.state.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/kstate")
public class KStateController {

    Logger logger = LoggerFactory.getLogger(KStateController.class);

    @Value("${shared.file.path}")
    String spath;

    @Value("${shared.file.name}")
    String sname;

    @PostMapping("/update")
    public void update(@RequestBody String input) {

        File f = new File(spath, sname);

        logger.info("Writing state: [{}]", input);

        try (FileWriter fw = new FileWriter(f, false)) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(input);
            bw.close();
        }
        catch (IOException e) {
            logger.error("Failure:", e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/read")
    public String read() {

        StringBuilder buf = new StringBuilder();

        File f = new File(spath, sname);

        try (FileReader fr = new FileReader(f)) {
            BufferedReader br = new BufferedReader(fr);

            boolean started = false;
            String line = br.readLine();
            while(line != null) {

                if(started) {
                    buf.append("|");
                }

                buf.append(line);
                line = br.readLine();
                started = true;
            }
            br.close();
        }
        catch (IOException e) {
            logger.error("Failure:", e);
            throw new RuntimeException(e);
        }

        String out = buf.toString();
        logger.info("Reading state: [{}]", out);
        return out;
    }
}