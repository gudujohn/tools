

import com.logic.InterfaceFileScannerLogic;

/**
 * 主函数入口
 * 
 * @author JiangGengchao
 * @date 2017年12月22日
 */
public class Main {
	public static void main(String[] args) throws Exception {
		long begin = System.currentTimeMillis();
		long end;
		// 获取所有未实现接口
		InterfaceFileScannerLogic.getAllUnUsedInterFaceFile("C:\\Users\\Admin\\workspace\\demo\\code");
		end = System.currentTimeMillis();
		System.out.println("未实现接口扫描共用时:" + (end - begin) / 1000 + "s");
		// 获取未定义service name的service
		// AnnotationServiceNameStandardLogic.validateAnnotationName("C:\\Users\\Admin\\workspace\\demo\\code");
		// end = System.currentTimeMillis();
		// System.out.println("未定义service name扫描共用时:" + (end - begin) / 1000 + "s");
		// 获取service name重复的service
		// AnnotationServiceNameStandardLogic.validateRepeatingService("C:\\Users\\Admin\\workspace\\demo\\code");
		// end = System.currentTimeMillis();
		// System.out.println("service name重复扫描共用时:" + (end - begin) / 1000 + "s");
	}
}
