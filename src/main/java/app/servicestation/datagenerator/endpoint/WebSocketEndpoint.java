package app.servicestation.datagenerator.endpoint;

import app.servicestation.datagenerator.logic.Reportable;
import app.servicestation.datagenerator.model.Identifier;
import io.vertx.core.json.Json;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class WebSocketEndpoint implements Endpoint {

    private ServerSocket server;
    private Socket socket;
    private ObjectOutputStream stream;
    private AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    public void init() {
        Executors.newCachedThreadPool().execute(this::establishConnection);
    }

    private void establishConnection() {
        System.err.println("Waiting for establishing connection");
        try {
            server = new ServerSocket(8090);
            socket = server.accept();
            stream = new ObjectOutputStream(socket.getOutputStream());
            stream.flush();
            flag.set(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareReport(Reportable reportable) {
        if (flag.get()) {
            List<ConsoleEndpoint.MeasureEntity> list = reportable.getMeasurableList().stream()
                    .map(i -> new ConsoleEndpoint.MeasureEntity(i.getIdentifier().getIdentifier(), i.getType(),
                            i.getMeasureTypes().stream().collect(Collectors.toMap(Identifier::getIdentifier, i::getMeasure))))
                    .collect(Collectors.toList());

            try {
                stream.writeObject(Json.encode(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
