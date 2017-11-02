def ip_to_binary(ip):

	ip_binary = [None] * 4
	ip_binary[0] = '{0:08b}'.format(int(ip[0]))
	ip_binary[1] = '{0:08b}'.format(int(ip[1]))
	ip_binary[2] = '{0:08b}'.format(int(ip[2]))
	ip_binary[3] = '{0:08b}'.format(int(ip[3]))
	
	return ip_binary

def binary_to_ip(binary):

	ip = [None] * 4
	ip[0] = int(binary[0], 2)
	ip[1] = int(binary[1], 2)
	ip[2] = int(binary[2], 2)
	ip[3] = int(binary[3], 2)  
	return ip

def get_ip(texto):
	boolean = True
	while boolean:
		ip_base = input(texto)
		if ip_base.count('.') < 3:
			print ('Endereço inválido')
		else:
			boolean = False

	list_ip = ip_base.split('.')
	return list_ip

def test_two_ips(ip1, ip2):
	compare = [None] * 4
	count = 0
	for ip in ip1:
		count2 = 0
		octeto = ''
		for octeto_ip in list(ip):

			if octeto_ip == '1' and list(ip2[count])[count2] == '1':
				octeto = octeto + '1'
			else:
				octeto = octeto + '0'
			
			count2 = count2 +1
		compare[count] = octeto
		count = count +1

	return compare

def get_subnet():

	subnet_list = [None] * 0

	boolean = True
	while boolean:
		print ('Para adicionar uma nova subnet digite o tamanho, caso queira calcular digite "ok"')
		subnet_length = input('Quantidade de hosts: ')
		if subnet_length != 'ok':
			subnet_list.insert(len(subnet_list), subnet_length)
			print ('Total subnets: ', len(subnet_list))
		else:
			boolean = False
				
	return subnet_list

def main():
	# list_ip = get_ip('Digite um IP base: ')
	ip = '172.184.12.15'
	list_ip = ip.split('.')
	ip_bin = ip_to_binary(list_ip)

	# list_def_mask = get_ip('Máscara Padrão: ')
	def_mask = '255.255.255.192'
	list_def_mask = def_mask.split('.')
	def_mask_bin = ip_to_binary(list_def_mask)

	print ('IP base: ', ip)
	print ('Máscara Padrão: ', def_mask)

	print (ip_bin[0],ip_bin[1],ip_bin[2],ip_bin[3])
	print (def_mask_bin[0],def_mask_bin[1],def_mask_bin[2],def_mask_bin[3])

	id_rede_binary = test_two_ips(ip_bin, def_mask_bin)
	list_id_rede = binary_to_ip(id_rede_binary)
	print ('ID de rede: ', list_id_rede[0], list_id_rede[1], list_id_rede[2], list_id_rede[3])

	print ('\n\n\n')

	subnet_list = get_subnet()

	print ('\nSubnet:')
	for print_subnet in subnet_list:
		for i in range(0, 16):
			if (int(print_subnet) + 2) >= 2 ** i and (int(print_subnet) + 2) < 2 ** (i+1):
				print ('Tamanho: ', print_subnet, ' | Mais 2: ', int(print_subnet) + 2, ' | Total: ', 2 ** (i + 1))


if __name__ == '__main__':
	main()