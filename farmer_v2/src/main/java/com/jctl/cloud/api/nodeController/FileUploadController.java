package com.jctl.cloud.api.nodeController;

import com.jctl.cloud.common.utils.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/13.
 */
@Controller
public class FileUploadController {

    @RequestMapping("test")
    public String toTest(HttpServletRequest request) {
//        UploadUtils uploadUtils = new UploadUtils();
//        String[] xx = uploadUtils.uploadFile(request);
//        System.out.print(xx);
        return "test/test";
    }
    @RequestMapping("test1")
    public void toTest1(HttpServletRequest request) {
        try{

            UploadUtils uploadUtils = new UploadUtils();
            String[] xx = uploadUtils.uploadFile(request);
            System.out.print(xx);
        }catch (Exception e){e.printStackTrace();}


//        return new String[] xxa;
    }



}
