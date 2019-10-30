package main;

import elgamal.ElGamal;
import file.FilesOperation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);

        System.out.println("Opcion:");
        String opcionValue = myObj.nextLine();

        if(opcionValue.equals("E")){ encryptFile(myObj); }
        else{ dencryptFile(myObj); }

    }

    public static void encryptFile(Scanner myObj){

        System.out.println("Ruta del archivo:");
        String pathValue = myObj.nextLine();

        System.out.println("Nombre archivo:");
        String nameFileValue = myObj.nextLine();

        System.out.println("===== ElGamal Encrypt =====");

        FilesOperation filesOperation = new FilesOperation();
        String originalString = filesOperation.readFile(pathValue.concat(nameFileValue));
        BigInteger dataEncrypt = new BigInteger(originalString.getBytes());


        // ElGamal test
        List<List<BigInteger>> pksk = ElGamal.KeyGen(1000);
        List<BigInteger> encrypt = ElGamal.Encrypt(pksk.get(0).get(0), pksk.get(0).get(1), pksk.get(0).get(2), dataEncrypt);

        //
        filesOperation.writeFile(pksk.get(1).get(0).toString().concat("-").concat(pksk.get(1).get(1).toString()), pathValue.concat("keyfile.txt"));
        filesOperation.writeFile(encrypt.get(0).toString().concat("-").concat(encrypt.get(1).toString()), pathValue.concat("Encriptado").concat(nameFileValue));

        System.out.println("Finalizo");
    }

    public static void dencryptFile(Scanner myObj){

        System.out.println("Ruta del archivo:");
        String pathValue = myObj.nextLine();

        System.out.println("Nombre archivo:");
        String nameFileValue = myObj.nextLine();

        System.out.println("Nombre archivo Llave:");
        String keyFileValue = myObj.nextLine();

        System.out.println("Nombre archivo salida:");
        String exitFileValue = myObj.nextLine();

        System.out.println("===== ElGamal Dencrypt =====");

        FilesOperation filesOperation = new FilesOperation();

        String keyText = filesOperation.readFile(pathValue.concat(keyFileValue));
        String contentText = filesOperation.readFile(pathValue.concat(nameFileValue));
        //ArrayList<String> pksk = filesOperation.readFileArray(pathValue.concat(keyFileValue));
        List<String> pksk = Arrays.asList(keyText.split("-"));
        List<String> content = Arrays.asList(contentText.split("-"));

        String contentOut = new String(ElGamal.Decrypt(new BigInteger(pksk.get(0).trim()), new BigInteger(pksk.get(1).trim()), new BigInteger(content.get(0).trim()), new BigInteger(content.get(1).trim())).toByteArray());


        filesOperation.writeFile(contentOut, pathValue.concat(exitFileValue));

        System.out.println("Finalizo");
    }


}