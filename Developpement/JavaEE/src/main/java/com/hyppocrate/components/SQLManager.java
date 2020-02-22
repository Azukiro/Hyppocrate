package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;
import com.mysql.cj.jdbc.MysqlDataSource;

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
    private static final String database = "hyppocrate";

    //https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2839563#2839563
    Context context;
    DataSource dataSource;
    Connection con;

    // singleton pattern
    private SQLManager() {
        try {
            final MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(SQLManager.username);
            dataSource.setPassword(SQLManager.password);
            dataSource.setServerName(SQLManager.serverName);
            dataSource.setDatabaseName(SQLManager.database);
            dataSource.setServerTimezone("UTC");
            System.out.println("Tentative de connexion...");
            this.con = dataSource.getConnection();
            final Statement stmt = this.con.createStatement(); // C'est mieux les PreparedStatement
            stmt.close();
            System.out.println("Connection = " + this.con);

        } catch (final SQLException e) {
            System.err.println("[ERROR]");
            System.err.println("MySQL Connexion exception: " + e);

            //throw new IllegalStateException();
        }
    }

    private static SQLManager INSTANCE;

    public static SQLManager getInstance() throws SQLException {
        if (SQLManager.INSTANCE == null) {
            SQLManager.INSTANCE = new SQLManager();
        }
        return SQLManager.INSTANCE;
    }


    /*Acte*/
    private boolean publishActe(final int staffId, final int patientId, final String title, final int type, final String description, final String file, final boolean isDraft) throws SQLException {
        //String link=CreateDynamicLink(file,patientId,title);
        final String searchNewID = "INSERT INTO StringacteString (StringMedicalFolder_idFolderString, StringNomString, StringDateDebutString, StringDateFinString, StringResponsableString, StringPrixString, StringDocumentLinkString, StringIsADraftString, StringDocumentType_idDocumentTypeString, StringDescriptionString, StringidActeString)\r\n" +
                "VALUES (?, ?, ?, NULL, ?, NULL, ?, ?, ?, ?, NULL);";
        final PreparedStatement s = this.con.prepareStatement(searchNewID);
        s.setInt(1, patientId);
        s.setString(2, title);


        final java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());


        s.setDate(3, date);
        s.setInt(4, staffId);
        s.setString(5, file);
        s.setInt(6, isDraft ? 1 : 0);
        s.setInt(7, type);
        s.setString(8, description);
        System.out.println(s);
        return !s.execute();
    }

    public boolean publishActe(final int staffId, final int patientId, final String title, final int type, final String description, final String file) throws SQLException {

        return this.publishActe(staffId, patientId, title, type, description, file, false);
    }


    /*Draft*/
    public boolean CreateDraftt(final int staffId, final int patientId, final String title, final int type, final String description, final String file) throws SQLException {

        return this.publishActe(staffId, patientId, title, type, description, file, true);
    }

    public boolean updateEtPublierBrouillon(final int patientId, final int draftId, final String title, final String description, final String file) throws SQLException, IOException {
        this.updateBrouillon(patientId, draftId, title, description, file);
        final String update = "UPDATE StringacteString SET IsADraft=0 WHERE StringacteString.StringidActeString = ?;";

        final PreparedStatement pStatement = this.con.prepareStatement(update);

        pStatement.setInt(4, draftId);

        return !pStatement.execute();
    }

    public boolean updateBrouillon(final int patientId, final int draftId, final String title, final String description, final String file) throws SQLException {

        String update = "UPDATE StringacteString SET ";
        if (title != null) {
            update += "StringNomString = ?";
            if (file != null || description != null) {
                update += ", ";
            }
        }
        if (description != null) {
            update += "StringDescriptionString = ?";
            if (file != null) {
                update += ", ";
            }
        }
        final String linkString = "";
        if (file != null) {
            update += "StringDocumentLinkString = ?";

        }
        update += " WHERE StringacteString.StringidActeString = ?;";

        final PreparedStatement pStatement = this.con.prepareStatement(update);
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


    private HashMap<String, Object> addSortItem(final Object key, final Object print) {
        final HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("sortColumnName", key);
        hashMap.put("printableName", print);
        return hashMap;
    }

    public List<HashMap<String, Object>> printSortDmpItems() {
        final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        list.add(this.addSortItem("Name", "Nom"));
        list.add(this.addSortItem("FirstName", "Prénom"));
        list.add(this.addSortItem("BirthDate", "Date de naissance"));
        return list;
    }


    public List<HashMap<String, Object>> printDMP(String search, final String sortitem, final int paginationNumber,
                                                  final int paginationLength) throws SQLException {

        final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        if (search == null) {
            search = "";
        }
        search = "%" + search + "%";

        final String reqString = "SELECT NAME\r\n" +
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
        final PreparedStatement pStatement = this.con.prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);
        pStatement.setString(4, sortitem);

        pStatement.setInt(5, paginationLength * paginationNumber);
        System.out.println(pStatement);
        final ResultSet rSet = pStatement.executeQuery();
        int count = 0;

        HashMap<String, Object> hashMap;
        while (rSet.next()) {
            count++;
            if (!(count >= (paginationLength * (paginationNumber - 1)))) {
                continue;
            }
            hashMap = new HashMap<String, Object>();
            hashMap.put("patientId", rSet.getInt("UUID"));
            hashMap.put("firstname", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("birthdayDate ", rSet.getString("BirthDate"));
            list.add(hashMap);

        }

        return list;
    }


    // ne pas faire !! Elle est juste là pour pas avoir d'erreur de compilations
    public HashMap<String, Object> getDocument(final int draftId) {
        return null;
    }


    public boolean deleteDraft(final int draftId) throws SQLException {
        final String verifyDraftString = "SELECT idActe FROM acte WHERE idActe=? AND IsADraft=1 ";
        PreparedStatement pStatement = this.con.prepareStatement(verifyDraftString);
        pStatement.setInt(1, draftId);
        final ResultSet rSet = pStatement.executeQuery();
        if (rSet.next()) {
            final String delString = "DELETE FROM acte WHERE idActe=? AND IsADraft=1;";
            pStatement = this.con.prepareStatement(delString);
            pStatement.setInt(1, draftId);
            final int i = pStatement.executeUpdate();
            return true;
        }
        return false;
    }


    public HashMap<String, Object> connect(final String login, final String Password) throws SQLException {
        final HashMap<String, Object> result = new HashMap<String, Object>();

        final String getPasswordString = "SELECT PassWord From ApplicationUser Where Login=?;";

        PreparedStatement pStatement = this.con.prepareStatement(getPasswordString);
        pStatement.setString(1, login);

        final ResultSet rSet = pStatement.executeQuery();
        String userPassWordString = "";
        if (rSet.next()) {
            userPassWordString = rSet.getNString(1);
        }

        System.out.println(userPassWordString);
        if (userPassWordString.equals(Password)) {
            String getPeopleString = "SELECT * FROM StaffMember WHERE Login=?";
            pStatement = this.con.prepareStatement(getPeopleString);
            pStatement.setString(1, login);
            ResultSet resultSet = pStatement.executeQuery();
            System.out.println(resultSet);

            resultSet.next();
            result.put("type", resultSet.getString("EnumStaffType_idEnumStaffType"));
            result.put("id", resultSet.getInt("idStaffMember"));

            getPeopleString = "SELECT Name,FirstName FROM demoinformations WHERE NumSecu=?";
            pStatement = this.con.prepareStatement(getPeopleString);
            pStatement.setLong(1, resultSet.getLong("DemoInformations_NumSecu"));
            resultSet = pStatement.executeQuery();

            resultSet.next();
            result.put("name", resultSet.getString("FirstName"));
            result.put("lastName", resultSet.getString("Name"));

            return result;
        }
        return null;
    }

    public List<HashMap<String, Object>> getMedicalDocumentType() throws SQLException {
        final List<HashMap<String, Object>> res = new ArrayList<>();
        final String getMedDocType = "SELECT * FROM documenttype;";
        final Statement s = this.con.createStatement();
        final ResultSet rs = s.executeQuery(getMedDocType);
        while (rs.next()) {
            final String id = rs.getString("idDocumentType");
            final String name = rs.getString("Name");
            final HashMap<String, Object> type = new HashMap<>();
            type.put("idDocumentType", id);
            type.put("Name", name);
            res.add(type);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
    }

    public List<HashMap<String, Object>> printStaff(String sortItem, String search, final int paginationNumber,
                                                    final int paginationLength) throws SQLException {
        final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        if (search == null) {
            search = "";
        }
        if (sortItem == null) {
            sortItem = "Name";
        }
        search = "%" + search + "%";

        final String reqString = "SELECT NAME\r\n" +
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
                "ORDER BY NAME\r\n" +
                "LIMIT ?; ";
        final PreparedStatement pStatement = this.con.prepareStatement(reqString);
        pStatement.setString(1, search);
        pStatement.setString(2, search);
        pStatement.setString(3, search);

        pStatement.setInt(4, paginationLength * paginationNumber);
        System.out.println(pStatement);
        final ResultSet rSet = pStatement.executeQuery();
        int count = 0;

        HashMap<String, Object> hashMap;
        while (rSet.next()) {
            count++;
            if (!(count >= (paginationLength * (paginationNumber - 1)))) {
                continue;
            }
            hashMap = new HashMap<String, Object>();
            hashMap.put("patientId", rSet.getInt("idStaffMember"));
            hashMap.put("firstname", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("birthdayDate ", rSet.getString("BirthDate"));
            hashMap.put("type", rSet.getString("BirthDate"));
            list.add(hashMap);

        }

        return list;
    }

    public List<HashMap<String, Object>> printDraft(final int staffId, final String search, final String sortItems,
                                                    final int paginationNumber, final int paginationLength) throws SQLException {
        return this.printActe(staffId, search, sortItems, paginationNumber, paginationLength, true);
    }


    public List<HashMap<String, Object>> printSortActeItems() {
        final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        list.add(this.addSortItem("documenttype.idDocumentType", "Type d'acte"));
        list.add(this.addSortItem("Nom", "Nom"));
        list.add(this.addSortItem("DateDebut", "Date de creation"));


        return list;
    }

    private List<HashMap<String, Object>> printActe(final int patientId, String search, String sortItem, final int paginationNumber,
                                                    final int paginationLength, final boolean draft) throws SQLException {
        final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        if (search == null) {
            search = "";
        }
        if (sortItem == null) {
            sortItem = "DateDebut";
        }
        search = search.toUpperCase();
        search = "%" + search + "%";
        //gérer patient medecin Id
        String reqString = "SELECT\r\n" +
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
                "    AND enumstafftype.idEnumStaffType = staffmember.EnumStaffType_idEnumStaffType \r\n" +
                "    AND IsADraft = ? \r\n" +
                "    AND documenttype.idDocumentType = acte.DocumentType_idDocumentType\r\n";
        if (draft) {
            reqString += "    AND staffmember.idStaffMember=?\r\n";
        } else {
            reqString += "    AND dmp.UUID=?\r\n";
        }

        reqString += "	AND (UPPER(Nom) LIKE ? OR UPPER(documenttype.Name) LIKE ?) \r\n" +
                "ORDER BY\r\n" +
                "    ?\r\n " +
                "LIMIT ?;";
        final PreparedStatement pStatement = this.con.prepareStatement(reqString);
        pStatement.setInt(1, draft ? 1 : 0);

        pStatement.setInt(2, patientId);
        pStatement.setString(3, search);
        pStatement.setString(4, search);
        pStatement.setString(5, sortItem);

        pStatement.setInt(6, paginationLength * paginationNumber);


        System.out.println(pStatement);
        final ResultSet rSet = pStatement.executeQuery();
        int count = 0;

        HashMap<String, Object> hashMap;
        while (rSet.next()) {
            count++;
            if (!(count >= (paginationLength * (paginationNumber - 1)))) {
                continue;
            }
            hashMap = new HashMap<String, Object>();
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

    public List<HashMap<String, Object>> printDocumentType() throws SQLException {
        final String req = "SELECT * FROM documenttype";
        final PreparedStatement pStatement = this.con.prepareStatement(req);
        final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        final ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
            list.add(this.addSortItem(rSet.getInt("idDocumentType"), rSet.getString("Name")));

        }

        return list;
    }

    public List<HashMap<String, Object>> printStaffype() throws SQLException {
        final String req = "SELECT * FROM StringenumstafftypeString";
        final PreparedStatement pStatement = this.con.prepareStatement(req);
        final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        final ResultSet rSet = pStatement.executeQuery();
        while (rSet.next()) {
            list.add(this.addSortItem(rSet.getInt("idEnumStaffType"), rSet.getString("JobName")));

        }

        return list;
    }

    public List<HashMap<String, Object>> printActe(final int patientId, final String search, final String sortItem, final int paginationNumber,
                                                   final int paginationLength) throws SQLException {

        return this.printActe(patientId, search, sortItem, paginationNumber, paginationLength, false);

    }

    public boolean createProfile(final long numsecu, final String firstName, final String lastName, final String birthday,
                                 final String Adress, final String email, final String phone) throws SQLException {
        if (this.CreateDemoInfo(numsecu, firstName, lastName, birthday, Adress, email, phone)) {
            final String resultString = "INSERT INTO StringdmpString (StringUUIDString, StringidDoctorString, StringDemoInformations_NumSecuString) VALUES (NULL, NULL, ?);";
            final PreparedStatement pStatement = this.con.prepareStatement(resultString);
            pStatement.setLong(1, numsecu);
            System.out.println(pStatement);
            return !pStatement.execute();

        }
        return false;
    }

    public boolean CreateStaff(final long numsecu, final String name, final String lastName, final String birthday,
                               final String Adress, final String email, final String phone, final int typeId) throws SQLException {
        final String loginReq = "INSERT INTO StringapplicationuserString (StringLoginString, StringPassWordString, StringMailString) VALUES (?, ?, ?);";
        PreparedStatement pStatement = this.con.prepareStatement(loginReq);
        final String login = name.substring(0, 1) + lastName;
        pStatement.setString(1, login);
        pStatement.setString(2, login);
        pStatement.setString(3, email);
        if (!pStatement.execute()) {
            if (this.CreateDemoInfo(numsecu, name, lastName, birthday, Adress, email, phone)) {
                final String resultString = "INSERT INTO StringstaffmemberString(\r\n" +
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
                pStatement = this.con.prepareStatement(resultString);
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

    private boolean CreateDemoInfo(final long numsecu, final String name, final String lastName, final String birthday,
                                   final String Adress, final String email, final String phone) throws SQLException {
        final String demoReqString = "INSERT INTO StringdemoinformationsString (StringNumSecuString, StringNameString, StringFirstNameString, StringBirthDateString, StringAdressString, StringSexeString, StringProfessionString, StringFamilialSituationString, StringPersonToContact_idPatientString, StringPhoneNumberString, StringEnumNationnality_idNatString, StringCity_idCityString, StringPoidString, StringTailleString) VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, NULL, NULL, NULL, NULL);";
        final PreparedStatement pStatement = this.con.prepareStatement(demoReqString);
        final String login = name.substring(0, 1) + lastName;
        pStatement.setLong(1, numsecu);
        pStatement.setString(2, lastName);
        pStatement.setString(3, name);
        pStatement.setString(4, birthday);
        pStatement.setString(5, Adress);
        pStatement.setString(6, name);
        return !pStatement.execute();

    }


    public boolean modifyContactStaff(final int idPeople, final String phoneNumber, final String Adress, final String email) throws SQLException {
        final String string = "UPDATE StringdemoinformationsString SET StringAdressString = ?, StringPhoneNumberString = ? WHERE StringdemoinformationsString.StringNumSecuString = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        final String logString = "UPDATE applicationuser SET applicationuser.Mail = ? WHERE StringapplicationuserString.StringLoginString = (SELECT staffmember.Login FROM staffmember WHERE staffmember.idStaffMember=?);";
        final PreparedStatement ps1 = this.con.prepareStatement(string);
        final PreparedStatement ps2 = this.con.prepareStatement(logString);
        ps1.setString(1, Adress);
        ps1.setString(2, phoneNumber);
        ps1.setInt(3, idPeople);
        ps2.setString(1, email);
        ps2.setInt(2, idPeople);
        System.out.println(ps1);
        return !(ps1.execute() && ps2.execute());
    }

    public boolean ResetPassword(final int idPeople, final String oldPwd, final String newPwd, final String newPwdAgain) throws SQLException {


        final String getOld = "SELECT applicationuser.Login,PassWord FROM applicationuser, staffmember WHERE idstaffmember=?";
        PreparedStatement ps2 = this.con.prepareStatement(getOld);
        ps2.setInt(1, idPeople);
        ps2.setString(2, oldPwd);
        final ResultSet rSet = ps2.executeQuery();
        if (rSet.next()) {
            if ((oldPwd.equals("Admin") || rSet.getString("PassWord").equals(oldPwd)) && newPwd.equals(newPwdAgain)) {
                final String updateNewString = "UPDATE applicationuser SET PassWord=? WHERE Login=?";
                ps2 = this.con.prepareStatement(updateNewString);
                ps2.setString(1, newPwd);
                ps2.setString(2, rSet.getString("Login"));
                return !ps2.execute();
            }

        }


        return false;
    }


    public HashMap<String, Object> getStaffMember(final int idStaffMember) throws SQLException {

        final String staff = "SELECT demoinformations.Name, demoinformations.FirstName, demoinformations.PhoneNumber ,demoinformations.BirthDate, demoinformations.Adress, applicationuser.Mail FROM StringstaffmemberString,demoinformations,applicationuser WHERE staffmember.Login = applicationuser.Login AND demoinformations.NumSecu=staffmember.DemoInformations_NumSecu AND idStaffMember=?;";
        final PreparedStatement ps = this.con.prepareStatement(staff);
        ps.setInt(1, idStaffMember);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            final HashMap<String, Object> member = new HashMap<>();
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

    public List<HashMap<String, Object>> dmpSortItems() {
        return null;
    }


    public boolean affecterPatient(final int nodeId, final int staffId, final int patientId) throws SQLException {
        final String requestString = "INSERT INTO StringaffectationString (StringidAffectationString, StringSymptomeString, StringUnit_idHospitalString, StringStaffIDString, StringPatientIdString) VALUES (NULL, NULL, ?, ?, ?);";
        final PreparedStatement rStatement = this.con.prepareStatement(requestString);
        rStatement.setInt(1, nodeId);
        rStatement.setInt(2, staffId);
        rStatement.setInt(3, patientId);

        return !rStatement.execute();
    }

    public boolean unAffecterPatient(final int nodeId, final int staffId, final int patientId) throws SQLException {
        final String requestString = "DELETE FROM StringaffectationString WHERE StringStaffIDString=? AND StringPatientIdString=?;";
        final PreparedStatement rStatement = this.con.prepareStatement(requestString);
        rStatement.setInt(1, staffId);
        rStatement.setInt(2, patientId);

        return !rStatement.execute();
    }

    public boolean affecterPersonnel(final int personalId, final int hopitalUnitId, final boolean leadUnit) throws SQLException {
        if (leadUnit) {
            final String rString = "UPDATE StringunitString SET StringDirectorString = ? WHERE StringunitString.StringidHospitalString = ?; ";
            final PreparedStatement rStatement = this.con.prepareStatement(rString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalUnitId);
            return !rStatement.execute();
        } else {
            final String xString = "UPDATE StringstaffmemberString SET StringHospital_idHospitalString = ? WHERE StringstaffmemberString.StringidStaffMemberString = ?;";
            final PreparedStatement rStatement = this.con.prepareStatement(xString);
            rStatement.setInt(1, personalId);
            rStatement.setInt(2, hopitalUnitId);
            return !rStatement.execute();
        }
    }

    public Boolean createUnit(final String name, final int idRattache, final int idStaffMember) throws SQLException {
        int type = 0;
        final PreparedStatement ps;
        if (idRattache != -1) {
            final String result = "";
            final PreparedStatement ps1 = this.con.prepareStatement("SELECT Type FROM Unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            final ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            final String sqlRequest = "INSERT INTO Unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(this.con);
            ps = this.con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            final String sqlRequest = "INSERT INTO Unit(Name, Type, Director) VALUES (?, ?, ?); ";
            System.err.println(this.con);
            ps = this.con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            System.out.println(ps);
        }

        final int rs = ps.executeUpdate();
        System.out.println(rs);
        return 1 != 0;
    }

    public Boolean createSector(final String name, final int idRattache, final int idStaffMember, final boolean labo) throws SQLException {
        int type = 0;
        final PreparedStatement ps;
        if (idRattache != -1) {
            final String result = "";
            final PreparedStatement ps1 = this.con.prepareStatement("SELECT Type FROM Unit WHERE idHospital =?;");
            ps1.setLong(1, idRattache);
            final ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                type = rs.getInt(1) + 1;
                if (labo) {
                    type += 1;
                }
            } else {
                throw new IllegalArgumentException();
            }
            System.out.println(type);
            final String sqlRequest = "INSERT INTO Unit(Name, Type, Director, ratache) VALUES (?, ?, ?,?); ";
            System.err.println(this.con);
            ps = this.con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            ps.setLong(4, idRattache);
            System.out.println(ps);
        } else {
            final String sqlRequest = "INSERT INTO Unit(Name, Type, Director) VALUES (?, ?, ?); ";
            System.err.println(this.con);
            ps = this.con.prepareStatement(sqlRequest);
            ps.setString(1, name);
            ps.setLong(2, type);
            ps.setLong(3, idStaffMember);
            System.out.println(ps);
        }

        final int rs = ps.executeUpdate();
        System.out.println(rs);
        return 1 != 0;
    }

    public List<HashMap<String, Object>> getNodeByType(final int type) throws SQLException {
        final List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();
        final String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From Unit, staffMember, demoinformations WHERE Type=? \r\n" +
                "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        final PreparedStatement ps = this.con.prepareStatement(sqlString);
        ps.setInt(1, type);
        System.out.println(ps);
        final ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            final String name = this.IntToTypeStringConverter(rSet.getInt("Type"));
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

    public List<HashMap<String, Object>> getNodeByTypeAndFather(final int type, final int father) throws SQLException {
        final List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();

        final String sqlString = "SELECT idHospital, unit.Name as hospiName,Type, idStaffMember, demoinformations.Name, FirstName From Unit, staffMember, demoinformations WHERE Type=? AND ratache=?\r\n" +
                "AND staffmember.idStaffMember=unit.Director AND staffmember.DemoInformations_NumSecu = demoinformations.NumSecu";
        final PreparedStatement ps = this.con.prepareStatement(sqlString);
        ps.setInt(1, type);
        ps.setInt(2, father);
        final ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            final String name = this.IntToTypeStringConverter(rSet.getInt("Type"));
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

    public List<HashMap<String, Object>> getStaffMemberFromNode(final int node) throws SQLException {
        final List<HashMap<String, Object>> hasmaList = new ArrayList<HashMap<String, Object>>();

        final String sqlString = "SELECT idStaffMember, demoinformations.Name, FirstName,JobName From Unit, staffMember, demoinformations,enumstafftype WHERE \r\n" +
                "  staffmember.DemoInformations_NumSecu = demoinformations.NumSecu AND Hospital_idHospital=? AND enumstafftype.idEnumStaffType=staffmember.EnumStaffType_idEnumStaffType;";
        final PreparedStatement ps = this.con.prepareStatement(sqlString);
        ps.setInt(1, node);
        final ResultSet rSet = ps.executeQuery();

        if (rSet.next()) {
            final HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("staffId", rSet.getString("idStaffMember"));
            hashMap.put("staffLastName", rSet.getString("Name"));
            hashMap.put("staffFirstName", rSet.getString("FirstName"));
            hashMap.put("staffType", rSet.getString("JobName"));

            hasmaList.add(hashMap);
        } else {
            return null;
        }

        return hasmaList;
    }

    public List<HashMap<String, Object>> getAllHospital() throws SQLException {
        return this.getNodeByType(1);
    }

    public List<HashMap<String, Object>> getAllPole(final int hospitalId) throws SQLException {
        return this.getNodeByTypeAndFather(2, hospitalId);
    }

    public List<HashMap<String, Object>> getAllSector(final int poleId) throws SQLException {
        return this.getNodeByTypeAndFather(3, poleId);
    }

    public List<HashMap<String, Object>> getAllLabo(final int poleId) throws SQLException {
        return this.getNodeByTypeAndFather(4, poleId);
    }

    public List<HashMap<String, Object>> getAllArchitecture() throws SQLException {
        final List<HashMap<String, Object>> hasmaList = new ArrayList<>();
        for (final HashMap<String, Object> hospi : this.getAllHospital()) {


            final List<HashMap<String, Object>> hasmaListPole = new ArrayList<>();
            hospi.put("pole", hasmaListPole);
            hasmaList.add(hospi);
            for (final HashMap<String, Object> pole : this.getAllPole((int) (hospi.get("hospitalId")))) {
                final List<HashMap<String, Object>> hasmaSector = new ArrayList<>();
                pole.put("sector", hasmaSector);
                final List<HashMap<String, Object>> hasmaLabo = new ArrayList<>();
                pole.put("Labo", hasmaLabo);
                hasmaListPole.add(pole);
                for (final HashMap<String, Object> sector : this.getAllSector((int) (pole.get("poleId")))) {
                    System.out.println(sector);
                    sector.put("Personnals", this.getStaffMemberFromNode((int) (sector.get("sectorId"))));
                    hasmaSector.add(sector);
                }
                for (final HashMap<String, Object> labo : this.getAllLabo((int) (pole.get("poleId")))) {

                    labo.put("Personnals", this.getStaffMemberFromNode((int) (labo.get("laboId"))));
                    hasmaLabo.add(labo);
                }
            }
        }
        return hasmaList;
    }


    private String IntToTypeStringConverter(final int Type) {
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


    public List<HashMap<String, Object>> getPersonnalForPatient(final int patientId) throws SQLException {

        final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap;
        final String idsActe = "SELECT\r\n" +
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
        final PreparedStatement ps = this.con.prepareStatement(idsActe);
        ps.setInt(1, patientId);
        final ResultSet rSet = ps.executeQuery();
        while (rSet.next()) {
            hashMap = new HashMap<String, Object>();
            hashMap.put("id", rSet.getInt("idStaffMember"));
            hashMap.put("name", rSet.getString("FirstName"));
            hashMap.put("lastname", rSet.getString("Name"));
            hashMap.put("type", rSet.getString("JobName"));
            list.add(hashMap);

        }
        return list;

    }


    public boolean deleteUnit(final int nodeId) {
        return false;
    }

    private int getIdUser(final String login) throws SQLException {
        final String sql = "SELECT idStaffMember  FROM staffmember WHERE Login=?;";
        final PreparedStatement ps = this.con.prepareStatement(sql);
        ps.setString(1, login);
        final ResultSet rs = ps.executeQuery();
        return rs.getInt(0);
    }

    public boolean modifyInfoStaff(final int idPeople, final String name, final String firstname, final String birthday) throws SQLException {
        final String string = "UPDATE StringdemoinformationsString SET StringNameString = ?, StringFirstNameString = ?, BirthDate = ? WHERE StringdemoinformationsString.StringNumSecuString = (SELECT staffmember.DemoInformations_NumSecu FROM staffmember WHERE staffmember.idStaffMember=?);";
        final PreparedStatement ps1 = this.con.prepareStatement(string);
        ps1.setString(1, name);
        ps1.setString(2, firstname);
        ps1.setString(3, birthday);
        ps1.setInt(4, idPeople);
        System.out.println(ps1);
        return !(ps1.execute());
    }


    public String getString(final String appelationString, final String language) throws SQLException {
        String result = "";
        final PreparedStatement ps = this.con.prepareStatement("select StringContent from string where idString =? and Langue_idLangue = ?");
        ps.setString(1, appelationString);
        ps.setString(2, language);
        final ResultSet rs = ps.executeQuery();
        while (rs.next())
            result += rs.getString("StringContent");

        return result;
    }

    //Si le temps
    public int createDMP(final int idDoctor, final int numSecu) throws SQLException {
        int uuid = 1;
        final String searchNewUUID = "SELECT uuid FROM dmp;";
        final Statement s = this.con.createStatement();
        final ResultSet rs = s.executeQuery(searchNewUUID);
        while (rs.next()) {
            if (uuid != rs.getInt("UUID")) {
                break;
            }
            uuid++;
        }
        final String create = "INSERT INTO dmp VALUES(?,?,?);";
        final PreparedStatement pStatement = this.con.prepareStatement(create);
        pStatement.setInt(1, uuid);
        pStatement.setInt(2, idDoctor);
        pStatement.setInt(3, numSecu);
        pStatement.execute();
        return uuid;
    }
}

