package com.carlwu.minipacs;

import com.carlwu.minipacs.tools.ConstantsTools;
import org.dcm4che3.tool.storescp.StoreSCP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PACSServer  implements Runnable{

    public  void startServer(){
        String root = System.getProperty("user.dir");
        String url = root+("/config/sop-classes.properties");
        String directory = ConstantsTools.CONFIGER.getLocalDicomPath();
        String aeTitle=ConstantsTools.CONFIGER.getLocalPacsAeTitle();
        String port=ConstantsTools.CONFIGER.getLocalPacsPort();
        StoreSCP.main(new String[]{"storescp", "-b", aeTitle+":"+port, "--sop-classes", url, "--directory", directory, "--filepath", "{00100020}_{0020000D}_{00100010}_{00100040}_{00080061}/{0020000E}/{00080018}.dcm"});
//        StoreSCP.main(new String[]{"storescp", "-b", aeTitle+":"+port, "--sop-classes", url, "--directory", directory});

    }


//    public static void main(String[] args) throws IOException {
//        //storescp -b STORESCP:11112
//        Properties prop = new Properties();
//        String url = PACSServer.class.getClassLoader().getResource("sop-classes.properties").getPath();
//        System.out.println(url);
//        InputStream in = PACSServer.class.getResourceAsStream("/sop-classes.properties");
//        prop.load(in);
//        String directory = "/home/burgess/pacs/dicom";
//        System.out.println(prop.getProperty("DeformableSpatialRegistrationStorage"));
//        StoreSCP.main(new String[]{"storescp", "-b", "DCM4CHEE:11112", "--sop-classes", url, "--directory", directory, "--filepath", "{00100020}/{0020000D}/{0020000E}/{00080018}.dcm"});
//
//    }

    @Override
    public void run() {
        startServer();
    }
}
