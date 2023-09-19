package com.example.serverclienthicaz.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread{
    private Socket clientSocket;
    private Server server;
    private BufferedReader reader;
    private PrintWriter writer;
    private ObjectOutputStream objectOutputStream;
    private   ObjectInputStream objectInputStream;



    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }
    @Override
    public void run() {
        try {

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            int i =1;
            while (true) {
                String message = reader.readLine();
                if (message.equals("GETMALSIYAHI")) {
                    server.handleGetMalSiyahiRequest(this);
                }
                if (message.equals("EXIT")) {
                    server.handleExit(this);
                    clientSocket.close();
                    break;
                }
                if (message.equals("GETHAZIRMALSIYAHI")) {
                    server.handleGetHazirMalSiyahi(this);
                }
                if (message.equals("INSERTHAZIRMAL")) {
                    String hazirmal = reader.readLine();
                    server.insertHazirMal(hazirmal, this);
                }
                if (message.equals("DELETEHAZIRMEHSUL")) {
                    String mehsulId = reader.readLine();
                    server.deleteHazirMal(Integer.parseInt(mehsulId));
                }
                if (message.equals("GETHAZIRANBAR")) {
                    server.handleGetHazirAnbar(this);
                }
                if (message.equals("INSERTANBARNEWMEHSUL")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");

                        if (splitValues.length == 2) {
                            String malName = splitValues[0];
                            String malMiqdar = splitValues[1];
                            server.insertHazirAnbar(malName, Double.parseDouble(malMiqdar));
                        }
                    }
                }
                if (message.equals("GETADMINPASSWORD")){
                    server.handleGetUser(this);
                }if (message.equals("DELETEANBARMEHSUL")){
                    String mehsulId= reader.readLine();
                    server.deleteHazirAnbar(Integer.parseInt(mehsulId));
                }if (message.endsWith("RESETHAZIRANBAR")){
                    server.resetHazirAnbar();
                }if (message.equals("UPDATEHAZIRANBARAMOUNT")){
                    String id =reader.readLine();
                    String amount= reader.readLine();
                    server.updateHazirAnbarByAmount(Integer.parseInt(id),Double.parseDouble(amount));
                }if (message.equals("GETTEHVILLIST")){
                    server.handleGetTehvil(this);
                }if (message.equals("GETTEHVININFO")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.handleGetTehvilInfo(this,nr);
                }if (message.equals("GETRECEPTLIST")){
                    server.handleGetReceptList(this);
                }if (message.equals("GETRECEPTINFO")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.handleGetReceptInfo(this,nr);
                }if (message.equals("GETPROSESANBAR")){
                    server.handleGetAnbar(this);
                }if (message.equals("GETEMELIYYATLIST")){
                    server.handleGetEmeliyyatList(this);
                }if (message.equals("INSERTEMELIYYAT")){
                    String command= reader.readLine();
                        server.handleInsertEmeliyyat(this,command);

                }if (message.equals("INSERTEMELIYYATITEM")){
                    while (true){
                        String command= reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 5) {
                            String mal=splitValues[0];
                            String vahid = splitValues[1];
                            double miqdar = Double.parseDouble(splitValues[2]);
                            int receptNr= Integer.parseInt(splitValues[3]);
                            int emeliyyatNr = Integer.parseInt(splitValues[4]);
                            server.handleInsertEmeliyyatItem(mal,vahid,miqdar,receptNr,emeliyyatNr);
                        }
                    }
                }if (message.equals("UPDATEPROSESANBAR")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 2) {
                            int malNr = Integer.parseInt(splitValues[0]);
                            double miqdar = Double.parseDouble(splitValues[1]);
                            server.handleUpdateProsesAnbar(miqdar, malNr);
                        }
                    }
                }if (message.equals("GETEMELIYYATINFO")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.handleEmeliyyatInfo(this,nr);
                }if (message.equals("GETQUERYMEDAXIL")){
                    server.handleQueryMedaxil(this);
                }if (message.equals("DELETEMEDAXIL")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.handleDeleteMedaxil(nr);
                }if (message.equals("GETXAMMALMALINFO")){
                    String mal = reader.readLine();
                    server.handleXammalMalInfo(this,mal);
                }if (message.equals("INSERTMEDAXIL")){
                    String kreditorName= reader.readLine();
                    server.insertMedaxil(this,kreditorName);
                }if (message.equals("INSERTMEDAXILFAKTURA")){
                    while (true){
                        String command = reader.readLine();
                        if (command.equals("DONE")){
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 4) {
                            String mal = splitValues[0];
                            double miqdar= Double.parseDouble(splitValues[1]);
                            double mebleg = Double.parseDouble(splitValues[2]);
                            int medaxilNr = Integer.parseInt(splitValues[3]);
                            server.insertMedaxilFaktura(mal,miqdar,mebleg,medaxilNr);
                        }

                    }
                }if (message.equals("GETMEDAXILINFO")){
                    int nr = Integer.parseInt(reader.readLine());
                    server.handleQueryMedaxilInfo(this,nr);
                }if (message.equals("QUERYALLXAMMAL")){
                    server.handleQueryAllXammal(this);
                }if (message.equals("QUERYKREDITORS")){
                    server.handleQueryKreditor(this);
                }if (message.equals("DELETEXAMMALFROMSIYAHI")){
                    int xammalNr= Integer.parseInt(reader.readLine());
                    server.deleteXammal(xammalNr);
                }if (message.equals("INSERTNEWMAL")){
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 3) {
                            String mal = splitValues[0];
                            String vahid =splitValues[1];
                            double ortaGiymet = Double.parseDouble(splitValues[2]);
                            server.insertNewMal(mal,vahid,ortaGiymet);
                        }
                    }
                }if (message.equals("QUERYKREDITORMEDAXILNUMS")){
                    String kreditor= reader.readLine();
                    server.queryKreditorMedaxilNums(this,kreditor);
                }if (message.equals("QUERYMEDAXILITEMS")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.queryMedaxilItems(this,nr);
                }
                if (message.equals("GETMEDAXILTARIX")){
                    int id = Integer.parseInt(reader.readLine());
                    server.getMedaxilTarix(this,id);
                }if (message.equals("INSERTKREDITOR")){
                    String name = reader.readLine();
                    server.insertKreditor(name);
                }if (message.equals("QUERYXAMMALANBARITEMS")){
                    server.queryXammalAnbar(this);
                }
                if (message.equals("INSERTANBAR")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 3) {
                            String mal = splitValues[0];
                            double ceki = Double.parseDouble(splitValues[1]);
                            double mebleg = Double.parseDouble(splitValues[2]);
                            server.insertXammalAnbar(mal, ceki, mebleg);
                        }
                    }
                }if (message.equals("QUERYMEXARIC")){
                    server.queryMexaric(this);
                }if (message.equals("DELETMEXARIC")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.deleteMexaric(nr);
                }if (message.equals("QUERYMEXERICITEMS")){
                    int nr = Integer.parseInt(reader.readLine());
                    server.queryMexaricItems(this,nr);
                }if (message.equals("INSERTMEXARIC")){
                    String name= reader.readLine();
                    server.insertMexaric(this,name);
                }if (message.equals("INSERTMEXARICFAKTURA")){
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 3) {
                            String mal = splitValues[0];
                            double ceki = Double.parseDouble(splitValues[1]);
                            int n = Integer.parseInt(splitValues[2]);
                            server.insertMexaricFaktura(mal, ceki, n);
                        }
                    }
                }if (message.equals("GETMOVCUDCEKI")){
                    String mal = reader.readLine();
                    server.handleMovcudCeki(this,mal);
                }if (message.equals("INSERTTEHVIL")){
                    String name= reader.readLine();
                    int nr = Integer.parseInt(reader.readLine());
                    server.insertTehvil(this,name,nr);
                }if (message.equals("INSERTEHVILITEMS")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 3) {
                            String mal = splitValues[0];
                            double ceki = Double.parseDouble(splitValues[1]);
                            int n = Integer.parseInt(splitValues[2]);
                            server.insertTehvilItems(mal, ceki, n);
                        }
                    }
                }if (message.equals("UPDATEXAMMALMAL")){
                    int nr = Integer.parseInt(reader.readLine());
                    double ceki = Double.parseDouble(reader.readLine());
                    double mebleg= Double.parseDouble(reader.readLine());
                    server.updateXammalItem(nr,ceki,mebleg);
                }if (message.equals("INSERTRECEPT")){
                    String ad = reader.readLine();
                    double itki= Double.parseDouble(reader.readLine());
                    double qaliq = Double.parseDouble(reader.readLine());
                    double sonQaliq =  Double.parseDouble(reader.readLine());
                    server.insertRecept(this,ad,itki,qaliq,sonQaliq);
                }if (message.equals("INSERTRECEPTITEM")){
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 4) {
                            String mal = splitValues[0];
                            String vahid = splitValues[1];
                            double miqdar = Double.parseDouble(splitValues[2]);
                            int n = Integer.parseInt(splitValues[3]);
                            server.insertReceptItem(mal, vahid,miqdar, n);
                        }
                    }
                }if (message.equals("DELETERECEPT")){
                    int nr = Integer.parseInt(reader.readLine());
                    server.deleteRecept(nr);
                }if (message.equals("RESETPROSESANBAR")){
                    server.resetProsesAnbar();
                }if (message.equals("UPDATEPROSESANBARBYID")){
                    int nr = Integer.parseInt(reader.readLine());
                    double amount = Double.parseDouble(reader.readLine());
                    server.updateProsesAnbarbyId(nr,amount);
                }if (message.equals("DELETETEHVIL")){
                    int nr = Integer.parseInt(reader.readLine());
                    int mexaricNr=Integer.parseInt(reader.readLine());
                    server.deleteTehvil(nr,mexaricNr);
                }if (message.equals("DELETEEMELIYYAT")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.deleteEmeliyyat(nr);
                }if (message.equals("UPDATEHAZIRMEHSUL")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 2) {
                            String name = splitValues[0];
                            double amount = Double.parseDouble(splitValues[1]);
                            server.decreaseHazirAnbarByName(amount,name);
                        }
                    }

                }if (message.equals("INCREASEHAZIRMEHSULANBAR")) {
                    while (true) {
                        String command = reader.readLine();
                        if (command.equals("DONE")) {
                            break;
                        }
                        String[] splitValues = command.split("/");
                        if (splitValues.length == 2) {
                            String name = splitValues[0];
                            double amount = Double.parseDouble(splitValues[1]);
                            server.increaseazirMehsulByname(amount, name);
                        }
                    }
                }if (message.equals("GETRECEPT")){
                    int nr= Integer.parseInt(reader.readLine());
                    server.getRecept(this,nr);
                }if (message.equals("DELETEEDITRECEPTITEM")){
                    int nr=Integer.parseInt(reader.readLine());
                    server.deleteEditRecept(nr);
                }if (message.equals("INSEERTEDITRECEPTITEMS")){
                    int nr=Integer.parseInt(reader.readLine());
                    String mal = reader.readLine();
                    String vahid= reader.readLine();
                    Double miqdar = Double.parseDouble(reader.readLine());
                    int receptNr=Integer.parseInt(reader.readLine());
                    server.insertEditReceptItem(nr,mal,vahid,miqdar,receptNr);
                }if (message.equals("UPDATEXAMMALSIYAHI")){
                    int nr= Integer.parseInt(reader.readLine());
                    double ortaGiymet=Double.parseDouble(reader.readLine());
                    server.updateXammalSiyahi(nr,ortaGiymet);
                }
                System.out.println("recieved message " + message);
            }
        }catch(IOException e){
                e.printStackTrace();
        }
        finally{
                try {
                    reader.close();
                    writer.close();
                    objectOutputStream.close();
                    clientSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    public Socket getClientSocket() {
        return clientSocket;
    }

    public synchronized void sendListResponse(List<?> List) {
        try {
            objectOutputStream.writeObject(List);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void sendObject(Object object){
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized List<?> objectReader(){
        try {
            return (List<?>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized void sendMessage(String message){
        try {
            writer.println(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
