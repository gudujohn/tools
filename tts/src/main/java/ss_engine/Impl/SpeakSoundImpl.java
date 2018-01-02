package ss_engine.Impl;

import java.applet.AudioClip;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.media.sound.JavaSoundAudioClip;

import ss_engine.ICharactorTool;
import ss_engine.ISpeakSound;

/**
 * 播放声音的类
 * 
 * @author JiangGengChao
 */
@SuppressWarnings("restriction")
public class SpeakSoundImpl implements ISpeakSound {

	// 需要播放的声音文件列表
	private List<String> fileNames;
	// 声音剪辑池
	private List<AudioClip> pool;
	private int speed = 400;

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * 构造函数
	 * 
	 * @param needSpeak
	 * @param url
	 */
	public SpeakSoundImpl(String needSpeak) {
		ICharactorTool myPinYin = new MyGetPinYin();
		try {
			this.fileNames = myPinYin.getNcnFullSpell(needSpeak);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 获取声音文件路径
	 */
	@Override
	public void setSoundSource() {
		pool = new ArrayList<AudioClip>();
		if (MyGetPinYin.getPath() == null) {
			for (String s : fileNames) {
				try {
					InputStream inputStream = SpeakSoundImpl.class.getClassLoader().getResourceAsStream("TTSSounds/" + s);
					if (inputStream != null) {
						pool.add(new JavaSoundAudioClip(inputStream));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// TODO 加载指定路径的声音文件
		}
	}

	/**
	 * 播放声音文件
	 */
	@Override
	public void playSound() {
		this.setSoundSource();
		for (AudioClip ac : pool) {
			ac.play();
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		playSound();
	}

}
