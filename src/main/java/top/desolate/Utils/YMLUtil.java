package top.desolate.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YMLUtil {
    private String ymlFilePath = "";

    public void setYmlFilePath(String ymlFilePath) {
        this.ymlFilePath = ymlFilePath;
    }

    //解析yml封装
    public String getYmlValue(String ymlKeyPath) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        InputStream input = null;
        try {
            input = new FileInputStream(ymlFilePath);
        } catch (FileNotFoundException e) {
            return null;
        }
        Map<String,Object> map;
        try {
            map = objectMapper.readValue(input, Map.class);
        } catch (IOException e) {
            return null;
        }
        String[] split = ymlKeyPath.split("\\.");
        Map<String, Object> info = new HashMap<>();
        String cron = "";
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                info = (Map<String,Object>) map.get(split[i]);
            } else if (i == split.length - 1) {
                if (info.get(split[i]) instanceof String)
                    cron = (String) info.get(split[i]);
                else
                    cron = Integer.toString((Integer) info.get(split[i]));
            } else {
                info = (Map<String,Object>) info.get(split[i]);
            }
            if (info == null) {
                return null;
            }
        }
        return cron;
    }
}
