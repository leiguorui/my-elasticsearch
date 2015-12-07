package cn.injava.es.spring.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * spring容器的监听器，当容器启动，会执行onApplicationEvent()
 *
 * Application listeners run synchronously in Spring.
 *
 * If you want to make sure you're code is executed only once, just keep some state in your component.
 *
 * Created by Green Lei on 2015/8/7 11:36.
 */
@Component
public class StartupHousekeeper {

    @Autowired
    private SaveUserActionTask task;

    @PostConstruct
    public void onApplicationEvent() {
        new Thread(){
            public void run(){
                while (true){
                    task.run();
                }
            }
        }.start();

    }
}