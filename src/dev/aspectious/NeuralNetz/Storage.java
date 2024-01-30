package dev.aspectious.NeuralNetz;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Storage {
	public static boolean save(Nlayer layers[], String path) {
		System.out.println("[INFO] Saving Data to file \"" + path + "\"...");
		String DAT = "{";
		
		for (int i=0; i<layers.length-1;i++) {
			String[] data = new String[layers[i].nextHop.length];
			for (int j=0; j<data.length; j++) {
				data[j] = "" + layers[i].nextHop[j].getWeight();
			}
			
			String export = "[";
			for (int k=0; k<data.length; k++) {
				export += data[k] + ",";
			}
			export += ("" + layers[i].bias);
			export += "]";
			
			DAT += export;
		}
		
		DAT += "}";
		
		try {
			File file = new File(path);
			FileWriter writer = new FileWriter(file);
			
			writer.write(DAT);
			writer.close();
			System.out.println("[INFO] Saved data to file \"" + path + "\".");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	public static Nlayer[] loadFile(String path) {
		System.out.println("[INFO] Loading Data from file \"" + path + "\"...");
		String dat = "";
		try {
		      File myObj = new File(path);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        dat += data;
		      }
		      myReader.close();
		      System.out.println("[INFO] Data loaded from file \"" + path + "\".");
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		String[] newdat = dat.split("]");
		String[] newnewdat = new String[dat.split("]").length-1];
		Nlayer[] layers = new Nlayer[newnewdat.length];
		for (int i=0; i<newnewdat.length; i++) {
			newnewdat[i] = newdat[i].replace("{", "").replace("[", "").replace("]","");
			String[] data = newnewdat[i].split(",");
			Nlayer layer = new Nlayer(Integer.parseInt(data[0]));
			layer.nextHop = new Nlink[data.length-1];
			
			for (int j=0; j<(data.length-1); j++) {
				layer.nextHop[j] = new Nlink();
				layer.nextHop[j].setWeight(Float.parseFloat(data[j+1]));
			}
			layer.bias = Float.parseFloat(data[data.length-1]);
			layers[i]= layer;
		}
		
		return layers;
		
	}
}
