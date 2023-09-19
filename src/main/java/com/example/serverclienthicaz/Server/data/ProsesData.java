package com.example.serverclienthicaz.Server.data;

import com.example.serverclienthicaz.Server.ModelAnbar.XammalMal;
import com.example.serverclienthicaz.Server.ModelProses.VirtualAnbar;
import com.example.serverclienthicaz.Server.ModelProses.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProsesData {
    private Connection connection;
    private PreparedStatement insertHazirMal;
    private PreparedStatement deleteHazirMal;
    private PreparedStatement deleteRecept;
    private PreparedStatement deleteReceptItem;
    private PreparedStatement insertHazirMehsulAnbar;
    private PreparedStatement deleteHazirMehsulAnbar;
    private PreparedStatement existsInAnbar;
    private PreparedStatement increaseAnbar;
    private PreparedStatement updateHazirAnbarAmount;
    private PreparedStatement updateProsesAnbarAmount;
    private PreparedStatement updateHazirMehsulByName;
    private PreparedStatement updateHazirMehsulAnbar;

    private PreparedStatement queryTehvilItems;
    private PreparedStatement queryReceptItems;
    private PreparedStatement insertEmeliyyatItem;

    private PreparedStatement insertEmeliyyat;

    private PreparedStatement updateProsesAnbar;

    private PreparedStatement queryEmeliiyatItem;
    private PreparedStatement insertTehvil;
    private PreparedStatement insertTehvilItems;

    private PreparedStatement insertProsesAnbar;
    private PreparedStatement increaseProsesAnbar;
    private PreparedStatement existsInProsesAnbar;
    private PreparedStatement insertRecept;

    private PreparedStatement insertReceptItem;


    private PreparedStatement deleteTehvil;
    private PreparedStatement deleteTehvilItems;

    private PreparedStatement decreaseProsesAnbar;
    private PreparedStatement getMexaricNr;
    private PreparedStatement deleteEmeliyyat;
    private PreparedStatement deleteEditReceptitem;

    private PreparedStatement getRecept;

    private PreparedStatement insertEditReceptItems;

    private static final String DB_NAME="HicazProses.db";

    private static final String DB_PATH="jdbc:sqlite:/Users/muhammadfeyzullayev/Documents/Java/Server-Client-Hicaz/";
    private static final String TABLE_MAL="Mal";
    private static final String COLUMN_MAL_ID="id";
    private static final String COLUMN_MAL_NAME="name";
    private static final String TABLE_HAZIR_MEHSUL="HazirMehsul";
    private static final String COLUMN_HAZIR_MEHSUL_ID="Id";
    private static final String COLUMN_HAZIR_MEHSUL_MAL="Mal";
    private static final String COLUMN_HAZIR_MEHSUL_VAHID="Vahid";
    private static final String COLUMN_HAZIR_MEHSUL_AMOUNT="Amount";



    private static final String TABLE_USERS="User";
    private static final String COLUMN_USERS_NAME="Name";
    private static final String COLUMN_USERS_PASSWORD="Password";

    private static final String TABLE_TEHVIL="Tehvil";
    private static final String COLUMN_TEHVIL_NR="Nr";
    private static final String COLUMN_TEHVIL_DATE="Date";
    private static final String COLUMN_TEHVIL_QEBULEDEN="QEBULCU";
    private static final String COLUMN_TEHVIL_MEXARICNR="MexaricNr";

    private static final String TABLE_TEHVILITEMS="TehvilItems";
    private static final String COLUMN_TEHVILITEMS_NR="Nr";
    private static final String COLUMN_TEHVILITEMS_MALNR="MalNr";
    private static final String COLUMN_TEHVILITEMS_MAL="Mal";
    private static final String COLUMN_TEHVILITEMS_CEKI="Ceki";
    private static final String COLUMN_TEHVILITEMS_TEHVILNR="TehvilNr";

    private static final String TABLE_GERIDONUSH="GeriDonush";
    private static final String COLUMN_GERIDONUSH_NR="Nr";
    private static final String COLUMN_GERIDONUSH_MALNR="MalNr";
    private static final String COLUMN_GERIDONUSH_MAL="Mal";
    private static final String COLUMN_GERIDONUSH_MIQDAR="Miqdar";
    private static final String COLUMN_GERIDONUSH_TEHVILNR="TehvilNr";

    private static final String TABLE_RECEPT="Recept";
    private static final String COLUMN_RECEPT_NR="Nr";
    private static final String COLUMN_RECEPT_NAME="Name";
    private static final String COLUMN_RECEPT_ITKI="Itki";
    private static final String COLUMN_RECEPT_QALIQ="Qaliq";
    private static final String COLUMN_RECEPT_SONQALIQ="SonQaliq";

    private static final String TABLE_RECEPTITEMS="ReceptItems";
    private static final String COLUMN_RECEPTITEMS_NR="Nr";
    private static final String COLUMN_RECEPTITEMS_VAHID="Vahid";
    private static final String COLUMN_RECEPTITEMS_MAL="Mal";
    private static final String COLUMN_RECEPTITEMS_MIQDAR="Miqdar";
    private static final String COLUMN_RECEPTITEMS_RECEPTNR="ReceptNr";

    private static final String TABLE_ANBAR="anbar";
    private static final String COLUMN_ANBAR_NR="Nr";
    private static final String COLUMN_ANBAR_MALNR="MalNr";
    private static final String COLUMN_ANBAR_MAL="Mal";
    private static final String COLUMN_ANBAR_VAHID="Vahid";
    private static final String COLUMN_ANBAR_MIQDAR="Miqdar";

    private static final String TABLE_EMELIYYAT="Emeliyyat";
    private static final String COLUMN_EMELIYYAT_NR="Nr";
    private static final String COLUMN_EMELIYYAT_DATE="Date";
    private static final String COLUMN_EMELIYYAT_TEHVILNR="TehvilNr";

    private static final String TABLE_EMELIYYATITEM="EmeliyyatItem";
    private static final String COLUMN_EMELIYYATITEM_MAL="Mal";
    private static final String COLUMN_EMELIYYATITEM_VAHID="Vahid";
    private static final String COLUMN_EMELIYYATITEM_MIQDAR="Miqdar";
    private static final String COLUMN_EMELIYYATITEM_RECEPTNR="ReceptNr";
    private static final String COLUMN_EMELIYYATITEM_TEHVILNR="TehvilNr";
    private static final String COLUMN_EMELIYYATITEM_EMELIYYATNR="EmeliyyatNr";



    private static final String QUERY_HAZIR_MAL="SELECT * FROM " + TABLE_MAL;
    private static final String EXIST_IN_ANBAR="SELECT COUNT(*) FROM " + TABLE_HAZIR_MEHSUL + " WHERE " + COLUMN_HAZIR_MEHSUL_MAL + " = ?";
    private static final String EXISTS_IN_PROSESANBAR="SELECT COUNT(*) FROM " + TABLE_ANBAR + " WHERE " + COLUMN_ANBAR_MALNR + " = ?";
    private static final String INSERT_HAZIR_MAL="INSERT INTO " + TABLE_MAL + "( " + COLUMN_MAL_NAME + ") VALUES(?)";
    private static final String DELETE_HAZIR_MAL ="DELETE FROM " + TABLE_MAL + " WHERE " + COLUMN_MAL_ID + " = ?";
    private static final String DELETE_RECEPT="DELETE FROM " + TABLE_RECEPT + " WHERE "  + COLUMN_RECEPT_NR +  " = ?";
    private static final String DELETE_EDIT_RECEPTITEM="DELETE FROM " + TABLE_RECEPTITEMS + " WHERE " + COLUMN_RECEPTITEMS_NR + " = ?";
    private static final String DELETE_RECEPTITEM="DELETE FROM " + TABLE_RECEPTITEMS + " WHERE " + COLUMN_RECEPTITEMS_RECEPTNR + " = ?";

    private static final String QUERY_HAZIR_MEHSUL="SELECT * FROM " +TABLE_HAZIR_MEHSUL ;
    private static final String INSERT_HAIR_MEHSUL="INSERT INTO " + TABLE_HAZIR_MEHSUL + "( " +COLUMN_HAZIR_MEHSUL_MAL + ", " + COLUMN_HAZIR_MEHSUL_VAHID +
            ", " + COLUMN_HAZIR_MEHSUL_AMOUNT + ") VALUES(?,?,?)";
    private static final String DELETE_HAZIR_MEHSUL = "DELETE FROM " + TABLE_HAZIR_MEHSUL + " WHERE " + COLUMN_HAZIR_MEHSUL_ID + " = ?";
    private static final String DELETE_EMELIYYAT="DELETE FROM " + TABLE_EMELIYYAT + " WHERE " + COLUMN_EMELIYYAT_NR + " = ?";
    private static final String INCREASE_ANBAR="UPDATE " + TABLE_HAZIR_MEHSUL +" SET " + COLUMN_HAZIR_MEHSUL_AMOUNT + " = " + COLUMN_HAZIR_MEHSUL_AMOUNT
            + " + ?" +" WHERE " + COLUMN_HAZIR_MEHSUL_MAL + " = ?";

    private static final String QUERY_ALL_USER="SELECT * FROM " + TABLE_USERS;
    private static final String RESET_AMOUNT_QUERY = "UPDATE " + TABLE_HAZIR_MEHSUL +
            " SET " + COLUMN_HAZIR_MEHSUL_AMOUNT + " = 0.0";
    private static final String UPDATE_HAZIR_MEHSUL_ANBAR="UPDATE " + TABLE_HAZIR_MEHSUL + " SET " + COLUMN_HAZIR_MEHSUL_AMOUNT + " = " +  COLUMN_HAZIR_MEHSUL_AMOUNT +
            " - ? "  + " WHERE " +COLUMN_HAZIR_MEHSUL_MAL + " = ?";
    private static final String RESET_AMOUNT_PROSESANBAR="UPDATE " + TABLE_ANBAR +
            " SET " + COLUMN_ANBAR_MIQDAR + " = 0.0";
    private static final String UPDATE_AMOUNT_BY_ID = "UPDATE " + TABLE_HAZIR_MEHSUL +
            " SET " + COLUMN_HAZIR_MEHSUL_AMOUNT + " = ?" +
            " WHERE " + COLUMN_HAZIR_MEHSUL_ID + " = ?";
    private static final String UPDATE_PROSESANBAR_AMOUNT_ID="UPDATE " + TABLE_ANBAR + " SET " + COLUMN_ANBAR_MIQDAR + " = ?" +
            " WHERE " + COLUMN_ANBAR_MALNR + " = ?";
    private static final String UPDATE_PROSESANBAR_AMOUNT_NAME="UPDATE " + TABLE_ANBAR + " SET " + COLUMN_ANBAR_MIQDAR + " = ?" +
            " WHERE " + COLUMN_ANBAR_MAL + " = ?";
    private static final  String UPDATE_HAZIR_ANBAR_BY_NAME="UPDATE " + TABLE_HAZIR_MEHSUL +
            " SET " + COLUMN_HAZIR_MEHSUL_AMOUNT + " = " + COLUMN_HAZIR_MEHSUL_AMOUNT + " + ?" +
            " WHERE " + COLUMN_HAZIR_MEHSUL_MAL + " = ?";
    private static final String QUERY_TEHVIL="SELECT * FROM " + TABLE_TEHVIL;
    private static final String QUERY_TEHVILITEMS="SELECT * FROM " + TABLE_TEHVILITEMS + " WHERE " + COLUMN_TEHVILITEMS_TEHVILNR + " = ?";

    private static final String QUERY_RECEPT="SELECT * FROM " + TABLE_RECEPT;
    private static final String QUERY_RECEPT_ITEMS="SELECT * FROM " + TABLE_RECEPTITEMS
            + " WHERE "  + COLUMN_RECEPTITEMS_RECEPTNR + " = ?";

    private static final String QUERY_ANBAR="SELECT * FROM " + TABLE_ANBAR;
    private static final String QUERY_EMELIYYAT="SELECT * FROM " + TABLE_EMELIYYAT;

    private static final String INSERT_EMELIYYATITEM="INSERT INTO " + TABLE_EMELIYYATITEM
             + "( " + COLUMN_EMELIYYATITEM_MAL + ", " +COLUMN_EMELIYYATITEM_VAHID +", " +COLUMN_EMELIYYATITEM_MIQDAR +
            ", " + COLUMN_EMELIYYATITEM_RECEPTNR + ", "  + COLUMN_EMELIYYATITEM_EMELIYYATNR +
            ") VALUES(?,?,?,?,?)";

    private static final String INSERT_EMELIYYAT="INSERT INTO " + TABLE_EMELIYYAT +
            "( " + COLUMN_EMELIYYAT_DATE+  ") VALUES(?)";
    private static final String UPDATE_PROSESANBAR="UPDATE " + TABLE_ANBAR  +  " SET " + COLUMN_ANBAR_MIQDAR + " = ? " + " WHERE " + COLUMN_ANBAR_MALNR + " = ?";


    private static final String QUERY_EMELIYYATITEMLIST="SELECT * FROM " + TABLE_EMELIYYATITEM + " WHERE " + COLUMN_EMELIYYATITEM_EMELIYYATNR + " = ?";

    private static final String INSERT_TEHVIL="INSERT INTO "   + TABLE_TEHVIL + "(" + COLUMN_TEHVIL_DATE + ", " + COLUMN_TEHVIL_QEBULEDEN + ", " + COLUMN_TEHVIL_MEXARICNR
            + ") VALUES(?,?,?)";

    private static final String INSERT_TEHVILITEMS="INSERT INTO " + TABLE_TEHVILITEMS + "( " + COLUMN_TEHVILITEMS_MALNR + ", " +
            COLUMN_TEHVILITEMS_MAL + ", " + COLUMN_TEHVILITEMS_CEKI + ", " + COLUMN_TEHVILITEMS_TEHVILNR +
            ") VALUES(?,?,?,?)";

    private static final String INSERT_PROSESANBAR="INSERT INTO "  +TABLE_ANBAR + "( " + COLUMN_ANBAR_MALNR +", " +
            COLUMN_ANBAR_MAL + ", " + COLUMN_ANBAR_VAHID + ", " + COLUMN_ANBAR_MIQDAR + ") VALUES(?,?,?,?)";


    private static final String INCREASE_PROSESANBAR="UPDATE " + TABLE_ANBAR +  " SET " + COLUMN_ANBAR_MIQDAR +
            " = " + COLUMN_ANBAR_MIQDAR + " + ? WHERE " + COLUMN_ANBAR_MALNR + " =?";

    private static final String INSERT_RECEPT="INSERT  INTO " + TABLE_RECEPT + " ( " + COLUMN_RECEPT_NAME + ", " + COLUMN_RECEPT_ITKI + ", " + COLUMN_RECEPT_QALIQ
            + ", " + COLUMN_RECEPT_SONQALIQ + ") VALUES(?,?,?,?)";

    private static final String INSERT_RECEPT_ITEM="INSERT INTO " + TABLE_RECEPTITEMS + "( " + COLUMN_RECEPTITEMS_MAL + ", " + COLUMN_RECEPTITEMS_VAHID + ", " +
            COLUMN_RECEPTITEMS_MIQDAR + ", " + COLUMN_RECEPTITEMS_RECEPTNR + " ) VALUES (?,?,?,?)";
    private static final String DELETE_TEHVIL="DELETE FROM " +  TABLE_TEHVIL  + " WHERE " + COLUMN_TEHVIL_NR + " = ?";
    private static final String DECREASE_PROSESANBAR="UPDATE " + TABLE_ANBAR + " SET " + COLUMN_ANBAR_MIQDAR + " = " + COLUMN_ANBAR_MIQDAR + "  - ?" +
    " WHERE " + COLUMN_ANBAR_MALNR + " = ?";
    private static final String DELETE_TEHVIL_ITEM="DELETE FROM " + TABLE_TEHVILITEMS + " WHERE " + COLUMN_TEHVILITEMS_TEHVILNR + " = ?";
    private static final String GET_TEHVILNR="SELECT " + COLUMN_TEHVIL_NR + " FROM " + TABLE_TEHVIL + " WHERE " + COLUMN_TEHVIL_MEXARICNR + " = ?";

    private static final String GETRECEPT= "SELECT * FROM " + TABLE_RECEPT + " WHERE " + COLUMN_RECEPT_NR + " = ? LIMIT 1"; ;

    private static final String INSERT_EDIT_RECEPT="INSERT INTO " + TABLE_RECEPTITEMS + " (" +
            COLUMN_RECEPTITEMS_NR + ", " + COLUMN_RECEPTITEMS_MAL + ", " + COLUMN_RECEPTITEMS_VAHID +
            ", " + COLUMN_RECEPTITEMS_MIQDAR + ", " +  COLUMN_RECEPTITEMS_RECEPTNR  + " ) VALUES(?,?,?,?,?)";


    private static final String CONNECTION_STRING=DB_PATH+DB_NAME;
    public void open(){
        try {
            connection= DriverManager.getConnection(CONNECTION_STRING);
            insertHazirMal=connection.prepareStatement(INSERT_HAZIR_MAL);
            deleteHazirMal =connection.prepareStatement(DELETE_HAZIR_MAL);
            insertHazirMehsulAnbar=connection.prepareStatement(INSERT_HAIR_MEHSUL);
            deleteHazirMehsulAnbar=connection.prepareStatement(DELETE_HAZIR_MEHSUL);
            existsInAnbar= connection.prepareStatement(EXIST_IN_ANBAR);
            increaseAnbar=connection.prepareStatement(INCREASE_ANBAR);
            updateHazirAnbarAmount=connection.prepareStatement(UPDATE_AMOUNT_BY_ID);
            queryTehvilItems=connection.prepareStatement(QUERY_TEHVILITEMS);
            queryReceptItems = connection.prepareStatement(QUERY_RECEPT_ITEMS);
            insertEmeliyyatItem=connection.prepareStatement(INSERT_EMELIYYATITEM);
            insertEmeliyyat=connection.prepareStatement(INSERT_EMELIYYAT);
            updateProsesAnbar=connection.prepareStatement(UPDATE_PROSESANBAR);
            queryEmeliiyatItem=connection.prepareStatement(QUERY_EMELIYYATITEMLIST);
            insertTehvil=connection.prepareStatement(INSERT_TEHVIL);
            insertTehvilItems= connection.prepareStatement(INSERT_TEHVILITEMS);
            insertProsesAnbar = connection.prepareStatement(INSERT_PROSESANBAR);
            increaseProsesAnbar = connection.prepareStatement(INCREASE_PROSESANBAR);
            existsInProsesAnbar=connection.prepareStatement(EXISTS_IN_PROSESANBAR);
            insertRecept =connection.prepareStatement(INSERT_RECEPT);
            insertReceptItem=connection.prepareStatement(INSERT_RECEPT_ITEM);
            deleteRecept= connection.prepareStatement(DELETE_RECEPT);
            deleteReceptItem = connection.prepareStatement(DELETE_RECEPTITEM);
            updateProsesAnbarAmount =connection.prepareStatement(UPDATE_PROSESANBAR_AMOUNT_ID);
            deleteTehvil=connection.prepareStatement(DELETE_TEHVIL);
            decreaseProsesAnbar = connection.prepareStatement(DECREASE_PROSESANBAR);
            deleteTehvilItems=connection.prepareStatement(DELETE_TEHVIL_ITEM);
            getMexaricNr=connection.prepareStatement(GET_TEHVILNR);
            deleteEmeliyyat=connection.prepareStatement(DELETE_EMELIYYAT);
            updateHazirMehsulByName =  connection.prepareStatement(UPDATE_HAZIR_ANBAR_BY_NAME);
            updateHazirMehsulAnbar =connection.prepareStatement(UPDATE_HAZIR_MEHSUL_ANBAR);
            getRecept = connection.prepareStatement(GETRECEPT);
            deleteEditReceptitem=connection.prepareStatement(DELETE_EDIT_RECEPTITEM);
            insertEditReceptItems=connection.prepareStatement(INSERT_EDIT_RECEPT);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void close(){
        try {
            if (connection!=null){
                connection.close();
            }if (insertHazirMal!=null){
                insertHazirMal.close();
            }if (deleteHazirMal !=null){
                deleteHazirMal.close();
            }if (insertHazirMehsulAnbar!=null){
                insertHazirMehsulAnbar.close();
            }if (deleteHazirMehsulAnbar!=null){
                deleteHazirMehsulAnbar.close();
            }if (existsInAnbar!=null){
                existsInAnbar.close();
            } if (increaseAnbar!=null){
                increaseAnbar.close();
            }if (updateHazirAnbarAmount!=null){
                updateHazirAnbarAmount.close();
            }if (queryTehvilItems!=null){
                queryTehvilItems.close();
            }if (queryReceptItems!=null){
                queryReceptItems.close();
            }if (insertEmeliyyatItem!=null){
                insertEmeliyyatItem.close();
            }if (insertEmeliyyat!=null){
                insertEmeliyyat.close();
            }if (updateProsesAnbar!=null){
                updateProsesAnbar.close();
            }if (queryEmeliiyatItem!=null){
                queryEmeliiyatItem.close();
            }if (insertTehvil!=null){
                insertTehvil.close();
            }if (insertTehvilItems!=null){
                insertTehvilItems.close();
            }if (insertProsesAnbar!=null){
                insertProsesAnbar.close();
            }if (increaseProsesAnbar!=null){
                increaseProsesAnbar.close();
            }if (existsInProsesAnbar!=null){
                existsInProsesAnbar.close();
            }if (insertRecept!=null){
                insertRecept.close();
            }if (deleteRecept!=null){
                deleteRecept.close();
            }if (deleteReceptItem!=null){
                deleteReceptItem.close();
            }if (updateProsesAnbarAmount!=null){
                updateProsesAnbarAmount.close();
            }if (deleteTehvil!=null){
                deleteTehvil.close();
            }if (decreaseProsesAnbar!=null){
                decreaseProsesAnbar.close();
            }if (deleteTehvilItems!=null){
                deleteTehvilItems.close();
            }if (deleteEmeliyyat!=null){
                deleteEmeliyyat.close();
            }if (updateHazirMehsulAnbar!=null){
                updateHazirMehsulAnbar.close();
            }if (updateHazirMehsulByName!=null){
                updateHazirMehsulByName.close();
            }if (getRecept!=null){
                getRecept.close();
            }if (deleteEditReceptitem!=null){
                deleteEditReceptitem.close();
            }if (insertEditReceptItems!=null){
                insertEditReceptItems.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<HazirMal> queryHazirMal(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_HAZIR_MAL)){
            List<HazirMal> hazirMalList= new ArrayList<>();
            while (resultSet.next()){
                HazirMal hazirMal = new HazirMal();
                hazirMal.setId(resultSet.getInt(1));
                hazirMal.setName(resultSet.getString(2));
                hazirMalList.add(hazirMal);
            }
            return hazirMalList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int insertHazirMal(String name) {
        try {
            connection.setAutoCommit(false);
            insertHazirMal.setString(1,name);
            int nrAffectedRows = insertHazirMal.executeUpdate();
            if (nrAffectedRows == 1) {
                connection.commit();
                return 1;
            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
                return -1;
            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
                return 1;
            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());
                return -1;

            }
        }
    }
    public void deleteHazirMal(int mehsulId){
        try {
            deleteHazirMal.setInt(1,mehsulId);
            deleteHazirMal.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean doesExistAnbar(String malAdi){
        boolean exists=false;
        try {
            existsInAnbar.setString(1,malAdi);
            ResultSet resultSet = existsInAnbar.executeQuery();
            if (resultSet.next()){
                int count= resultSet.getInt(1);
                exists=count>0;
            }
        }catch (SQLException e){
            e.getMessage();

        }
        return exists;
    }
    public int insertHazirAnbar(String ad,double amount){
        try {
            connection.setAutoCommit(false);
            if (doesExistAnbar(ad)){
                increaseAnbar.setDouble(1, amount);
                increaseAnbar.setString(2,ad);
                int nrAffectedRows= increaseAnbar.executeUpdate();
                if (nrAffectedRows==1){
                    connection.commit();
                }
            }else{
                insertHazirMehsulAnbar.setString(1,ad);
                insertHazirMehsulAnbar.setString(2,"kg");
                insertHazirMehsulAnbar.setDouble(3,amount);
                int nrAffectedRows = insertHazirMehsulAnbar.executeUpdate();
                if (nrAffectedRows == 1) {
                    connection.commit();
                    return 1;
                }
                }

        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
                return -1;
            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
                return 1;
            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());
                return -1;

            }
        }
    }

    public List<HazirAnbar> queryHazirMehsulAnbar(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_HAZIR_MEHSUL)){
            List<HazirAnbar> hazirAnbarList= new ArrayList<>();
            while (resultSet.next()){
                HazirAnbar hazirAnbar = new HazirAnbar();
                hazirAnbar.setId(resultSet.getInt(1));
                hazirAnbar.setMal(resultSet.getString(2));
                hazirAnbar.setVahid(resultSet.getString(3));
                hazirAnbar.setMiqdar(resultSet.getDouble(4));
                hazirAnbarList.add(hazirAnbar);
            }
            return hazirAnbarList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void deleteHazirMehsul(int mehsulId){
        try {
            deleteHazirMehsulAnbar.setInt(1,mehsulId);
            deleteHazirMehsulAnbar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> queryUser(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_ALL_USER)){
            List<User> userList= new ArrayList<>();
            while (resultSet.next()){
                User user= new User();
                user.setName(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                userList.add(user);
            }
            return userList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void resetHazirAnbar(){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(RESET_AMOUNT_QUERY);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateHazirAnbarByAmount(int id,double amount){
        try {
            connection.setAutoCommit(false);
            updateHazirAnbarAmount.setDouble(1,amount);
            updateHazirAnbarAmount.setInt(2,id);
            int nrAffectedRows = updateHazirAnbarAmount.executeUpdate();
            if (nrAffectedRows == 1) {
                connection.commit();

            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");

            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);

            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());


            }
        }
    }

    public List<Tehvil> queryTehvil(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_TEHVIL)){
            List<Tehvil> tehvilList= new ArrayList<>();
            while (resultSet.next()){
                Tehvil tehvil = new Tehvil();
                tehvil.setNr(resultSet.getInt(1));
                tehvil.setDate(resultSet.getString(2));
                tehvil.setTehvilci(resultSet.getString(3));
                tehvil.setMexaricNr(resultSet.getInt(4));
                tehvilList.add(tehvil);
            }
            return tehvilList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<TehvilItems> queryTehvilItems(int nr){

        try {
            queryTehvilItems.setInt(1,nr);
            ResultSet resultSet= queryTehvilItems.executeQuery();
            List<TehvilItems> tehvilItemsList=  new ArrayList<>();
            while (resultSet.next()){
                TehvilItems tehvilItem = new TehvilItems();
                tehvilItem.setNr(resultSet.getInt(1));
                tehvilItem.setMalNr(resultSet.getInt(2));
                tehvilItem.setMal(resultSet.getString(3));
                tehvilItem.setCeki(resultSet.getDouble(4));
                tehvilItem.setTehvilNr(resultSet.getInt(5));
                tehvilItemsList.add(tehvilItem);
            }
            return tehvilItemsList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<Recept> queryRecept(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_RECEPT)){
            List<Recept> receptList= new ArrayList<>();
            while (resultSet.next()){
                Recept recept = new Recept();
                recept.setNr(resultSet.getInt(1));
                recept.setName(resultSet.getString(2));
                recept.setItki(resultSet.getDouble(3));
                recept.setQaliq(resultSet.getDouble(4));
                recept.setSonQaliq(resultSet.getDouble(5));
                receptList.add(recept);
            }
            return receptList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<ReceptItem> queryReceptItems(int nr){

        try {
            queryReceptItems.setInt(1,nr);
            ResultSet resultSet= queryReceptItems.executeQuery();
            List<ReceptItem> receptItemList=  new ArrayList<>();
            while (resultSet.next()){
                ReceptItem receptItem = new ReceptItem();
                receptItem.setNr(resultSet.getInt(1));
                receptItem.setVahid(resultSet.getString(3));
                receptItem.setMal(resultSet.getString(2));
                receptItem.setMiqdar(resultSet.getDouble(4));
                receptItem.setReceptNr(resultSet.getInt(5));
                receptItemList.add(receptItem);
            }
            return receptItemList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<VirtualAnbar> queryAnbar(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_ANBAR)){
            List<VirtualAnbar> virtualAnbarList = new ArrayList<>();
            while (resultSet.next()){
                VirtualAnbar virtualAnbar = new VirtualAnbar();
                virtualAnbar.setNr(resultSet.getInt(1));
                virtualAnbar.setMalNr(resultSet.getInt(2));
                virtualAnbar.setMal(resultSet.getString(3));
                virtualAnbar.setVahid(resultSet.getString(4));
                virtualAnbar.setCeki(resultSet.getDouble(5));
                virtualAnbarList.add(virtualAnbar);
            }
            return virtualAnbarList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<Emeliyyat> qeuryEmeliyyat(){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(QUERY_EMELIYYAT)){
            List<Emeliyyat> arrayList= new ArrayList<>();
            while (resultSet.next()){
                Emeliyyat emeliyyat = new Emeliyyat();
                emeliyyat.setNr(resultSet.getInt(1));
                emeliyyat.setDate(resultSet.getString(2));
                arrayList.add(emeliyyat);
            }
            return arrayList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int insertEmeliyyatItem(String mal,String vahid,double miqdar,int receptNr,int emeliyyatNr) {
        try {
            connection.setAutoCommit(false);
            insertEmeliyyatItem.setString(1,mal);
            insertEmeliyyatItem.setString(2,vahid);
            insertEmeliyyatItem.setDouble(3,miqdar);
            insertEmeliyyatItem.setInt(4,receptNr);
            insertEmeliyyatItem.setInt(5,emeliyyatNr);
            int nrAffectedRows = insertEmeliyyatItem.executeUpdate();
            if (nrAffectedRows == 1) {
                connection.commit();
                return 1;
            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
                return -1;
            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
                return 1;
            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());
                return -1;

            }
        }
    }
    public int insertEmeliyyat(String date){
        int generetedKey=-1;
        try {
            connection.setAutoCommit(false);
            insertEmeliyyat.setString(1,date);
            int nrAffectedRows= insertEmeliyyat.executeUpdate();
            if (nrAffectedRows==1){
                connection.commit();
                ResultSet generatedKeys = insertEmeliyyat.getGeneratedKeys();
                if (generatedKeys.next()){
                    generetedKey=generatedKeys.getInt(1);
                }
            }
        }catch (Exception e){
            System.out.println("error "+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to rollback");
            }
        }finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couldn't set auto commit to true " +g.getMessage());
            }
        }
        return generetedKey;
    }
    public void updateProsesAnbar(double miqdar,int mehsulId){
        try {
            updateProsesAnbar.setDouble(1,miqdar);
            updateProsesAnbar.setInt(2,mehsulId);
            updateProsesAnbar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateHazirMehsulByname(double miqdar, String mehsul){
        try {
            updateHazirMehsulAnbar.setDouble(1,miqdar);
            updateHazirMehsulAnbar.setString(2,mehsul);
            updateHazirMehsulAnbar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void increaseazirMehsulByname(double miqdar, String mehsul){
        try {
            updateHazirMehsulByName.setDouble(1,miqdar);
            updateHazirMehsulByName.setString(2,mehsul);
            updateHazirMehsulByName.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<EmeliyyatItem> queryEmeliyyatItem(int nr){

        try {
            queryEmeliiyatItem.setInt(1,nr);
            ResultSet resultSet= queryEmeliiyatItem.executeQuery();
            List<EmeliyyatItem> emeliyyatItem=  new ArrayList<>();
            while (resultSet.next()){
                EmeliyyatItem e= new EmeliyyatItem();
                e.setNr(resultSet.getInt(1));
                e.setMal(resultSet.getString(2));
                e.setVahid(resultSet.getString(3));
                e.setMiqdar(resultSet.getDouble(4));
                e.setReceptNr(resultSet.getInt(5));
                e.setEmeliyyatNr(resultSet.getInt(6));
                emeliyyatItem.add(e);
            }
            return emeliyyatItem;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int insertTehvil(String qebulchu,int nr){
        int generetedKey=-1;
        try {
            connection.setAutoCommit(false);
            LocalDate today = LocalDate.now();
            insertTehvil.setString(1, String.valueOf(today));
            insertTehvil.setString(2,qebulchu);
            insertTehvil.setInt(3,nr);
            int nrAffectedRows= insertTehvil.executeUpdate();
            if (nrAffectedRows==1){
                connection.commit();
                ResultSet generatedKeys = insertTehvil.getGeneratedKeys();
                if (generatedKeys.next()){
                    generetedKey=generatedKeys.getInt(1);
                }
            }
        }catch (Exception e){
            System.out.println("error "+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to rollback");
            }
        }finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couldn't set auto commit to true " +g.getMessage());
            }
        }
        return generetedKey;
    }
    public void insertTehvilitems(int malNr,String malAdi,String vahid,double ceki,int tehvilNr){

        try {
            connection.setAutoCommit(false);
            insertTehvilItems.setInt(1,malNr);
            insertTehvilItems.setString(2,malAdi);
            insertTehvilItems.setDouble(3,ceki);
            insertTehvilItems.setDouble(4,tehvilNr);
            int nrAffectedRows= insertTehvilItems.executeUpdate();
            if (nrAffectedRows==1){
                connection.commit();
                insertProsesAnbar(malNr,malAdi,vahid,ceki);
            }
        }catch (Exception e){
            System.out.println("error "+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to rollback");
            }
        }finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couldn't set auto commit to true " +g.getMessage());
            }
        }

    }
    private void insertProsesAnbar(int malNr,String mal,String vahid,double ceki){
        try {
            connection.setAutoCommit(false);
            if (doesExistProsesAnbar(malNr)){
                increaseProsesAnbar.setDouble(1, ceki);
                increaseProsesAnbar.setInt(2,malNr);
                int nrAffectedRows= increaseProsesAnbar.executeUpdate();
                if (nrAffectedRows==1){
                    connection.commit();
                }
            }else{
                insertProsesAnbar.setInt(1,malNr);
                insertProsesAnbar.setString(2,mal);
                insertProsesAnbar.setString(3,vahid);
                insertProsesAnbar.setDouble(4,ceki);
                int nrAffectedRows = insertProsesAnbar.executeUpdate();
                if (nrAffectedRows == 1) {
                    connection.commit();

                }
            }

        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");

            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);

            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());


            }
        }
    }
    private boolean doesExistProsesAnbar(int malNr){
        boolean exists=false;
        try {
            existsInProsesAnbar.setInt(1,malNr);
            ResultSet resultSet = existsInProsesAnbar.executeQuery();
            if (resultSet.next()){
                int count= resultSet.getInt(1);
                exists=count>0;
            }
        }catch (SQLException e){
            e.getMessage();

        }
        return exists;
    }
    public int insertRecept(String name,double itki,double qaliq,double sonQaliq){
        int generetedKey=-1;
        try {
            connection.setAutoCommit(false);
            insertRecept.setString(1,name);
            insertRecept.setDouble(2,itki);
            insertRecept.setDouble(3,qaliq);
            insertRecept.setDouble(4,sonQaliq);
            int nrAffectedRows= insertRecept.executeUpdate();
            if (nrAffectedRows==1){
                connection.commit();
                ResultSet generatedKeys = insertRecept.getGeneratedKeys();
                if (generatedKeys.next()){
                    generetedKey=generatedKeys.getInt(1);
                }
            }
        }catch (Exception e){
            System.out.println("error "+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to rollback");
            }
        }finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couldn't set auto commit to true " +g.getMessage());
            }
        }
        return generetedKey;
    }

    public void insertReceptItem(String mal,String vahid,double miqdar,int receptNr){
        try {
            connection.setAutoCommit(false);
            insertReceptItem.setString(1,mal);
            insertReceptItem.setString(2,vahid);
            insertReceptItem.setDouble(3,miqdar);
            insertReceptItem.setInt(4,receptNr);
            int nrAffectedRows= insertReceptItem.executeUpdate();
            if (nrAffectedRows==1){
                connection.commit();
            }
        }catch (Exception e){
            System.out.println("error "+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to rollback");
            }
        }finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couldn't set auto commit to true " +g.getMessage());
            }
        }
    }

    public void deleteRecept(int receptNr){
        try {
            deleteRecept.setInt(1,receptNr);
            deleteReceptItem.setInt(1,receptNr);
            deleteReceptItem.executeUpdate();
            deleteRecept.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteEditRecept(int receptNr){
        try {
           deleteEditReceptitem.setInt(1,receptNr);
           deleteEditReceptitem.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void resetProsesAnbar(){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(RESET_AMOUNT_PROSESANBAR);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertEditReceptItem(int nr,String mal,String vahid,double miqdar,int receptNr){
        try {
            insertEditReceptItems.setInt(1,nr);
            insertEditReceptItems.setString(2,mal);
            insertEditReceptItems.setString(3,vahid);
            insertEditReceptItems.setDouble(4,miqdar);
            insertEditReceptItems.setInt(5,receptNr);
            insertEditReceptItems.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateProsesAnbarByAmount(int id,double amount){
        try {
            connection.setAutoCommit(false);
            updateProsesAnbarAmount.setDouble(1,amount);
            updateProsesAnbarAmount.setInt(2,id);
            int nrAffectedRows = updateProsesAnbarAmount.executeUpdate();
            if (nrAffectedRows == 1) {
                connection.commit();

            }
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");

            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);

            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());


            }
        }
    }

    public void deleteTehvil(int nr){
        try {
            deleteTehvil.setInt(1,nr);
            int nrAffectedRows = deleteTehvil.executeUpdate();
            if (nrAffectedRows == 1) {
                List<TehvilItems> tehvilItems= queryTehvilItems(nr);
                for (TehvilItems items:tehvilItems){

                    decreaseProsesAnbar(items.getMalNr(),items.getCeki());
                }
                deleteTehvilItems.setInt(1,nr);
                deleteTehvilItems.executeUpdate();
            } else {
                System.out.println("Failed to delete sale.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void decreaseProsesAnbar(int malNr,double ceki) {
        try {
            connection.setAutoCommit(false);
            decreaseProsesAnbar.setDouble(1, ceki);
            decreaseProsesAnbar.setInt(2, malNr);
            int nrAffectedRows = decreaseProsesAnbar.executeUpdate();
            if (nrAffectedRows == 1) {
                connection.commit();
            }

        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            } catch (SQLException f) {
                System.out.println("failed to rollback");
            }
        } finally {
            try {
                System.out.println("committing the changes  and setting to true");
                connection.setAutoCommit(true);
            } catch (SQLException g) {
                System.out.println("couldn't set auto commit to true " + g.getMessage());
            }
        }
    }

    public int retrieveMexaricNr(int mexaricNrValue)  {

        try {
            getMexaricNr.setInt(1, mexaricNrValue);
            ResultSet resultSet = getMexaricNr.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COLUMN_TEHVIL_NR);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return -1;
    }

    public void deleteEmeliyyat(int id){
        try {
            deleteEmeliyyat.setInt(1,id);
            deleteEmeliyyat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Recept getRecept(int nr){
        try {
            getRecept.setInt(1,nr);
            ResultSet resultSet= getRecept.executeQuery();
            if (resultSet.next()){
               Recept recept = new Recept();
               recept.setNr(resultSet.getInt(1));
               recept.setName(resultSet.getString(2));
               recept.setItki(resultSet.getDouble(3));
               recept.setQaliq(resultSet.getDouble(4));
               recept.setSonQaliq(resultSet.getDouble(5));
               return recept;
            }
            return null;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }






}
