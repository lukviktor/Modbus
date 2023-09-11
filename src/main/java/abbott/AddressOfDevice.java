package abbott;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

public class AddressOfDevice {
    public void addressOfDevice() throws Exception {
        String portname = "COM1"; //имя используемого последовательного порта
        int unitid = 1; //идентификатор устройства, с которым мы будем разговаривать, смотрите в первом вопросе
        int ref = 0; //ссылка, с которой начинать чтение
        int count = 0; //количество считываемыx данных
        int repeat = 1; //цикл для повторения транзакции

// настройка мастера modbus
        //ModbusCoupler.createModbusCoupler(null);
        ModbusCoupler.getReference();
        ModbusCoupler.getReference().setUnitID(1); //<-- this is the master id and it doesn't really matter

// setup serial parameters
        SerialParameters params = new SerialParameters();
        params.setPortName(portname);
        params.setBaudRate(9600);
        params.setDatabits(8);
        params.setParity("None");
        params.setStopbits(1);
        params.setEncoding("ascii");
        params.setEcho(false);

// open the connection
        SerialConnection con;
        con = new SerialConnection(params);
        // con = new SerialConnection(params);
        con.open();

// prepare a request
        ReadInputRegistersRequest req;
        req = new ReadInputRegistersRequest(ref, count);
        // req = new ReadInputRegistersRequest(ref, count);
        req.setUnitID(unitid); // <-- remember, this is the slave id from the first connection
        req.setHeadless();

// prepare a transaction
        ModbusSerialTransaction trans;
        trans = new ModbusSerialTransaction(con);
        trans.setRequest(req);

// execute the transaction repeat times because serial connections arn't exactly trustworthy...
        int k = 0;
        do {
            trans.execute();
            ReadInputRegistersResponse res;
            res = (ReadInputRegistersResponse) trans.getResponse();
            for (int n = 0; n < res.getWordCount(); n++) {
                System.out.println("Word " + n + "=" + res.getRegisterValue(n));
            }
            k++;
        } while (k < repeat);

// close the connection
        con.close();
    }
}
