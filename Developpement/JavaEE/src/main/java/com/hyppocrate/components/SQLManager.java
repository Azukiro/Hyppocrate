package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.naming.Context;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.*;
import java.util.Date;

// TODO: 16/01/2020
public class SQLManager implements ISingleton {

    private String direcoryPatientString="PatientDirectory";
    //private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "hyppocrytos";
    private static final String password = "hyppocrytos-SQL2019"; // le mien c'était le mot de passe de mon compte windows
    private static final String serverName = "localhost";
    private static final String database = "hyppocrate";

    //https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2839563#2839563
    Context context;
    DataSource dataSource;
    Connection  con;
    
    // singleton pattern
    private SQLManager() throws SQLException {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setServerName(serverName);
            dataSource.setDatabaseName(database);
            dataSource.setServerTimezone("UTC");
            System.out.println("Tentative de connexion...");
            con = dataSource.getConnection();
            Statement stmt = con.createStatement(); // C'est mieux les PreparedStatement
            stmt.close();
            System.out.println("Connection = " + con);

        } catch (SQLException e) {
            System.err.println("[ERROR]");
            System.err.println("MySQL Connexion exception: " + e.toString());

            //throw new IllegalStateException();
        }
    }

    private static SQLManager INSTANCE = null;

    public static SQLManager getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new SQLManager();
        }
        return INSTANCE;
    }




    /*Acte*/
    private boolean publishActe(int staffId, int patientId, String title, int type, String description, String file, boolean isDraft) throws SQLException {
        //String link=CreateDynamicLink(file,patientId,title);
        String searchNewID = "INSERT INTO StringacteString (StringMedicalFolder_idFolderString, StringNomString, StringDateDebutString, StringDateFinString, StringResponsableString, StringPrixString, StringDocumentLinkString, StringIsADraftString, StringDocumentType_idDocumentTypeString, StringDescriptionString, StringidActeString)\r\n"+
                "VALUES (?, ?, ?, NULL, ?, NULL, ?, ?, ?, ?, NULL);";
        PreparedStatement s = con.prepareStatement(searchNewID);
        s.setInt(1, patientId);
        s.setString(2, title);


        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());


        s.setDate(3,  date);
        s.setInt(4, staffId);
        s.setString(5, file);
        s.setInt(6, isDraft?1:0);
        s.setInt(7, type);
        s.setString(8, description);
        System.out.println(s);
        return !s.execute();
    }

    public boolean publishActe(int staffId, int patientId, String title, int type, String description, String file) throws SQLException {

        return publishActe(staffId, patientId, title, type, description, file,false);
    }



    /*Draft*/
    public boolean CreateDraftt(int staffId, int patientId, String title, int type, String description, String file) throws SQLException {

        return publishActe(staffId, patientId, title, type, description, file, true);
    }

    public boolean updateEtPublierBrouillon(int patientId,int draftId, String title,  String description,String file) throws SQLException, IOException {
        updateBrouillon(patientId,draftId, title, description, file);
        String update= "UPDATE StringacteString SET IsADraft=0 WHERE StringacteString.StringidActeString = ?;";

        PreparedStatement pStatement = con.prepareStatement(update);

        pStatement.setInt(4, draftId);

        return !pStatement.execute();
    }

    public boolean updateBrouillon(int patientId,int draftId, String title, String description,String file) throws SQLException {

        String update= "UPDATE StringacteString SET ";
        if(title!=null) {
            update+="StringNomString = ?";
            if(file!=null || description!=null) {
                update+=", ";
            }
        }
        if(description!=null) {
            update+="StringDescriptionString = ?";
            if(file!=null ) {
                update+=", ";
            }
        }
        String linkString="";
        if(file!=null) {
            update+="StringDocumentLinkString = ?";

        }
        update+=" WHERE StringacteString.StringidActeString = ?;";

        PreparedStatement pStatement = con.prepareStatement(update);
        if(title!=null) {
            pStatement.setString(1, title);
            if(description!=null) {
                pStatement.setString(2, description);
                if(file!=null) {
                    pStatement.setString(3, file);
                    pStatement.setInt(4, draftId);
                }else {
                    pStatement.setInt(3, draftId);
                }
            }else if(file!=null){
                pStatement.setString(2, file);
                pStatement.setInt(3, draftId);
            }else {
                pStatement.setInt(2, draftId);
            }
        }else if(description!=null) {
            pStatement.setString(1, description);
            if(file!=null){
                pStatement.setString(2, file);
                pStatement.setInt(3, draftId);
            }else {
                pStatement.setInt(2, draftId);
            }
        }else  if(file!=null){
            pStatement.setString(1, file);
            pStatement.setInt(2, draftId);
        }


        System.out.println(pStatement);
        return !(pStatement.execute());
    }





    private HashMap<String, Object> addSortItem(Object key, Object print){
        HashMap<String, Object> hashMap=new HashMap<String, Object>();
        hashMap.put("sortColumnName", key);
        hashMap.put("printableName", print);
        return hashMap;
    }

    public List<HashMap<String, Object>> printSortDmpItems() {
        ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();

        list.add(addSortItem("Name", "Nom"));
        list.add(addSortItem("FirstName", "Prénom"));
        list.add(addSortItem("BirthDate", "Date de naissance"));
        return list;
    }


    public List<HashMap<String, Object>> printDMP( String search, String sortitem, int paginationNumber,
                                                   int paginationLength) throws SQLException {

        List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
        if(search==null) {
            search="";
        }
        search="%"+search+"%";

        String reqString="SELECT NAME\r\n" +
                "    ,\r\n" +
                "    FirstName,\r\n" +
                "    BirthDate,\r\n" +
                "    UUID\r\n" +
                "FROM\r\n" +
                "    DMP,\r\n" +
                "    demoinformations\r\n" +
                "WHERE (NAME LIKE ?\r\n" +
                "    OR FirstName LIKE ? \r\n" +
                "    OR BirthDate LIKE ?)\r\n" +
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

   
    // ne pas faire !! Elle est juste là pour pas avoir d'erreur de compilations
    public HashMap<String, Object> getDocument(int draftId) {
        return null;
    }


    public boolean deleteDraft(int draftId) throws SQLException {
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



    public HashMap<String, Object> connect(String login, String Password) throws SQLException {
        HashMap<String, Object> result = new HashMap<String, Object>();

        String getPasswordString = "SELECT PassWord From ApplicationUser Where Login=?;";

        PreparedStatement pStatement = con.prepareStatement(getPasswordString);
        pStatement.setString(1, login);

        ResultSet rSet = pStatement.executeQuery();
        String userPassWordString = "";
        if (rSet.next()) {
            userPassWordString = rSet.getNString(1);
        }

        System.out.println(userPassWordString);
        if (userPassWordString.equals(Password)) {
            String getPeopleString = "SELECT * FROM StaffMember WHERE Login=?";
            pStatement = con.prepareStatement(getPeopleString);
            pStatement.setString(1, login);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println(resultSet);

            resultSet.next();
            result.put("type", resultSet.getString("EnumStaffType_idEnumStaffType"));
            result.put("id", resultSet.getInt("idStaffMember"));

            getPeopleString = "SELECT Name,FirstName FROM demoinformations WHERE NumSecu=?";
            pStatement = con.prepareStatement(getPeopleString);
            pStatement.setLong(1, resultSet.getLong("DemoInformations_NumSecu"));
            resultSet = pStatement.executeQuery();

            resultSet.next();
            result.put("name", resultSet.getString("FirstName"));
            result.put("lastName", resultSet.getString("Name"));

            return result;
        }
        return null;
    }

    public List<HashMap<String, Object>> getMedicalDocumentType() throws SQLException{
        List<HashMap<String, Object>> res = new ArrayList<>();
        String getMedDocType = "SELECT * FROM documenttype;";
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(getMedDocType);
        while (rs.next()) {
            String id = rs.getString("idDocumentType");
            String name = rs.getString("Name");
            HashMap<String, Object> type = new HashMap<>();
            type.put("idDocumentType", id);
            type.put("Name", name);
            res.add(type);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
    }
    public List<HashMap<String, Object>> printStaff(String sortItem, String search, int paginationNumber,
                                                    int paginationLength) throws SQLException {
        List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
        if(search==null) {
            search="";
        }
        if(sortItem==null) {
            sortItem="Name";
        }
        search="%"+search+"%";

        String reqString="SELECT NAME\r\n" +
                "    ,\r\n" +
                "    FirstName,\r\n" +
                "    BirthDate,\r\n" +
                "    idStaffMember\r\n" +
                "FROM\r\n" +
                "    DMP,\r\n" +
                "    demoinformations\r\n" +
                "WHERE NAME LIKE ?\r\n" +
                "    OR FirstName LIKE ? \r\n" +
                "    OR BirthDate LIKE ?\r\n" +
                "ORDER BY NAME\r\n"+
                "LIMIT ?; ";
        PreparedStatement pStatement=con.prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);

        pStatement.setInt(4, paginationLength*paginationNumber);
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
            hashMap.put("patientId", rSet.getInt("idStaffMember"));
            hashMap.put("firstname", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("birthdayDate ", rSet.getString("BirthDate"));
            hashMap.put("type", rSet.getString("BirthDate"));
            list.add(hashMap);

        }

        return list;
    }

    public List<HashMap<String, Object>> printDraft(int staffId, String search, String sortItems,
                                                    int paginationNumber, int paginationLength) throws SQLException {
        return printActe(staffId, search, sortItems, paginationNumber, paginationLength, true);
    }


    public List<HashMap<String, Object>> printSortActeItems()  {
        ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();

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
        //gérer patient medecin Id
        String reqString="SELECT\r\n" +
                "    StringidActeString,\r\n" +
                "    StringNomString,\r\n" +
                "    StringResponsableString,\r\n" +
                "    dmp.UUID,\r\n" +
                "    demoinformations.Name,\r\n" +
                "    demoinformations.FirstName,\r\n" +
                "    enumstafftype.JobName,\r\n" +
                "    DateDebut,\r\n" +
                "    Description,\r\n" +
                "    DocumentLink,\r\n" +
                "    documenttype.Name as document\r\n" +
                "FROM\r\n" +
                "    StringacteString,\r\n" +
                "    dmp,\r\n" +
                "    staffmember,\r\n" +
                "    documenttype,\r\n" +
                "    demoinformations,\r\n" +
                "    enumstafftype\r\n" +
                "WHERE\r\n" +
                "    acte.MedicalFolder_idFolder = dmp.UUID \r\n" +
                "    AND acte.Responsable = staffmember.idStaffMember \r\n" +
                "    AND demoinformations.NumSecu = staffmember.DemoInformations_NumSecu \r\n" +
                "    AND enumstafftype.idEnumStaffType = staffmember.EnumStaffType_idEnumStaffType \r\n"+
                "    AND IsADraft = ? \r\n"+
                "    AND documenttype.idDocumentType = acte.DocumentType_idDocumentType\r\n";
        if(draft) {
            reqString+="    AND staffmember.idStaffMember=?\r\n";
        }else {
            reqString+="    AND dmp.UUID=?\r\n";
        }

        reqString+= "	AND (UPPER(Nom) LIKE ? OR UPPER(documenttype.Name) LIKE ?) \r\n" +
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
            hashMap.put("actId", rSet.getInt("idActe"));
            hashMap.put("patientId", rSet.getString("UUID"));
            hashMap.put("staffId", rSet.getString("Responsable"));
            hashMap.put("staffName", rSet.getString("Name"));
            hashMap.put("staffFirstName", rSet.getString("FirstName"));
            hashMap.put("staffJob", rSet.getString("JobName"));
            hashMap.put("description ", rSet.getString("Description"));
            hashMap.put("date", rSet.getTimestamp("DateDebut"));
            hashMap.put("link", rSet.getString("DocumentLink").split("|"));
            hashMap.put("type", rSet.getString("document"));
            hashMap.put("title", rSet.getString("Nom"));
            list.add(hashMap);

        }

        return list;
    }
    public List<HashMap<String, Object>> printDocumentType() throws SQLException{
        String req="SELECT * FROM documenttype";
        PreparedStatement pStatement=con.prepareStatement(req);
        ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
        ResultSet rSet=pStatement.executeQuery();
        while (rSet.next()) {
            list.add(addSortItem(rSet.getInt("idDocumentType"), rSet.getString("Name")));

        }

        return list;
    }

    public List<HashMap<String, Object>> printStaffype() throws SQLException{
        String req="SELECT * FROM StringenumstafftypeString";
        PreparedStatement pStatement=con.prepareStatement(req);
        ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
        ResultSet rSet=pStatement.executeQuery();
        while (rSet.next()) {
            list.add(addSortItem(rSet.getInt("idEnumStaffType"), rSet.getString("JobName")));

        }

        return list;
    }
    public List<HashMap<String, Object>> printActe(int patientId, String search, String sortItem, int paginationNumber,
                                                   int paginationLength) throws SQLException {

        return printActe(patientId, search, sortItem, paginationNumber, paginationLength, false);

    }

    public boolean createProfile(long numsecu,String firstName, String lastName, String birthday,
                                 String Adress,String email,String phone) throws SQLException {
        if(CreateDemoInfo(numsecu, firstName, lastName, birthday, Adress, email, phone)) {
            String resultString="INSERT INTO StringdmpString (StringUUIDString, StringidDoctorString, StringDemoInformations_NumSecuString) VALUES (NULL, NULL, ?);";
            PreparedStatement pStatement=con.prepareStatement(resultString);
            pStatement.setLong(1, numsecu);
            System.out.println(pStatement);
            return !pStatement.execute();

        }
        return false;
    }

    public boolean CreateStaff(long numsecu,String name, String lastName, String birthday,
                               String Adress,String email,String phone,int typeId) throws SQLException {
        String loginReq="INSERT INTO StringapplicationuserString (StringLoginString, StringPassWordString, StringMailString) VALUES (?, ?, ?);";
        PreparedStatement pStatement=con.prepareStatement(loginReq);
        String login=name.substring(0,1)+lastName;
        pStatement.setString(1, login);
        pStatement.setString(2, login);
        pStatement.setString(3, email);
        if(!pStatement.execute()) {
            if(CreateDemoInfo(numsecu, name, lastName, birthday, Adress, email, phone)) {
                String resultString="INSERT INTO StringstaffmemberString(\r\n" +
                        "    StringidStaffMemberString,\r\n" +
                        "    StringSkillsString,\r\n" +
                        "    StringEnumStaffType_idEnumStaffTypeString,\r\n" +
                        "    StringDemoInformations_NumSecuString,\r\n" +
                        "    StringIBANString,\r\n" +
                        "    StringBICString,\r\n" +
                        "    StringHospital_idHospitalString,\r\n" +
                        "    StringLoginString\r\n" +
                        ")\r\n" +
                        "VALUES(\r\n" +
                        "    NULL,\r\n" +
                        "    NULL,\r\n" +
                        "    ?,\r\n" +
                        "    ?,\r\n" +
                        "    NULL,\r\n" +
                        "    NULL,\r\n" +
                        "    NULL,\r\n" +
                        "    ?\r\n" +
                        ");";
                pStatement=con.prepareStatement(resultString);
                pStatement.setInt(1, typeId);
                pStatement.setLong(2, numsecu);
                pStatement.setString(3, login);
                System.out.println(pStatement);
                return !pStatement.execute();

            }else {
                System.out.println("échec");
            }
        }

        return false;
    }

    private boolean CreateDemoInfo(long numsecu,String name, String lastName, String birthday,
                                   String Adress,String email,String phone) throws SQLException {
        String demoReqString="INSERT INTO StringdemoinformationsString (StringNumSecuString, StringNameString, StringFirstNameString, StringBirthDateString, StringAdressString, StringSexeString, StringProfessionString, StringFamilialSituationString, StringPersonToContact_idPatientString, StringPhoneNumberString, StringEnumNationnality_idNatString, StringCity_idCityString, StringPoidString, StringTailleString) VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, NULL, NULL, NULL, NULL);";
        PreparedStatement pStatement=con.prepareStatement(demoReqString);
        String login=name.substring(0,1)+lastName;
        pStatement.setLong(1, numsecu);
        pStatement.setString(2, lastName);
        pStatement.setString(3, name);
        pStatement.setString(4, birthday);
        pStatement.setString(5, Adress);
        pStatement.setString(6, name);
        return !pStatement.execute();

    }



    public boolean modifyContactStaff(int idPeople, String phoneNumber, String Adress, String email) throws SQLException {
        String string="UPDATE StringdemoinformationsString SET StringAdressString = ?, StringPhoneNumberString = ? WHERE StringdemoinformationsString.StringNumSecuString = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        String logString="UPDATE applicationuser SET applicationuser.Mail = ? WHERE StringapplicationuserString.StringLoginString = (SELECT staffmember.Login FROM staffmember WHERE staffmember.idStaffMember=?);";
        PreparedStatement ps1=con.prepareStatement(string);
        PreparedStatement ps2=con.prepareStatement(logString);
        ps1.setString(1, Adress);
        ps1.setString(2, phoneNumber);
        ps1.setInt(3, idPeople);
        ps2.setString(1, email);
        ps2.setInt(2, idPeople);
        System.out.println(ps1);
        return !(ps1.execute() && ps2.execute());
    }

    public boolean ResetPassword(int idPeople, String oldPwd, String newPwd, String newPwdAgain) throws SQLException {


        String getOld="SELECT applicationuser.Login,PassWord FROM applicationuser, staffmember WHERE idstaffmember=?";
        PreparedStatement ps2=con.prepareStatement(getOld);
        ps2.setInt(1, idPeople);
        ps2.setString(2, oldPwd);
        ResultSet rSet=ps2.executeQuery();
        if(rSet.next()) {
            if((oldPwd.equals("Admin") || rSet.getString("PassWord").equals(oldPwd)) && newPwd.equals(newPwdAgain)) {
                String updateNewString="UPDATE applicationuser SET PassWord=? WHERE Login=?";
                ps2=con.prepareStatement(updateNewString);
                ps2.setString(1, newPwd);
                ps2.setString(2, rSet.getString("Login"));
                return !ps2.execute();
            }

        }






        return false;
    }



    public HashMap<String, Object> getStaffMember(int idStaffMember) throws SQLException {

        String staff = "SELECT demoinformations.Name, demoinformations.FirstName, demoinformations.PhoneNumber ,demoinformations.BirthDate, demoinformations.Adress, applicationuser.Mail FROM StringstaffmemberString,demoinformations,applicationuser WHERE staffmember.Login = applicationuser.Login AND demoinformations.NumSecu=staffmember.DemoInformations_NumSecu AND idStaffMember=?;";
        PreparedStatement ps = con.prepareStatement(staff);
        ps.setInt(1, idStaffMember);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            HashMap<String, Object> member = new HashMap<>();
            member.put("firstName",rs.getString("FirstName"));
            member.put("lastName",rs.getString("Name"));
            member.put("birthdayDate",rs.getString("BirthDate"));
            member.put("phone",rs.getString("PhoneNumber"));
            member.put("address",rs.getString("Adress"));
            member.put("email",rs.getString("Mail"));



            return member;
        }
        return null;
    }


    // TODO: 18/02/2020

    public int getEnumStaffType(int idStaffMember) {
        return 0;
    }

    public HashMap<String, Object> updatePassword(int idStaffMember, String newPwd) {
        return null;
    }

    public List<HashMap<String, Object>> patientSortItems() {
        return null;
    }

    public List<HashMap<String, Object>> dmpSortItems() {
        return null;
    }

    public List<HashMap<String, Object>> draftSortItems() {
        return null;
    }

    public boolean affecterPatient(int nodeId, int staffId, int patientId) throws SQLException {
        String requestString = "INSERT INTO StringaffectationString (StringidAffectationString, StringSymptomeString, StringUnit_idHospitalString, StringStaffIDString, StringPatientIdString) VALUES (NULL, NULL, ?, ?, ?);";
        PreparedStatement rStatement = con.prepareStatement(requestString);
        rStatement.setInt(1, nodeId);
        rStatement.setInt(2, staffId);
        rStatement.setInt(3, patientId);

        return !rStatement.execute();
    }

    public boolean unAffecterPatient(int nodeId, int staffId, int patientId) throws SQLException {
        String requestString = "DELETE FROM StringaffectationString WHERE StringStaffIDString=? AND StringPatientIdString=?;";
        PreparedStatement rStatement = con.prepareStatement(requestString);
        rStatement.setInt(1, staffId);
        rStatement.setInt(2, patientId);

        return !rStatement.execute();
    }

    public boolean affecterPersonnel(int personalId, int hopitalUnitId, boolean leadUnit) throws SQLException {
        if(leadUnit) {
            String rString="UPDATE StringunitString SET StringDirectorString = ? WHERE StringunitString.StringidHospitalString = ?; ";
            PreparedStatement rStatement = con.prepareStatement(rString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalUnitId);
            return !rStatement.execute();
        }else {
            String xString="UPDATE StringstaffmemberString SET StringHospital_idHospitalString = ? WHERE StringstaffmemberString.StringidStaffMemberString = ?;";
            PreparedStatement rStatement = con.prepareStatement(xString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalUnitId);
            return !rStatement.execute();
        }
    }

    public Boolean createUnit(String name, int idRattache, int idStaffMember) throws SQLException {
        int type = 0;
        PreparedStatement ps;
        if (idRattache != -1) {
            String result = "";
            PreparedStatement ps1 = con.prepareStatement("SELECT Type FROM Unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            String sqlRequest = "INSERT INTO Unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(con);
            ps = con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            String sqlRequest = "INSERT INTO Unit(Name, Type, Director) VALUES (?, ?, ?); ";
            System.err.println(con);
            ps = con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            System.out.println(ps);
        }

        int rs = ps.executeUpdate();
        System.out.println(rs);
        return 1 != 0;
    }

    public Boolean createSector(String name, int idRattache, int idStaffMember, boolean labo) throws SQLException {
        int type = 0;
        PreparedStatement ps;
        if (idRattache != -1) {
            String result = "";
            PreparedStatement ps1 = con.prepareStatement("SELECT Type FROM Unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
                if(labo) {
                    type+=1;
                }
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            String sqlRequest = "INSERT INTO Unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(con);
            ps = con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            String sqlRequest = "INSERT INTO Unit(Name, Type, Director) VALUES (?, ?, ?); ";
            System.err.println(con);
            ps = con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            System.out.println(ps);
        }

        int rs = ps.executeUpdate();
        System.out.println(rs);
        return 1 != 0;
    }

/*
    public void affectPatient(int nodeId, int staffId, int patientId) {
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




    /* j'ai pas compris private
    public boolean createPatient(int staffId, Contact contact, Private private) {
        return null;
    }
    public List<HashMap<String, Object>> getHospitalArchitecture() throws SQLException {

        return getNodeChild(-1);
    }*/

    public List<HashMap<String, Object>> getNodeByType(int type) throws SQLException {
        List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();
        String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From Unit, staffMember, demoinformations WHERE Type=? \r\n" +
                "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, type);
        System.out.println(ps);
        ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            String name = IntToTypeStringConverter(rSet.getInt("Type"));
            hashMap.put(name + "Id", rSet.getInt("idHospital"));
            hashMap.put(name + "Name", rSet.getString("hospiName"));
            hashMap.put(name + "LeaderId", rSet.getString("idStaffMember"));
            hashMap.put(name + "LeaderName", rSet.getString("Name"));
            hashMap.put(name + "LeaderFirstName", rSet.getString("FirstName"));
            hasmaList.add(hashMap);
        }else {
            return null;
        }

        return hasmaList;
    }

    public List<HashMap<String, Object>> getNodeByTypeAndFather(int type,int father) throws SQLException {
        List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();

        String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From Unit, staffMember, demoinformations WHERE Type=? AND ratache=?\r\n" +
                "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, type);
        ps.setInt(2, father);
        ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            String name = IntToTypeStringConverter(rSet.getInt("Type"));
            hashMap.put(name + "Id", rSet.getInt("idHospital"));
            hashMap.put(name + "Name", rSet.getString("hospiName"));
            hashMap.put(name + "LeaderId", rSet.getString("idStaffMember"));
            hashMap.put(name + "LeaderName", rSet.getString("Name"));
            hashMap.put(name + "LeaderFirstName", rSet.getString("FirstName"));
            hasmaList.add(hashMap);
        }else {
            return null;
        }

        return hasmaList;
    }

    public List<HashMap<String, Object>> getStaffMemberFromNode(int node) throws SQLException{
        List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();

        String sqlString = "SELECT idStaffMember, demoinformations.Name, FirstName,JobName From Unit, staffMember, demoinformations,enumstafftype WHERE \r\n" +
                "  staffmember.DemoInformations_NumSecu = demoinformations.NumSecu AND Hospital_idHospital=? AND enumstafftype.idEnumStaffType=staffmember.EnumStaffType_idEnumStaffType;";
        PreparedStatement ps = con.prepareStatement(sqlString);
        ps.setInt(1, node);
        ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("staffId", rSet.getString("idStaffMember"));
            hashMap.put("staffLastName", rSet.getString("Name"));
            hashMap.put("staffFirstName", rSet.getString("FirstName"));
            hashMap.put("staffType", rSet.getString("JobName"));

            hasmaList.add(hashMap);
        }else {
            return null;
        }

        return hasmaList;
    }

    public List<HashMap<String, Object>> getAllHospital() throws SQLException{
        return getNodeByType(1);
    }

    public List<HashMap<String, Object>> getAllPole(int hospitalId) throws SQLException{
        return getNodeByTypeAndFather(2,hospitalId);
    }

    public List<HashMap<String, Object>> getAllSector(int poleId) throws SQLException{
        return getNodeByTypeAndFather(3,poleId);
    }

    public List<HashMap<String, Object>> getAllLabo(int poleId) throws SQLException{
        return getNodeByTypeAndFather(4,poleId);
    }

    public List<HashMap<String, Object>> getAllArchitecture() throws SQLException{
        List<HashMap<String, Object>> hasmaList = new ArrayList<>();
        for (HashMap<String, Object> hospi : getAllHospital()) {


            List<HashMap<String, Object>> hasmaListPole = new ArrayList<>();
            hospi.put("pole", hasmaListPole);
            hasmaList.add(hospi);
            for (HashMap<String, Object> pole : getAllPole((int)(hospi.get("hospitalId")))) {
                List<HashMap<String, Object>> hasmaSector = new ArrayList<>();
                pole.put("sector", hasmaSector);
                List<HashMap<String, Object>> hasmaLabo = new ArrayList<>();
                pole.put("Labo", hasmaLabo);
                hasmaListPole.add(pole);
                for (HashMap<String, Object> sector : getAllSector((int)(pole.get("poleId")))) {
                    System.out.println(sector);
                    sector.put("Personnals", getStaffMemberFromNode((int)(sector.get("sectorId"))));
                    hasmaSector.add(sector);
                }
                for (HashMap<String, Object> labo : getAllLabo((int)(pole.get("poleId")))) {

                    labo.put("Personnals", getStaffMemberFromNode((int)(labo.get("laboId"))));
                    hasmaLabo.add(labo);
                }
            }
        }
        return hasmaList;
    }


    private String IntToTypeStringConverter(int Type) {
        switch (Type) {
            case 1:

                return "hospital";
            case 2:

                return "pole";
            case 3:

                return "sector";
            case 4:

                return "labo";

            default:
                return "Unknow";
        }
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




    public boolean deleteUnit(int nodeId) {
        return false;
    }

    private int getIdUser(String login) throws SQLException {
        String sql="SELECT idStaffMember  FROM staffmember WHERE Login=?;";
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1,login);
        ResultSet rs=ps.executeQuery();
        return rs.getInt(0);
    }

    public boolean modifyInfoStaff(int idPeople, String name, String firstname, String birthday) throws SQLException {
        String string="UPDATE StringdemoinformationsString SET StringNameString = ?, StringFirstNameString = ?, BirthDate = ? WHERE StringdemoinformationsString.StringNumSecuString = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        PreparedStatement ps1=con.prepareStatement(string);
        ps1.setString(1, name);
        ps1.setString(2, firstname);
        ps1.setString(3, birthday);
        ps1.setInt(4, idPeople);
        System.out.println(ps1);
        return !(ps1.execute());
    }

    public Object searchDMPs(int staffId, int patientId, String actPrintableName, String search, int paginationNumber, int paginationLength) {
        return null;
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

    private List getActes(int UUID) throws SQLException {
        List<HashMap<String, Object>> actes = new ArrayList<>();
        String getActe = "SELECT * FROM acte WHERE MedicalFolder_idFolder=?;";
        PreparedStatement ps = con.prepareStatement(getActe);
        ps.setString(1, UUID+"");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HashMap<String, Object> acte = new HashMap<>();
            acte.put("idActe", rs.getString("idActe"));
            acte.put("MedicalFolder_idFolder", rs.getString("MedicalFolder_idFolder"));
            acte.put("Nom", rs.getString("Nom"));
            acte.put("DateDebut", rs.getString("DateDebut"));
            acte.put("DateFin", rs.getString("DateFin"));
            acte.put("Responsable", rs.getString("Responsable"));
            acte.put("Prix", rs.getString("Prix"));

            actes.add(acte);
        }
        if (actes.size() == 0) {
            return null; //Envoyer une exception dontExist
        }
        return actes;
    }

    private String getName(int numSecu) {
        return null;
    }

    private void deleteMedicalDocument(String idActe) throws SQLException {
        String delete="DELETE FROM medicaldocument WHERE Acte_idActe=?";
        PreparedStatement ps=con.prepareStatement(delete);
        ps.setString(1,idActe);
        ps.execute();
    }

    private boolean modifyDemographicInfo(int idPeople, String name, String lastName, Date birthday, String Adress, String city, String family, boolean isPatient) {
        return false;
    }

    private boolean modifyContactPatient(int idPeople, String phoneNumber, String phoneLandLine, String email, boolean isPatient) {
        return false;
    }


    public HashMap<String, Object> getStaffType(String login) {
        return null;
    }
*/

    public String getString(String appelationString, String language) throws SQLException {
        String result = "";
        PreparedStatement ps = con.prepareStatement("select StringContent from string where idString =? and Langue_idLangue = ?");
        ps.setString(1, appelationString);
        ps.setString(2, language);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
           result += rs.getString("StringContent");

        return result;
    }

 //Si le temps
    public int createDMP(int idDoctor, int numSecu) throws SQLException {
        int uuid = 1;
        String searchNewUUID = "SELECT uuid FROM dmp;";
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(searchNewUUID);
        while (rs.next()) {
            if (uuid != rs.getInt("UUID")) {
                break;
            }
            uuid++;
        }
        String create="INSERT INTO dmp VALUES(?,?,?);";
        PreparedStatement pStatement = con.prepareStatement(create);
        pStatement.setInt(1, uuid);
        pStatement.setInt(2, idDoctor);
        pStatement.setInt(3, numSecu);
        pStatement.execute();
        return uuid;
    }
     /*public boolean deletePersonnalForPatient(int nodeId, int staffId, int patientId) {
        return false;
    }*/


    /*public boolean createProfileAndSendEmail(int typeId, String name, String lastName, int birthDate, int phoneNumber, int phoneLandline, String email) throws SQLException {

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
        return false;
    }

    // voir le formatage de la réponse dans api rest
    public List<Object> getAllInfrastructure() {
        return null;
    }
    public List<Object> getAllHospitals() {
        return null;
    }
    public List<Object> getAllPoles(int idHospital) {
        return null;
    }
    public List<Object> getAllSectors(int nodeId) {
        return null;
    }
    public List<Object> getAllUnit(int nodeId) {
        return null;
    }



    /*public boolean publishBrouillonActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }
    Fonction en double
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
    public boolean createPatient(int staffId, int phoneNumber, int phoneLandline, String email, String firstname, String lastName, int identityCardNumber, String nationality, String birthDate, String file) {
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

     public HashMap<String, Object> getStaffMember(String email) throws SQLException {
        String staff = "SELECT * FROM staffmember WHERE email=?;";
        PreparedStatement ps = con.prepareStatement(staff);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            HashMap<String, Object> member = new HashMap<>();
            member.put("idStaffMember",rs.getInt("idStaffMember"));
            member.put("Skills",rs.getString("Skills"));

            String staffType = "SELECT * FROM enumstafftype WHERE idEnumStaffType=?;";
            PreparedStatement psType = con.prepareStatement(staffType);
            psType.setInt(1, rs.getInt("EnumStaffType_idEnumStaffType"));
            ResultSet rsType = psType.executeQuery();
            rsType.next();
            member.put("EnumStaffType_idEnumStaffType",rsType.getString("JobName"));
            member.put("DemoInformations_NumSecu",rs.getLong("DemoInformations_NumSecu"));
            member.put("IBAN",rs.getString("IBAN"));
            member.put("BIC",rs.getString("BIC"));

            String staffHospital = "SELECT * FROM unit WHERE idHospital=?;";
            PreparedStatement psHospital = con.prepareStatement(staffHospital);
            psHospital.setInt(1, rs.getInt("Hospital_idHospital"));
            ResultSet rsHospital = psHospital.executeQuery();
            rsHospital.next();
            member.put("Hospital_idHospital",rsHospital.getString("Name"));
            member.put("NbBureau",rs.getInt("NbBureau"));
            member.put("Login",rs.getString("Login"));

            return member;
        }
        return null;
    }
     private boolean _firstAboParcour=true;



    public List<HashMap<String, Object>> getArchitecture(int nodeId) throws SQLException {
        String sqlString = "SELECT ratache From unit WHERE idHospital=?";
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
    private List<HashMap<String, Object>> getNodeChild(int nodeId) throws SQLException {

        List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();
        String sqlString = null;
        if (nodeId < 0) {
            sqlString = "SELECT idHospital, Name, Type From unit WHERE ratache=?";
            nodeId=17;
        } else {
            if(_firstAboParcour) {
                sqlString = "SELECT idHospital, Name,Type From unit WHERE idHospital=?";
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
            sqlString = "SELECT idHospital, Name, Type From unit WHERE ratache=?";


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
 */




}

