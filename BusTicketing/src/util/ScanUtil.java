package util;

import java.util.Scanner;

public class ScanUtil {
	// 스캐너를 손쉽게 사용할 수 있는 static 메서드를 가지고 있음
	static Scanner sc = new Scanner(System.in);

	public static String nextLine() {
		return sc.nextLine();
	}

	public static String nextLine(String text) {
		System.out.print(text);
		return sc.nextLine();
	}

	public static int nextInt() {
		while (true) {
			try {
				int result = Integer.parseInt(sc.nextLine());
				return result;
			} catch (NumberFormatException e) {
				System.out.println("잘못 입력!!");
			}
		}
	}
	
	public static int nextInt(String text) {
		System.out.print(text);
		while (true) {
			try {
				int result = Integer.parseInt(sc.nextLine());
				return result;
			} catch (NumberFormatException e) {
				System.out.println("잘못 입력!!");
			}
		}
	}
}
