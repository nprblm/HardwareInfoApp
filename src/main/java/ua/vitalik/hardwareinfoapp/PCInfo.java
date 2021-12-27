package ua.vitalik.hardwareinfoapp;

import oshi.SystemInfo;
import oshi.hardware.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PCInfo {

    private static final String ERROR = "ERROR, FILE NOT FOUND";
    private static SystemInfo systemInfo = new SystemInfo();
    private static HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private static final DB dataBase = new DB();

    //Get Windows version(Vista/XP/7/8/10)
    public static String getWindowsVersion ()
    {
        return System.getProperty("os.name");
    }

    //Get architecture version (32/64)
    public static String getArchVersion ()
    {
        return System.getProperty("os.arch");
    }

    //Get number of CPU cores (1/2/4/8/16)
    public static int getNumberOfCores ()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    //Get Hard Drive information (Name & Memory size)
    public static void getHardDriveInfo ()
    {
        File[] roots = File.listRoots();

        for (File root : roots) {
            System.out.println("Hard Drive name: " + root.getAbsolutePath());
            System.out.println("Total space: " + (root.getTotalSpace()/(int)Math.pow(1024,3))+" GB\n");
        }
    }

    //Get CPU name
    public static String getCPUInfo () throws SQLException, IOException {
        CentralProcessor processor = hardware.getProcessor();
        String CPUName = processor.toString();
        CPUName = deleteBrackets(CPUName);
        recordDataBase();
        CPUName= nameContains(CPUName, dataBase.getCPUList());

        return CPUName;
    }

    //Get GPU(-s) name(-s)
    public static ArrayList<String> getGPUInfo () throws SQLException, IOException {
        List<GraphicsCard> card = (List<GraphicsCard>) hardware.getGraphicsCards();
        ArrayList <String> card_list = new ArrayList<String>();
        recordDataBase();
        for(int i=0;i<card.size();i++)
        {
            String GPUName = card.get(i).toString();
            GPUName = deleteBrackets(GPUName);
            GPUName = nameContains(GPUName, dataBase.getGPUList());
            if(!GPUName.equals(ERROR))
            {
                card_list.add(GPUName);
            }
        }
        return card_list;
    }

    //Get RAM information
    public static int getRAMInfo ()
    {
        int memorySize = 0;
        List<PhysicalMemory> mem = hardware.getMemory().getPhysicalMemory();
        for (PhysicalMemory phMem : mem)
        {
            String str =phMem.toString().toLowerCase();
            memorySize+= Integer.parseInt(str.substring(str.indexOf("capacity:")+9,str.indexOf("gib")).trim());
        }
        return memorySize;
    }

    //delete all "(...)"
    private static String deleteBrackets (String str)
    {
        while(str.indexOf("(")!=-1)
        {
            str = str.substring(0, str.indexOf("(")) + str.substring(str.indexOf(")") + 1);
        }
        return str;
    }

    //search info about CPU/GPU in DataBase
    private static String nameContains(String str, ArrayList<String> list)
    {
        int number = 0;
        String res="";
        for(String line : list)
        {
            if((str.toLowerCase(Locale.ROOT).contains(line.toLowerCase(Locale.ROOT)))&&(line.length() > number))
            {
                number = line.length();
                res = line;
            }
        }
        return (res==""?ERROR:res);
    }

    private static void recordDataBase() throws SQLException, IOException {
        dataBase.record();
    }
}
