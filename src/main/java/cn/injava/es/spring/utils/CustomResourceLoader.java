package cn.injava.es.spring.utils;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * spring项目 资源文件的读取
 *
 * 用法: 在spring xml中添加 <bean id="customResourceLoader" class="cn.injava.es.spring.utils.CustomResourceLoader"></bean>
 *
 * Created by Green Lei on 2015/12/4 16:52.
 */
public class CustomResourceLoader implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    /**
     * 设置spring的资源加载器
     * @param resourceLoader
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 根据路径或者文件地址获取文件
     *
     * @param location
     * Loading resource file from application folder : "file:data.txt"
     * Loading resource file from classpath : "classpath:classpathdata.txt"
     * Loading resource file from file system : "file:c:/temp/filesystemdata.txt"
     * Loading resource file from any URL : "http://howtodoinjava.com/readme.txt"
     * @return resource data as string
     * @throws IOException
     */
    public String getResourceData(String location) throws IOException{
        Resource resource = resourceLoader.getResource(location);
        InputStream in = resource.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            stringBuilder.append(line);
        }
        reader.close();

        return stringBuilder.toString();
    }
}