package Helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final ThreadLocal<String> IS_RESPONSIVE = new ThreadLocal<>();

    public static String getProperty(String key) {
        String res = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            // get the property value
            res = prop.getProperty(key);
        } catch (IOException ex) {
            Report.verifyValue("getProperty:", "", ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Report.verifyValue("getProperty: input close", "", e.getMessage());
                }
            }
        }
        return res;
    }

    public static String getProperty(String key, String file) {
        String res = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            // get the property value
            res = prop.getProperty(key);
        } catch (IOException ex) {
            Report.verifyValue("getProperty:", "", ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Report.verifyValue("getProperty: input close", "", e.getMessage());
                }
            }
        }
        return res;
    }

    public static void setProperty(String key, String value, String file) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            // load a properties file
            input = new FileInputStream(file);
            prop.load(input);
            input.close();
            // set the property value
            FileOutputStream out = new FileOutputStream(file);
            prop.setProperty(key, value);
            prop.store(out, value);
        } catch (IOException ex) {
            Report.verifyValue("setProperty:", "", ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Report.verifyValue("setProperty: input close", "", e.getMessage());
                }
            }
        }
    }

    public static String getSubjectProperty() {
        return getProperty("subject", "data.properties");
    }



    public static void setSubjectProperty(String value) {
        setProperty("subject", value, "data.properties");
    }



    public static boolean isProd() {
        return getProperty("url").contains("/test/");
    }

    public static boolean isTrials() {
        String[] arrUrl = getProperty("url").split("/");
        if (arrUrl.length < 4) {
            return true;
        } else {
            return getProperty("url").contains("/trials/");
        }
    }

    /**
     * @param bugName like BUG9618
     * @return false if bugName contains in key skip_bugs into config.properties
     */
    public static boolean needSkipBug(String bugName) {
        return !getProperty("skip_bugs").contains(bugName);
    }

    public static boolean isResponsive() {
        boolean bRes = false;
        if (IS_RESPONSIVE.get() == null) {
            if (getProperty("responsive") == null || getProperty("responsive").equals("false")) {
                bRes = false;
            } else if (getProperty("responsive").equals("true")) {
                bRes = true;
            }
            IS_RESPONSIVE.set(String.valueOf(bRes));
        } else {
            bRes = Boolean.parseBoolean(IS_RESPONSIVE.get());
        }
        return bRes;
    }

    public static boolean isMobile() {
        boolean bRes = false;
        //TBD
        return bRes;
    }
}
