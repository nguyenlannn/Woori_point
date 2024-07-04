package com.example.woori_base.until;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class HeaderGenerator {

    public static String generateGLB_ID(){
        String glbID = "WGSS";
        String date = HeaderGenerator.convertDateTIme2String(new Date());

//        String enviroiment = randomInt==0?"L":randomInt==1?"D":randomInt==2?"T":"P";
        String chanel = "MCE";
        String unknown = "API010100";

        //index tăng dần, quay vòng 99-00
        Random r = new Random(); int randomInt = r.nextInt(3);
        String rd = (r.nextInt(89)+10) +"";
        return glbID+date+ HeaderGenerator.generateENV_DSCD()+chanel+unknown+rd;
    }

    public static String generatePRG_SRNO(){
        return "000";//start 000, increment 1 if
    }

    public static String generateFST_TMS_CHNL_DSCD(){
        return "API";
    }


    public static String generateTMS_CHNL_DSCD(){
        return "MCE";
    }

    public static String generateTMS_NOD_NO(){
        return "01";
    }

    public static String generateENV_DSCD(){
        return StringUtils.rightPad("D",1," ");
    }

    public static String generateIP_AD(){
        return StringUtils.rightPad("",15," ");
    }
    public static String generatePC_MAC_AD(){
        return StringUtils.rightPad("",12," ") ;//TODO: how to get mac address?
    }

    public static String generateTRN_TRM_NO(){
        return StringUtils.rightPad("",8," ");
    }

    public static String generateSCRN_NO(){
        return StringUtils.rightPad("",7," ");//TODO: how to get ?
    }
    public static String generatePBOK_PRTR_USE_YN(){
        return StringUtils.rightPad("",1," ");//TODO: how to get ?
    }
    public static String generateTRM_MNG_BR_CD(){
        return StringUtils.rightPad("",5," ");//TODO: how to get ?
    }
    public static String generateSSN_KEY_NO(){
        return StringUtils.rightPad("",30," ");//TODO: how to get ?
    }
    public static String generateFOUT_IST_CD(){
        return StringUtils.rightPad("",8," ");//TODO: how to get ?
    }
    public static String generateFOUT_APL_CD(){
        return StringUtils.rightPad("",16," ");//TODO: how to get ?
    }
    public static String generateMSG_CHSE_NO(){
        return StringUtils.rightPad("",32," ");//TODO: how to get ?
    }
    public static String generateFOUT_RSP_TRN_CD(){
        return StringUtils.rightPad("",7," ");//TODO: how to get ?-> cần hỏi
    }
    public static String generateMSG_REQ_TS(){
        return StringUtils.rightPad(HeaderGenerator.convertDateTIme2String(new Date()),17," ");//TODO: copy to   response
    }
    public static String generateMSG_RSP_TS(){
        return StringUtils.rightPad("",17," ");//TODO: if response message
    }
    public static String generateMSG_VER_NO(){
        return StringUtils.rightPad("0087",4," ");
    }
    public static String generateTRN_CD(){
        return StringUtils.rightPad("275001V",7," ");
    }
    public static String generateMSG_DSCD(){
        Random r = new Random(); int randomInt = r.nextInt(3);
        return StringUtils.rightPad(randomInt==0?"Q":randomInt==1?"R":randomInt==2?"P":"A",1," ");
    }
    public static String generateMSG_TP_DSCD(){
        //TODO:
        return StringUtils.rightPad("1",1," ");
    }

    public static String generateTRTP_DSCD(){
        return StringUtils.rightPad("00",2," ");////TODO: ask mr Uk
    }

    public static String generatePBNPB_YN(){
        return StringUtils.rightPad("",1," ");
    }
    public static String generateICCD_RDER_USE_YN(){
        return StringUtils.rightPad("N",1," ");
    }
    public static String generateBDY_TRMOD_YN(){
        return StringUtils.rightPad("N",1," ");
    }
    public static String generateCAN_CRC_DSCD(){
        return StringUtils.rightPad("0",1," ");
    }

    public static String generatePRRST_DSCD(){
        return StringUtils.rightPad("0",1," ");
    }

    public static String generateBK_CD(){
        return StringUtils.rightPad("W5970",5," ");//TODO: ask mr Uk
    }
    public static String generateBR_CD(){
        return StringUtils.rightPad("001",5," ");//TODO: ask mr Uk
    }
    public static String generateOPR_USER_NO(){
        return StringUtils.rightPad("SMCIG022",9," ");//TODO: ask mr Uk
    }

    public static String generateBPM_MNG_YN(){
        return StringUtils.rightPad("",1," ");
    }
    public static String generatePRC_ID(){
        return StringUtils.rightPad("",12," ");
    }
    public static String generateTSK_ID(){
        return StringUtils.rightPad("",12," ");
    }
    public static String generateAPV_STS_DSCD(){
        return StringUtils.rightPad("0",1," ");
    }
    public static String generateAPV_DSCD(){
        return StringUtils.rightPad("0",1," ");
    }
    public static String generateAPV_LVL_NO(){
        return StringUtils.rightPad("",6," ");
    }
    public static String generateSQ1_APV_USER_NO(){
        return StringUtils.rightPad("",9," ");
    }
    public static String generateSQ1_APVPE_REJ_RSN_TXT(){
        return StringUtils.rightPad("",50," ");
    }
    public static String generateSQ2_APV_USER_NO(){
        return StringUtils.rightPad("",9," ");
    }
    public static String generateSQ2_APVPE_REJ_RSN_TXT(){
        return StringUtils.rightPad("",50," ");
    }
    public static String generateMGRAPV_MSG_CN(){
        return StringUtils.rightPad("00",2," ");
    }
    public static String generateFILLER(){
        return StringUtils.rightPad("",30," ");
    }
    public static String generateOMSG_AR_USE_YN(){
        return StringUtils.rightPad("0",1," ");
    }
//    public static String generateOther(){
//        return StringUtils.rightPad("0",1220," ");
//    }
    public static String convertDateTIme2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date);
    }
    public static void main(String[] args) {
        System.out.println(HeaderGenerator.generateGLB_ID());
        String GLB_ID = "";
        String body = "<root dataType=\"IN\"><275020VRequest  seq=\"1\" device=\"TM\"><params><msgTrno>BRAND1</msgTrno><msgDscd>EW008</msgDscd><apCusNo>MGV01</apCusNo><tmsDt>20230803</tmsDt><tmsTm>053104</tmsTm><trnSrno>20230625104623487295</trnSrno><prrstDscd>401</prrstDscd><actNo>100300005068</actNo></params></275020VRequest></root>";
        StringBuffer stringBufferFull = generateMessage(body);
        System.out.println(stringBufferFull.toString());

        String base64String = "a77278f3de168c7baefcc2ba3bb45bccedaf988a4f2c424c55d7e1eb8db3d903";

        // Kiểm tra và giải mã chuỗi base64
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            String decodedString = new String(decodedBytes);

            System.out.println("Chuỗi đã giải mã: " + decodedString);
        } catch (IllegalArgumentException e) {
            System.out.println("Chuỗi không phải là base64 hợp lệ.");
        }
    }

    public static StringBuffer generateMessage(String body) {
        StringBuffer stringBufferContent = new StringBuffer();

        stringBufferContent.append(HeaderGenerator.generateGLB_ID());
        stringBufferContent.append(HeaderGenerator.generatePRG_SRNO());
        stringBufferContent.append(HeaderGenerator.generateFST_TMS_CHNL_DSCD());
        stringBufferContent.append(HeaderGenerator.generateTMS_CHNL_DSCD());
        stringBufferContent.append(HeaderGenerator.generateTMS_NOD_NO());
        stringBufferContent.append(HeaderGenerator.generateENV_DSCD());
        stringBufferContent.append(HeaderGenerator.generateIP_AD());
        stringBufferContent.append(HeaderGenerator.generatePC_MAC_AD());
        stringBufferContent.append(HeaderGenerator.generateTRN_TRM_NO());
        stringBufferContent.append(HeaderGenerator.generateSCRN_NO());
        stringBufferContent.append(HeaderGenerator.generatePBOK_PRTR_USE_YN());
        stringBufferContent.append(HeaderGenerator.generateTRM_MNG_BR_CD());
        stringBufferContent.append(HeaderGenerator.generateSSN_KEY_NO());
        stringBufferContent.append(HeaderGenerator.generateFOUT_IST_CD());
        stringBufferContent.append(HeaderGenerator.generateFOUT_APL_CD());
        stringBufferContent.append(HeaderGenerator.generateMSG_CHSE_NO());
        stringBufferContent.append(HeaderGenerator.generateFOUT_RSP_TRN_CD());
        stringBufferContent.append(HeaderGenerator.generateMSG_REQ_TS());
        stringBufferContent.append(HeaderGenerator.generateMSG_RSP_TS());
        stringBufferContent.append(HeaderGenerator.generateMSG_VER_NO());
        stringBufferContent.append(HeaderGenerator.generateTRN_CD());
        stringBufferContent.append(HeaderGenerator.generateMSG_DSCD());
        stringBufferContent.append(HeaderGenerator.generateMSG_TP_DSCD());
        stringBufferContent.append(HeaderGenerator.generateTRTP_DSCD());
        stringBufferContent.append(HeaderGenerator.generatePBNPB_YN());
        stringBufferContent.append(HeaderGenerator.generateICCD_RDER_USE_YN());
        stringBufferContent.append(HeaderGenerator.generateBDY_TRMOD_YN());
        stringBufferContent.append(HeaderGenerator.generateCAN_CRC_DSCD());
        stringBufferContent.append(HeaderGenerator.generatePRRST_DSCD());
        stringBufferContent.append(HeaderGenerator.generateBK_CD());
        stringBufferContent.append(HeaderGenerator.generateBR_CD());
        stringBufferContent.append(HeaderGenerator.generateOPR_USER_NO());
        stringBufferContent.append(HeaderGenerator.generateBPM_MNG_YN());
        stringBufferContent.append(HeaderGenerator.generatePRC_ID());
        stringBufferContent.append(HeaderGenerator.generateTSK_ID());
        stringBufferContent.append(HeaderGenerator.generateAPV_STS_DSCD());
        stringBufferContent.append(HeaderGenerator.generateAPV_DSCD());
        stringBufferContent.append(HeaderGenerator.generateAPV_LVL_NO());
        stringBufferContent.append(HeaderGenerator.generateSQ1_APV_USER_NO());
        stringBufferContent.append(HeaderGenerator.generateSQ1_APVPE_REJ_RSN_TXT());
        stringBufferContent.append(HeaderGenerator.generateSQ2_APV_USER_NO());
        stringBufferContent.append(HeaderGenerator.generateSQ2_APVPE_REJ_RSN_TXT());
        stringBufferContent.append(HeaderGenerator.generateMGRAPV_MSG_CN());
        stringBufferContent.append(HeaderGenerator.generateFILLER());
        stringBufferContent.append(HeaderGenerator.generateOMSG_AR_USE_YN());
//        stringBufferContent.append(HeaderGenerator.generateOther());

        int headerlength = stringBufferContent.length();
        String STD_HEAD_LN = StringUtils.leftPad(headerlength+"",8,"0");


        StringBuffer stringBufferFullHeader = new StringBuffer();
        stringBufferFullHeader.append(STD_HEAD_LN).append(stringBufferContent).append(body);

        StringBuffer stringBufferFull = new StringBuffer();
        int meslength = stringBufferFullHeader.length();
        String FUL_MSG_LN = StringUtils.leftPad(meslength+"",8,"0");
        stringBufferFull.append(FUL_MSG_LN).append(stringBufferFullHeader);
        return stringBufferFull;
    }
}
