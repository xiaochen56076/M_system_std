package ADRAF.com.nk.tool;

/**
 * 保存用户信息工具类，相当于一个全局静态变量
 *
 */
public class UserStateTool {
	private static String username = null;
	private static String password = null;
	private static String vcode = null;

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

	public static void setvcode(String vcode) {
		UserStateTool.vcode = vcode;
	}

	public static String getvcode() {
		return vcode;
	}

}
