//package com.jctl.cloud.common.converter;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONPObject;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// * @author lewKay
// */
//public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter{
//    @Override
//    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        if (obj instanceof JSONPObject) {
//            JSONPObject jsonp = (JSONPObject) obj;
//            OutputStream out = outputMessage.getBody();
//            String text = jsonp.getFunction() + "(" + JSON.toJSONString(jsonp.toJSONString(), getFeatures()) + ")";
//            System.out.println(text);
//            byte[] bytes = text.getBytes(getCharset());
//            out.write(bytes);
//        } else {
//            super.writeInternal(obj, outputMessage);
//        }
//    }
//}
