package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.metamodel.relational.Database;

import javax.naming.Context;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SQLManager implements ISingleton {

    private final String direcoryPatientString = "PatientDirectory";
    //private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String username = "hyppocrytos";
    private static final String password = "hyppocrytos-SQL2019"; // le mien c'était le mot de passe de mon compte windows
    private static final String serverName = "localhost";
    private static final String database = "hyppocrytos";

    //https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2839563#2839563
    Context context;
    MysqlDataSource dataSource;
    Connection con;
    // singleton pattern
    private SQLManager() {
        try {
            dataSource = new MysqlDataSource();
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
            System.err.println("MySQL Connexion exception: " + e);

            //throw new IllegalStateException();
        }
    }
    private Connection getCon() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SQLManager INSTANCE;

    public static SQLManager getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new SQLManager();
        }
        return INSTANCE;
    }


    /*Acte*/
    private boolean publishActe(int staffId, int patientId, String title, int type, String description, String file, boolean isDraft) throws SQLException {
        //String link=CreateDynamicLink(file,patientId,title);
        final String searchNewID = "INSERT INTO acte (MedicalFolder_idFolder, Nom, DateDebut, DateFin, Responsable, Prix, DocumentLink, IsADraft, DocumentType_idDocumentType, Description, idActe)\r\n" +
            "VALUES (?, ?, ?, NULL, ?, NULL, ?, ?, ?, ?, NULL);";
        PreparedStatement s = getCon().prepareStatement(searchNewID);
        s.setInt(1, patientId);
        s.setString(2, title);


        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());


        s.setDate(3, date);
        s.setInt(4, staffId);
        s.setString(5, file);
        s.setInt(6, isDraft ? 1 : 0);
        s.setInt(7, type);
        s.setString(8, description);
        System.out.println(s);
        return !s.execute();
    }

    public boolean publishActe(int staffId, int patientId, String title, int type, String description, String file) throws SQLException {

        return publishActe(staffId, patientId, title, type, description, file, false);
    }


    /*Draft*/
    public boolean CreateDraftt(int staffId, int patientId, String title, int type, String description, String file) throws SQLException {

        return publishActe(staffId, patientId, title, type, description, file, true);
    }

    public boolean updateEtPublierBrouillon(int patientId, int draftId, String title, String description, String file) throws SQLException, IOException {
        updateBrouillon(patientId, draftId, title, description, file);
        final String update = "UPDATE acte SET IsADraft=0 WHERE acte.idActe = ?;";

        PreparedStatement pStatement = getCon().prepareStatement(update);

        pStatement.setInt(1, draftId);

        return !pStatement.execute();
    }

    public boolean updateBrouillon(int patientId, int draftId, String title, String description, String file) throws SQLException {

        String update = "UPDATE acte SET ";
        if (title != null) {
            update += "Nom = ?";
            if (file != null || description != null) {
                update += ", ";
            }
        }
        if (description != null) {
            update += "Description = ?";
            if (file != null) {
                update += ", ";
            }
        }
        final String linkString = "";
        if (file != null) {
            update += "DocumentLink = ?";

        }
        update += " WHERE acte.idActe = ?;";

        PreparedStatement pStatement = getCon().prepareStatement(update);
        if (title != null) {
            pStatement.setString(1, title);
            if (description != null) {
                pStatement.setString(2, description);
                if (file != null) {
                    pStatement.setString(3, file);
                    pStatement.setInt(4, draftId);
                } else {
                    pStatement.setInt(3, draftId);
                }
            } else if (file != null) {
                pStatement.setString(2, file);
                pStatement.setInt(3, draftId);
            } else {
                pStatement.setInt(2, draftId);
            }
        } else if (description != null) {
            pStatement.setString(1, description);
            if (file != null) {
                pStatement.setString(2, file);
                pStatement.setInt(3, draftId);
            } else {
                pStatement.setInt(2, draftId);
            }
        } else if (file != null) {
            pStatement.setString(1, file);
            pStatement.setInt(2, draftId);
        }


        System.out.println(pStatement);
        return !(pStatement.execute());
    }


    private HashMap < String, Object > addSortItem(Object key, Object print) {
        HashMap < String, Object > hashMap = new HashMap < String, Object > ();
        hashMap.put("sortColumnName", key);
        hashMap.put("printableName", print);
        return hashMap;
    }

    public List < HashMap < String, Object >> printSortDmpItems() {
        ArrayList < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();

        list.add(addSortItem("Name", "Nom"));
        list.add(addSortItem("FirstName", "Prénom"));
        list.add(addSortItem("BirthDate", "Date de naissance"));
        return list;
    }


    public HashMap < String, Object > printDMP(String search, String sortitem, int paginationNumber,
        int paginationLength) throws SQLException {

        List < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();
        if (search == null) {
            search = "";
        }
        if (sortitem == null || sortitem.equals("")) {
            sortitem = "FirstName";
        }
        search = "%" + search + "%";

        final String reqString = "SELECT NAME\r\n" +
            "    ,\r\n" +
            "    FirstName,\r\n" +
            "    BirthDate,\r\n" +
            "    UUID\r\n" +
            "FROM\r\n" +
            "    dmp,\r\n" +
            "    demoinformations\r\n" +
            "WHERE (NAME LIKE ?\r\n" +
            "    OR FirstName LIKE ? \r\n" +
            "    OR BirthDate LIKE ?)\r\n" +
            "    AND demoinformations.NumSecu=dmp.DemoInformations_NumSecu\r\n" +
            "ORDER BY " + sortitem;
        PreparedStatement pStatement = getCon().prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);
        System.out.println(pStatement);
        ResultSet rSet = pStatement.executeQuery();
        int count = 0;
        HashMap < String, Object > hashMap = new HashMap < String, Object > ();
        boolean hasNext = false;
        while (rSet.next()) {

            if ((count < (paginationLength * (paginationNumber - 1)))) {

                count++;
                continue;
            } else if (count >= (paginationLength * (paginationNumber))) {

                hasNext = true;
                break;
            }
            hashMap = new HashMap < String, Object > ();
            hashMap.put("patientId", rSet.getInt("UUID"));
            hashMap.put("firstName", rSet.getString("FirstName"));
            hashMap.put("lastName", rSet.getString("Name"));
            hashMap.put("birthdayDate", rSet.getString("BirthDate"));

            count++;
            list.add(hashMap);


        }
        HashMap < String, Object > hashMap2 = new HashMap < String, Object > ();
        hashMap2.put("result", list);
        hashMap2.put("hasNext", hasNext);


        return hashMap2;
    }


    // ne pas faire !! Elle est juste là pour pas avoir d'erreur de compilations
    public HashMap < String, Object > getDocument(int draftId) {
        return null;
    }


    public boolean deleteDraft(int draftId) throws SQLException {
        final String verifyDraftString = "SELECT idActe FROM acte WHERE idActe=? AND IsADraft=1 ";
        PreparedStatement pStatement = getCon().prepareStatement(verifyDraftString);
        pStatement.setInt(1, draftId);
        ResultSet rSet = pStatement.executeQuery();
        if (rSet.next()) {
            final String delString = "DELETE FROM acte WHERE idActe=? AND IsADraft=1;";
            pStatement = getCon().prepareStatement(delString);
            pStatement.setInt(1, draftId);
            int i = pStatement.executeUpdate();
            return true;
        }
        return false;
    }


    public HashMap < String, Object > connect(String login, String Password) throws SQLException {
        HashMap < String, Object > result = new HashMap < String, Object > ();

        final String getPasswordString = "SELECT PassWord From applicationuser Where Login=?;";

        PreparedStatement pStatement = getCon().prepareStatement(getPasswordString);
        pStatement.setString(1, login);

        ResultSet rSet = pStatement.executeQuery();
        String userPassWordString = "";
        if (rSet.next()) {
            userPassWordString = rSet.getNString(1);
        }

        System.out.println(userPassWordString);
        if (userPassWordString.equals(Password)) {
            String getPeopleString = "SELECT * FROM staffmember WHERE Login=?";
            pStatement = getCon().prepareStatement(getPeopleString);
            pStatement.setString(1, login);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println(resultSet);

            resultSet.next();
            result.put("type", resultSet.getString("EnumStaffType_idEnumStaffType"));
            result.put("id", resultSet.getInt("idStaffMember"));

            getPeopleString = "SELECT Name,FirstName FROM demoinformations WHERE NumSecu=?";
            pStatement = getCon().prepareStatement(getPeopleString);
            pStatement.setLong(1, resultSet.getLong("DemoInformations_NumSecu"));
            resultSet = pStatement.executeQuery();

            resultSet.next();
            result.put("firstName", resultSet.getString("FirstName"));
            result.put("lastName", resultSet.getString("Name"));

            return result;
        }
        return null;
    }

    public List < HashMap < String, Object >> getMedicalDocumentType() throws SQLException {
        List < HashMap < String, Object >> res = new ArrayList < > ();
        final String getMedDocType = "SELECT * FROM documenttype;";
        Statement s = getCon().createStatement();
        ResultSet rs = s.executeQuery(getMedDocType);
        while (rs.next()) {
            String id = rs.getString("idDocumentType");
            String name = rs.getString("Name");
            HashMap < String, Object > type = new HashMap < > ();
            type.put("idDocumentType", id);
            type.put("Name", name);
            res.add(type);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
    }

    public HashMap < String, Object > printStaff(String sortItem, String search, int paginationNumber,
        int paginationLength) throws SQLException {
        List < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();
        if (search == null) {
            search = "";
        }
        if (sortItem == null || sortItem.equals("")) {
            sortItem = "Name";
        }
        search = "%" + search + "%";

        final String reqString = "SELECT NAME\r\n" +
            "    ,\r\n" +
            "    FirstName,\r\n" +
            "    BirthDate,\r\n" +
            "    idStaffMember,enumstafftype.idEnumStaffType\r\n" +
            "FROM\r\n" +
            "    staffmember,\r\n" +
            "    demoinformations," +
            "enumstafftype\r\n" +
            "WHERE (NAME LIKE ?\r\n" +
            "    OR FirstName LIKE ? \r\n" +
            "    OR BirthDate LIKE ?)  AND enumstafftype.idEnumStaffType = staffmember.EnumStaffType_idEnumStaffType AND demoinformations.NumSecu = staffmember.DemoInformations_NumSecu \r\n" +
            "ORDER BY " + sortItem;
        PreparedStatement pStatement = getCon().prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);

        System.out.println(pStatement);
        ResultSet rSet = pStatement.executeQuery();
        int count = 0;

        HashMap < String, Object > hashMap;
        boolean hasNext = false;
        while (rSet.next()) {
            if ((count < (paginationLength * (paginationNumber - 1)))) {
                count++;
                continue;
            } else if (count >= (paginationLength * (paginationNumber))) {
                hasNext = true;
                break;
            }
            hashMap = new HashMap < String, Object > ();
            hashMap.put("staffId", rSet.getInt("idStaffMember"));
            hashMap.put("firstName", rSet.getString("FirstName"));
            hashMap.put("lastName", rSet.getString("Name"));
            hashMap.put("birthdayDate", rSet.getString("BirthDate"));
            hashMap.put("staffType", rSet.getString("idEnumStaffType"));
            list.add(hashMap);
            count++;

        }
        HashMap < String, Object > hashMap2 = new HashMap < String, Object > ();
        hashMap2.put("result", list);
        hashMap2.put("hasNext", hasNext);

        return hashMap2;
    }

    public HashMap < String, Object > printDraft(int staffId, String search, String sortItems,
        int paginationNumber, int paginationLength) throws SQLException {
        return printActe(staffId, search, sortItems, paginationNumber, paginationLength, true);
    }


    public List < HashMap < String, Object >> printSortActeItems() {
        ArrayList < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();

        list.add(addSortItem("documenttype.idDocumentType", "Type d'acte"));
        list.add(addSortItem("Nom", "Nom"));
        list.add(addSortItem("DateDebut", "Date de creation"));


        return list;
    }

    private HashMap < String, Object > printActe(int patientId, String search, String sortItem, int paginationNumber,
        int paginationLength, boolean draft) throws SQLException {
        List < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();

        if (search == null) {
            search = "";
        }
        if (sortItem == null || sortItem.equals("")) {
            sortItem = "DateDebut";
        }
        search = search.toUpperCase();
        search = "%" + search + "%";
        //gérer patient medecin Id
        String reqString = "SELECT\r\n" +
            "    idActe,\r\n" +
            "    Nom,\r\n" +
            "    Responsable,\r\n" +
            "    dmp.UUID,\r\n" +
            "    applicationuser.Mail,\r\n" +
            "    demoinformations.Name,\r\n" +
            "    demoinformations.FirstName,\r\n" +
            "    demoinformations.FirstName,\r\n" +
            "    demoinformations.PhoneNumber,\r\n" +
            "    enumstafftype.idEnumStaffType,\r\n" +
            "    DateDebut,\r\n" +
            "    Description,\r\n" +
            "    DocumentLink,\r\n" +
            "     documenttype.idDocumentType as document\r\n" +
            "FROM\r\n" +
            "    acte,\r\n" +
            "    dmp,\r\n" +
            "    staffmember,\r\n" +
            "    documenttype,\r\n" +
            "    demoinformations,\r\n" +
            "    enumstafftype," +
            "applicationuser\r\n" +
            "WHERE\r\n" +
            "    acte.MedicalFolder_idFolder = dmp.UUID \r\n" +
            "    AND acte.Responsable = staffmember.idStaffMember \r\n" +
            "    AND applicationuser.Login = staffmember.Login \r\n" +
            "    AND demoinformations.NumSecu = staffmember.DemoInformations_NumSecu \r\n" +
            "    AND enumstafftype.idEnumStaffType = staffmember.EnumStaffType_idEnumStaffType \r\n" +
            "    AND IsADraft = ? \r\n" +
            "    AND documenttype.idDocumentType = acte.DocumentType_idDocumentType\r\n";
        if (draft) {
            reqString += "    AND staffmember.idStaffMember=?\r\n";
        } else {
            reqString += "    AND dmp.UUID=?\r\n";
        }

        reqString += "	AND (UPPER(Nom) LIKE ? OR UPPER(documenttype.Name) LIKE ?) \r\n" +
            "ORDER BY " + sortItem + ";";
        PreparedStatement pStatement = getCon().prepareStatement(reqString);
        pStatement.setInt(1, draft ? 1 : 0);

        pStatement.setInt(2, patientId);
        pStatement.setString(3, search);
        pStatement.setString(4, search);



        System.out.println(pStatement);
        ResultSet rSet = pStatement.executeQuery();
        int count = 0;

        HashMap < String, Object > hashMap;
        boolean hasNext = false;
        while (rSet.next()) {

            if ((count < (paginationLength * (paginationNumber - 1)))) {
                count++;
                continue;
            } else if (count >= (paginationLength * (paginationNumber))) {
                hasNext = true;
                break;
            }


            hashMap = new HashMap < String, Object > ();


            hashMap.put("actId", rSet.getInt("idActe"));
            hashMap.put("patientId", rSet.getInt("UUID"));
            hashMap.put("staffId", rSet.getString("Responsable"));
            hashMap.put("staffLastName", rSet.getString("Name"));
            hashMap.put("staffFirstName", rSet.getString("FirstName"));
            hashMap.put("staffPhoneNumber", rSet.getString("PhoneNumber"));
            hashMap.put("staffEmail", rSet.getString("Mail"));
            hashMap.put("staffType", rSet.getInt("idEnumStaffType"));
            hashMap.put("description", rSet.getString("Description"));
            hashMap.put("date", rSet.getString("DateDebut"));
            hashMap.put("link", rSet.getString("DocumentLink").split("\\|"));
            hashMap.put("actType", rSet.getInt("document"));
            hashMap.put("title", rSet.getString("Nom"));
            count++;
            list.add(hashMap);

        }
        for (HashMap < String, Object > hashMap2: list) {
            String demoPatient = "SELECT demoinformations.Name, demoinformations.FirstName FROM dmp, demoinformations WHERE dmp.DemoInformations_NumSecu=demoinformations.NumSecu AND dmp.UUID=?";

            PreparedStatement pStatement2 = con.prepareStatement(demoPatient);
            pStatement2.setInt(1, (int) hashMap2.get("patientId"));
            ResultSet rSet2 = pStatement.executeQuery();
            if (rSet2.next()) {
                hashMap2.put("patientName", rSet2.getString("Name"));
                hashMap2.put("patientFirstName", rSet2.getString("FirstName"));
            }
        }
        HashMap < String, Object > hashMap3 = new HashMap < String, Object > ();
        hashMap3.put("result", list);
        hashMap3.put("hasNext", hasNext);

        return hashMap3;
    }

    public List < HashMap < String, Object >> printDocumentType() throws SQLException {
        final String req = "SELECT * FROM documenttype";
        PreparedStatement pStatement = getCon().prepareStatement(req);
        ArrayList < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
            list.add(addSortItem(rSet.getInt("idDocumentType"), rSet.getString("Name")));

        }

        return list;
    }

    public List < HashMap < String, Object >> printStaffype() throws SQLException {
        final String req = "SELECT * FROM enumstafftype";
        PreparedStatement pStatement = getCon().prepareStatement(req);
        ArrayList < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
            list.add(addSortItem(rSet.getInt("idEnumStaffType"), rSet.getString("JobName")));

        }

        return list;
    }

    public HashMap < String, Object > printActe(int patientId, String search, String sortItem, int paginationNumber,
        int paginationLength) throws SQLException {

        return printActe(patientId, search, sortItem, paginationNumber, paginationLength, false);

    }

    public boolean createProfile(long numsecu, String firstName, String lastName, String birthday,
        String Adress, String email, String phone) throws SQLException {
        if (CreateDemoInfo(numsecu, firstName, lastName, birthday, Adress, email, phone)) {
            final String resultString = "INSERT INTO dmp (UUID, idDoctor, DemoInformations_NumSecu) VALUES (NULL, NULL, ?);";
            PreparedStatement pStatement = getCon().prepareStatement(resultString);
            pStatement.setLong(1, numsecu);
            System.out.println(pStatement);
            return !pStatement.execute();

        }
        return false;
    }

    public boolean CreateStaff(long numsecu, String name, String lastName, String birthday,
        String Adress, String email, String phone, int typeId) throws SQLException {
        final String loginReq = "INSERT INTO applicationuser (Login, PassWord, Mail) VALUES (?, ?, ?);";
        PreparedStatement pStatement = getCon().prepareStatement(loginReq);
        String login = name.substring(0, 1) + lastName;
        pStatement.setString(1, login);
        pStatement.setString(2, login);
        pStatement.setString(3, email);
        if (!pStatement.execute()) {
            if (CreateDemoInfo(numsecu, name, lastName, birthday, Adress, email, phone)) {
                final String resultString = "INSERT INTO staffmember(\r\n" +
                    "    idStaffMember,\r\n" +
                    "    Skills,\r\n" +
                    "    EnumStaffType_idEnumStaffType,\r\n" +
                    "    DemoInformations_NumSecu,\r\n" +
                    "    IBAN,\r\n" +
                    "    BIC,\r\n" +
                    "    Hospital_idHospital,\r\n" +
                    "    Login\r\n" +
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
                pStatement = getCon().prepareStatement(resultString);
                pStatement.setInt(1, typeId);
                pStatement.setLong(2, numsecu);
                pStatement.setString(3, login);
                System.out.println(pStatement);
                return !pStatement.execute();

            } else {
                System.out.println("échec");
            }
        }

        return false;
    }

    private boolean CreateDemoInfo(long numsecu, String name, String lastName, String birthday,
        String Adress, String email, String phone) throws SQLException {
        final String demoReqString = "INSERT INTO demoinformations (NumSecu, Name, FirstName, BirthDate, Adress, Sexe, Profession, FamilialSituation, PersonToContact_idPatient, PhoneNumber, EnumNationnality_idNat, City_idCity, Poid, Taille) VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, NULL, NULL, NULL, NULL);";
        PreparedStatement pStatement = getCon().prepareStatement(demoReqString);
        String login = name.substring(0, 1) + lastName;
        pStatement.setLong(1, numsecu);
        pStatement.setString(2, lastName);
        pStatement.setString(3, name);
        pStatement.setString(4, birthday);
        pStatement.setString(5, Adress);
        pStatement.setString(6, phone);
        return !pStatement.execute();

    }


    public boolean modifyContactStaff(int idPeople, String phoneNumber, String Adress, String email) throws SQLException {
        final String string = "UPDATE demoinformations SET Adress = ?, PhoneNumber = ? WHERE demoinformations.NumSecu = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        final String logString = "UPDATE applicationuser SET applicationuser.Mail = ? WHERE applicationuser.Login = (SELECT staffmember.Login FROM staffmember WHERE staffmember.idStaffMember=?);";
        PreparedStatement ps1 = con.prepareStatement(string);
        ps1.setString(1, Adress);
        ps1.setString(2, phoneNumber);
        ps1.setInt(3, idPeople);

        boolean x = ps1.execute();
        PreparedStatement ps2 = con.prepareStatement(logString);

        ps2.setString(1, email);
        ps2.setInt(2, idPeople);
        System.out.println(ps2);
        return !(ps2.execute() && x);
    }

    public boolean ResetPassword(int idPeople, String oldPwd, String newPwd, String newPwdAgain) throws SQLException {


        final String getOld = "SELECT applicationuser.Login,PassWord FROM applicationuser, staffmember WHERE idstaffmember=?  AND applicationuser.Login=staffmember.Login";
        PreparedStatement ps2 = getCon().prepareStatement(getOld);
        ps2.setInt(1, idPeople);
        ResultSet rSet = ps2.executeQuery();
        if (rSet.next()) {
            if ((oldPwd.equals("Admin") || rSet.getString("PassWord").equals(oldPwd)) && newPwd.equals(newPwdAgain)) {
                final String updateNewString = "UPDATE applicationuser SET PassWord=? WHERE Login=?";
                ps2 = getCon().prepareStatement(updateNewString);
                ps2.setString(1, newPwd);
                ps2.setString(2, rSet.getString("Login"));
                return !ps2.execute();
            }

        }


        return false;
    }


    public HashMap < String, Object > getStaffMember(int idStaffMember) throws SQLException {

        final String staff = "SELECT demoinformations.Name, demoinformations.FirstName, demoinformations.PhoneNumber ,demoinformations.BirthDate, demoinformations.Adress, applicationuser.Mail FROM staffmember,demoinformations,applicationuser WHERE staffmember.Login = applicationuser.Login AND demoinformations.NumSecu=staffmember.DemoInformations_NumSecu AND idStaffMember=?;";
        PreparedStatement ps = getCon().prepareStatement(staff);
        ps.setInt(1, idStaffMember);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            HashMap < String, Object > member = new HashMap < > ();
            member.put("firstName", rs.getString("FirstName"));
            member.put("lastName", rs.getString("Name"));
            member.put("birthdayDate", rs.getString("BirthDate"));
            member.put("phone", rs.getString("PhoneNumber"));
            member.put("address", rs.getString("Adress"));
            member.put("email", rs.getString("Mail"));


            return member;
        }
        return null;
    }


    // TODO: 18/02/2020

    public List < HashMap < String, Object >> dmpSortItems() {
        return null;
    }


    public boolean affecterPatient(int nodeId, int staffId, int patientId) throws SQLException {
        final String requestString = "INSERT INTO affectation (idAffectation, Symptome, unit_idHospital, StaffID, PatientId) VALUES (NULL, NULL, ?, ?, ?);";
        PreparedStatement rStatement = getCon().prepareStatement(requestString);
        rStatement.setInt(1, nodeId);
        rStatement.setInt(2, staffId);
        rStatement.setInt(3, patientId);

        return !rStatement.execute();
    }

    public boolean unAffecterPatient(int staffId, int patientId) throws SQLException {
        final String requestString = "DELETE FROM affectation WHERE StaffID=? AND PatientId=?;";
        PreparedStatement rStatement = getCon().prepareStatement(requestString);
        rStatement.setInt(1, staffId);
        rStatement.setInt(2, patientId);

        return !rStatement.execute();
    }

    public boolean affecterPersonnel(int personalId, int hopitalunitId, boolean leadunit) throws SQLException {
        if (leadunit) {
            final String rString = "UPDATE unit SET Director = ? WHERE unit.idHospital = ?; ";
            PreparedStatement rStatement = getCon().prepareStatement(rString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalunitId);
            return !rStatement.execute();
        } else {
            final String xString = "UPDATE staffmember SET Hospital_idHospital = ? WHERE staffmember.idStaffMember = ?;";
            PreparedStatement rStatement = getCon().prepareStatement(xString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalunitId);
            return !rStatement.execute();
        }
    }

    public Boolean createUnit(String name, int idRattache, int idStaffMember) throws SQLException {
        int type = 1;
        PreparedStatement ps;
        if (idRattache != -1) {
            final String result = "";
            PreparedStatement ps1 = getCon().prepareStatement("SELECT Type FROM unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            final String sqlRequest = "INSERT INTO unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(con);
            ps = getCon().prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            final String sqlRequest = "INSERT INTO unit(Name, Type, Director, ratache) VALUES (?, ?, ?, 17); ";
            System.err.println(con);
            ps = getCon().prepareStatement(sqlRequest);
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
            final String result = "";
            PreparedStatement ps1 = getCon().prepareStatement("SELECT Type FROM unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
                if (labo) {
                    type += 1;
                }
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            final String sqlRequest = "INSERT INTO unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(con);
            ps = getCon().prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            final String sqlRequest = "INSERT INTO unit(Name, Type, Director) VALUES (?, ?, ?); ";
            System.err.println(con);
            ps = getCon().prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            System.out.println(ps);
        }

        int rs = ps.executeUpdate();
        System.out.println(rs);
        return 1 != 0;
    }

    public List < HashMap < String, Object >> getNodeByType(int type) throws SQLException {
        List < HashMap < String, Object >> hasmaList = new ArrayList < > ();
        final String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From unit, staffmember, demoinformations WHERE Type=? \r\n" +
            "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        PreparedStatement ps = getCon().prepareStatement(sqlString);
        ps.setInt(1, type);
        System.out.println(ps);
        ResultSet rSet = ps.executeQuery();

        while (rSet.next()) {
            HashMap < String, Object > hashMap = new HashMap < > ();
            String name = IntToTypeStringConverter(rSet.getInt("Type"));
            hashMap.put(name + "Id", rSet.getInt("idHospital"));
            hashMap.put(name + "Name", rSet.getString("hospiName"));
            hashMap.put(name + "LeaderId", rSet.getString("idStaffMember"));
            hashMap.put(name + "LeaderName", rSet.getString("Name"));
            hashMap.put(name + "LeaderFirstName", rSet.getString("FirstName"));
            hasmaList.add(hashMap);
        }

        return hasmaList;
    }

    public List < HashMap < String, Object >> getNodeByTypeAndFather(int type, int father) throws SQLException {
        List < HashMap < String, Object >> hasmaList = new ArrayList < HashMap < String, Object >> ();

        final String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From unit, staffmember, demoinformations WHERE Type=? AND ratache=?\r\n" +
            "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        PreparedStatement ps = getCon().prepareStatement(sqlString);
        ps.setInt(1, type);
        ps.setInt(2, father);
        ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            HashMap < String, Object > hashMap = new HashMap < String, Object > ();
            String name = IntToTypeStringConverter(rSet.getInt("Type"));
            hashMap.put(name + "Id", rSet.getInt("idHospital"));
            hashMap.put(name + "Name", rSet.getString("hospiName"));
            hashMap.put(name + "LeaderId", rSet.getString("idStaffMember"));
            hashMap.put(name + "LeaderName", rSet.getString("Name"));
            hashMap.put(name + "LeaderFirstName", rSet.getString("FirstName"));
            hasmaList.add(hashMap);
        } else {
            return null;
        }

        return hasmaList;
    }

    public List < HashMap < String, Object >> getStaffMemberFromNode(int node) throws SQLException {
        List < HashMap < String, Object >> hasmaList = new ArrayList < HashMap < String, Object >> ();

        final String sqlString = "SELECT idStaffMember, demoinformations.Name, FirstName, idEnumStaffType From unit, staffmember, demoinformations,enumstafftype WHERE \r\n" +
            "  staffmember.DemoInformations_NumSecu = demoinformations.NumSecu AND Hospital_idHospital=? AND enumstafftype.idEnumStaffType=staffmember.EnumStaffType_idEnumStaffType;";
        PreparedStatement ps = getCon().prepareStatement(sqlString);
        ps.setInt(1, node);
        ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            HashMap < String, Object > hashMap = new HashMap < String, Object > ();
            hashMap.put("staffId", rSet.getInt("idStaffMember"));
            hashMap.put("staffFullName", rSet.getString("FirstName") + " " + rSet.getString("Name"));
            hashMap.put("staffType", rSet.getInt("idEnumStaffType"));

            hasmaList.add(hashMap);
        } else {
            return null;
        }

        return hasmaList;
    }

    public List < HashMap < String, Object >> getAllHospital() throws SQLException {
        return getNodeByType(1);
    }

    public List < HashMap < String, Object >> getAllPole(int hospitalId) throws SQLException {
        if (hospitalId == -1) {
            return getNodeByType(2);
        }

        return getNodeByTypeAndFather(2, hospitalId);
    }

    public List < HashMap < String, Object >> getAllSector(int poleId) throws SQLException {
        if (poleId == -1) {
            return getNodeByType(3);
        }
        return getNodeByTypeAndFather(3, poleId);
    }

    public List < HashMap < String, Object >> getAllLabo(int poleId) throws SQLException {
        if (poleId == -1) {
            return getNodeByType(4);
        }
        return getNodeByTypeAndFather(4, poleId);
    }

    public List < HashMap < String, Object >> getAllArchitecture() throws SQLException {
        List < HashMap < String, Object >> hasmaList = new ArrayList < > ();
        for (HashMap < String, Object > hospi: getAllHospital()) {


            List < HashMap < String, Object >> hasmaListPole = new ArrayList < > ();
            hospi.put("poles", hasmaListPole);
            hasmaList.add(hospi);
            List < HashMap < String, Object >> polee = getAllPole((int)(hospi.get("hospitalId")));
            if (polee == null || polee.size() == 0) {
                continue;
            }
            for (HashMap < String, Object > pole: polee) {
                List < HashMap < String, Object >> hasmaSector = new ArrayList < > ();
                pole.put("sectors", hasmaSector);
                List < HashMap < String, Object >> hasmaLabo = new ArrayList < > ();
                pole.put("labos", hasmaLabo);
                hasmaListPole.add(pole);

                List < HashMap < String, Object >> sectorr = getAllSector((int)(pole.get("poleId")));
                if (sectorr == null || sectorr.size() == 0) {
                    continue;
                }
                for (HashMap < String, Object > sector: sectorr) {
                    System.out.println(sector);
                    sector.put("staff", getStaffMemberFromNode((int)(sector.get("sectorId"))));
                    hasmaSector.add(sector);
                }

                List < HashMap < String, Object >> laboo = getAllLabo((int)(pole.get("poleId")));
                if (laboo == null || laboo.size() == 0) {
                    continue;
                }
                for (HashMap < String, Object > labo: laboo) {

                    labo.put("staff", getStaffMemberFromNode((int)(labo.get("laboId"))));
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


    public List < HashMap < String, Object >> getPersonnalForPatient(int patientId) throws SQLException {

        List < HashMap < String, Object >> list = new ArrayList < HashMap < String, Object >> ();
        HashMap < String, Object > hashMap;
        final String idsActe =
            "SELECT idStaffMember, FirstName, demoinformations.Name  FROM affectation, dmp, demoinformations, staffmember \n" +
            "WHERE affectation.PatientId = dmp.UUID AND demoinformations.NumSecu = staffmember.DemoInformations_NumSecu\n" +
            "AND staffmember.idStaffMember = affectation.StaffID AND dmp.UUID =?\n";

        PreparedStatement ps = getCon().prepareStatement(idsActe);
        ps.setInt(1, patientId);
        ResultSet rSet = ps.executeQuery();
        while (rSet.next()) {
            hashMap = new HashMap < String, Object > ();
            hashMap.put("staffId", rSet.getInt("idStaffMember"));
            hashMap.put("staffFullName", rSet.getString("FirstName") + " " + rSet.getString("Name"));

            list.add(hashMap);

        }
        return list;

    }


    public boolean deleteUnit(int nodeId) {
        return false;
    }

    private int getIdUser(String login) throws SQLException {
        final String sql = "SELECT idStaffMember  FROM staffmember WHERE Login=?;";
        PreparedStatement ps = getCon().prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        return rs.getInt(0);
    }

    public boolean modifyInfoStaff(int idPeople, String name, String firstName, String birthday) throws SQLException {
        final String string = "UPDATE demoinformations SET Name = ?, FirstName = ?, BirthDate = ? WHERE demoinformations.NumSecu = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        PreparedStatement ps1 = getCon().prepareStatement(string);
        ps1.setString(1, name);
        ps1.setString(2, firstName);
        ps1.setString(3, birthday);
        ps1.setInt(4, idPeople);
        System.out.println(ps1);
        return !(ps1.execute());
    }


    public String getString(String appelationString, String language) throws SQLException {
        String result = "";
        PreparedStatement ps = getCon().prepareStatement("select StringContent FROM string where id =? and Langue_idLangue = ?");
        ps.setString(1, appelationString);
        ps.setString(2, language);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            result += rs.getString("StringContent");

        return result;
    }

    //Si le temps
    public int createDMP(int idDoctor, int numSecu) throws SQLException {
        int uuid = 1;
        final String searchNewUUID = "SELECT uuid FROM dmp;";
        Statement s = getCon().createStatement();
        ResultSet rs = s.executeQuery(searchNewUUID);
        while (rs.next()) {
            if (uuid != rs.getInt("UUID")) {
                break;
            }
            uuid++;
        }
        final String create = "INSERT INTO dmp VALUES(?,?,?);";
        PreparedStatement pStatement = getCon().prepareStatement(create);
        pStatement.setInt(1, uuid);
        pStatement.setInt(2, idDoctor);
        pStatement.setInt(3, numSecu);
        pStatement.execute();
        return uuid;
    }
}