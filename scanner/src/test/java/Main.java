import java.io.File;
import java.util.List;

import com.common.filter.PngFilter;
import com.common.util.FileUtil;
import com.common.util.StopWatch;
import com.logic.AnnotationServiceNameStandardLogic;
import com.logic.FileNameLogic;
import com.logic.InterfaceFileScannerLogic;

/**
 * 主函数入口
 * 
 * @author JiangGengchao
 * @date 2017年12月22日
 */
public class Main {
	public static void main(String[] args) throws Exception {
		// 获取所有未实现接口
//		getAllUnImplInterface("C:\\Users\\Admin\\workspace\\demo\\code");
//		getAllUnDefineServiceNameService("C:\\Users\\Admin\\workspace\\demo\\code");
//		getAllReDefineServiceNameService("C:\\Users\\Admin\\workspace\\demo\\code");
//		File file = new File("/run/media/john/file_shd/work/workspace/xh/irmsng/trunk/code/static/irm-staticweb-business/image/resourcetree/entitytype/met_shelf.16.png");
//		file.renameTo(new File("/run/media/john/file_shd/work/workspace/xh/irmsng/trunk/code/static/irm-staticweb-business/image/resourcetree/entitytype/MET_SHELF.16.png"));
//		upperFileName("/run/media/john/file_shd/work/workspace/xh/irmsng/trunk/code/static/irm-staticweb-business/image/resourcetree/specialtytype/mini");
		List<File> pngFiles = FileUtil.getFilterFiles("/run/media/john/file_shd/work/workspace/xh/irmsng/trunk/code/static/irm-staticweb-business/image/resourcetree/specialtytype/mini",
				new PngFilter());
		System.out.println(pngFiles.size());
	}

	// 获取所有未实现接口
	public static void getAllUnImplInterface(String path) {
		StopWatch watch = new StopWatch();
		watch.start();
		InterfaceFileScannerLogic.getAllUnUsedInterFaceFile(path);
		watch.stop();
		System.out.println("未实现接口扫描共用时:" + watch.getLastTaskTimeMillis() + "s");
	}

	// 获取未定义service name的service
	public static void getAllUnDefineServiceNameService(String path) {
		StopWatch watch = new StopWatch();
		watch.start();
		AnnotationServiceNameStandardLogic.validateAnnotationName(path);
		watch.stop();
		System.out.println("未定义service name扫描共用时:" + watch.getLastTaskTimeMillis() + "s");
	}

	// 获取service name重复的service
	public static void getAllReDefineServiceNameService(String path) {
		StopWatch watch = new StopWatch();
		watch.start();
		AnnotationServiceNameStandardLogic.validateRepeatingService(path);
		watch.stop();
		System.out.println("service name重复扫描共用时:" + watch.getLastTaskTimeMillis() + "s");
	}

	// 文件名大写
	public static void upperFileName(String path) {
		StopWatch watch = new StopWatch();
		watch.start();
		FileNameLogic.upperCaseAllPngFileName(path);
		watch.stop();
		System.out.println("service name重复扫描共用时:" + watch.getLastTaskTimeMillis() + "s");
	}
}
