package lk.ijse.chatRoom.Util;

import lk.ijse.chatRoom.Controller.LoginFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMaintainer implements Runnable {

    public static ArrayList<ClientMaintainer> clientMaintainerArrayList = new ArrayList<>();

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String clientName;

    public ClientMaintainer(Socket socket){

        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.clientName = LoginFormController.clientName;
            System.out.println("client Maintanet Started....!");
            clientMaintainerArrayList.add(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        String groupMsg;

        while (socket.isConnected()) {
            try {
                groupMsg = dataInputStream.readUTF();
                defaultMsg(groupMsg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void defaultMsg(String groupMsg) {
        for (ClientMaintainer clientMaintainer : clientMaintainerArrayList){
            try {
                clientMaintainer.dataOutputStream.writeUTF(clientName+ " : " + groupMsg + "\n");
                clientMaintainer.dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
