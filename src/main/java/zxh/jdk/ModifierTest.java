package zxh.jdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射修饰符工具类测试
 */
public class ModifierTest {

    public static void main(String[] args) throws Exception {
        print();

        System.out.println();

        //获取class
        Class c_ = Entity.class;

        System.out.println("类："+ printModifier(c_.getModifiers()));

        //构造方法
        Constructor constructor = c_.getConstructor();
        System.out.println("构造方法："+printModifier(constructor.getModifiers()));

        //方法
        System.out.println("operation方法："+printModifier(c_.getDeclaredMethod("operation").getModifiers()));

        //获取id字段
        Field field = c_.getDeclaredField("id");
        //打印字段修饰的
        System.out.println("id字段："+printModifier(field.getModifiers()));
    }

    public static String printModifier(int mod){
        StringBuilder msg = new StringBuilder();
        if(Modifier.isAbstract(mod)) msg.append("abstract ");

        if(Modifier.isFinal(mod)) msg.append("final ");

        if(Modifier.isInterface(mod)) msg.append("interface ");

        if(Modifier.isNative(mod)) msg.append( "native ");

        if(Modifier.isPrivate(mod)) msg.append( "private ");

        if(Modifier.isProtected(mod)) msg.append( "protected ");

        if (Modifier.isPublic(mod)) msg.append("public ");

        if(Modifier.isStatic(mod)) msg.append("static ");

        if(Modifier.isStrict(mod)) msg.append("strict ");

        if(Modifier.isSynchronized(mod)) msg.append("synchronized ");

        if(Modifier.isTransient(mod)) msg.append("transient ");

        if(Modifier.isVolatile(mod)) msg.append("voloatile ");

        return msg.toString()+" 修饰";

    }

    public static void print(){
        System.out.println("类的修饰符："+Modifier.toString(Modifier.classModifiers()));//public protected private abstract static final strictfp
        System.out.println("接口的修饰符："+Modifier.toString(Modifier.interfaceModifiers()));//public protected private abstract static strictfp
        System.out.println("构造器的修饰符："+Modifier.toString(Modifier.constructorModifiers()));//public protected private
        System.out.println("方法的修饰符："+Modifier.toString(Modifier.methodModifiers()));//public protected private abstract static final synchronized native strictfp
        System.out.println("字段的修饰符："+Modifier.toString(Modifier.fieldModifiers()));//public protected private static final transient volatile
        System.out.println("参数的修饰符："+Modifier.toString(Modifier.parameterModifiers()));//fin
    }
}

final class Entity{
    private volatile int id;

    public Entity(){

    }

    public synchronized void operation(){

    }

}
