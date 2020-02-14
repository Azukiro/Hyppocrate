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

    public List<HashMap<String, Object>> printDMP(String staffId, String patientId, String actPrintableName,  String search, int paginationNumber, int paginationLength) throws SQLException{
        //Les parametres apres patientId n'ont pas encore ete implante dans la methode
        List<HashMap<String, Object>> res = new ArrayList<>();
        String getMedDocType = "SELECT * FROM dmp WHERE idDoctor=? AND DemoInformations_NumSecu=?;";
        PreparedStatement ps = con.prepareStatement(getMedDocType);
        ps.setString(1, staffId);
        ps.setString(2, patientId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("UUID");
            String staff = rs.getString("idDoctor");
            String patient = rs.getString("DemoInformations_NumSecu");
            HashMap<String, Object> type = new HashMap<>();
            type.put("UUID",id);
            type.put("idDoctor",staff);
            type.put("DemoInformations_NumSecu",patient);
            res.add(type);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
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

    public List<HashMap<String, Object>> printStaff(int patientId, String search, String sortItem, int paginationNumber, int paginationLength) {
        return null;
    }

    public List<HashMap<String, Object>> printActe(String patientId, String search, String sortItem, int paginationNumber, int paginationLength) throws SQLException{
        List<HashMap<String, Object>> res = new ArrayList<>();
        String getMedDocType = "SELECT * FROM acte JOIN dmp WHERE dmp.UUID=acte.MedicalFolder_idFolder AND dmp.DemoInformations_NumSecu=?;";
        PreparedStatement ps = con.prepareStatement(getMedDocType);
        ps.setString(1, patientId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("idActe");
            String name = rs.getString("Nom");
            String dateDebut = rs.getString("DateDebut");
            String dateFin = rs.getString("DateFin");
            String prix = rs.getString("Prix");
            HashMap<String, Object> acte = new HashMap<>();
            acte.put("idActe",id);
            acte.put("Nom",name);
            acte.put("DateDebut",dateDebut);
            acte.put("DateFin",dateFin);
            acte.put("Prix",prix);
            res.add(acte);
        }
        if (res.size() == 0) {
            return null;
        }
        return res;
    }

    public HashMap<String, Object> getActe(int documentId) throws SQLException {
        String getActe = "SELECT * FROM acte WHERE idActe=?;";
        PreparedStatement ps = con.prepareStatement(getActe);
        ps.setString(1, documentId+"");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            HashMap<String, Object> acte = new HashMap<>();
            acte.put("idActe", rs.getString("idActe"));
            acte.put("MedicalFolder_idFolder", rs.getString("MedicalFolder_idFolder"));
            acte.put("Nom", rs.getString("Nom"));
            acte.put("DateDebut", rs.getString("DateDebut"));
            acte.put("DateFin", rs.getString("DateFin"));
            acte.put("Responsable", rs.getString("Responsable"));
            acte.put("Prix", rs.getString("Prix"));
            return acte;
        }
        return null; //Envoyer une exception InvalidIdActe
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
    public List<HashMap<String, Object>> getNodeChild(int nodeId) {
        return null;
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

    /*public boolean createProfileAndSendEmail(int typeId, String name, String lastName, int birthDate, int phoneNumber, int phoneLandline, String email) throws SQLException {
        String login=name+lastName;
        String profile="INSERT INTO applicationuser VALUES ('?', '?', '?');";
        PreparedStatement ps=con.prepareStatement(profile);
        ps.setString(1,login);
        ps.setString(2,login);
        return false;
    }*/

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

}

