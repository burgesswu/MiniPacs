package com.carlwu.minipacs.tools;

import com.carlwu.minipacs.ui.UiConsts;

import java.io.File;

/**
 * 工具层相关的常量
 *
 * @author Bob
 */
public class ConstantsTools {

    // 配置文件
    /**
     * 配置文件 路径
     */
    public final static String PATH_CONFIG = UiConsts.CURRENT_DIR + File.separator + "config" + File.separator
            + "config.xml";

    /**
     * properties路径
     */
    public final static String PATH_PROPERTY = UiConsts.CURRENT_DIR + File.separator + "config" + File.separator
            + "zh-cn.properties";
    /**
     * 配置文件dom实例
     */
    public final static ConfigManager CONFIGER = ConfigManager.getConfigManager();
    /**
     * xpath
     */
    public final static String XPATH_LAST_SYNC_TIME = "//root/status/lastSyncTime";
    public final static String XPATH_LAST_KEEP_TIME = "//root/status/lastKeepTime";
    public final static String XPATH_SUCCESS_TIME = "//root/status/successTime";
    public final static String XPATH_FAIL_TIME = "//root/status/failTime";

    public final static String XPATH_TYPE_FROM = "//root/database/from/type";
    public final static String XPATH_HOST_FROM = "//root/database/from/host";
    public final static String XPATH_NAME_FROM = "//root/database/from/name";
    public final static String XPATH_USER_FROM = "//root/database/from/user";
    public final static String XPATH_PASSWORD_FROM = "//root/database/from/password";


    public final static String XPATH_AUTO_BAK = "//root/setting/autoBak";
    public final static String XPATH_DEBUG_MODE = "//root/setting/debugMode";
    public final static String XPATH_STRICT_MODE = "//root/setting/strictMode";
    public final static String XPATH_MYSQL_PATH = "//root/setting/mysqlPath";
    public final static String XPATH_PRODUCT_NAME = "//root/setting/productname";

    public final static String XPATH_POSITION_CODE = "//root/increase/POSITION_CODE";

    /**
     * 日志文件 路径
     */
    public final static String PATH_LOG = UiConsts.CURRENT_DIR + File.separator + "log" + File.separator + "log.log";
    public static final String XPATH_LOGIN_NAME = "//root/login/USER_NAME";
    public static final String XPATH_LOGIN_PASS = "//root/login/USER_PASS";

    public static final String XPATH_BASE_URL = "//root/base/URL";
    public static final String XPATH_LOCAL_PACS_AETITLE = "//root/base/LOCAL_PACS_AETITLE";
    public static final String XPATH_LOCAL_PACS_PORT = "//root/base/LOCAL_PACS_PORT";
    public static final String XPATH_REMOTE_PACS_PORT = "//root/base/REMOTE_PACS_PORT";
    public static final String XPATH_REMOTE_PACS_URL = "//root/base/REMOTE_PACS_URL";
    public static final String XPATH_REMOTE_PACS_AETITLE = "//root/base/REMOTE_PACS_AETITLE";
    public static final String XPATH_LOCAL_DICOM_PATH = "//root/base/LOCAL_DICOM_PATH";
    public static final String XPATH_LOGIN_TOKEN = "//root/login/TOKEN";
    public static final String XPATH_UPLOAD_MODE = "//root/base/UPLOAD_MODE";
    public static final String XPATH_SQLITE_DB = "//root/base/SQLITE_DB";
}
