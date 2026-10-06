package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
  private   Properties properties = new Properties();
  public ConfigReader(){
      // for java 8 -my version of java
      try (InputStream inputStream = getClass()
              .getClassLoader()
              .getResourceAsStream("config.properties")) {

          if (inputStream == null) {
              throw new IllegalStateException("config.properties not found");
          }

          properties.load(inputStream);

      } catch (IOException e) {
          throw new RuntimeException("Failed to read config.properties", e);
      }
  }
  public String getProperty(String property){
      String value=properties.getProperty(property);
      if(value==null||value.isEmpty()){
          throw new IllegalStateException("Missing property  "+property);
      }
      return value;
  }

}
