package com.emcloud.mi;

import java.util.Arrays;
import java.util.List;

public class DbUtil {
    public static void main(String[] args) {
        String[] dbs = { "EmCloudUAA",
        "EmCloudWeb",
        "EmCloudOU",
        "EmCloudMci",
        "EmCloudLoc",
        "EmCloudMi",
        "EmCloudCpi",
        "EmCloudNfs",
            "EmCloudDict",
        "EmCloudArc",
        "EmCloudCore" };

//        genDevDb(dbs);
//        genProdDb(dbs);
//        genCpConfig(dbs);

        genFrontDb(dbs);


    }

public static  void genCpConfig(String[] dbs){
    String cpcmd = "cp ../%s/src/main/resources/config/application-dev.yml ./%s-dev.yml";
    String cpProdcmd = "cp ../%s/src/main/resources/config/application-prod.yml ./%s-prod.yml";
    for (String db:dbs) {
        System.out.println(String.format(cpcmd, db ,db ));
        System.out.println(String.format(cpProdcmd,db, db ));
    }
}

    public static void genDevDb(String[] dbs){
        String dropDbCmd = "drop database IF EXISTS %sDev;";
        String createDbCmd="create database IF NOT EXISTS %sDev default charset utf8mb4 COLLATE utf8mb4_general_ci;";
        String grantCmd = "grant all on %sDev.* to " ;
        for (String db:dbs) {
            System.out.println(String.format(dropDbCmd, db ));
            System.out.println(String.format(createDbCmd, db ));
            System.out.println(String.format(grantCmd, db ) + "'hxxndev'@'%';" );
        }
    }

    public static void genProdDb(String[] dbs){
        String dropDbCmd = "drop database IF EXISTS %s;";
        String createDbCmd="create database IF NOT EXISTS %s default charset utf8mb4 COLLATE utf8mb4_general_ci;";
        String grantCmd = "grant all on %s.* to " ;
        for (String db:dbs) {
            System.out.println(String.format(dropDbCmd, db ));
            System.out.println(String.format(createDbCmd, db ));
            System.out.println(String.format(grantCmd, db ) + "'hxxnprod'@'%';" );
        }
    }


    public static void genFrontDb(String[] dbs){
        String dropDbCmd = "drop database IF EXISTS %sFront;";
        String createDbCmd="create database IF NOT EXISTS %sFront default charset utf8mb4 COLLATE utf8mb4_general_ci;";
        String grantCmd = "grant all on %sFront.* to " ;
        for (String db:dbs) {
            System.out.println(String.format(dropDbCmd, db ));
            System.out.println(String.format(createDbCmd, db ));
            System.out.println(String.format(grantCmd, db ) + "'hxxndev'@'%';" );
        }
    }
}
