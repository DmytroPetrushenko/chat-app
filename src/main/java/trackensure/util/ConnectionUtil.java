package trackensure.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
    private static final String DB_PROPERTIES = "db.properties";
    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    public static Connection getConnection() {
        logger.info("getConnection method was called.");
        Connection connection = null;
        try {
            ConnectionUtil connectionUtil = new ConnectionUtil();
            FileInputStream stream = new FileInputStream(connectionUtil.getFileFromResources());
            Properties dbProperties = new Properties();
            dbProperties.load(stream);

            String url = dbProperties.getProperty("url");
            Class.forName(dbProperties.getProperty("driver"));
            connection = DriverManager.getConnection(url, dbProperties);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            logger.error("Can't get a connection!", e);
        }
        return connection;
    }

    private File getFileFromResources() {
        logger.info("getFileFromResources method was called.");

        File file = null;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(DB_PROPERTIES);
        if (resource == null) {
            logger.error("file is not found!");
        } else {
            file = new File(resource.getFile());
        }
        return file;
    }
}
