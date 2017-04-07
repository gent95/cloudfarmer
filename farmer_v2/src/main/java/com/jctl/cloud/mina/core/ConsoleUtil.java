package com.jctl.cloud.mina.core;

public class ConsoleUtil {

    //7种硬件控制命令类型，relay->node
    public enum CtrlCmdName {
        //请求传感器信息->0x01  应答->0x81
        CMD_REQUEST_SENSOR_INFO,
        //控制开关->0x02  应答->0x81
        CMD_CONTROL_SWITCH_ON_OFF,
        //切换频点->0x06  应答->0x81
        CMD_CONTROL_SWITCH_POINTS,
        //切换功率->0x07  应答->0x81
        CMD_CONTROL_SWITCH_POWER,
        //控制传感器电源->0x09  应答->0x81
        CMD_CONTROL_POWER_SUPPLY,
        //读取传感器电源状态->0x0A  应答->0x81
        CMD_REQUEST_POWER_SUPPLY_INFO,
        //查询版本号->0x0F  应答->0x8F
        CMD_REQUEST_VERSION_INFO
    }

    //从传感器2个原始数据，获取译码数
    public static Double getFloatFrom2byte(String data) {
//    	System.out.println(data);
        if (data == "FFFF")
            return null;
        int vin = Integer.parseInt(data, 16);
        int dataZS;
        int dataXS;
        double value;
        dataZS = vin >> 8;
        if (dataZS > 0x7f) {
            //负数
            dataZS = dataZS - 0x100;
        }
        dataXS = vin & 0xff;
        value = ((double) dataZS) + ((double) dataXS) / 100;
        if (dataZS < 0) {
            value = ((double) dataZS) - ((double) dataXS) / 100;
        }
        return value;
    }


    //从传感器2个原始数据，获取译码数
    public static Double getCo2From2byte(String data) {
        System.out.println(data);
        if (data == "FFFF")
            return null;
        Double value = Integer.parseInt(data, 16) * 1.00;
        return value;
    }


    public static String byteToHEXString(int algorism) {
        String result;
        //方法返回为无符号整数基数为16的整数参数的字符串表示形式
        result = Integer.toHexString((algorism & 0x000000FF) | 0xFFFFFF00).substring(6);

        return result;
    }

    public static String shortToHEXString(int algorism) {
        String result;
        //方法返回为无符号整数基数为16的整数参数的字符串表示形式
        result = Integer.toHexString((algorism & 0x0000FFFF) | 0xFFFF0000).substring(4).toUpperCase();
        return result;
    }

    public static String getXorForTxPayload(String txPayload) {
        int xorData = 0;
        for (int i = 0; i < txPayload.length(); i = i + 2) {
            Integer byteInt = Integer.valueOf(txPayload.substring(i, i + 2), 16);
            xorData = xorData ^ byteInt;
        }
        return byteToHEXString(xorData).toUpperCase();
    }

    //向中继发送获取中继信息的命令
    public static String getStringForComTr() {
        return "Gateway:";
    }

    //向节点发送的命令
    public static String getStringForComTx(CtrlCmdName cmd, String clientMac, String serverMac, int deviceType, int ctrlState) {
        int length;
        String length2Str;
        String headStr, lengthStr, optionStr, cmdStr, lenFromStr, macFromStr, lenToStr, macToStr, dataStr, checkStr, TailStr;
        headStr = "AA";
        optionStr = "00";
        lenFromStr = byteToHEXString(serverMac.length() / 2);
        macFromStr = serverMac;
        lenToStr = byteToHEXString(clientMac.length() / 2);
        macToStr = clientMac;
        TailStr = "BB";

        switch (cmd) {
            case CMD_REQUEST_SENSOR_INFO:
                cmdStr = "01";
                dataStr = shortToHEXString(deviceType);
                break;
            case CMD_CONTROL_SWITCH_ON_OFF:
                cmdStr = "02";
                dataStr = byteToHEXString(ctrlState);
                break;
            case CMD_CONTROL_SWITCH_POINTS:
                cmdStr = "06";
                dataStr = byteToHEXString(ctrlState);
                break;
            case CMD_CONTROL_SWITCH_POWER:
                cmdStr = "07";
                dataStr = byteToHEXString(ctrlState);
                break;
            case CMD_CONTROL_POWER_SUPPLY:
                cmdStr = "09";
                dataStr = "";
                break;
            case CMD_REQUEST_POWER_SUPPLY_INFO:
                cmdStr = "0A";
                dataStr = "";
                break;
            case CMD_REQUEST_VERSION_INFO:
                cmdStr = "8F";
                dataStr = "";
                break;
            default:
                cmdStr = "EE";
                dataStr = "";
                break;
        }
        length = 1 + 1 + 1 + 1 + 1 + (serverMac.length() / 2) + 1 + (clientMac.length() / 2) + (dataStr.length() / 2) + 1 + 1;
        lengthStr = byteToHEXString(length);
        length2Str = byteToHEXString(length * 2);
        String dataPayloadStr = lengthStr + optionStr + cmdStr + lenFromStr + macFromStr + lenToStr + macToStr + dataStr;
        checkStr = getXorForTxPayload(dataPayloadStr);
        String strTx = "Client:AT+TM=" + length2Str + headStr + dataPayloadStr + checkStr + TailStr + "\r\n";
        return strTx;
    }

}
