package dev.noway.smarthome.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/rtsp")
@RestController
public class ApiRtspController {

    @ResponseBody
    @RequestMapping(value = "/html")
    public StreamingResponseBody getHtmlStream1(@RequestParam(value = "any", required = false) String any) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> responseEntity = restTemplate.exchange( "http://192.168.1.11/home.html", HttpMethod.GET, null, Resource.class );
        InputStream st = responseEntity.getBody().getInputStream();
        return (os) -> {
            readAndWrite(st, os);
        };
    }

    @ResponseBody
    @RequestMapping(value = "/video")
    public StreamingResponseBody getVidoeStream1(HttpServletRequest httpServletRequest) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(httpServletRequest.getLocalPort());
        ResponseEntity<Resource> responseEntity = restTemplate.exchange( "http://192.168.1.11/home.html", HttpMethod.GET, null, Resource.class );
        InputStream st = responseEntity.getBody().getInputStream();
        return (os) -> {
            readAndWrite(st, os);
        };
    }

    private void readAndWrite(final InputStream is, OutputStream os)
            throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }




}
