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
    private SQLManager() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Delire","root","");
//here sonoo is database name, root is username and password

        }catch(Exception e){ System.out.println(e);}
    }


    private static SQLManager INSTANCE = null;

    public static SQLManager getInstance() {
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
        PreparedStatement ps = con.prepareStatement("select StringContent from String where idString =? and Langue = ?");
        ps.setString(1, appelationString);
        ps.setString(2, language);
        ResultSet rs=ps.executeQuery();
        while(rs.next())
           result+=rs.toString();

        return result;

    }

    public int createDMP(int idDoctor, int numSecu) {
        return 0;
    }

    public void deleteDMP(int UUID) {
        return;
    }

    public boolean publishActe(int staffId, int patientId, String title, int type, int description, File file) {
        return false;
    }

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

    public HashMap<String, Object> connect(String login, String Password) {
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

    public boolean createUnit(String name, int idRattache, int idStaffMember) {
        return false;
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

    public boolean createProfileAndSendEmail(int typeId, String name, String lastName, int birthDate, int phoneNumber, int phoneLandline, String email) {
        return false;
    }

    private int getIdUser(String login) {
        return 0;
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
