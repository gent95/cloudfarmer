package com.jctl.cloud.api.message;

import com.jctl.cloud.manager.message.service.WaringMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStreamWriter;

/**
 * Created by gent on 2017/3/24.
 */
@Controller
@RequestMapping("messageController")
public class AmessageController {

    @Autowired
    private WaringMessageService waringMessageService;

    public void pushMessage(HttpServletRequest request){
        request.getSession();
    }
}
