import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class test {
	public void draw() {
		Scanner input = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+09:00");
		Date today = new Date();
		Random r = new Random();
		int balance = 10000;

		// 상품 리스트 array
		List<String> productA = new ArrayList<String>(
				Arrays.asList("COLA, 2021-03-21T01:00:32+09:00", "CIDER, 2022-02-10T02:28:56+09:00",
						"FANTA, 2023-05-02T02:20:19+09:00", "WATAR, 2024-04-28T17:30:38+09:00",
						"WELCHS, 2023-07-08T05:40:27+09:00", "MOGUMOGU, 2023-08-13T16:00:08+09:00"));
		List<String> productB = new ArrayList<String>(
				Arrays.asList("CHICKEN, 2023-03-23T02:20:19+09:00", "PIZZA, 2023-04-23T11:10:39+09:00",
						"HAMBURGER, 2023-05-02T06:00:19+09:00", "SANDWICHES, 2023-05-12T10:30:27+09:00"));

		// A상품과 B상품을 정의
		HashMap<String, List<String>> product = new HashMap<String, List<String>>();
		product.put("A", productA);
		product.put("B", productB);

		// B상품의 남은 갯수를 정의
		HashMap<Integer, Integer> leftB = new HashMap<Integer, Integer>();
		leftB.put(0, 3);
		leftB.put(1, 3);
		leftB.put(2, 3);
		leftB.put(3, 3);

		while (balance > 0) {
			System.out.println("원하는 뽑기 횟수를 선택해주세요.");
			int pickUp = input.nextInt();
			if (balance < (pickUp * 100)) {
				System.out.println("현재 잔액보다 뽑기 횟수가 더 많습니다. 다시 입력해주세요");
				pickUp = input.nextInt();
			}
			// 당첨과 꽝 확인
			for (int i = 0; i < pickUp; pickUp--) {
				String type;
				int probA = r.nextInt(100);
				if (probA < 90) {
					type = "A";
				} else {
					int probB = r.nextInt(100);
					if (probB < 90) {
						type = "lt";
					} else {
						type = "B";
					}
				}

				if (type.equals("lt")) {
					System.out.println("꽝입니다.");
					balance -= 100;
				} else {
					// 랜덤한 상품을 가져온다
					int random = r.nextInt(product.get(type).size());
					String giveaway = product.get(type).get(random);
					String[] check = giveaway.replaceAll(" ", "").split(",");

					// 남은 유통기한을 확인
					
						Date exDate;
						try {
							exDate = sdf.parse(check[1]);
							
							if (exDate.after(today)) {
								System.out.println("축하합니다 " + check[0] + "에 당첨되었습니다.");
								if (type.equals("B")) {
									int left = leftB.get(random) - 1;
									leftB.put(random, left);
									if (leftB.get(random) == 0) {
										leftB.remove(random);
										product.get("B").remove(random);
									}
								}
								balance -= 100;
								
							} else {
								i--;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
			}

			System.out.println("남은 잔액은 " + balance + "입니다.");

		}

	}

	public static void main(String[] args) {
		Scanner scnn = new Scanner(System.in);
		test test = new test();
		String start;

		System.out.println("---------------------");
		System.out.println("draw를 입력하시면 상품 뽑기를 시작합니다.");
		System.out.println("---------------------");
		start = scnn.nextLine();
		while (true) {
			if (start.equals("draw")) {
				test.draw();
			} else {
				System.out.println("올바르지 않은 문구를 입력하였습니다. 다시 입력해주세요");
				start = scnn.nextLine();
			}
		}

	}

}
