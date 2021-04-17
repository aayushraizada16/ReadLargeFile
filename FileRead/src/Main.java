import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main {

    public static void main(String[] args) {

        try{

            //Read data from file

            int bufferSize = 4*1024; //Reading Size of Max 4KB
            
            FileReader fin=new FileReader("/Users/aayushraizada/Documents/test.txt");
            BufferedReader bin=new BufferedReader(fin,bufferSize);
            int read;


            StringBuilder sb1 = new
                    StringBuilder("");
            while((read=bin.read())!=-1){
                sb1.append((char)read);
            }



            //Count number of words

            String str = sb1.toString();

            String[] b = str.split(" "); // stored in array and splitted

            //DatabaseUpdation
            AddorUpdateData(b);


            bin.close();
            fin.close();
        }catch(Exception e){System.out.println(e);}



    }


    //Method For Updation&Addition in database
    public static void AddorUpdateData(String[] str)
    {


        Connection c = null;
        Statement stmt = null;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres","");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();



            //adding new element

            for (int k = 0; k < str.length ; k ++ )
            {

                ResultSet rs = stmt.executeQuery( "select case when word = '"+str[k]+"' then 1 else 0 end as active_status from words where word = '"+str[k]+"';" );

                int flag=0;
                while ( rs.next() ) {

                    flag = rs.getInt("active_status");

                }


                if(flag == 1)
                {

                    ResultSet rs1 =  stmt.executeQuery("SELECT * FROM WORDS WHERE word = '"+str[k]+"'");

                    int times = 0;
                    while ( rs1.next() ) {

                        times = rs1.getInt("OCCURS");
                        times = times+1;

                    }


                    String sqlupdate = "UPDATE WORDS set OCCURS = '"+times+"' where WORD='"+str[k]+"';";
                    stmt.executeUpdate(sqlupdate);
                }



                else
                {
                    String sqladd = "INSERT INTO WORDS (WORD,OCCURS) "
                            + "VALUES ('"+ str[k] + "', 1);";
                    stmt.executeUpdate(sqladd);
                }



            }




            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");


    }





}



