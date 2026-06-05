package ADRAF.com.nk.tool;

/**
 * 保存用户信息工具类，相当于一个全局静态变量
 *
 */
public class UserStateTool {
	private static String username = null;// 用户名
	private static String password = null;// 用户密码

	public static void setUsername(String username) {
		UserStateTool.username = username;
	}

	public static String getUsername() {
		return username;
	}

	public static void setPassword(String password) {
		UserStateTool.password = password;
	}

	public static String getPassword() {
		return password;
	}

}
