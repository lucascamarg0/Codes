package main;

import java.math.BigInteger;
import java.util.ArrayList;

public class Control {
	private static final Control control = new Control();

	public static Control getInstance() {
		return control;
	}
	
//	public String discoverClass(String ip) {
//		String classe = "IP Classe ";
//		String[] ipParts = ip.split("\\.");
//		Integer octeto1 = Integer.parseInt(ipParts[0]);
//		
//		if (octeto1 >= 1 && octeto1 <= 127) {
//			classe = classe + "A"
//					+ "\nPrivate Address Space: 10.0.0.0 to 10.255.255.255"
//					+ "\nDefault Subnet Mask: 255.0.0.0";
//		}else if(octeto1 >= 128 && octeto1 <= 191) {
//			classe = classe + "B"
//					+ "\nPrivate Address Space: 172.16.0.0 to 172.31.255.255"
//					+ "\nDefault Subnet Mask: 255.255.0.0";
//		}else if(octeto1 >= 192 && octeto1 <= 223) {
//			classe = classe + "C"
//					+ "\nPrivate Address Space: 192.168.0.0 to 192.168.255.255"
//					+ "\nDefault Subnet Mask: 255.255.255.0";
//		}else if(octeto1 >= 224 && octeto1 <= 239) {
//			classe = classe + "D";
//		}else {
//			classe = classe + "E";
//		}
//		
//		return classe;
//	}
	public String[] discoverClass(String ip) {
		String[] classe = new String[3];
		String[] ipParts = ip.split("\\.");
		Integer octeto1 = Integer.parseInt(ipParts[0]);
		
		if (octeto1 >= 1 && octeto1 <= 127) {
			classe[0] = "A";
			classe[1] = "255.0.0.0";
			classe[2] = addressToBinary(classe[1]);
		}else if(octeto1 >= 128 && octeto1 <= 191) {
			classe[0] = "B";
			classe[1] = "255.255.0.0";
			classe[2] = addressToBinary(classe[1]);
		}else if(octeto1 >= 192 && octeto1 <= 223) {
			classe[0] = "C";
			classe[1] = "255.255.255.0";
			classe[2] = addressToBinary(classe[1]);
		}else if(octeto1 >= 224 && octeto1 <= 239) {
			classe[0] = "D";
		}else {
			classe[0] = "E";
		}
		return classe;
	}
	
	public String binaryToAddress(String binary) {
		
		ArrayList<String> strings = new ArrayList<String>();
		int index = 0;
		while (index < binary.length()) {
		    strings.add(binary.substring(index, Math.min(index + 8,binary.length())));
		    index += 8;
		}
		
		String octeto1 = "" + Integer.parseInt(strings.get(0), 2);
		String octeto2 = "" + Integer.parseInt(strings.get(1), 2);
		String octeto3 = "" + Integer.parseInt(strings.get(2), 2);
		String octeto4 = "" + Integer.parseInt(strings.get(3), 2);
		
		return octeto1 + "." + octeto2 + "." + octeto3 + "." + octeto4;
	}
	
	public String addressToBinary(String ip) {

		String[] ipParts = ip.split("\\.");

		String octeto1 = Integer.toBinaryString(Integer.parseInt(ipParts[0]));
		String octeto2 = Integer.toBinaryString(Integer.parseInt(ipParts[1]));
		String octeto3 = Integer.toBinaryString(Integer.parseInt(ipParts[2]));
		String octeto4 = Integer.toBinaryString(Integer.parseInt(ipParts[3]));
		
		octeto1 = String.format("%08d", Integer.parseInt(octeto1));
		octeto2 = String.format("%08d", Integer.parseInt(octeto2));
		octeto3 = String.format("%08d", Integer.parseInt(octeto3));
		octeto4 = String.format("%08d", Integer.parseInt(octeto4));
		
		return octeto1 + octeto2 + octeto3 + octeto4;
	}
	
	public static int nextPowerOf2(final int a){
		int b = 1;
		while (b < a)
		{
		    b = b << 1;
		}
		return b;
    }
	
	public boolean validateAddress(String address) {
		
		String[] addressParts = address.split("\\.");

		if (Integer.parseInt(addressParts[0]) > 255 ||
			Integer.parseInt(addressParts[1]) > 255 ||
			Integer.parseInt(addressParts[2]) > 255 ||
			Integer.parseInt(addressParts[3]) > 255) {
			return false;
		}
		return true;
	}

	public String concatTexto(String texto, String concat) {
		
		return texto + "\n" + concat;
	}
	
	public int borrowedBits(int subnet) {
		int potencia = nextPowerOf2(subnet);
		int potCount;
		
		for (potCount = 1; potCount <= 16; potCount++) {
			potencia = potencia / 2;
			if (potencia == 1)
				break;
		}
		
		return potCount;
	}
	
	public String addHosts(String maskBinary, int bits) {
		
		for (int i = 31; i >= 0; i--) {
			if (maskBinary.charAt(i) == '0') {
				maskBinary = maskBinary.substring(0,i)+'1'+maskBinary.substring(i+1);
				bits = bits - 1;
				if (bits == 0) {
					break;
				}
			}
		}
		
		return maskBinary;		
	}
	
	public String addSubnet(String maskBinary, int bits) {
		
		for (int i = 0; i< 32; i++) {
			if (maskBinary.charAt(i) == '0') {
				maskBinary = maskBinary.substring(0,i)+'1'+maskBinary.substring(i+1);
				bits = bits - 1;
				if (bits == 0) {
					break;
				}
			}
		}
		
		return maskBinary;
	}
	
	public String avaliaIP(String ip, Integer subnet, Integer hosts) {
		String text = "\n";
		
		if (!validateAddress(ip)) {
			return "IP Invalido";
		}
		
		
		String[] classe = discoverClass(ip);
		String ipBinary = addressToBinary(ip);
		
		text = "Classe " + classe[0];
		text = concatTexto(text, "IP: " + ip);
		text = concatTexto(text, "Default Subnet Mask: " + classe[1]);
		text = concatTexto(text, ipBinary);
		text = concatTexto(text, classe[2]);
		
		BigInteger b1 = new BigInteger(ipBinary, 2);
		BigInteger b2 = new BigInteger(classe[2], 2);
		
		String bitwise = "" + b1.and(b2).toString(2);
//		text = concatTexto(text, bitwise);

		String id = binaryToAddress(bitwise);
		text = concatTexto(text, "\nID de Rede: " + id);
		
		int bits = 0;
		String customMask = "";
		
		if (subnet != null) {
			bits = borrowedBits(subnet);
			customMask = addSubnet(classe[2], bits);
		}
		
		if (hosts != null) {
			bits = borrowedBits(hosts);
			customMask = addHosts(classe[2], bits);
		}
		customMask = binaryToAddress(customMask);
		text = concatTexto(text, "\nCustom Subnet Mask: " + customMask);
		text = concatTexto(text, "Borrowed Bits: " + bits);
		return text;
	}
}
