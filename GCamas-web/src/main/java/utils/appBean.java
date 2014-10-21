/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@ApplicationScoped
public class appBean {

    /**
     * Creates a new instance of appBean
     */
    public static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

    public appBean() {
    }

    public String getBaseUrl() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String serverContextPath = servletContext.getContextPath();
        return serverContextPath;
    }

    /**
     * *
     * obtiene la ruta completa del httprequest mas el puerto del servidor
     *
     * @return
     */
    public String getBaseUrl2() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String requestServer = request.getServerName();
        String requestScheme = request.getScheme();
        int serverPort = request.getServerPort();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String serverContextPath = servletContext.getContextPath();
        String logoutUrl = requestScheme + "://" + requestServer + ":" + Integer.toString(serverPort) + serverContextPath;
        return logoutUrl;
    }

     /**
     * *
     * Metodo que valida el Rut de una persona chilena
     *
     * @param vrut Rut de la persona
     * @param vverificador Digito VErificador del Rut
     * @return Retorna un True si el rut es Correcto o False si el Rut es
     * Incorrecto
     */
    public static boolean validarRut(String vrut, String vverificador) {
        boolean flag = false;
        String rut = vrut.trim();
        String posibleVerificador = vverificador.trim();
        int cantidad = rut.length();
        int factor = 2;
        int suma = 0;
        String verificador = "";
        for (int i = cantidad; i > 0; i--) {
            if (factor > 7) {
                factor = 2;
            }
            suma += (Integer.parseInt(rut.substring((i - 1), i))) * factor;
            factor++;
        }
        verificador = String.valueOf(11 - suma % 11);
        if (verificador.equals(posibleVerificador)) {
            flag = true;
        } else {
            if ((verificador.equals("10")) && (posibleVerificador.toLowerCase().equals("k"))) {
                flag = true;
            } else {
                if ((verificador.equals("11") && posibleVerificador.equals("0"))) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * *
     * Metodo que Formatea el RUT
     *
     * @param rut recibe el RUT ingresado
     * @return retorna el rut formateado
     */
    public static String FormateaRut(String rut) {
        String tmpstr = "";
        int largo = rut.length();
        int i, j;
        for (i = 0; i < rut.length(); i++) {
            if (rut.charAt(i) != ' ' && rut.charAt(i) != '.' && rut.charAt(i) != '-') {
                tmpstr = tmpstr + rut.charAt(i);
            }
        }
        rut = tmpstr;
        tmpstr = "";
        for (i = 0; rut.charAt(i) == '0'; i++);
        for (; i < rut.length(); i++) {
            tmpstr = tmpstr + rut.charAt(i);
        }
        rut = tmpstr;
        String invertido = "";
        for (i = (largo - 1), j = 0; i >= 0; i--, j++) {
            invertido = invertido + rut.charAt(i);
        }
        String drut = "";
        drut = drut + invertido.charAt(0);
        drut = drut + '-';
        int cnt = 0;
        for (i = 1, j = 2; i < largo; i++, j++) {
            if (cnt == 3) {
                drut = drut + '.';
                j++;
                drut = drut + invertido.charAt(i);
                cnt = 1;
            } else {
                drut = drut + invertido.charAt(i);
                cnt++;
            }
        }
        invertido = "";
        for (i = (drut.length() - 1), j = 0; i >= 0; i--, j++) {
            invertido = invertido + drut.charAt(i);
        }
        if (invertido == "-") {
            invertido = "";
        }
        return invertido;
    }

    public String getUserName() {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    /**
     * *
     * Metodo que me permite calcular la edad de una persona
     *
     * @param today fecha actual en la que se esta registrando
     * @param fechaNacimiento fecha de nacimiento ingresado
     * @return Retorna un String con el valor de la fecha de Nacimiento, retorna
     * -1
     */
    public static String CalculaEdad(Date today, Date fechaNacimiento) {
        String edad;
        int temp = today.getYear() - fechaNacimiento.getYear();
        if (today.getMonth() < fechaNacimiento.getMonth()) {
            temp--;
        } else if (today.getMonth() == fechaNacimiento.getMonth() && today.getDate() < fechaNacimiento.getDate()) {
            temp--;
        }
        int months = 0;
        if (temp == 0) {
            months = today.getMonth() - fechaNacimiento.getMonth();
            if (months < 0) {
                months = 12 - (fechaNacimiento.getMonth() - today.getMonth());
            }
            int days = today.getDate() - fechaNacimiento.getDate();
            edad = months + "m " + days + "d ";
        } else {
            edad = "" + temp;
        }
        return edad;
    }
    
 }
