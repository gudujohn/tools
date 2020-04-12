package com.support.executor;

import java.awt.*;
import java.util.concurrent.Executor;

import com.enhance.common.util.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.constant.PictureConst;
import com.enhance.common.support.ThreadCallBack;
import com.enhance.common.util.Detect;
import com.enhance.swing.dialog.ExceptionDialog;
import com.service.RestService;
import com.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestSupportExecutor {

	@Autowired
	@Qualifier("threadRestExecutor")
	private Executor threadRestExecutor;
	@Autowired
	private RestService restService;

	@Async("mainThreadRestExecutor")
	public <T> void threadExchange(String restUrl, HttpMethod httpMethod, HttpEntity<?> httpEntity, Class<T> responseType, ThreadCallBack... callBacks) {
		threadExchange(restUrl, httpMethod, httpEntity, responseType, false, callBacks);
	}

	@Async("mainThreadRestExecutor")
	public <T> void threadExchange(String restUrl, HttpMethod httpMethod, HttpEntity<?> httpEntity, Class<T> responseType, boolean mustCallBack, ThreadCallBack... callBacks) {
		T response = null;
		try {
			response = restService.exchange(restUrl, httpMethod, httpEntity, responseType);
			doCallBack(response, null, callBacks);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (mustCallBack) {
				doCallBack(response, e, callBacks);
			} else {
				ExceptionDialog.traceException(Frame.getFrames()[0], "异常", "调用接口" + restUrl + "出现错误", e);
			}
		} finally {
			ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
			ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
		}
	}

	@Async("mainThreadRestExecutor")
	public void csfThreadPostTest(String restUrl, HttpEntity<?> httpEntity, int tcount, int count, ThreadCallBack callBack) {
		if (tcount <= 1) {
			for (int i = 0; i < count; i++) {
				String result = restService.exchange(restUrl, HttpMethod.POST, httpEntity, String.class);
				callBack.execute(result);
			}
			ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
			ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
		} else {
			signCount = 0;
			int endCount = tcount * count;
			for (int i = 0; i < tcount; i++) {
				RestSupportExecutor.RestTask task = new RestSupportExecutor.RestTask(restUrl, HttpMethod.POST, httpEntity, count, endCount, callBack);
				threadRestExecutor.execute(task);
			}
		}
	}

	private <T> void doCallBack(T response, Exception e, ThreadCallBack... callBacks) {
		if (Detect.notEmpty(callBacks)) {
			for (ThreadCallBack callBack : callBacks) {
				if (Detect.notNull(e)) {
					callBack.execute(e);
				} else {
					callBack.execute(response);
				}
			}
		}
	}

	private static int signCount;

	private class RestTask implements Runnable {
		private int count = 10;
		private int endCount;
		private String restUrl;
		private HttpMethod httpMethod;
		private HttpEntity<?> httpEntity;
		private ThreadCallBack callBack;

		public RestTask(String restUrl, HttpMethod httpMethod, HttpEntity<?> httpEntity, int count, int endCount, ThreadCallBack callBack) {
			this.count = count;
			this.restUrl = restUrl;
			this.httpMethod = httpMethod;
			this.httpEntity = httpEntity;
			this.callBack = callBack;
			this.endCount = endCount;
		}

		@Override
		public void run() {
			for (int i = 0; i < count; i++) {
				signCount++;
				log.info(i + "\t");
				String result = restService.exchange(restUrl, httpMethod, httpEntity, String.class);
				callBack.execute(result);
			}
			if (signCount == endCount) {
				ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
				ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
			}
		}
	}

}
