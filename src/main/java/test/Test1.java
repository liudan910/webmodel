package test;

import com.domain.Message;
import com.service.Consumer;
import com.service.Manager;
import com.service.Producer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liudan19 on 2017/10/31.
 */
public class Test1 {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        registerConsumer(ctx);
        produce(ctx);
    }
    public static void registerConsumer(ApplicationContext ctx ){
        Manager manager = ctx.getBean("manager",Manager.class);
        for(int i = 0; i<10; i++){
            //Consumer consumer = ctx.getBean("consumer",Consumer.class);      //summary1: 使用bean方式获取实例，因默认是单例模式，故实际上为同一对象引用
            Consumer consumer = new Consumer();
            System.out.println(consumer);
            manager.register(consumer);
        }
    }
    public static void produce(ApplicationContext ctx){
        Producer p = ctx.getBean("producer",Producer.class);
        Message message = new Message();
        message.setContent("message1");
        p.sendMessage(message);
    }
}
