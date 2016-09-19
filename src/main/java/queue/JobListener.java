package queue;

/**
 * 任务接听回调接口定义
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午2:07:11
 */
public interface JobListener {

	/**
	 * 暂定接口
	 */
	public void onStart();

	/**
	 * 当收到消息的时候
	 * 
	 * @param message
	 */
	public void onMessage(String message);

	/**
	 * 暂定接口
	 */
	public void onDestory();

}
