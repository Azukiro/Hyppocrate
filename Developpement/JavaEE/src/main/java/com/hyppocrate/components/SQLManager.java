package com.hyppocrate.components;

import com.hyppocrate.utilities.ISingleton;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


// TODO: 16/01/2020
public class SQLManager implements ISingleton {

    //private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String user = "root";
    private static final String password = "mettre pwd ici";

    //https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2839563#2839563
    Context context;
    DataSource dataSource;
    Connection conn;
    // singleton pattern
    private SQLManager()
    {
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mydb");
            Connection conn = dataSource.getConnection(user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID FROM USERS");
            rs.close();
            stmt.close();
            conn.close();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
    private static SQLManager INSTANCE = null;
    public static SQLManager getInstance()
    {
        if (INSTANCE == null)
        {   INSTANCE = new SQLManager();
        }
        return INSTANCE;
    }


    public HashMap<String, Object> getStaffType(String login) {
        return null;
    }


    public String getString(String idStr,String idLangue) {
        return null;
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
    public List<HashMap<String, Object>> getBrouillon(int patientId, String search, String sortItems, int paginationNumber, int paginationLength) {
        return null;
    }
    public HashMap<String, Object> getDocument(int draftId) {
        return null;
    }
    public boolean updateEtPublierBrouillon(int draftId, String title, String type, String description) {
        return false;
    }
    public boolean updateDraft(int draftId, String title, String type, String description) {
        return false;
    }
    public boolean deleteDraft(int draftId) {
        return false;
    }
    public boolean createDraft(int staffId, int nodeId, int patientId, String title, String type, int description, String file) {
        return false;
    }

    public void publierMedicalDocument(int idActe, int idMedicalDocument, String name, int isADraft, Date date, String link, int type, String champsObligatoire, int extension) {
        return;
    }
    public List<HashMap<String, Object>> getOrdonnace(int idActe,int idMedicalDocument, String Url) {
        return null;
    }
    public List<String> getCompteRendu(int idActe,int idMedicalDocument, String Url) {
        return null;
    }
    public boolean writeOrdonnace( List<HashMap<String, Object>> idActe, int isAdraft) {
        return false;
    }
    public boolean writeCompteRendu( String importante,  String text, String idActe, int isAdraft) {
        return false;
    }
    public boolean findMDP(String login) {
        return false;
    }
    public HashMap<String, Object> connect(String login,String Password) {
        return null;
    }

    public List<HashMap<String, Object>> searchPatients(int staffId, String sortColumnName, String search, int paginationNumber, int paginationLenght){
        return null;
    }
    public List<HashMap<String, Object>> searchDMPs(int staffId, int patientId, String actPrintableName, String search, int paginationNumber, int paginationLenght){
        return null;
    }
    public List<HashMap<String, Object>> getMedicalDocumentType() {
        return null;
    }
    public List<HashMap<String, Object>> printStaff(int patientId, String search , String sortItem, int paginationNumber , int paginationLength ) {
        return null;
    }
    public List<HashMap<String, Object>> printActe(int patientId, String search , String sortItem, int paginationNumber , int paginationLength ) {
        return null;
    }
    public HashMap<String, Object> getActe(int documentId) {
        return null;
    }
    public void createProfile(int idStaffMember, String skills, int idStaffType, int idUser, int numSecu, String IBAN,  String BIC, int idHospital, int nbBureau) {
        return;
    }
    public boolean modifyDemoStaff(int idPeople, int profileType, String name, String lastName, Date birthday, String Adress, String city, String family ) {
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
    public HashMap<String, Object> getStaffMember(int idStaffMember) {
        return null;
    }

    public HashMap<String, Object> getStaffMember(String email) {
        return null;
    }
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
    public List<Object> getAllSector(int nodeId) {
        return null;
    }
    public List<Object> getAllUnit(int nodeId) {
        return null;
    }
    public boolean createUnit(String name, int fatherId,int idStaffMember) {
        return false;
    }
    public boolean deleteUnit(int nodeId) {
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
    private boolean modifyDemographicInfo (int idPeople, String name, String lastName, Date birthday, String Adress, String city, String family, boolean isPatient) {
        return false;
    }
    private boolean modifyContactPatient(int idPeople, String phoneNumber, String phoneLandLine, String email,boolean isPatient) {
        return false;
    }

}
