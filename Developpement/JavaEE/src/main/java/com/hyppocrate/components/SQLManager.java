package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;
import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SQLManager implements ISingleton {

    private Connection con;
    // singleton pattern
    private SQLManager() throws ClassNotFoundException, SQLException {

            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/mydb?characterEncoding=latin1","root","");
//here sonoo is database name, root is username and password


    }


    private static SQLManager INSTANCE = null;

    public static SQLManager getInstance() throws SQLException, ClassNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new SQLManager();
        }
        return INSTANCE;
    }

    public HashMap<String, Object> getStaffType(String login) {
        return null;
    }

    public String getString(String appelationString, String language) throws IllegalAccessException, SQLException {
        String result="";
        PreparedStatement ps = con.prepareStatement("SELECT StringContent FROM string WHERE idString =? AND Langue_idLangue = ?");
        ps.setString(1, appelationString);
        ps.setString(2, language);
        ResultSet rs=ps.executeQuery();

        while(rs.next()) {
            result+=rs.getString(1);
        }
        System.out.println(result);
        return result;

    }

    public int createDMP(int idDoctor, int numSecu) {
        return 0;
    }

    public void deleteDMP(int UUID) throws SQLException {

    }

    public boolean publishActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }

    /*public void createActe(int idFolder, String idActe, String nom, Date debut, Date fin, int idStaffMember, int prix) {

        String sqlString="INSERT INTO acte VALUES (?, '?', '?', '?', '?', ?, ?)";
        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, idFolder);

    }*/

    public boolean publishBrouillonActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }

    public List<String> printSortItems() {
        return null;
    }

    public java.util.List<HashMap<String, Object>> getBrouillon(int patientId, String search, String sortItems, int paginationNumber, int paginationLength) {
        return null;
    }

    public HashMap<String, Object> getDocument(int draftId) {
        return null;
    }

    public boolean updateEtPublierBrouillon(int draftId, String title, String type, String description) {
        return false;
    }

    public boolean updateBrouillon(int draftId, String title, String type, String description) {
        return false;
    }

    public boolean deleteBrouillon(int draftId) {

        return false;
    }

    public void publierMedicalDocument(int idActe, int idMedicalDocument, String name, int isADraft, Date date, String link, int type, String champsObligatoire, int extension) {
        return;
    }

    public List<HashMap<String, Object>> getOrdonnace(int idActe, int idMedicalDocument, String Url) {
        return null;
    }

    public List<String> getCompteRendu(int idActe, int idMedicalDocument, String Url) {
        return null;
    }

    public boolean writeOrdonnace(List<HashMap<String, Object>> idActe, int isAdraft) {
        return false;
    }

    public boolean writeCompteRendu(String importante, String text, String idActe, int isAdraft) {
        return false;
    }

    public boolean findMDP(String login) {
        return false;
    }

    public HashMap<String, Object> connect(String login, String Password) throws SQLException {
        HashMap<String, Object> result=new HashMap<String, Object>();
        String getPasswordString="SELECT PassWord From ApplicationUser Where Login=?;";
        PreparedStatement pStatement=con.prepareStatement(getPasswordString);
        pStatement.setString(1, login);
        ResultSet rSet=pStatement.executeQuery();
        String userPassWordString="";
        if(rSet.next()) {
            userPassWordString=rSet.getNString(1);
        }
        System.out.println(userPassWordString);
        if(userPassWordString.equals(Password)) {
            String getPeopleString="SELECT * FROM StaffMember WHERE Login=?";
            pStatement=con.prepareStatement(getPeopleString);
            pStatement.setString(1, login);
            ResultSet resultSet=pStatement.executeQuery();
            System.out.println(resultSet);
            resultSet.next();
            result.put("type",resultSet.getString("EnumStaffType_idEnumStaffType"));
            result.put("id",resultSet.getInt("idStaffMember"));
            getPeopleString="SELECT Name,FirstName FROM demoinformations WHERE NumSecu=?";
            pStatement=con.prepareStatement(getPeopleString);
            pStatement.setInt(1, resultSet.getInt("DemoInformations_NumSecu"));
            resultSet=pStatement.executeQuery();

            resultSet.next();
            result.put("name",resultSet.getString("FirstName"));
            result.put("lastName",resultSet.getString("Name"));

            return result;
        }
        return null;
    }

    public List<HashMap<String, Object>> printDMP(String search, String sortitem, int paginationNumber, int paginationLength) {
        return null;
    }

    public List<HashMap<String, Object>> getMedicalDocumentType() {
        return null;
    }

    public List<HashMap<String, Object>> printStaff(int patientId, String search, String sortItem, int paginationNumber, int paginationLength) {
        return null;
    }

    public List<HashMap<String, Object>> printActe(int patientId, String search, String sortItem, int paginationNumber, int paginationLength) {
        return null;
    }

    public HashMap<String, Object> getActe(int documentId) {
        return null;
    }

    public void createProfile(int idStaffMember, String skills, int idStaffType, int idUser, int numSecu, String IBAN, String BIC, int idHospital, int nbBureau) {
        return;
    }

    public boolean modifyDemoStaff(int idPeople, int profileType, String name, String lastName, Date birthday, String Adress, String city, String family) {
        String demoCreate="INSERT INTO demoinformations\n" +
                "VALUES ('numSecu', 'lastName', 'firstName', 'birthday'," +
                " 'Adress', 'sexe', 'profession', 'family', 'contact', 'phoneNumber', 'idNat', 'idCity', 'poids', 'taille');";

        return false;
    }

    public boolean modifyDemoPatient(int idPeople, String name, String lastName, Date birthday, String Adress, String city, String family) {
        return false;
    }

    public boolean modifyContactStaff(int idPeople, String phoneNumber, String phoneLandLine, String email) {
        return false;
    }

    public boolean modifyContactPatient(int idPeople, String phoneNumber, String phoneLandLine, String email) {
        return false;
    }

    public List<HashMap<String, Object>> getAllStaffMembers() {
        return null;
    }

    public List<HashMap<String, Object>> getAllStaffMembersFromUnit(int UnitId) {
        return null;
    }

    public List getStaffMembers(int idStaffType) {
        return null;
    }

    public HashMap<String, Object> getStaffMember(int idStaffMember) {
        return null;
    }

    public HashMap<String, Object> updatePassword(int idStaffMember, String previousPwd, String newPwd) {
        return null;
    }

    public void affecterPatient(int nodeId, int staffId, int patientId) {
        return;
    }

    public boolean affecterPersonnel(int personalId, int hopitalUnitId, boolean leadUnit) {
        return false;
    }

    public HashMap<String, Object> getHospitalArchitecture(int personalId) {
        return null;
    }

    public boolean unaffectPersonnel(int personalId, int hopitalUnitId) {
        return false;
    }

    public boolean unaffectPersonnelLeader(int personalId, int hopitalUnitId, int leadId) {
        return false;
    }

    public boolean createUnit(String name, int idRattache, int idStaffMember) throws SQLException {
        int type=0;

        if(idRattache!=-1) {
            String result="";
            PreparedStatement ps = con.prepareStatement("SELECT Type FROM Unit WHERE idHospital =?;");
            ps.setLong(1, idRattache);
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                type=rs.getInt(1)+1;
            }else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);

        }

        String sqlRequest="INSERT INTO Unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
        PreparedStatement ps = con.prepareStatement(sqlRequest);
        ps.setString(1, name);
        ps.setLong(2, type);
        ps.setLong(3, idStaffMember);
        ps.setLong(4, idRattache);
        System.out.println(ps);
        int rs=ps.executeUpdate();

        return rs!=0;
    }

    /* j'ai pas compris private
    public boolean createPatient(int staffId, Contact contact, Private private) {
        return null;
    }*/
    public List<HashMap<String, Object>> getNodeChild() throws SQLException {

        return getNodeChild(-1);
    }

    private String IntToTypeStringConverter(int Type) {
        switch (Type) {
            case 1:

                return "hospital";
            case 2:

                return "pole";
            case 3:

                return "sector";

            default:
                return "Unknow";
        }
    }

    private boolean _firstAboParcour=true;



    public List<HashMap<String, Object>> getArchitecture(int nodeId) throws SQLException {
        String sqlString = "SELECT ratache From Unit WHERE idHospital=?";
        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, nodeId);
        ResultSet rSet = ps.executeQuery();
        rSet.next();
        int parent=rSet.getInt("ratache");
        System.out.println(parent);
        if(parent!=-1) {
            return getNodeChild(parent);
        }
        return null;
    }
    public List<HashMap<String, Object>> getNodeChild(int nodeId) throws SQLException {

        List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();
        String sqlString = null;
        if (nodeId < 0) {
            sqlString = "SELECT idHospital, Name, Type From Unit WHERE ratache=?";
            nodeId=17;
        } else {
            if(_firstAboParcour) {
                sqlString = "SELECT idHospital, Name,Type From Unit WHERE idHospital=?";
                PreparedStatement ps = con.prepareStatement(sqlString);
                ps.setInt(1, nodeId);
                ResultSet rSet = ps.executeQuery();

                _firstAboParcour=false;
                if (rSet.next()) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    String name=IntToTypeStringConverter(rSet.getInt("Type"));
                    hashMap.put(name+"Id", rSet.getInt("idHospital"));
                    hashMap.put(name+"Name", rSet.getString("Name"));
                    var x=getNodeChild(rSet.getInt("idHospital"));
                    String name2=IntToTypeStringConverter(rSet.getInt("Type")+1);
                    if(x.size()>0)
                        hashMap.put(name2, x);
                    hasmaList.add(hashMap);
                    _firstAboParcour=true;
                    return hasmaList;
                }
            }
            sqlString = "SELECT idHospital, Name, Type From Unit WHERE ratache=?";


        }

        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, nodeId);
        ResultSet rSet = ps.executeQuery();

        while (rSet.next()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            String name=IntToTypeStringConverter(rSet.getInt("Type")+1);

            hashMap.put("nameId", rSet.getInt("idHospital"));
            hashMap.put("nameName", rSet.getString("Name"));

            System.out.println(rSet.getInt("idHospital"));
            var x=getNodeChild(rSet.getInt("idHospital"));
            if(x.size()>0)
                hashMap.put(name, x);
            hasmaList.add(hashMap);
        }

        return hasmaList;

    }

    public List<HashMap<String, Object>> getPersonnalForPatient(int patientId) {
        return null;
    }

    public boolean deletePersonnalForPatient(int nodeId, int staffId, int patientId) {
        return false;
    }

    public List<HashMap<String, Object>> getPersonnalType() {
        return null;
    }

    public boolean createProfileAndSendEmail(int typeId, String name, String lastName, int birthDate, int phoneNumber, int phoneLandline, String email) {
        /*String login=name+lastName;
        String profile="INSERT INTO applicationuser VALUES ('?', '?', '?');";
        PreparedStatement ps=con.prepareStatement(profile);
        ps.setString(1,login);
        ps.setString(2,login);
        ps.setString();/*
        return false;
    }

    private int getIdUser(String login) throws SQLException {
        String sql="SELECT idStaffMember  FROM staffmember WHERE Login=?;";
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1,login);
        ResultSet rs=ps.executeQuery();
        return rs.getInt(0);
    }

    private boolean patientExist(int numSecu) {
        return false;
    }

    private boolean isValidate(int idDemandeExamen) {
        return false;
    }

    private List getExamens(int UUID) {
        return null;
    }

    private List getActes(int UUID) {
        return null;
    }

    private String getName(int numSecu) {
        return null;
    }

    private void deleteMedicalDocument(String idActe) {
        return;
    }

    private boolean modifyDemographicInfo(int idPeople, String name, String lastName, Date birthday, String Adress, String city, String family, boolean isPatient) {
        return false;
    }

    private boolean modifyContactPatient(int idPeople, String phoneNumber, String phoneLandLine, String email, boolean isPatient) {
        return false;
    }

}

