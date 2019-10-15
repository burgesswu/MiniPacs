package com.carlwu.minipacs.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件管理 单例
 *
 * @author Bob
 */
public class ConfigManager {
    private volatile static ConfigManager confManager;
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    public Document document;

    /**
     * 私有的构造
     */
    private ConfigManager() {
        reloadDom();
    }

    /**
     * 读取xml，加载到dom
     */
    public void reloadDom() {
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new File(ConstantsTools.PATH_CONFIG));
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error("Read config xml error:" + e.toString());
        }
    }

    /**
     * 获取实例，线程安全
     *
     * @return
     */
    public static ConfigManager getConfigManager() {
        if (confManager == null) {
            synchronized (ConfigManager.class) {
                if (confManager == null) {
                    confManager = new ConfigManager();
                }
            }
        }
        return confManager;
    }

    /**
     * 把document对象写入新的文件
     *
     * @throws Exception
     */
    public void writeToXml() throws Exception {
        // 排版缩进的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码
        format.setEncoding("UTF-8");
        // 创建XMLWriter对象,指定了写出文件及编码格式
        XMLWriter writer = null;
        writer = new XMLWriter(
                new OutputStreamWriter(new FileOutputStream(new File(ConstantsTools.PATH_CONFIG)), StandardCharsets.UTF_8), format);

        // 写入
        writer.write(document);
        writer.flush();
        writer.close();

    }

    public String getLastSyncTime() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LAST_SYNC_TIME).getText();
    }

    public void setLastSyncTime(String lastSyncTime) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LAST_SYNC_TIME).setText(lastSyncTime);
        writeToXml();
    }

    public String getLastKeepTime() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LAST_KEEP_TIME).getText();
    }

    public void setLastKeepTime(String lastKeepTime) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LAST_KEEP_TIME).setText(lastKeepTime);
        writeToXml();
    }

    public String getSuccessTime() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_SUCCESS_TIME).getText();
    }

    public void setSuccessTime(String successTime) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_SUCCESS_TIME).setText(successTime);
        writeToXml();
    }

    public String getFailTime() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_FAIL_TIME).getText();
    }

    public void setFailTime(String failTime) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_FAIL_TIME).setText(failTime);
        writeToXml();
    }

    public String getTypeFrom() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_TYPE_FROM).getText();
    }

    public void setTypeFrom(String typeFrom) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_TYPE_FROM).setText(typeFrom);
        writeToXml();
    }

    public String getHostFrom() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_HOST_FROM).getText();
    }

    public void setHostFrom(String hostFrom) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_HOST_FROM).setText(hostFrom);
        writeToXml();
    }

    public String getNameFrom() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_NAME_FROM).getText();
    }

    public void setNameFrom(String nameFrom) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_NAME_FROM).setText(nameFrom);
        writeToXml();
    }

    public String getUserFrom() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_USER_FROM).getText();
    }

    public void setUserFrom(String userFrom) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_USER_FROM).setText(userFrom);
        writeToXml();
    }

    public String getPasswordFrom() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_PASSWORD_FROM).getText();
    }

    public void setPasswordFrom(String passwordFrom) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_PASSWORD_FROM).setText(passwordFrom);
        writeToXml();
    }







    public String getAutoBak() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_AUTO_BAK).getText();
    }

    public void setAutoBak(String autoBak) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_AUTO_BAK).setText(autoBak);
        writeToXml();
    }

    public String getDebugMode() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_DEBUG_MODE).getText();
    }

    public void setDebugMode(String debugMode) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_DEBUG_MODE).setText(debugMode);
        writeToXml();
    }

    public String getStrictMode() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_STRICT_MODE).getText();
    }

    public void setStrictMode(String strictMode) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_STRICT_MODE).setText(strictMode);
        writeToXml();
    }

    public String getMysqlPath() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_MYSQL_PATH).getText();
    }

    public void setMysqlPath(String mysqlPath) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_MYSQL_PATH).setText(mysqlPath);
        writeToXml();
    }

    public String getProductName() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_PRODUCT_NAME).getText();
    }

    public void setProductName(String productName) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_PRODUCT_NAME).setText(productName);
        writeToXml();
    }

    public String getPositionCode() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_POSITION_CODE).getText();
    }

    public void setPositionCode(String positionCode) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_POSITION_CODE).setText(positionCode);
        writeToXml();
    }


    public String getUserName() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_NAME).getText();
    }

    public void setUserName(String userName) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_NAME).setText(userName);
        writeToXml();
    }

    public String getUserPass() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_PASS).getText();
    }

    public void setUserPass(String userPass) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_PASS).setText(userPass);
        writeToXml();
    }


    public Object getBaseUrl() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_BASE_URL).getText();
    }

    public void setBaseUrl(String baseUrl) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_BASE_URL).setText(baseUrl);
        writeToXml();
    }


    public String getRemotePacsPort() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_PORT).getText();
    }

    public void setRemotePacsPort(String remotePacsPort) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_PORT).setText(remotePacsPort);
        writeToXml();
    }

    public String getRemotePacsUrl() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_URL).getText();
    }

    public void setRemotePacsUrl(String remotePacsUrl) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_URL).setText(remotePacsUrl);
        writeToXml();
    }

    public String getRemotePacsAeTitle() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_AETITLE).getText();
    }

    public void setRemotePacsAeTitle(String remotePacsAeTitle) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_REMOTE_PACS_AETITLE).setText(remotePacsAeTitle);
        writeToXml();
    }

    public String getLocalPacsAeTitle() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_PACS_AETITLE).getText();
    }

    public void setLocalPacsAeTitle(String localPacsAeTitle) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_PACS_AETITLE).setText(localPacsAeTitle);
        writeToXml();
    }

    public String getLocalDicomPath() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_DICOM_PATH).getText();
    }

    public void setLocalDicomPath(String localDicomPath) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_DICOM_PATH).setText(localDicomPath);
        writeToXml();
    }

    public String getLocalPacsPort() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_PACS_PORT).getText();
    }

    public void setLocalPacsPort(String localPacsPort) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOCAL_PACS_PORT).setText(localPacsPort);
        writeToXml();
    }

    public String getToken() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_TOKEN).getText();
    }

    public void setToken(String token) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_LOGIN_TOKEN).setText(token);
        writeToXml();
    }

    public String getUploadMode() {
        return this.document.selectSingleNode(ConstantsTools.XPATH_UPLOAD_MODE).getText();

    }

    public void setUploadMode(String mode) throws Exception {
        this.document.selectSingleNode(ConstantsTools.XPATH_UPLOAD_MODE).setText(mode);
        writeToXml();
    }

}
