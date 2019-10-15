package com.carlwu.minipacs.tools;

import org.dcm4che3.io.DicomInputStream;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DcmReader {

    public Map readDcmFile(String filePath) {
        try {
            DicomInputStream dis = new DicomInputStream(new File(filePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
