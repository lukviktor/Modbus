package abbott;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.net.InetAddress;


public class ModbusExample {
    public String ip() {
        return "192.168.0.1";
    }
    public void connectDevice() {
        try {
            InetAddress ipAddress = InetAddress.getByName("192.168.0.1");
            // Устанавливаем соединение с устройством по Modbus TCP
            TCPMasterConnection connection = new TCPMasterConnection(ipAddress);
            connection.connect();
            // Создаем объект для транзакции Modbus TCP
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);

            // Создаем запрос чтения регистров
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(0, 1); // Читаем один регистр, начиная с адреса 0

            // Устанавливаем запрос в транзакцию
            transaction.setRequest(request);

            // Выполняем транзакцию
            transaction.execute();

            // Получаем ответ от устройства
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) transaction.getResponse();

            // Получаем значение регистра из ответа
            int registerValue = response.getRegisterValue(0);

            // Выводим значение регистра
            System.out.println("Значение регистра: " + registerValue);

            // Закрываем соединение
            connection.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ModbusExample().connectDevice();
    }
}
