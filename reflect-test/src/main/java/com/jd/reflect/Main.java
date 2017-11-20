package com.jd.reflect;

import com.jd.reflect.entity.Apple;
import com.jd.reflect.entity.Orange;
import com.jd.reflect.iface.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Main
 * Description: Main
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/6/15
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    interface IFACE {
        void doSomething();
    }

    class InnerMain implements IFACE {
         InnerMain(){

         }

        @Override
        public void doSomething() {
            logger.info(this.getClass().getName() + "doSomething");
        }
    }


    public static void main(String[] args) {
        try{

            Main main = new Main();
            main.printFruit(new Apple());
            main.printFruit(new Orange());

        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    private void printFruit(Fruit fruit){
        fruit.eat();
        logger.info("fruit:" + fruit.getClass().getName());
        printInnerMain(new InnerMain());
    }

    private void printInnerMain(IFACE iface){
        iface.doSomething();
        logger.info("iface:" + iface.getClass().getName());
    }
}
