import bean.aop.MathCalculator;
import bean.autowire.BookDao;
import bean.autowire.BookService;
import bean.lifecycle.LifeCycleDemo;
import bean.property.Liu;
import config.*;
import dependency.CommandManager;
import event.EmailService;
import org.junit.Test;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author galileo
 * @date 2019/7/31 15:47
 */
public class MyTest {
    /**
     * 创建IOC容器
     */
    private AnnotationConfigApplicationContext applicationContext;

    @Test
    public void registryTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(MainConfig.class);
        System.out.println("--容器启动完毕--");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void lifeCycleTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("容器创建ok");
        System.out.println("开始获取对象");
        applicationContext.getBean("car");
        //关闭容器
        applicationContext.close();
    }

    @Test
    public void propertyTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(PropertyConfig.class);
        System.out.println("容器创建ok");
        System.out.println("开始获取对象");
        Liu liu = (Liu) applicationContext.getBean("liu");
        System.out.println(liu.toString());

        System.out.println("从运行环境中读取Property");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("liu.nickName");
        System.out.println("liu.nickName:" + property);
        //关闭容器
        applicationContext.close();
    }

    @Test
    public void autowireTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(AutowireConfig.class);

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
        BookDao bookDao = (BookDao) applicationContext.getBean("bookDao2");
        System.out.println(bookDao);

        //关闭容器
        applicationContext.close();
    }

    @Test
    public void aopTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
        mathCalculator.foo("sss");
    }

    @Test
    public void eventTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(EventConfig.class);
        EmailService emailService = (EmailService) applicationContext.getBean("emailService");
        emailService.send("black");
    }

    @Test
    public void dependencyTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(DependencyConfig.class);

        CommandManager commandManager = (CommandManager) applicationContext.getBean("commandManager");
        commandManager.test();
        commandManager.test();
        commandManager.test();
    }

    @Test
    public void LifeTest() {
        applicationContext =
                new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        applicationContext.start();

        applicationContext.close();
    }
}
