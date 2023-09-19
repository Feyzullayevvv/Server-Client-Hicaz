package com.example.serverclienthicaz.Server;

import com.example.serverclienthicaz.Server.ModelAnbar.*;
import com.example.serverclienthicaz.Server.ModelProses.VirtualAnbar;
import com.example.serverclienthicaz.Server.ModelProses.*;
import com.example.serverclienthicaz.Server.data.AnbarData;
import com.example.serverclienthicaz.Server.data.ProsesData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private AnbarData anbarData;
    private ProsesData prosesData;

    private List<ClientHandler> clients= new ArrayList<>();


    public void start(int port){
        try {
            anbarData =new AnbarData();
            prosesData=new ProsesData();
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket,this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleGetMalSiyahiRequest(ClientHandler clientHandler) {
        anbarData.open();
        List<XammalMal> xammalMalList = anbarData.queryAllMal();
        anbarData.close();
        clientHandler.sendListResponse(xammalMalList);
    }
    public void handleExit(ClientHandler clientHandler){
        clients.remove(clientHandler);
        System.out.println("Client left " + clientHandler.getClientSocket().getInetAddress().getHostAddress());
    }
    public void handleGetHazirMalSiyahi(ClientHandler clientHandler){
        prosesData.open();
        List<HazirMal> hazirMalList= prosesData.queryHazirMal();
        prosesData.close();
        clientHandler.sendListResponse(hazirMalList);
    }
    public void insertHazirMal(String mal,ClientHandler clientHandler){
        prosesData.open();
        int n =prosesData.insertHazirMal(mal);
        prosesData.close();
        clientHandler.sendMessage(String.valueOf(n));

    }
    public void deleteHazirMal(int mehsulId){
        prosesData.open();
        prosesData.deleteHazirMal(mehsulId);
        prosesData.close();
    }
    public void insertHazirAnbar(String ad,double amount){
        prosesData.open();
        prosesData.insertHazirAnbar(ad,amount);
        prosesData.close();
    }


    public void handleGetHazirAnbar(ClientHandler clientHandler){
        prosesData.open();
        List<HazirAnbar> hazirAnbarList = prosesData.queryHazirMehsulAnbar();
        prosesData.close();
        clientHandler.sendListResponse(hazirAnbarList);
    }

    public void handleGetUser(ClientHandler clientHandler){
        prosesData.open();
        List<User> users= prosesData.queryUser();
        prosesData.close();
        clientHandler.sendListResponse(users);
    }
    public void deleteHazirAnbar(int mehsuldId){
        prosesData.open();
        prosesData.deleteHazirMehsul(mehsuldId);
        prosesData.close();
    }
    public void resetHazirAnbar(){
        prosesData.open();
        prosesData.resetHazirAnbar();
        prosesData.close();
    }

    public void updateHazirAnbarByAmount(int id,double amount){
        prosesData.open();
        prosesData.updateHazirAnbarByAmount(id, amount);
        prosesData.close();
    }

    public void handleGetTehvil(ClientHandler clientHandler){
        prosesData.open();
        List<Tehvil> tehvilList = prosesData.queryTehvil();
        prosesData.close();
        clientHandler.sendListResponse(tehvilList);
    }

    public void handleGetTehvilInfo(ClientHandler clientHandler,int nr){
        prosesData.open();
        List<TehvilItems> tehvilItems = prosesData.queryTehvilItems(nr);
        prosesData.close();
        clientHandler.sendListResponse(tehvilItems);
    }

    public void handleGetReceptList(ClientHandler clientHandler){
        prosesData.open();
        List<Recept> receptItemList= prosesData.queryRecept();
        prosesData.close();
        clientHandler.sendListResponse(receptItemList);

    }

    public void handleGetReceptInfo(ClientHandler clientHandler,int nr){
        prosesData.open();
        List<ReceptItem> receptItemList = prosesData.queryReceptItems(nr);
        prosesData.close();
        clientHandler.sendListResponse(receptItemList);
    }
    public void handleGetAnbar(ClientHandler clientHandler){
        prosesData.open();
        List<VirtualAnbar> virtualAnbarList =  prosesData.queryAnbar();
        prosesData.close();
        clientHandler.sendListResponse(virtualAnbarList);
    }

    public void handleGetEmeliyyatList(ClientHandler clientHandler){
        prosesData.open();
        List<Emeliyyat> emeliyyatList = prosesData.qeuryEmeliyyat();
        prosesData.close();
        clientHandler.sendListResponse(emeliyyatList);
    }
    public void handleInsertEmeliyyat(ClientHandler clientHandler,String date){
        prosesData.open();
        int n = prosesData.insertEmeliyyat(date);
        prosesData.close();
        clientHandler.sendMessage(String.valueOf(n));

    }
    public void handleInsertEmeliyyatItem(String mal,String vahid,double miqdar,int receptNr,int emeliyyatNr){
        prosesData.open();
        prosesData.insertEmeliyyatItem(mal,vahid,miqdar,receptNr,emeliyyatNr);
        prosesData.close();
    }
    public void handleUpdateProsesAnbar(double miqdar,int mehsul){
        prosesData.open();
        prosesData.updateProsesAnbar(miqdar,mehsul);
        prosesData.close();
    }
    public void handleEmeliyyatInfo(ClientHandler clientHandler,int nr){
        prosesData.open();
        List<EmeliyyatItem> emeliyyatItems = prosesData.queryEmeliyyatItem(nr);
        prosesData.close();
        clientHandler.sendListResponse(emeliyyatItems);
    }

    public void handleQueryMedaxil(ClientHandler clientHandler){
        anbarData.open();
        List<Medaxil> medaxilList= anbarData.queryMedaxil();
        anbarData.close();
        clientHandler.sendListResponse(medaxilList);
    }

    public void handleDeleteMedaxil(int medaxilNr){
        anbarData.open();
        anbarData.deleteMedaxil(medaxilNr);
        anbarData.close();
    }

    public void handleXammalMalInfo(ClientHandler clientHandler,String mal){
        anbarData.open();
        XammalMal mal1 = anbarData.getMal(mal);
        anbarData.close();
        clientHandler.sendObject(mal1);
    }
    public void insertMedaxil(ClientHandler clientHandler,String kreditorName){
        anbarData.open();
        int n = anbarData.insertMedaxil(kreditorName);
        anbarData.close();
        clientHandler.sendMessage(String.valueOf(n));
    }
    public void insertMedaxilFaktura(String mal,double ceki,double miqdar,int medaxilNr){
        anbarData.open();
        anbarData.insertMedaxilFaktura(mal, ceki, miqdar, medaxilNr);
        anbarData.close();
    }
    public void handleQueryMedaxilInfo(ClientHandler clientHandler,int nr){
        anbarData.open();
        List<MedaxilFaktura> list=anbarData.queryMedaxilItems(nr);
        anbarData.close();
        clientHandler.sendListResponse(list);
    }

    public void handleQueryAllXammal(ClientHandler clientHandler){
        anbarData.open();
        List<XammalMal> xammalMalList = anbarData.queryAllMal();
        anbarData.close();
        clientHandler.sendListResponse(xammalMalList);
    }
    public void handleQueryKreditor(ClientHandler clientHandler){
        anbarData.open();
        List<Kreditor> list = anbarData.queryKreditor();

        anbarData.close();
        clientHandler.sendListResponse(list);
    }
    public void deleteXammal(int xammalNr){
        anbarData.open();
        anbarData.deleteMal(xammalNr);
        anbarData.close();
    }

    public void insertNewMal(String ad,String vahid,double ortaGiymet){
        anbarData.open();
        anbarData.insertMal(ad,vahid,ortaGiymet);
        anbarData.close();
    }
    public void queryKreditorMedaxilNums(ClientHandler clientHandler,String kreditorName){
        anbarData.open();
        List<Integer> t = anbarData.kreditorMedaxilNums(kreditorName);
        anbarData.close();
        clientHandler.sendListResponse(t);
    }
    public void queryMedaxilItems(ClientHandler clientHandler,int nr){
        anbarData.open();
        List<MedaxilFaktura> l= anbarData.queryMedaxilItems(nr);
        anbarData.close();
        clientHandler.sendListResponse(l);
    }

    public void getMedaxilTarix(ClientHandler clientHandler,int nr){
        anbarData.open();
        String date= anbarData.getMedaxilTarix(nr);
        anbarData.close();
        clientHandler.sendMessage(date);
    }
    public void insertKreditor(String  name){
        anbarData.open();
        anbarData.insertKreditor(name);
        anbarData.close();
    }
    public void queryXammalAnbar(ClientHandler clientHandler){
        anbarData.open();
        List<AnbarItem> l =anbarData.queryAnbarItems();
        anbarData.close();
        clientHandler.sendListResponse(l);
    }

    public void insertXammalAnbar(String mal,double ceki,double miqdar){
        anbarData.open();
        anbarData.insertAnbar(mal,ceki,miqdar);
        anbarData.close();
    }
    public void queryMexaric(ClientHandler clientHandler){
        anbarData.open();
        List<Mexaric> mexaricList = anbarData.queryMexaric();
        anbarData.close();
        clientHandler.sendListResponse(mexaricList);
    }


    public void queryMexaricItems(ClientHandler clientHandler,int nr){
        anbarData.open();
        List<MexaricFaktura> list= anbarData.queryMexaricItems(nr);
        anbarData.close();
        clientHandler.sendListResponse(list);
    }
    public void insertMexaric(ClientHandler clientHandler,String tehvilci){
        anbarData.open();
        int n = anbarData.insertMexaric(tehvilci);
        anbarData.close();
        clientHandler.sendMessage(String.valueOf(n));
    }
    public void insertMexaricFaktura(String mal,double ceki,int n){
        anbarData.open();
        anbarData.insertMexaricFaktura(mal, ceki, n);
        anbarData.close();

    }
    public void handleMovcudCeki(ClientHandler clientHandler,String mal){
        anbarData.open();
        AnbarItem item = anbarData.getItem(mal);
        anbarData.close();
        clientHandler.sendMessage(String.valueOf(item.getCeki()));
    }
    public void insertTehvil(ClientHandler clientHandler,String tehvilci,int mexairNr){
        prosesData.open();
        int nr= prosesData.insertTehvil(tehvilci,mexairNr);
        prosesData.close();
        clientHandler.sendMessage(String.valueOf(nr));
    }
    public void insertTehvilItems(String mal,double ceki,int n){
        anbarData.open();
        XammalMal m= anbarData.getMal(mal);
        anbarData.close();
        prosesData.open();
        prosesData.insertTehvilitems(m.getNr(),mal,m.getVahid(),ceki,n);
        prosesData.close();
    }

    public void updateXammalItem(int nr,double ceki,double mebleg){
        anbarData.open();
        anbarData.updateXammalAnbarItem(nr,ceki,mebleg);
        anbarData.close();
    }
    public void insertRecept(ClientHandler clientHandler,String ad,double itki, double qaliq,double sonQaliq){
        prosesData.open();
        int nr = prosesData.insertRecept(ad,itki,qaliq,sonQaliq);
        prosesData.close();
        clientHandler.sendMessage(String.valueOf(nr));
    }

    public void insertReceptItem(String mal,String vahid,double miqdar,int receptNr){
        prosesData.open();
        prosesData.insertReceptItem(mal,vahid,miqdar,receptNr);
        prosesData.close();
    }
    public void deleteRecept(int nr){
        prosesData.open();
        prosesData.deleteRecept(nr);
        prosesData.close();
    }

    public void resetProsesAnbar(){
        prosesData.open();
        prosesData.resetProsesAnbar();
        prosesData.close();
    }
    public void updateProsesAnbarbyId(int nr,double amount){
        prosesData.open();
        prosesData.updateProsesAnbarByAmount(nr,amount);
        prosesData.close();
    }

    public void deleteTehvil(int nr,int mexaricNr){
        prosesData.open();
        prosesData.deleteTehvil(nr);
        prosesData.close();
        anbarData.open();
        anbarData.deleteMexaric(mexaricNr);
        anbarData.close();
    }
    public void deleteMexaric(int nr){
        anbarData.open();
        anbarData.deleteMexaric(nr);
        anbarData.close();
        prosesData.open();
        int n =  prosesData.retrieveMexaricNr(nr);
        prosesData.deleteTehvil(n);
        prosesData.close();
    }

    public void deleteEmeliyyat(int nr){
        prosesData.open();
        prosesData.deleteEmeliyyat(nr);
        prosesData.close();
    }

    public void decreaseHazirAnbarByName(double miqdar, String mehsul){
        prosesData.open();
        prosesData.updateHazirMehsulByname(miqdar,mehsul);
        prosesData.close();
    }
    public void increaseazirMehsulByname(double miqdar,String mehsul){
        prosesData.open();
        prosesData.increaseazirMehsulByname(miqdar,mehsul);
        prosesData.close();
    }

    public void getRecept(ClientHandler clientHandler,int nr){
        prosesData.open();
        Recept recept = prosesData.getRecept(nr);
        prosesData.close();
        clientHandler.sendObject(recept);
    }
    public void deleteEditRecept(int nr){
        prosesData.open();
        prosesData.deleteEditRecept(nr);
        prosesData.close();
    }
    public void insertEditReceptItem(int nr,String mal,String vahid,double miqdar,int receptNr){
        prosesData.open();
        prosesData.insertEditReceptItem(nr,mal,vahid,miqdar,receptNr);
        prosesData.close();
    }

    public void updateXammalSiyahi(int malnr,double value){
        anbarData.open();
        anbarData.updateXammalSiyahi(malnr,value);
        anbarData.close();
    }


        public static void main(String[] args) {
        Server server= new Server();
        server.start(5555);
    }


}
