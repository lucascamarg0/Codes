package teste;

public class potencia {
    public static int nextPowerOf2(final int a){
        int b = 1;
        while (b < a)
        {
            b = b << 1;
        }
        return b;
    }

	public static void main(String[] args) {
		Integer pot = 750;
		
//        for i in range(0, 16):
//            if (int(print_subnet) + 2) >= 2 ** i and (int(print_subnet) + 2) < 2 ** (i + 1):
//                print('Tamanho: ', print_subnet, ' | Mais 2: ', int(
//                    print_subnet) + 2, ' | Total: ', 2 ** (i + 1))
		
//		for (int i = 0; i <= 16; i++) {
//			System.out.println(i);
//			if ()
//		}
		

		System.out.println(nextPowerOf2(pot));
		System.out.println(Integer.toBinaryString(nextPowerOf2(pot) - 1));
	}

}
