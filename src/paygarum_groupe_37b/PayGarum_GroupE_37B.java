package paygarum_groupe_37b;

import Database.Database;
import Database.MySqlConnection;
import View.Registration;


public class PayGarum_GroupE_37B {

    public static void main(String[] args) {
        Database db = new MySqlConnection();
        if(db.openConnection()!=null){
            System.out.println("Con Sucess");
        }else{
            System.out.println("Con Fail");
        }
        
        
        new Registration().setVisible(true);

}
}