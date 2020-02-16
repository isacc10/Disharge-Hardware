/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocp.dotation.util;


/**
 *
 * @author Jamal
 */
public class Controller {
        
        private static String errorMessage = "";
        
        
        public final static String getErrorMessage(){
            return errorMessage;
        }
        public final static void setErrorMessage(final String message ){
            errorMessage = message ;
        } 
    
        public static boolean isValidUserName ( final String userName ) {
		//start with [ a TO z OR A TO Z ]
		//contains all words and all digits and '-' '_' '.';
		//Min Length : 5
		//Max Length : 16
		final String regExpression = "^[a-zA-Z][\\w._-]{4,15}$";
		//Att: il faut verifier que le nom d'utilisateur est unique
                //using dao
		if( userName == null || !userName.matches( regExpression ) ){
                    
                    errorMessage = "the username has already used \nOr is in an invalid format.\n"+
                            "Please use only letters (a-z),\nnumbers, and ( . - _ )";
                    return false;
                }        
		return true;
	}
	
	public static boolean isValidName ( final String name ) {
		final String regExpression = "^[a-zA-Z ]{2,15}$";
		if( name == null || !name.matches( regExpression ) ){
                    errorMessage = "Please use only letters (a-z).";
		    return false;
                }
		return true;
	}
	
        public static boolean isValidNumberPhone( final String phone ){
            final String regExpression = "^[0-9]{,10}$";
            if(phone.length()!=10 || phone==null){
                return false;
            }else{
                for(int i=0; i<phone.length(); i++){
                    if(phone.charAt( i )>'9' || phone.charAt( i )<'0'){
                        return false;
                    }
                }
            }
            /*
            if( phone == null || !phone.matches( regExpression ) ){
                errorMessage = "Please check the number dialed,\nand try again.";
                System.err.println("FALSE");
                return false;
            }  
            */
            return true;
        }
	public static boolean isValidEmail ( final String email ) {
		final String regExpression = "^[\\w._-]+@[\\w.-]+\\.[a-z]{2,}$";
		if( email == null || !email.matches( regExpression ) ){
                    errorMessage = "The Email Address is in an invalid format.\ntry again!";
                    return false;
                } 
		return true;
	}
        //isValidPassword
        public static boolean isValidPassword ( final String password ) {
            //^                 -> start-of-string
            //(?=.*[0-9])       -> a digit must occur at least once
            //(?=.*[a-z])       -> a lower case letter must occur at least once
            //(?=.*[A-Z])       -> an upper case letter must occur at least once
            //(?=.*[@#$%^&+=])  -> a special character must occur at least once
            //(?=\S+$)          -> no whitespace allowed in the entire string
            //.{8,}             -> anything, at least eight places though
            //$                 -> end-of-string
            final String regExpression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            if( password  == null || !password.matches( regExpression ) ){
                errorMessage = "Your password must be have at least\n" +
                               "* 8 characters long\n" +
                               "* 1 uppercase & 1 lowercase character\n" +
                               "* 1 special character ( @#$%^&+= )\n"+
                               "* 1 number";
                return false;
            }    
            return true;
        }
        
}
