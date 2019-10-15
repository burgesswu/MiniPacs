package com.carlwu.minipacs;

import com.carlwu.minipacs.tools.ConstantsTools;
import org.dcm4che3.tool.storescu.StoreSCU;

import java.io.IOException;


public class PACSClient  {
//    private static String url = PACSClient.class.getClassLoader().getResource("rel-sop-classes.properties").getPath();
//    private static String aeTitle = ConstantsTools.CONFIGER.getRemotePacsAeTitle();
//    private static String port = ConstantsTools.CONFIGER.getRemotePacsPort();
//    private static String pacsUrl = ConstantsTools.CONFIGER.getRemotePacsUrl();
//
//    public  void sendStore(String dcmFile) {
//        StoreSCU.main(new String[]{"storescu", "-c", aeTitle + "@"+pacsUrl+":" + port, "--rel-sop-classes", url,  dcmFile});
//
//    }


    public static void main(String[] args) throws IOException {
        try {
            String url = PACSClient.class.getClassLoader().getResource("rel-sop-classes.properties").getPath();
            String directory = "/home/burgess/pacs/dicom/201803502/1.2.840.113619.2.278.3.839569194.406.1566954974.988/";
            StoreSCU.main(new String[]{"storescu", "-c", "DCM4CHEE@127.0.0.1:11112", "--rel-sop-classes", url, directory });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
