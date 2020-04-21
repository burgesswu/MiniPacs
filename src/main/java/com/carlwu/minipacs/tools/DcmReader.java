package com.carlwu.minipacs.tools;

import com.alibaba.fastjson.JSON;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;

import java.io.File;
import java.io.IOException;

public class DcmReader {
    public static Attributes readDcmFile(String filePath) {
        try {
            DicomInputStream dis = new DicomInputStream(new File(filePath));
            Attributes dataSet = dis.readDataset(-1, Tag.PixelData);
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Attributes readDcmFile(String filePath,String newFilePath) {
        try {
            DicomInputStream dis = new DicomInputStream(new File(filePath));
            Attributes dataSet = dis.readDataset(-1, Tag.PixelData);
            System.out.println(new String(dataSet.getBytes(Tag.PatientName),"GB18030"));

            System.out.println(dataSet.getString(Tag.PatientID));


            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static void main(String[] args) {
        String   newPath="C:\\Users\\Burgess\\Desktop\\nn.dcm";
        DcmReader.readDcmFile("C:\\Users\\Burgess\\Desktop\\刘春梅\\AT0867_77868206100.155840759.dcm",newPath);
//        DcmReader.readDcmFile("/home/burgess/pacs/dicom2/3914AC:\Users\Burgess\Desktop\刘春梅55.dcm");
    }

}
