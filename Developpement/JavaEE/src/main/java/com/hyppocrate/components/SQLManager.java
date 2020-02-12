package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
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



 //Si le temps
    public int createDMP(int idDoctor, int numSecu) {
        return 0;
    }



    public boolean publishActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }
    public boolean updateEtPublierBrouillon(int draftId, String title, String type, String description, File file) {
        return false;
    }

    public boolean updateBrouillon(int draftId, String title, String type, String description) {
        return false;
    }


    /*public boolean publishBrouillonActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }
    Fonction en double
    */



    private HashMap<String, String> addSortItem(String key, String print){
        HashMap<String, String> hashMap=new HashMap<String, String>();
        hashMap.put("sortColumnName", key);
        hashMap.put("printableName", print);
        return hashMap;
    }

    public List<HashMap<String, String>> printSortDmpItems() {
        ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();

        list.add(addSortItem("Name", "Nom"));
        list.add(addSortItem("FirstName", "Prénom"));
        list.add(addSortItem("BirthDate", "Date de naissance"));
        return list;
    }


    public List<HashMap<String, Object>> printDMP( int staffId,String search, String sortitem, int paginationNumber,
                                                   int paginationLength) throws SQLException {

        List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
        if(search==null) {
            search="";
        }if(sortitem==null) {
            sortitem="Name";
        }
        search="%"+search+"%";
        search=search.toUpperCase();
        String reqString="SELECT NAME\r\n" +
                "    ,\r\n" +
                "    FirstName,\r\n" +
                "    BirthDate,\r\n" +
                "    UUID\r\n" +
                "FROM\r\n" +
                "    DMP,\r\n" +
                "    demoinformations\r\n" +
                "WHERE (UPPER(NAME) LIKE ?\r\n" +
                "    OR UPPER(FirstName) LIKE ? \r\n" +
                "    OR UPPER(BirthDate) LIKE ?)\r\n" +
                "    AND demoinformations.NumSecu=DMP.DemoInformations_NumSecu\r\n" +
                "ORDER BY ?\r\n" +
                "LIMIT ?; ";
        PreparedStatement pStatement=con.prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);
        pStatement.setString(4, sortitem);

        pStatement.setInt(5, paginationLength*paginationNumber);
        System.out.println(pStatement);
        ResultSet rSet=pStatement.executeQuery();
        int count=0;

        HashMap<String, Object> hashMap;
        while (rSet.next()) {
            count++;
            if(!(count>=(paginationLength*(paginationNumber-1)))){
                continue;
            }
            hashMap= new HashMap<String, Object>();
            hashMap.put("patientId", rSet.getInt("UUID"));
            hashMap.put("firstname", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("birthdayDate ", rSet.getString("BirthDate"));
            list.add(hashMap);

        }

        return list;
    }


   

    /*//Jsp à quoi sa sert à oublier
    public HashMap<String, Object> getDocument(int draftId) {
        return null;
    }
*/

    public boolean deleteBrouillon(int draftId) throws SQLException {
        /*INSERT INTO `medicaldocument` (`idMedicalDocument`, `DocumentName`, `IsADraft`, `Date`, `DocumentLink`, `DocumentType_idDocumentType`, `ChampsObligatoire_Name`, `Stream_Extension`, `Acte_idActe`) VALUES ('', 'Icic', '1', '2020-02-05', '//document//link', '1', 'Symptômes', '1', '1'); */
        String verifyDraftString="SELECT idActe FROM acte WHERE idActe=? AND IsADraft=1 ";
        PreparedStatement pStatement=con.prepareStatement(verifyDraftString);
        pStatement.setInt(1, draftId);
        ResultSet rSet=pStatement.executeQuery();
        if(rSet.next()) {
            String delString="DELETE FROM acte WHERE idActe=? AND IsADraft=1;";
            pStatement=con.prepareStatement(delString);
            pStatement.setInt(1, draftId);
            int i=pStatement.executeUpdate();
            return true;
        }
        return false;
    }
/*
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
    */
 

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



    /*public List<HashMap<String, Object>> getMedicalDocumentType() {
        return null;
    }*/
/*
    public List<HashMap<String, Object>> printStaff(int patientId, String search, String sortItem, int paginationNumber, int paginationLength) {
        return null;
    }*/

    public List<HashMap<String, Object>> printDraft(int patientId, String search, String sortItems,
                                                    int paginationNumber, int paginationLength) throws SQLException {
        return printActe(patientId, search, sortItems, paginationNumber, paginationLength, true);
    }

    public ArrayList<HashMap<String, String>> printSortActeItems() throws SQLException {
        ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();

        list.add(addSortItem("documenttype.idDocumentType", "Type d'acte"));
        list.add(addSortItem("Nom", "Nom"));
        list.add(addSortItem("DateDebut", "Date de creation"));


        return list;
    }

    private List<HashMap<String, Object>> printActe(int patientId, String search, String sortItem, int paginationNumber,
                                                    int paginationLength,boolean draft) throws SQLException {
        List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();

        if(search==null) {
            search="";
        }
        if(sortItem==null) {
            sortItem="DateDebut";
        }
        search=search.toUpperCase();
        search="%"+search+"%";

        String reqString="SELECT\r\n" +
                "    `idActe`,\r\n" +
                "    `Nom`,\r\n" +
                "    `Responsable`,\r\n" +
                "    dmp.UUID,\r\n" +
                "    DateDebut,\r\n" +
                "    Description,\r\n" +
                "    DocumentLink,\r\n" +
                "    documenttype.Name,\r\n" +
                "    unit.idHospital\r\n" +
                "FROM\r\n" +
                "    `acte`,\r\n" +
                "    dmp,\r\n" +
                "    staffmember,\r\n" +
                "    unit,\r\n" +
                "    documenttype\r\n" +
                "WHERE\r\n" +
                "    acte.MedicalFolder_idFolder = dmp.UUID \r\n" +
                "    AND acte.Responsable = staffmember.idStaffMember \r\n" +
                "    AND unit.idHospital = staffmember.Hospital_idHospital \r\n"+
                "    AND IsADraft = ? \r\n"+
                "    AND documenttype.idDocumentType = acte.DocumentType_idDocumentType\r\n" +
                "    AND dmp.UUID=?\r\n"
                + "	AND (UPPER(Nom) LIKE ? OR UPPER(documenttype.Name LIKE) ?) \r\n" +
                "ORDER BY\r\n" +
                "    ?\r\n "+
                "LIMIT ?;";
        PreparedStatement pStatement=con.prepareStatement(reqString);
        pStatement.setInt(1, draft?1:0);

        pStatement.setInt(2, patientId);
        pStatement.setString(3, search);
        pStatement.setString(4, search);
        pStatement.setString(5, sortItem);

        pStatement.setInt(6, paginationLength*paginationNumber);



        System.out.println(pStatement);
        ResultSet rSet=pStatement.executeQuery();
        int count=0;

        HashMap<String, Object> hashMap;
        while (rSet.next()) {
            count++;
            if(!(count>=(paginationLength*(paginationNumber-1)))){
                continue;
            }
            hashMap= new HashMap<String, Object>();
            hashMap.put("acteId", rSet.getInt("idActe"));
            hashMap.put("nodeId", rSet.getString("idHospital"));
            hashMap.put("patientId", rSet.getString("UUID"));
            hashMap.put("medecinId ", rSet.getString("Responsable"));
            hashMap.put("description ", rSet.getString("Description"));
            hashMap.put("date", rSet.getTimestamp("DateDebut"));
            hashMap.put("link", rSet.getString("DocumentLink"));
            hashMap.put("type", rSet.getString("Name"));
            hashMap.put("title", rSet.getString("Nom"));
            list.add(hashMap);

        }

        return list;
    }
    public List<HashMap<String, Object>> printActe(int patientId, String search, String sortItem, int paginationNumber,
                                                   int paginationLength) throws SQLException {

        return printActe(patientId, search, sortItem, paginationNumber, paginationLength, false);
    }


   /* public HashMap<String, Object> getActe(int documentId) {
        return null;
    }*/

   /*Uniquement si le temps
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

    */

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
    public List<HashMap<String, Object>> getHospitalArchitecture() throws SQLException {

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
                    List<HashMap<String, Object>> x=getNodeChild(rSet.getInt("idHospital"));
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
            List<HashMap<String, Object>> x=getNodeChild(rSet.getInt("idHospital"));
            if(x.size()>0)
                hashMap.put(name, x);
            hasmaList.add(hashMap);
        }

        return hasmaList;

    }

    public List<HashMap<String, Object>> getPersonnalForPatient(int patientId) throws SQLException {

        List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> hashMap;
        String idsActe="SELECT\r\n" +
                "    staffmember.idStaffMember,\r\n" +
                "    enumstafftype.JobName,\r\n" +
                "    demoinformations.Name,\r\n" +
                "    demoinformations.FirstName\r\n" +
                "FROM\r\n" +
                "    staffmember,\r\n" +
                "    acte,\r\n" +
                "    demoinformations,\r\n" +
                "    enumstafftype\r\n" +
                "WHERE\r\n" +
                "    acte.Responsable = staffmember.idStaffMember AND demoinformations.NumSecu = staffmember.DemoInformations_NumSecu \r\n" +
                "AND acte.MedicalFolder_idFolder = ? \r\n" +
                "AND enumstafftype.idEnumStaffType = staffmember.EnumStaffType_idEnumStaffType;";
        PreparedStatement ps=con.prepareStatement(idsActe);
        ps.setInt(1, patientId);
        ResultSet rSet=ps.executeQuery();
        while (rSet.next()) {
            hashMap=new HashMap<String, Object>();
            hashMap.put("id", rSet.getInt("idStaffMember"));
            hashMap.put("name", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("type", rSet.getString("JobName"));
            list.add(hashMap);

        }
        return list;

    }

    /*public boolean deletePersonnalForPatient(int nodeId, int staffId, int patientId) {
        return false;
    }*/

    /*public List<HashMap<String, Object>> getPersonnalType() {
        return null;
    }*/
/*
    public boolean createProfileAndSendEmail(int typeId, String name, String lastName, int birthDate, int phoneNumber, int phoneLandline, String email) {
        String login=name+lastName;
        String profile="INSERT INTO applicationuser VALUES ('?', '?', '?');";
        PreparedStatement ps=con.prepareStatement(profile);
        ps.setString(1,login);
        ps.setString(2,login);
        ps.setString();
        return false;
    }*/

    private int getIdUser(String login) throws SQLException {
        String sql="SELECT idStaffMember  FROM staffmember WHERE Login=?;";
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1,login);
        ResultSet rs=ps.executeQuery();
        return rs.getInt(0);
    }
/*
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
    */


}

