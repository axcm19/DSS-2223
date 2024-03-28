package data;

public class DAOconfig {
    static final String USERNAME = "root";                       // Actualizar conforme o vosso utilizador de sql (provavelmente é root)
    static final String PASSWORD = "pass";                       // Actualizar conforme a vossa password de sql
    private static final String DATABASE = "projeto_dss";          // usei este nome para a base de dados
    //private static final String DRIVER = "jdbc:mariadb";        // Usar para MariaDB

    private static final String DRIVER = "jdbc:mysql";        // Usar para MySQL

    //static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE+"?serverTimezone=Australia/Sydney";  // já tentei usar Portugal/Lisbon nao funciona // deixei ficar

}
