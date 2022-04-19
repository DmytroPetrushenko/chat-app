package trackensure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_PROPERTIES = "db.properties";

    public static Connection getConnection() {
        try {
            ConnectionUtil connectionUtil = new ConnectionUtil();
            FileInputStream stream = new FileInputStream(connectionUtil.getFileFromResources());
            Properties dbProperties = new Properties();
            dbProperties.load(stream);

            String url = dbProperties.getProperty("url");
            Class.forName(dbProperties.getProperty("driver"));
            return DriverManager.getConnection(url, dbProperties);
        } catch (ClassNotFoundException | SQLException | IOException  e) {
            throw new RuntimeException("Can't get a connection!", e);
        }
    }

    private File getFileFromResources() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(DB_PROPERTIES);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
