package com.example.reto1;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;


@RestController()
@RequestMapping("api/v1/personas")
public class ManipularCSV {

    private BufferedReader lector; //leer el archivo
    private String linea; // recibe la linea de cada fila
    private String partes[] = null ; // almacena cada linea leida

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.CREATED)


    public List <String[]> leerArchivo(@RequestBody String nombreArchivo){

        List <String[]> lineaArchivo = new ArrayList<>();//lista dentro de una lista

        try{
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = lector.readLine()) != null){
                partes = linea.split(","); //lee y separa por comas
                imprimirLinea();
                lineaArchivo.add((partes));//en lista por parte
                System.out.println();
            }
            lector.close();
            linea = null;
            partes = null;

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
      return lineaArchivo;
    }

    public void imprimirLinea(){
        for(int i =0;i < partes.length; i++){
            System.out.println(partes[i]+ " | ");
        }
    }

    //@GetMapping("/validate-email")
    @PostMapping("/validate-email")
    public Map<String, String> validateEmail(@RequestParam String email) {
        Map<String, String> result = new HashMap<>();
        boolean isValid = isValidEmail(email);

        result.put("email", email);
        result.put("valid", String.valueOf(isValid));

        return result;
    }
     //validacion de correo
    private boolean isValidEmail(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regex);
    }


    /*public boolean ValidarEmail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }*/

    //validacion fechas  2
    //@GetMapping("/validate-dob")
    @PostMapping("/validate-dob")
    public Map<String, String> validateDateOfBirth(@RequestParam String dob) {
        System.out.println(dob);
        LocalDate fecha = LocalDate.parse(dob);

        Map<String, String> result = new HashMap<>();
        boolean isValid = isValidDateOfBirth(fecha);

        result.put("dob", dob.toString());
        result.put("valid", String.valueOf(isValid));

        return result;
    }
    private boolean isValidDateOfBirth(LocalDate dob) {
        LocalDate minDate = LocalDate.of(1980, 1, 1);
        return dob.isAfter(minDate);
    }


   /* private boolean isValidDateOfBirth(LocalDate dob) {
        LocalDate minDate = LocalDate.of(1980, 1, 1);
        return dob.isAfter(minDate);
    }*/

    //validacion titulos  3
    private final HashSet<String> validJobTitles = new HashSet<>(Arrays.asList(
            "Haematologist", "Phytotherapist", "Building surveyor",
            "Insurance account manager", "Educational psychologist"
    ));

    //@GetMapping("/validate-job-title")
    @PostMapping ("/validate-job-title")
    public Map<String, String> validateJobTitle(@RequestParam String jobTitle) {
        Map<String, String> result = new java.util.HashMap<>();
        boolean isValid = isValidJobTitle(jobTitle);

        result.put("jobTitle", jobTitle);
        result.put("valid", String.valueOf(isValid));

        return result;
    }





        @PostMapping("/validate")
        public ResponseEntity<Map<String, String>> validate(
                @RequestParam(required = false) String email,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                @RequestParam(required = false) String jobTitle) {

            Map<String, String> result = new HashMap<>();

            if (email != null) {
                boolean isValidEmail = isValidEmail(email);
                result.put("email", email);
                result.put("validEmail", String.valueOf(isValidEmail));
            }

            if (dob != null) {
                boolean isValidDob = isValidDateOfBirth(dob);
                result.put("dob", dob.toString());
                result.put("validDob", String.valueOf(isValidDob));
            }

            if (jobTitle != null) {
                boolean isValidJobTitle = isValidJobTitle(jobTitle);
                result.put("jobTitle", jobTitle);
                result.put("validJobTitle", String.valueOf(isValidJobTitle));
            }
            System.out.println(result);

            return ResponseEntity.ok(result);
        }
    private boolean isValidEmail1(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(regex, email);
    }
    private boolean isValidDateOfBirth1(LocalDate dob) {
        LocalDate minDate = LocalDate.of(1980, 1, 1);
        return dob.isAfter(minDate);
    }

    private boolean isValidJobTitle1(String jobTitle) {
        String[] validTitles = {
                "Haematologist", "Phytotherapist", "Building surveyor",
                "Insurance account manager", "Educational psychologist"
        };
        for (String validTitle : validTitles) {
            if (validTitle.equalsIgnoreCase(jobTitle)) {
                return true;
            }
        }
        return false;
    }



    private boolean isValidJobTitle(String jobTitle) {
        return validJobTitles.contains(jobTitle);
    }
    public String getLinea() {
        return linea;
    }

    public BufferedReader getLector() {
        return lector;
    }

    public String[] getPartes() {
        return partes;
    }
}
