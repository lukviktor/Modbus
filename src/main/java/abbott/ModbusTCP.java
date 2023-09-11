package abbott;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.net.InetAddress;

public class ModbusTCP {
            public static void main(String args[]) {
            try {
                // экземпляры класса
                TCPMasterConnection con = null; // коннект
                ModbusTCPTransaction trans = null; // транзакция
                ReadInputRegistersRequest rreq = null; // чтение запроса
                ReadInputRegistersResponse rres = null; // чтение ответа
                WriteCoilRequest req = null; // запись


                // Переменные для хранения параметров
                InetAddress addr = null; // slave адрес
                int port = 502; // port по умолчанию


                // IP адрес
                addr = InetAddress.getByName("192.168.0.1");

                // Открываем соединение
                con = new TCPMasterConnection(addr);
                con.setPort(port);
                con.connect();

                // Отправляем запрос
                int k = 4000;
                rreq = new ReadInputRegistersRequest(k, 2);

                // транзакция чтения данных
                trans = new ModbusTCPTransaction(con);
                trans.setRequest(rreq);

                // Чтение транзакции
                trans.execute();
                rres = (ReadInputRegistersResponse) trans.getResponse();
                System.out.println("Hex Значение регистра " + "= "
                        + rres.getHexMessage());

                // ~~~~~~~~~~~~~~~~~~~~ The functional Write Request
                // ~~~~~~~~~~~~~~~~~~~~
                // 3w. Prepare the request
                //req = new WriteCoilRequest(coil, true); // Switching ON the "DO 1"
                // (= address 17)

                // 4w. Prepare the transaction
                trans = new ModbusTCPTransaction(con);
                trans.setRequest(req);

                // 5w. Execute the transaction repeat times
                trans.execute();

                // 6. Close the connection
                con.close();

            } catch (Exception ex) {
                System.out.println("Error");
                ex.printStackTrace();
            }
        }
    }

