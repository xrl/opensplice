/*
 *                         OpenSplice DDS
 *
 *   This software and documentation are Copyright 2006 to 2011 PrismTech
 *   Limited and its licensees. All rights reserved. See file:
 *
 *                     $OSPL_HOME/LICENSE 
 *
 *   for full copyright notice and license terms. 
 *
 */
package org.opensplice.common.util;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.LogManager;

import javax.swing.JOptionPane;

/** 
 * Base class for tooling initializers. Its responsibilities are to initialize
 * logging facilities according to commandline parameters and to validate
 * whether the correct Java Vitual Machine version is used.
 * 
 *  @date Sep 1, 2004
 */
public class Initializer {
    private final String vendorString = "Sun Microsystems Inc.";
    
    /**
     * Initializes the application, using the configuration that is supplied
     * as argument. If the supplied file does not exist or no argument was
     * supplied, the default is used. The default is: 
     * <USER_HOME>/.splice_tooling.properties.  
     * 
     * @param args The list of arguments supplied by the user. Only the first 
     *             argument is used, the rest will be ignored. The first
     *             argument must supply the location of a java properties file.
     */
    public void initializeConfig(String[] args, ConfigValidator validator){
        boolean result = false;
        
        Config.getInstance().setValidator(validator);
        
        if(args.length > 0){
            System.out.println("Reading configuration from " + args[0] + ".");
            result = Config.getInstance().load(args[0]);
            
            if(!result){
                System.out.println("Applying default configuration.");
                result = Config.getInstance().loadDefault();
            } 
        }
        else{
            result = Config.getInstance().loadDefault();
        }
        
        if(!result){
            System.out.println("Default configuration could not be read.");
        } else {
            String loggingFileName = Config.getInstance().getProperty("logging");
            
            if(loggingFileName != null){
                try {
                    FileInputStream is = new FileInputStream(loggingFileName);
                    LogManager.getLogManager().readConfiguration(is);
                }
                catch (FileNotFoundException e) {
                    System.out.println("Specified logging config file not found. Logging is disabled.");
                    LogManager.getLogManager().reset();    
                } 
                catch (SecurityException e) {
                    System.out.println("Specified logging config file not valid. Logging is disabled.");
                    LogManager.getLogManager().reset();
                } 
                catch (IOException e) {
                    System.out.println("Specified logging config file not valid. Logging is disabled.");
                    LogManager.getLogManager().reset();
                }
            } else {
                LogManager.getLogManager().reset();
            }
        }
    }
    
    /**
     * Validates whether a compatible Java virtual machine is used.
     * 
     * The version of Java must be &gt;= 1.4 and should be &gt;= 1.5.0. If the 
     * used version is &st; 1.4, the application exits with an error message. If
     * 1.4 &st;= version &st; 1.5, false is returned, but the application
     * proceeds.
     * 
     * @return true, if java version &gt;= 1.5 and false otherwise. 
     */
    public boolean validateJVMVersion(){
        int token;
        boolean result = true;
                
        String version = System.getProperty("java.version");
        String vendor  = System.getProperty("java.vm.vendor"); 
        StringTokenizer tokenizer = new StringTokenizer(version, ".");
        
        if(!this.vendorString.equals(vendor)){
            this.printVersionErrorAndExit(vendor, version);
            result = false;
        } else if(tokenizer.hasMoreTokens()){
            token = Integer.parseInt(tokenizer.nextToken());
            
            if(token < 1){
                this.printVersionErrorAndExit(vendor, version);
            }
            
            if(tokenizer.hasMoreTokens()){
                token = Integer.parseInt(tokenizer.nextToken());
                
                if(token < 4){
                    this.printVersionErrorAndExit(vendor, version);
                } else if(token == 4){
                    result = false;
                }
            }
            else{
                this.printVersionErrorAndExit(vendor, version);
                result = false;
            }
        }
        else{
            this.printVersionErrorAndExit(vendor, version);
            result = false;
        }
        return result;
    }
    
    /**
     * Prints JVM version demands as well as the used version and exits the 
     * application.
     */
    private void printVersionErrorAndExit(String vendor, String version){
        if((vendor != null) && (!this.vendorString.equals(vendor))){
            System.err.println("Your Java vendor is '" + vendor + "', but '" + this.vendorString + "' is required.\nBailing out...");
        } else {
            System.err.println("Your Java version is '" + version + "', but version >= '1.4' is required.\nBailing out...");
        }
        System.exit(0);
    }
    
    /**
     * Displays a Java version warning.
     * 
     * @param parent The GUI parent which must be used as parent for displaying 
     *               the message. If the supplied component == null, the version
     *               warning is displayed on standard out (System.out).
     */
    public void showVersionWarning(Component parent){
        if(parent != null){
            JOptionPane.showMessageDialog(parent, "You are using Java version " + System.getProperty("java.version") + ",\nbut version >= 1.5.0 is recommended.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else{
            System.err.println("You are using Java version " + System.getProperty("java.version") + ",\nbut version >= 1.5.0 is recommended.");
        }
    }
}
