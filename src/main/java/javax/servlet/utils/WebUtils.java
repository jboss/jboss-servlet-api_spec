package javax.servlet.utils;

import java.lang.reflect.Method;
import java.util.Enumeration;
/**
 * 原理：通过反射机制来实现对象的生成
 * 使用场景：JavaWeb Servlet开发
 * 技术迭代：Spring、SpringBoot中可以直接在 Controller 直接设置对象入参，则不需要此工具
 * Author：  chenjjia
 * Date：    2021/11/11 13:05
 * WeChat：  China_JoJo_
 * Blog：    https://juejin.cn/user/1856417285289304/posts
 * Github：  https://github.com/chenjjiaa
 */
public class WebUtil {
    /**
     * 作用：将前端传入的参数封装成一个对象然后返回到 Controller
     * @param request：HttpServletRequest，内部包含了请求头带来的所有信息、参数等
     * @param object 要返回的对象类型，通过反射构造此对象的属性
     * @return
     */
    public static Object makeRequestToObject(HttpServletRequest request,
                                             Object object) {
        Enumeration fieldNames = request.getParameterNames();
        while (fieldNames.hasMoreElements()) {
            Class c = object.getClass(); // 获取类
            String fieldName = (String) fieldNames.nextElement(); // 获取属性名
            String setMethodName = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1); // 得到方法名
            try {
                Method setMethod = c.getDeclaredMethod(setMethodName, String.class);
                setMethod.invoke(object,request.getParameter(fieldName)); // 调用方法，实现对象属性赋值
            } catch (Exception e) {
                // 有些对象没有某些参数，就不必理会 不用抛出异常
                
            }
        }
        return object;
    }
}
